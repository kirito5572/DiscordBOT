package com.kirito5572.commands.main.owneronlycommand;

import com.kirito5572.objects.main.EventPackage;
import com.kirito5572.objects.main.ICommand;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

import static com.kirito5572.listener.main.Listener.getID1;

public class BotOwnerNoticeCommand implements ICommand {
    private final Logger logger = LoggerFactory.getLogger(BotOwnerNoticeCommand.class);
    @Override
    public void handle(@NotNull List<String> args, @NotNull EventPackage event) {
        if(!Objects.requireNonNull(event.member()).getUser().getId().equals(getID1())) {
            return;
        }
        StringBuilder temp = new StringBuilder();
        for (String arg : args) {
            temp.append(arg).append(" ");
        }
        event.message().delete().queue();
        String message = temp.toString();
        if(message.contains("!here")) {
            message = message.replaceFirst("!here", "@here");
        }
        if(message.contains("!everyone")) {
            message = message.replaceFirst("!everyone", "@everyone");
        }
        if (event.getJDA().getSelfUser().getId().equals("592987181186940931")) {
            try {
                Objects.requireNonNull(Objects.requireNonNull(event.getJDA().getGuildById("617222347425972234")).getTextChannelById("617224261139955722")) //캣카페
                        .sendMessage(message).queue();
                Objects.requireNonNull(Objects.requireNonNull(event.getJDA().getGuildById("617757206929997895")).getTextChannelById("617759661881556994")) //데어라
                        .sendMessage(message).queue();
                Objects.requireNonNull(Objects.requireNonNull(event.getJDA().getGuildById("479625309788962816")).getTextChannelById("479625309788962818")) //심플
                        .sendMessage(message).queue();
                Objects.requireNonNull(Objects.requireNonNull(event.getJDA().getGuildById("508913681279483913")).getTextChannelById("539470263121608740")) //선우형
                        .sendMessage(message).queue();
                Objects.requireNonNull(Objects.requireNonNull(event.getJDA().getGuildById("453817631603032065")).getTextChannelById("574856464347430914")) //주먹밥
                        .sendMessage(message).queue();
                Objects.requireNonNull(Objects.requireNonNull(event.getJDA().getGuildById("607390893804093442")).getTextChannelById("620082301413621771")) //소프냥이
                        .sendMessage(message).queue();
                Objects.requireNonNull(Objects.requireNonNull(event.getJDA().getGuildById("607390203086372866")).getTextChannelById("607543076734369792")) //제이
                        .sendMessage(message).queue();
                Objects.requireNonNull(Objects.requireNonNull(event.getJDA().getGuildById("439780696999985172")).getTextChannelById("441164108671221761")) //네코샘플
                        .sendMessage(message).queue();
            } catch (NullPointerException e) {

                StackTraceElement[] eStackTrace = e.getStackTrace();
                StringBuilder a = new StringBuilder();
                for (StackTraceElement stackTraceElement : eStackTrace) {
                    a.append(stackTraceElement).append("\n");
                }
                logger.warn(a.toString());
            }
        } else {
            Objects.requireNonNull(Objects.requireNonNull(event.getJDA().getGuildById("600010501266866186")).getTextChannelById("600015178821664769")).sendMessage(message).queue();
        }
        event.getChannel().sendMessage("전송이 완료되었습니다.").queue();
    }

    @NotNull
    @Override
    public String getHelp() {
        return "디스코드 제작자가 공지하기 위해 쓰는 것입니다.";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "공지";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "other";
    }
}
