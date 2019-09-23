package BOT.Commands.ONIGIRIServerCustom;

import BOT.Objects.ICommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class NEKODICOServerCustom implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

    }

    @Override
    public String getHelp() {
        return "자동으로 역할을 부여합니다. 이모지 반응형 입니다.";
    }

    @Override
    public String getInvoke() {
        return "자동역할";
    }

    @Override
    public String getSmallHelp() {
        return "serverCustom";
    }
}
