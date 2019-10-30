package BOT.Commands.Music;

import BOT.Music.GuildMusicManager;
import BOT.Music.PlayerManager;
import BOT.Objects.ICommand;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.*;
import java.util.concurrent.BlockingQueue;

public class QueueMixCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        Random random = new Random();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
        BlockingQueue<AudioTrack> queue = musicManager.scheduler.getQueue();
        AudioPlayer player = musicManager.player;
        List<AudioTrack> queueList = new ArrayList<>(queue);
        Collections.shuffle(queueList, random);
        queue.clear();
        queue.addAll(queueList);
        event.getChannel().sendMessage("재생 목록이 셔플되었습니다.").queue();
    }

    @Override
    public String getHelp() {
        return "재생목록을 셔플 합니다.";
    }

    @Override
    public String getInvoke() {
        return "셔플";
    }

    @Override
    public String getSmallHelp() {
        return "music";
    }
}
