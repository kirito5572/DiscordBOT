package BOT.Commands;

import BOT.App;
import BOT.Objects.ICommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class upTimeCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        Date nowDate = new Date();
        Date upTime = new Date(nowDate.getTime() - App.getDate().getTime());
        SimpleDateFormat format1 = new SimpleDateFormat( "dd일 hh시간 mm분 ss초 z");
        SimpleDateFormat format = new SimpleDateFormat( "yyyy/MM/dd aa HH:mm:ss z");
        String sendTime = format1.format(upTime);
        String nowTime = format.format(nowDate);
        event.getChannel().sendMessage("시작 시간: " + App.getTime() + "\n"
                + "현재 시간" + nowTime + "\n"
                + "가동 시간: " + sendTime).queue();
    }

    @Override
    public String getHelp() {
        return "봇 프로세서가 얼마나 오래 켜져있는지를 검색합니다.\n" +
                "사용법: `" + App.getPREFIX() + getInvoke() + "`";
    }

    @Override
    public String getInvoke() {
        return "업타임";
    }

    @Override
    public String getSmallHelp() {
        return "가동 시간은?";
    }
}
