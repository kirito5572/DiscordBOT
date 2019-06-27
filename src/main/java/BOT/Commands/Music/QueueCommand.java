package BOT.Commands.Music;

import BOT.Constants;
import BOT.Music.GuildMusicManager;
import BOT.Music.PlayerManager;
import BOT.objects.CommandManager;
import BOT.objects.ICommand;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class QueueCommand implements ICommand {
    private final CommandManager manager;

    public QueueCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
        BlockingQueue<AudioTrack> queue = musicManager.scheduler.getQueue();

        String joined = String.join("", args);

        if(joined.equals("")) {
            joined = "1";
        }

        if(queue.isEmpty()) {
            channel.sendMessage("재생목록이 비었습니다.").queue();

            return;
        }

        int maxTrackCount = Math.min(queue.size(), 20 * Integer.parseInt(joined));
        int minTrackCount = Math.min(queue.size(), 20 * (Integer.parseInt(joined) - 1));
        List<AudioTrack> tracks = new ArrayList<>(queue);
        if(minTrackCount >= queue.size()) {
            channel.sendMessage("`queue " + joined + "`는 비어있습니다.\n" +
                    "`queue " + (int)Math.ceil(queue.size() / 20.0) + "`까지 재생목록이 존재합니다.").queue();

            return;
        }
        EmbedBuilder builder = EmbedUtils.defaultEmbed()
                .setTitle("현재 재생목록 (총합: " + queue.size() + ") 페이지: " + joined);
        for(int i = minTrackCount; i < maxTrackCount; i++) {
            AudioTrack track = tracks.get(i);
            AudioTrackInfo info = track.getInfo();

            builder.appendDescription(String.format(
                    (i + 1) + ". %s - %s\n",
                    info.title,
                    info.author
            ));
        }
        if(queue.size() > maxTrackCount) {
            builder.appendDescription("다음 재생목록 확인: `"+ Constants.PREFIX + getInvoke() + " " + (Integer.parseInt(joined) + 1) + "`");
        }

        channel.sendMessage(builder.build()).queue();
    }

    @Override
    public String getHelp() {
        return "재생목록을 보여줍니다\n" +
                "사용법 `" + Constants.PREFIX + getInvoke() + "`";
    }

    @Override
    public String getInvoke() {
        return "queue";
    }
}
