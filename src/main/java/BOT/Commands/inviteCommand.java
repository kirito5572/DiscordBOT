package BOT.Commands;

import BOT.Objects.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class inviteCommand implements ICommand {
    @Override
    public void handle(List<String> args, @NotNull GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage("https://discordapp.com/api/oauth2/authorize?client_id=592987181186940931&permissions=8&scope=bot").queue();
    }

    @NotNull
    @Override
    public String getHelp() {
        return "봇 초대 링크를 받아옵니다.";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "초대";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "other";
    }
}
