package BOT.Commands;

import BOT.App;
import BOT.Objects.ICommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class VersionCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(App.isDEBUG_MODE() || App.isONLINE_DEBUG()) {
            event.getChannel().sendMessage("빌드 버젼 V 1.3.5 Preview 6(" + App.getTime() + ")").queue();
            return;
        }
        event.getChannel().sendMessage("빌드 버젼 V 1.3.5 Preview 5(" + App.getTime() + ")").queue();
        if(event.getGuild().getId().equals("508913681279483913")) {
            event.getChannel().sendMessage("봇 사이트: ``http://ritobot.mystrikingly.com``").queue();
            event.getChannel().sendMessage("업데이트 내역: \n" +
                    "```" +
                    " - 서버 커스텀 명령어 공개처형을 일반 명령어로 변경\n" +
                    " - 공개 처형 명령어 기능 개선\n" +
                    " - 필터링 데이터베이스 추가" +
                    "```").queue();
        }
    }

    @Override
    public String getHelp() {
        return "빌드 버젼을 알려줍니다. \n" +
                "사용법: `" + App.getPREFIX() + getInvoke() + "`";
    }

    @Override
    public String getInvoke() {
        return "봇버젼";
    }

    @Override
    public String getSmallHelp() {
        return "봇의 빌드 버젼을 알려줍니다";
    }
}
