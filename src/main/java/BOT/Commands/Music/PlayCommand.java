package BOT.Commands.Music;

import BOT.App;
import BOT.Music.GuildMusicManager;
import BOT.Music.PlayerManager;
import BOT.Objects.ICommand;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.GuildVoiceState;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class PlayCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        TextChannel channel = event.getChannel();
        AudioManager audioManager = event.getGuild().getAudioManager();
        GuildVoiceState memberVoiceState = event.getMember().getVoiceState();
        VoiceChannel voiceChannel = memberVoiceState.getChannel();
        if(!audioManager.isConnected()) {

            Member selfMember = event.getGuild().getSelfMember();

            if(!selfMember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)) {
                channel.sendMessageFormat("%s 보이스 채널에 들어올 권한이 없습니다.",voiceChannel).queue();
                return;
            }

        }
        if(!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("먼저 보이스 채널에 들어오세요").queue();
            return;
        }

        if(args.isEmpty()) {
            channel.sendMessage("URL을 입력헤주세요").queue();

            return;
        }

        String input = String.join(" ", args);

        if(!isUrl(input) && !input.startsWith("ytsearch:")) {
            channel.sendMessage("정확한 유튜브, soundcloud 또는 bandcamp의 링크를 보내주세요.").queue();

            return;
        }

        PlayerManager manager = PlayerManager.getInstance();
        if(!audioManager.isConnected()) {
            audioManager.openAudioConnection(voiceChannel);
        }
        manager.loadAndPlay(event.getChannel(), input);

        GuildMusicManager musicManager = manager.getGuildMusicManager(event.getGuild());
        BlockingQueue<AudioTrack> queue = musicManager.scheduler.getQueue();

        if(queue.isEmpty()) {
            manager.getGuildMusicManager(event.getGuild()).player.setVolume(50);
        }
    }

    private boolean isUrl(String input) {
        try {
            new URL(input);

            return true;
        } catch (MalformedURLException ignored) {
            return false;
        }
    }

    @Override
    public String getHelp() {
        return "URL의 노래를 재생합니다\n" +
                "사용법 : '" + App.getPREFIX() + getInvoke() + "'[URL]";
    }

    @Override
    public String getInvoke() {
        return "재생";
    }

    @Override
    public String getSmallHelp() {
        return "music";
    }
}
