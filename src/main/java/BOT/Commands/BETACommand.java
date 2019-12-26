package BOT.Commands;

import BOT.Objects.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BETACommand implements ICommand {
    @Override
    public void handle(List<String> args, @NotNull GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage("베타 기능의 봇은 이 링크로 초대할 수 있어요!\n" +
                "https://discordapp.com/api/oauth2/authorize?client_id=606477746616401920&permissions=8&scope=bot").queue();
    }

    @Nullable
    @Override
    public String getHelp() {
        return null;
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "베타";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "others";
    }
}
