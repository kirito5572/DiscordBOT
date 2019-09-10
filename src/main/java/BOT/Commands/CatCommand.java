package BOT.Commands;

import BOT.Constants;
import BOT.Music.PlayerManager;
import BOT.Objects.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static BOT.Listener.ONIGIRIListener.convertInputStreamToFile;

public class CatCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        Member selfMember = event.getGuild().getSelfMember();

        if(!selfMember.hasPermission(Permission.MESSAGE_EMBED_LINKS)) {
            event.getChannel().sendMessage("봇이 링크 메세지를 보낼 권한이 없습니다.").queue();

            return;
        }

        WebUtils.ins.scrapeWebPage("https://nekos.life/").async((document) -> {
            String a = document.getElementsByTag("head").first().toString();
            int b = a.indexOf("meta property=\"og:url\" content=\"");
            int c = a.indexOf("<meta property=\"og:image\" content=\"");
            a = a.substring(b + 32, c - 5);
            EmbedBuilder embed = EmbedUtils.embedImage(a);
            TextChannel channel = event.getChannel();
            Message message;

            if(event.getGuild().getId().equals("600010501266866186")) {
                try {
                    message = channel.sendMessage("*주의 이 커맨드는 네다씹 커맨드입니다*." + "\n" +
                            "실행을 원하시면 :one: 아니면 :two:").complete();

                    Thread.sleep(400);
                    String ID = channel.getLatestMessageId();

                    channel.addReactionById(ID, "1\u20E3").queue();
                    channel.addReactionById(ID, "2\u20E3").queue();
                    Thread.sleep(500);

                    for (int i = 0; i < 11; i++) {
                        Thread.sleep(1000);
                        System.out.println(channel.getMessageById(ID).complete().getReactions().get(0).getReactionEmote().getEmote());
                        if (channel.getMessageById(ID).complete().getReactions().get(0).getCount() == 2) {
                            Random random = new Random();
                            PrivateChannel channel1;
                            channel1 = event.getAuthor().openPrivateChannel().complete();
                            if(random.nextInt(100) > 5) { //95%
                                channel1.sendMessage(embed.build()).queue();
                                message.delete().queue();
                            } else { //5%
                                Random fileRandom = new Random();
                                int intTemp = fileRandom.nextInt(6);
                                InputStream inputStream = this.getClass().getResourceAsStream("/" + "lol" + intTemp +".jpg");
                                File file;
                                try {
                                    file = convertInputStreamToFile(inputStream, ".jpg");
                                } catch (IOException e) {
                                    e.printStackTrace();

                                    event.getChannel().sendMessage("에러가 발생했습니다.").queue();

                                    return;
                                }
                                channel1.sendFile(file).queue();
                                message.delete().queue();
                            }

                            return;
                        } else if (channel.getMessageById(ID).complete().getReactions().get(1).getCount() == 2) {
                            message.delete().complete();
                            message = event.getChannel().sendMessage("전송이 취소되었습니다.").complete();
                            message.delete().queueAfter(5, TimeUnit.SECONDS);
                            return;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                message = channel.sendMessage("대기 시간이 초과되어 전송이 취소되었습니다.").complete();
                message.delete().queueAfter(5, TimeUnit.SECONDS);

            } else {
                event.getChannel().sendMessage(embed.build()).queue();
            }
        });
    }

    @Override
    public String getHelp() {
        return "랜덤 네코 생성기 \n" +
                "사용법: `" + Constants.PREFIX + getInvoke() + "`";
    }

    @Override
    public String getInvoke() {
        return "네코";
    }

    @Override
    public String getSmallHelp() {
        return "카와이 네코쟝";
    }
}