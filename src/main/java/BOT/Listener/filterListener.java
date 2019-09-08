package BOT.Listener;

import BOT.Objects.CommandManager;
import BOT.Objects.FilterList;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class filterListener extends ListenerAdapter {
    private final CommandManager manager;
    private final Logger logger = LoggerFactory.getLogger(Listener.class);
    public filterListener(CommandManager manager) {
        this.manager = manager;
    }
    private boolean publicflag = false;
    private String latestMessage = "";

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();
        Message message = event.getMessage();

        String[] List = FilterList.getList();
        String[] list = FilterList.getCharList();
        String[] Lists = FilterList.getWebList();
        String id = "";
        String rawMessage = message.getContentRaw();
        if(!rawMessage.equals(latestMessage)) {
            latestMessage = rawMessage;
        } else {

            return;
        }
        Logger logger = LoggerFactory.getLogger(filterListener.class);
        if(event.getGuild().getId().equals("453817631603032065")) {
            return;

        }
        for (String s : Lists) {
            if(rawMessage.contains(s)) {
                try {
                    if(event.getGuild().getSelfMember().getUser().getId().equals(event.getMember().getUser().getId())) {
                        return;
                    }
                    if(message.getMember().getUser().isBot()) {
                        return;
                    }
                    if(rawMessage.contains("cdn.discord")) {
                        return;
                    }
                    if(rawMessage.contains("discordapp")) {
                        return;
                    }
                    if(rawMessage.contains("tenor.com")) {
                        return;
                    }
                    if(rawMessage.contains("steam")) {
                        return;
                    }
                    if(event.getGuild().getId().equals("617222347425972234")) {
                        if(event.getChannel().getId().equals("620104084799750154")) {
                            return;
                        }
                    }
                    if(event.getGuild().getId().equals("600010501266866186")) {
                        if(event.getChannel().getId().equals("600021475629727745")) {
                            if(rawMessage.contains("youtube")) {
                                return;
                            }
                            if(rawMessage.contains("twitch")) {
                                return;
                            }
                            if(rawMessage.contains("youtu")) {
                                return;
                            }
                        }
                    }
                    if(event.getGuild().getId().equals("607390893804093442")) {
                        if(event.getChannel().getId().equals("607543954476630016")) {
                            if(rawMessage.contains("youtube")) {
                                return;
                            }
                            if(rawMessage.contains("twitch")) {
                                return;
                            }
                            if(rawMessage.contains("youtu")) {
                                return;
                            }
                        }
                    }
                    if(rawMessage.contains("&검색")) {
                        return;
                    }
                    if(rawMessage.contains("&search")) {
                        return;
                    }
                    if(rawMessage.contains("&재생")) {
                        return;
                    }
                    if(rawMessage.contains("&play")) {
                        return;
                    }
                    if(rawMessage.contains("&p")) {
                        return;
                    }
                    if(message.getMember().hasPermission(Permission.ADMINISTRATOR) || message.getMember().hasPermission(Permission.MANAGE_ROLES)) {
                        logger.warn("관리자가 링크를 첨부했으나, 관리자는 필터링 되지 않습니다.");

                        return;
                    }
                    if(message.getMember().getUser().getId().equals("342951769627688960")) {
                        return;
                    }
                    if(message.getMember().getUser().getId().equals("342951769627688960")) {
                        return;
                    }
                    if(!event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
                        event.getChannel().sendMessage("링크가 입력되었으나 봇이 삭제할 권한이 없습니다.").queue();

                        return;
                    }
                    message.delete().queue();
                    event.getChannel().sendMessage(event.getMember().getAsMention() + ", 링크를 보내지 마세요.").queue();
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        for (String value : list) {
            rawMessage = rawMessage.replace(value, "");
        }
        for (String s : List) {
            if (rawMessage.contains(s)) {
                try {
                    if(event.getGuild().getSelfMember().getUser().getId().equals(event.getMember().getUser().getId())) {

                        return;
                    }
                    if(message.getMember().hasPermission(Permission.ADMINISTRATOR) || message.getMember().hasPermission(Permission.MANAGE_ROLES)) {
                        logger.warn("관리자가 금지어를 말했으나, 관리자는 필터링 되지 않습니다.");

                        return;
                    }
                    if(!event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
                        event.getChannel().sendMessage("금지어가 입력되었으나 봇이 삭제할 권한이 없습니다.").queue();

                        return;
                    }
                    logger.warn(author.getAsMention() + "가 금지어를 사용하였습니다.\n" +
                            "금지어: " + message.getContentRaw());
                    rawMessage = rawMessage.replaceFirst(s,"||[데이터 말소]||");

                    boolean flag = true;
                    publicflag = true;
                    while(flag) {
                        boolean tempflag = false;
                        for(String s1 : List) {
                            if(rawMessage.contains(s1)) {
                                rawMessage = rawMessage.replaceFirst(s1,"||[데이터 말소]||");
                                tempflag = true;
                            }
                        } if(!tempflag) {
                            flag = false;
                        }
                    }
                    message.delete().complete();
                    id = event.getChannel().sendMessage(rawMessage + "\n " + author.getAsMention() + " 금지어가 포함되어 있어 자동으로 필터링 되어, 필터링 된 문장을 출력합니다.").complete().getId();
                    if(event.getGuild().getId().equals("617222347425972234")) {
                        event.getGuild().getTextChannelById("617244182653829140").sendMessage(author.getAsMention() + "가 금지어를 사용하였습니다.\n" +
                                "금지어: " + message.getContentRaw()).queue();
                    } else if (event.getGuild().getId().equals("617757206929997895")) {
                        event.getGuild().getTextChannelById("617760924714926113").sendMessage(author.getAsMention() + "가 금지어를 사용하였습니다.\n" +
                                "금지어: " + message.getContentRaw()).queue();
                    } else if(event.getGuild().getId().equals("607390893804093442")) {
                        event.getGuild().getTextChannelById("620091943522664466").sendMessage(author.getAsMention() + "가 금지어를 사용하였습니다.\n" +
                                "금지어: " + message.getContentRaw()).queue();
                    }
                } catch (Exception ignored) {
                }
            }
        }
        Message messages = event.getMessage();

        Role role;
        try {
            role = event.getGuild().getRolesByName("공개 처형", true).get(0);
        } catch (Exception ignored) {

            return;
        }
        int time;
        if(event.getGuild().getId().equals("453817631603032065")) {
            time = 10;
        } else if (event.getGuild().getId().equals("600010501266866186")) {
            time = 5;
        } else if (event.getGuild().getId().equals("617222347425972234")) {
            time = 2;
        } else if (event.getGuild().getId().equals("607390893804093442")){
            time = 7;
        } else {
            time = 7;
        }
        try {
            if (messages.getMember().getRoles().contains(role)) {
                if(publicflag) {
                    event.getChannel().deleteMessageById(id).queueAfter(time, TimeUnit.SECONDS);
                    EmbedBuilder embedBuilder = EmbedUtils.defaultEmbed()
                            .addField("공개 처형", "당신의 필터링된 메세지도 " + time + "초후 자동으로 삭제됩니다.", true);
                    event.getChannel().sendMessage(embedBuilder.build()).complete().delete().queueAfter(time, TimeUnit.SECONDS);
                } else {
                    messages.delete().queueAfter(time, TimeUnit.SECONDS);
                    EmbedBuilder embedBuilder = EmbedUtils.defaultEmbed()
                            .addField("공개 처형", "당신의 메세지는 " + time + "초후 자동으로 삭제됩니다.", true);
                    event.getChannel().sendMessage(embedBuilder.build()).complete().delete().queueAfter(time, TimeUnit.SECONDS);
                }
            }
        } catch (Exception ignored) {

        }
    }
}
