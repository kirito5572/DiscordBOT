package BOT.Listener;

import BOT.Objects.FilterList;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class filterListener extends ListenerAdapter {
    private final Logger logger = LoggerFactory.getLogger(filterListener.class);
    private boolean publicflag = false;
    private String latestMessage = "";

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();
        Message message = event.getMessage();
        Guild guild = event.getGuild();

        String[] List = FilterList.getList();
        String[] list = FilterList.getCharList();
        String[] Lists = FilterList.getWebList();
        String[] greenList = FilterList.getGreenList();
        String id = "";
        String rawMessage;
        try {
            rawMessage = message.getContentRaw();
        } catch (Exception e) {
            return;
        }
        if(!rawMessage.equals(latestMessage)) {
            latestMessage = rawMessage;
        } else {
            return;
        }
        boolean linkPass = false;
        try {
            if (author.getId().equals("342951769627688960") || author.getId().equals("492832169715040276")) {
                //그린서버 보안부
                return;
            }
            if (guild.getId().equals("453817631603032065")) {
                return;
            }
            if (guild.getId().equals("439780696999985172")) {
                return;
            }

            if(guild.getSelfMember().getUser().getId().equals(event.getMember().getUser().getId())) {
                linkPass = true;
            }
            if(author.isBot()) {
                linkPass = true;
            }
            if(rawMessage.contains("cdn.discord")) {
                linkPass = true;
            }
            if(rawMessage.contains("discordapp")) {
                linkPass = true;
            }
            if(rawMessage.contains("tenor.com")) {
                linkPass = true;
            }
            if(rawMessage.contains("steam")) {
                linkPass = true;
            }
            if(rawMessage.contains("&검색")) {
                linkPass = true;
            }
            if(rawMessage.contains("&search")) {
                linkPass = true;
            }
            if(rawMessage.contains("&재생")) {
                linkPass = true;
            }
            if(rawMessage.contains("&play")) {
                linkPass = true;
            }
            if(rawMessage.contains("&p")) {
                linkPass = true;
            }

            if(guild.getId().equals("600010501266866186")) {  //끄린이
                if(event.getMember().getRoles().contains(event.getGuild().getRoleById("616229894401294356"))) {
                    linkPass = true;
                }
            }
            if(guild.getId().equals("617222347425972234")) {  //캣카페
                if(event.getChannel().getId().equals("620104084799750154")) {
                    linkPass = true;
                }
            }
            if(guild.getId().equals("607390203086372866")) {  //제이 서버
                if(event.getChannel().getId().equals("607390781933617182")) {
                    linkPass = true;
                }
            }
            if(guild.getId().equals("508913681279483913") || guild.getId().equals("453817631603032065")) {  // 선우형 & 주먹밥
                linkPass = true;
            }
            if(guild.getId().equals("607390893804093442") || event.getChannel().getId().equals("600021475629727745")) { // 소프냥이 & 그린 영상 채널
                if(rawMessage.contains("youtube")) {
                    linkPass = true;
                }
                if(rawMessage.contains("twitch")) {
                    linkPass = true;
                }
                if(rawMessage.contains("youtu")) {
                    linkPass = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!linkPass) {
            for (String s : Lists) {
                if (rawMessage.contains(s)) {
                    try {
                        try {
                            if (event.getMember().getRoles().contains(guild.getRolesByName("공개 처형", true).get(0))) {
                                message.delete().complete();
                                event.getChannel().sendMessage(author.getAsMention() + ", 공개처형자의 링크는 예외 없이 즉시 차단됩니다.").queue();
                            }
                        } catch (Exception ignored) {
                        }

                        if (message.getMember().hasPermission(Permission.ADMINISTRATOR) || message.getMember().hasPermission(Permission.MANAGE_ROLES)) {
                            logger.warn("관리자가 링크를 첨부했으나, 관리자는 필터링 되지 않습니다. \n" +
                                    "서버: " + event.getGuild().getName() + "\n" +
                                    "이유: " + s + "\n" +
                                    "차단된 링크: " + message.getContentRaw());
                            return;
                        }
                        if (!guild.getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
                            event.getChannel().sendMessage("링크가 입력되었으나 봇이 삭제할 권한이 없습니다.").queue();

                            return;
                        }
                        message.delete().queue();
                        event.getChannel().sendMessage(event.getMember().getAsMention() + ", 링크를 보내지 마세요.").queue();
                        logger.warn("링크 필터링이 되었습니다. \n" +
                                "서버: " + event.getGuild().getName() + "\n" +
                                "이유: " + s + "\n" +
                                "차단된 링크: " + message.getContentRaw());
                        if(event.getGuild().getId().equals("600010501266866186")) {
                            event.getGuild().getTextChannelById("623841727823740928").sendMessage("링크 필터링이 되었습니다. \n" +
                                    "서버: " + event.getGuild().getName() + "\n" +
                                    "이유: " + s + "\n" +
                                    "차단된 링크: " + message.getContentRaw()).queue();
                        }
                        return;
                    } catch (Exception e) {
                        if (event.getJDA().getSelfUser().getId().equals("592987181186940931")) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        boolean clean = true;
        while (clean) {
            for (String values : list) {
                try {
                    rawMessage = rawMessage.replace(values, "");
                } catch (Exception ignored) {
                }
            }
            for (String values : list) {
                clean = rawMessage.contains(values);
            }
        }
        for (String s : List) {
            if (rawMessage.contains(s)) {
                try {
                    if(guild.getSelfMember().getUser().getId().equals(event.getMember().getUser().getId())) {

                        return;
                    }
                    if(author.isBot()) {

                        return;
                    }
                    if(message.getMember().hasPermission(Permission.ADMINISTRATOR) || message.getMember().hasPermission(Permission.MANAGE_ROLES)) {
                        logger.warn("관리자가 금지어를 말했으나, 관리자는 필터링 되지 않습니다. \n" +
                                "서버: " + event.getGuild().getName() + "\n" +
                                "금지어: " + s + "\n" +
                                "문장: " + message.getContentRaw());

                        return;
                    }
                    if(!guild.getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
                        event.getChannel().sendMessage("금지어가 입력되었으나 봇이 삭제할 권한이 없습니다.").queue();

                        return;
                    }
                    logger.warn(author.getAsMention() + "가 금지어를 사용하였습니다.\n" +
                            "서버: " + event.getGuild().getName() + "\n" +
                            "금지어: " + s + "\n" +
                            "문장: " + message.getContentRaw());
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
                    EmbedBuilder builder = EmbedUtils.defaultEmbed()
                            .setTitle("금지어 사용")
                            .setColor(Color.RED)
                            .addField("금지어 사용자", author.getAsMention(), false)
                            .addField("금지어", s, false)
                            .addField("문장", message.getContentRaw(), false);
                    switch (guild.getId()) {
                        case "617222347425972234":
                            guild.getTextChannelById("617244182653829140").sendMessage(builder.build()).queue();
                            break;
                        case "617757206929997895":
                            guild.getTextChannelById("617760924714926113").sendMessage(builder.build()).queue();
                            break;
                        case "607390893804093442":
                            guild.getTextChannelById("620091943522664466").sendMessage(builder.build()).queue();
                            break;
                        case "600010501266866186": //끄린이
                            guild.getTextChannelById("623841727823740928").sendMessage(builder.build()).queue();
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if(guild.getId().equals("600010501266866186")) {
            for (String s : greenList) {
                if(rawMessage.contains(s)) {
                    if(guild.getSelfMember().getUser().getId().equals(event.getMember().getUser().getId())) {

                        return;
                    }
                    if(message.getMember().hasPermission(Permission.ADMINISTRATOR) || message.getMember().hasPermission(Permission.MANAGE_ROLES)) {
                        logger.warn("관리자가 타서버 언급을 했으나, 관리자는 필터링 되지 않습니다.");

                        return;
                    }
                    message.delete().complete();
                    event.getChannel().sendMessage("타 서버 발언은 모두 차단됩니다.").queue();
                }
            }
        }
        Message messages = event.getMessage();

        Role role;
        try {
            role = guild.getRolesByName("공개 처형", true).get(0);
        } catch (Exception e) {
            return;
        }
        int time;
        switch (guild.getId()) {
            case "453817631603032065":
                time = 10;
                break;
            case "600010501266866186":
            case "607390893804093442":
                time = 5;
                break;
            case "617222347425972234":
                time = 2;
                break;
            default:
                time = 7;
                break;
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
