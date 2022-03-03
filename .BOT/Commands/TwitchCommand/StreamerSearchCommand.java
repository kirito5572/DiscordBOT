package BOT.Commands.TwitchCommand;

import com.kirito5572.App;
import com.kirito5572.objects.main.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StreamerSearchCommand implements ICommand {
    @Override
    public void handle(@NotNull List<String> args, @NotNull EventPackage event) {

    }

    @NotNull
    @Override
    public String getHelp() {
        return "트위치에서 스트리머를 검색합니다.\n" +
                "사용법: `" + App.getPREFIX() + getInvoke() + " <스트리머 ID>";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "트위치검색";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "twitch";
    }
}
