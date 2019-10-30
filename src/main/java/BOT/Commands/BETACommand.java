package BOT.Commands;

import BOT.Objects.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class BETACommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage("베타 기능의 봇은 이 링크로 초대할 수 있어요!\n" +
                "https://discordapp.com/api/oauth2/authorize?client_id=606477746616401920&permissions=8&scope=bot").queue();
    }

    @Override
    public String getHelp() {
        return null;
    }

    @Override
    public String getInvoke() {
        return "베타";
    }

    @Override
    public String getSmallHelp() {
        return "others";
    }
}
