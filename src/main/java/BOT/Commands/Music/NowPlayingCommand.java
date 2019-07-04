package BOT.Commands.Music;

import BOT.Constants;
import BOT.Music.GuildMusicManager;
import BOT.Music.PlayerManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import BOT.objects.ICommand;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class NowPlayingCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
        AudioPlayer player = musicManager.player;

        if (player.getPlayingTrack() == null) {
            channel.sendMessage("아무 노래도 재생하고 있지 않습니다..").queue();

            return;
        }

        AudioTrackInfo info = player.getPlayingTrack().getInfo();

        channel.sendMessage(EmbedUtils.embedMessage(String.format(
                "**재생중: ** [%s](%s)\n%s %s/%s",
                info.title,
                info.uri,
                player.isPaused() ? "\u23F8" : "▶",
                formatTime(player.getPlayingTrack().getPosition()),
                formatTime(player.getPlayingTrack().getDuration())
        )).build()).queue();
    }

    @Override
    public String getHelp() {
        return "지금 뭐 불러?\n" +
                "사용법: `" + Constants.PREFIX + getInvoke() + "`";
    }

    @Override
    public String getInvoke() {
        return "재생중";
    }

    @Override
    public String getSmallHelp() {
        return "노래 뭐 나와?";
    }

    private String formatTime(long timeInMillis) {
        final long hours = timeInMillis / TimeUnit.HOURS.toMillis(1) % 24;
        final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1) % 60;
        final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
