package BOT.Commands;

import BOT.Constants;
import BOT.Objects.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static BOT.Listener.ONIGIRIListener.convertInputStreamToFile;

public class CatCommand implements ICommand {
    private final Logger logger = LoggerFactory.getLogger(CatCommand.class);
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        Member selfMember = event.getGuild().getSelfMember();
        int num = 1;
        try {
            num = Integer.parseInt(args.get(0));
        } catch (Exception ignored) {

        }
        if(num > 10) {
            event.getChannel().sendMessage("최대 전송 가능량은 10개입니다.").queue();

            return;
        }

        if(!selfMember.hasPermission(Permission.MESSAGE_EMBED_LINKS)) {
            event.getChannel().sendMessage("봇이 링크 메세지를 보낼 권한이 없습니다.").queue();

            return;
        }
        TextChannel channel = event.getChannel();
        Message message;
        if(event.getGuild().getId().equals("600010501266866186")) {
            message = channel.sendMessage("*주의 이 커맨드는 네다씹 커맨드입니다*." + "\n" +
                    "실행을 원하시면 :one: 아니면 :two:").complete();

            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {

                StackTraceElement[] eStackTrace = e.getStackTrace();
                StringBuilder a = new StringBuilder();
                for (StackTraceElement stackTraceElement : eStackTrace) {
                    a.append(stackTraceElement).append("\n");
                }
                logger.warn(a.toString());
            }

            message.addReaction("1\u20E3").queue();
            message.addReaction("2\u20E3").queue();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {

                StackTraceElement[] eStackTrace = e.getStackTrace();
                StringBuilder a = new StringBuilder();
                for (StackTraceElement stackTraceElement : eStackTrace) {
                    a.append(stackTraceElement).append("\n");
                }
                logger.warn(a.toString());
            }
            boolean flag = true;

            for (int j = 0; j < 11; j++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                    StackTraceElement[] eStackTrace = e.getStackTrace();
                    StringBuilder a = new StringBuilder();
                    for (StackTraceElement stackTraceElement : eStackTrace) {
                        a.append(stackTraceElement).append("\n");
                    }
                    logger.warn(a.toString());
                }
                if (message.getReactions().get(0).getCount() == 2) {
                    message.delete().complete();
                    flag = false;
                } else if (message.getReactions().get(1).getCount() == 2) {
                    message.delete().complete();
                    message = event.getChannel().sendMessage("전송이 취소되었습니다.").complete();
                    message.delete().queueAfter(5, TimeUnit.SECONDS);
                    return;
                }
                if(!flag) {
                    j = 11;
                }
            }
            if (flag) {
                message.delete().queue();
                message = channel.sendMessage("대기 시간이 초과되어 전송이 취소되었습니다.").complete();
                message.delete().queueAfter(5, TimeUnit.SECONDS);

                return;
            }
        }
        for(int i = 0; i < num; i++) {
            WebUtils.ins.scrapeWebPage("https://nekos.life/").async((document) -> {
                String a = document.getElementsByTag("head").first().toString();
                int b = a.indexOf("meta property=\"og:url\" content=\"");
                int c = a.indexOf("<meta property=\"og:image\" content=\"");
                a = a.substring(b + 32, c - 5);
                EmbedBuilder embed = EmbedUtils.embedImage(a);

                if (event.getGuild().getId().equals("600010501266866186")) {
                    Random random = new Random();
                    PrivateChannel channel1;
                    channel1 = event.getAuthor().openPrivateChannel().complete();
                    if (random.nextInt(100) > 5) { //95%
                        channel1.sendMessage(embed.build()).queue();
                    } else { //5%
                        Random fileRandom = new Random();
                        int intTemp = fileRandom.nextInt(20);
                        InputStream inputStream = this.getClass().getResourceAsStream("/" + "lol" + intTemp + ".jpg");
                        File file;
                        try {
                            file = convertInputStreamToFile(inputStream, ".jpg");
                        } catch (IOException e) {

                            StackTraceElement[] eStackTrace = e.getStackTrace();
                            StringBuilder ba = new StringBuilder();
                            for (StackTraceElement stackTraceElement : eStackTrace) {
                                ba.append(stackTraceElement).append("\n");
                            }
                            logger.warn(ba.toString());

                            event.getChannel().sendMessage("에러가 발생했습니다.").queue();

                            return;
                        }
                        channel1.sendFile(file).queue();
                    }

                } else {
                    event.getChannel().sendMessage(embed.build()).queue();
                }
            });
        }
    }

    @Override
    public String getHelp() {
        return "랜덤 네코 생성기 \n" +
                "사용법: `" + Constants.PREFIX + getInvoke() + "` <반복숫자>(미입력시 1)";
    }

    @Override
    public String getInvoke() {
        return "네코";
    }

    @Override
    public String getSmallHelp() {
        return "other";
    }
}