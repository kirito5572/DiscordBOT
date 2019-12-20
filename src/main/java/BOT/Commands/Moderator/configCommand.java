package BOT.Commands.Moderator;

import BOT.Objects.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class configCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

    }

    @Override
    public String getHelp() {
        return "봇의 서버별 설정을 변경합니다.";
    }

    @Override
    public String getInvoke() {
        return "설정";
    }

    @Override
    public String getSmallHelp() {
        return null;
    }
}
