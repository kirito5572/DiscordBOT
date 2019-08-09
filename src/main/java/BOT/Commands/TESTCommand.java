package BOT.Commands;

import BOT.Objects.ICommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class TESTCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage("디버깅용 테스트 명령어 입니다.").queue();
    }

    @Override
    public String getHelp() {
        return "디버깅용 테스트 커맨드";
    }

    @Override
    public String getInvoke() {
        return "테스트";
    }

    @Override
    public String getSmallHelp() {
        return "디버깅용 테스트 커맨드입니다.";
    }
}
