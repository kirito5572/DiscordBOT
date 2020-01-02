package BOT.Commands;

import BOT.Objects.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TESTCommand implements ICommand {
    @Override
    public void handle(List<String> args, @NotNull GuildMessageReceivedEvent event) {
    }


    @NotNull
    @Override
    public String getHelp() {
        return "디버깅용 테스트 커맨드";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "테스트";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "other";
    }
}
