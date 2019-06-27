package BOT.Commands.Music;

import BOT.Constants;
import BOT.Music.PlayerManager;
import BOT.objects.ICommand;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class PlayCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        TextChannel channel = event.getChannel();

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

        manager.loadAndPlay(event.getChannel(), input);

        manager.getGuildMusicManager(event.getGuild()).player.setVolume(10);
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
        return "노래를 재생합니다.\n" +
                "사용법 : '" + Constants.PREFIX + getInvoke() + "'[URL]";
    }

    @Override
    public String getInvoke() {
        return "play";
    }
}
