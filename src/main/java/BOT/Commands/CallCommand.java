package BOT.Commands;

import BOT.Objects.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class CallCommand implements ICommand {
    @Override
    public void handle(List<String> args, @NotNull GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage(Objects.requireNonNull(event.getGuild().getMemberById("284508374924787713")).getAsMention()).queue();
        event.getChannel().sendMessage("무슨 오류가 발생했는지 상세하게 적어주시면 확인후에, 결과를 알려드리도록 하겠습니다.").queue();
    }

    @NotNull
    @Override
    public String getHelp() {
        return "봇 제작자를 호출 하는 명령어 입니다.";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "제작자";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "other";
    }
}
