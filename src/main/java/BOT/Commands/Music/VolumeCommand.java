package BOT.Commands.Music;

import BOT.Constants;
import BOT.Music.PlayerManager;
import BOT.objects.ICommand;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class VolumeCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        String joined = String.join("", args);

        if(Integer.parseInt(joined) < 10) {
            channel.sendMessage("최소 볼륨은 10입니다. 10보다 큰 수를 입력해주세요.").queue();

            return;
        } else if(Integer.parseInt(joined) > 100) {
            channel.sendMessage("최대 볼륨은 100입니다. 100보다 작은 수를 입력해주세요.").queue();

            return;
        }

        PlayerManager manager = PlayerManager.getInstance();
        manager.getGuildMusicManager(event.getGuild()).player.setVolume(Integer.parseInt(joined));

        channel.sendMessage("볼륨이 " + joined + "으로 변경되었습니다.").queue();
    }

    @Override
    public String getHelp() {
        return "노래 소리 조절하라고?" +
                "사용법: `" + Constants.PREFIX + getInvoke() + "`(숫자)";
    }

    @Override
    public String getInvoke() {
        return "볼륨";
    }

    @Override
    public String getSmallHelp() {
        return "볼륨 줄이기";
    }
}
