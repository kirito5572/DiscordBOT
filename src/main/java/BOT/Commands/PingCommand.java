package BOT.Commands;

import BOT.Constants;
import BOT.objects.ICommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class PingCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage("퐁!").queue((message) ->
                message.editMessageFormat("결과: %sms", event.getJDA().getPing()).queue()
        );
    }

    @Override
    public String getHelp() {
        return "퐁!\n" +
                "명령어: `" + Constants.PREFIX + getInvoke() + "`";
    }

    @Override
    public String getInvoke() {
        return "ping";
    }
}