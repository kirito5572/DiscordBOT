package BOT.Commands.Moderation;

import BOT.Constants;
import BOT.objects.ICommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class MuteCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

    }

    @Override
    public String getHelp() {
        return "시끄러운 사람이 있나요? 그 사람의 입을 닫아봅시다." +
                "사용법: `" + Constants.PREFIX + getInvoke() + "`(@유저)";
    }

    @Override
    public String getInvoke() {
        return "그입다물라";
    }
}
