package BOT.Listener;

import BOT.Objects.FilterList;
import BOT.Objects.SQL;
import BOT.Objects.config;
import BOT.Objects.linkConfirm;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static BOT.Objects.linkConfirm.getLink;

public class filterListener extends ListenerAdapter {
    private final Logger logger = LoggerFactory.getLogger(filterListener.class);
    private boolean publicflag = false;
    private String latestMessage = "";
    String[] List = FilterList.getList();
    String[] Lists = FilterList.getWebList();
    private static Map<String, List<String>> customFilterWordMap = new HashMap<>();

    @Override
    public void onGuildMessageUpdate(@Nonnull GuildMessageUpdateEvent event) {
        User author;
        Message message;
        Guild guild;
        Member member;
        Message messages;
        MessageChannel channel;
        JDA jda;
        try {
            author = event.getAuthor();
            message = event.getMessage();
            if(message.getAuthor().isBot()) {
                return;
            }
            if(message.getContentRaw().startsWith("&") || message.getContentRaw().startsWith("$$")) {
                return;
            }
            guild = event.getGuild();
            messages = event.getMessage();
            member = event.getMember();
            channel = event.getChannel();
            jda = event.getJDA();
            assert member != null;
            filter(author, message, guild, member, messages, channel, jda);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        User author;
        Message message;
        Guild guild;
        Member member;
        Message messages;
        MessageChannel channel;
        JDA jda;
        try {
            author = event.getAuthor();
            message = event.getMessage();
            if(message.getAuthor().isBot()) {
                return;
            }
            if(message.getContentRaw().startsWith("&") || message.getContentRaw().startsWith("$$")) {
                return;
            }
            guild = event.getGuild();
            messages = event.getMessage();
            member = event.getMember();
            channel = event.getChannel();
            jda = event.getJDA();
            assert member != null;
            filter(author, message, guild, member, messages, channel, jda);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filter(@NotNull User author, @NotNull Message message, @NotNull Guild guild, @NotNull Member member, @NotNull Message messages, @NotNull MessageChannel channel, @NotNull JDA jda) {
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
        boolean filterPass = false;
        boolean killPass = false;
        boolean customFilterPass = false;
        try {
            if (author.getId().equals("342951769627688960") || author.getId().equals("492832169715040276")) {
                //그린서버 보안부
                linkPass = true;
                filterPass = true;
            }
            String[] linkFilterDisable = config.getLinkFilterDisable();
            for (String s : linkFilterDisable) {
                if (guild.getId().equals(s)) {
                    linkPass = true;
                }
            }
            String[] customFilterDisable = config.getCustomFilterDisable();
            for (String s : customFilterDisable) {
                if (guild.getId().equals(s)) {
                    customFilterPass = true;
                }
            }
            String[] filterDisable = config.getFilterDisable();
            for (String s : filterDisable) {
                if (guild.getId().equals(s)) {
                    filterPass = true;
                }
            }
            String[] killfilterDisable = config.getKillfilterDiable();
            for (String s : killfilterDisable) {
                if (guild.getId().equals(s)) {
                    killPass = true;
                }
            }
            if(guild.getSelfMember().getUser().getId().equals(author.getId())) {
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
            if(rawMessage.contains(";;p")) {
                linkPass = true;
            }
            if(rawMessage.contains("그린아 재생")) {
                linkPass = true;
            }
            if(rawMessage.contains("그린아 검색")) {
                linkPass = true;
            }
            if(rawMessage.contains("=music")) {
                linkPass = true;
            }
            if(rawMessage.contains("=m p")) {
                linkPass = true;
            }
            if(rawMessage.contains("p! play")) {
                linkPass = true;
            }
            if(rawMessage.contains("!music")) {
                linkPass = true;
            }
            if(rawMessage.contains("!p")) {
                linkPass = true;
            }
            if(rawMessage.contains("!play")) {
                linkPass = true;
            }
            if(rawMessage.contains("!music")) {
                linkPass = true;
            }
            if(rawMessage.contains("!p")) {
                linkPass = true;
            }
            if(rawMessage.contains("!play")) {
                linkPass = true;
            }

            if(guild.getId().equals("600010501266866186")) {  //끄린이
                Role role = guild.getRoleById("616229894401294356");
                if(Objects.requireNonNull(member).getRoles().contains(role)) { //외무부
                    linkPass = true;
                }
            }
            if(guild.getId().equals("617222347425972234")) {  //캣카페
                if(channel.getId().equals("620104084799750154")) {
                    linkPass = true;
                }
            }
            if(guild.getId().equals("607390203086372866")) {  //제이 서버
                if(channel.getId().equals("607390781933617182")) {
                    linkPass = true;
                }
            }
            if(guild.getId().equals("607390893804093442") || channel.getId().equals("600021475629727745")) { // 소프냥이 & 그린 영상 채널
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

            StackTraceElement[] eStackTrace = e.getStackTrace();
            StringBuilder a = new StringBuilder();
            for (StackTraceElement stackTraceElement : eStackTrace) {
                a.append(stackTraceElement).append("\n");
            }
            logger.warn(a.toString());
        }
        if(!linkPass) {
            for (String s : Lists) {
                if (rawMessage.contains(s)) {
                    boolean isLink = linkConfirm.isLink(rawMessage, s);
                    System.out.println(isLink);
                    if(!isLink) {
                        return;
                    }
                    try {
                        try {
                            if (Objects.requireNonNull(member).getRoles().contains(guild.getRolesByName("공개 처형", true).get(0))) {
                                message.delete().complete();
                                channel.sendMessage("공개처형자 " + author.getAsMention() + ", 링크를 보내지 마세요.").complete().delete().queueAfter(3,TimeUnit.SECONDS);
                                logger.warn("공개 처형자 링크 필터링이 되었습니다. \n" +
                                        "서버: " + guild.getName() + "\n" +
                                        "이유: " + s + "\n" +
                                        "보낸 사람: " + member.getNickname() + "\n" +
                                        "차단된 링크: " + getLink());
                            }
                        } catch (Exception ignored) {
                        }

                        if (Objects.requireNonNull(message.getMember()).hasPermission(Permission.ADMINISTRATOR) || message.getMember().hasPermission(Permission.MANAGE_ROLES)) {
                            logger.warn("관리자가 링크를 첨부했으나, 관리자는 필터링 되지 않습니다. \n" +
                                    "서버: " + guild.getName() + "\n" +
                                    "이유: " + s + "\n" +
                                    "차단된 링크: " + getLink());
                            return;
                        }
                        if (!guild.getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
                            channel.sendMessage("링크가 입력되었으나 봇이 삭제할 권한이 없습니다.").queue();

                            return;
                        }
                        message.delete().queue();
                        channel.sendMessage(Objects.requireNonNull(member).getAsMention() + ", 링크를 보내지 마세요.").queue();
                        logger.warn("링크 필터링이 되었습니다. \n" +
                                "서버: " + guild.getName() + "\n" +
                                "이유: " + s + "\n" +
                                "보낸 사람: " + member.getEffectiveName() + "\n" +
                                "차단된 링크: " + getLink());
                        String channelId = SQL.configDownLoad(SQL.filterlog, guild.getId());
                        if(channelId.equals("error")) {
                            logger.error("링크 필터링 채널 전송중 에러가 발생했습니다!");
                        } else if(!channelId.equals("null")) {
                            EmbedBuilder builder = EmbedUtils.getDefaultEmbed()
                                    .setTitle("링크 필터링")
                                    .setColor(Color.RED)
                                    .addField("보낸 사람", member.getEffectiveName(), false)
                                    .addField("이유", s, false)
                                    .addField("차단된 링크", getLink(), false);
                            Objects.requireNonNull(guild.getTextChannelById(channelId)).sendMessage(builder.build()).queue();
                        }
                    } catch (Exception e) {
                        if (jda.getSelfUser().getId().equals("592987181186940931")) {

                            StackTraceElement[] eStackTrace = e.getStackTrace();
                            StringBuilder a = new StringBuilder();
                            for (StackTraceElement stackTraceElement : eStackTrace) {
                                a.append(stackTraceElement).append("\n");
                            }
                            logger.warn(a.toString());
                        }
                    }
                }
            }
        }
        if(!customFilterPass) {
            try {
                for (String s : customFilterWordMap.get(guild.getId())) {
                    if (rawMessage.contains(s)) {
                        System.out.println(s);
                    }
                }
            } catch (Exception e) {
                System.out.println(guild.getId());
                e.printStackTrace();
            }
        }
        if(!filterPass) {
            for (String s : List) {
                if (rawMessage.contains(s)) {
                    try {
                        if (guild.getSelfMember().getUser().getId().equals(Objects.requireNonNull(author).getId())) {

                            return;
                        }
                        if (author.isBot()) {

                            return;
                        }
                        if (Objects.requireNonNull(message.getMember()).hasPermission(Permission.ADMINISTRATOR) || message.getMember().hasPermission(Permission.MANAGE_ROLES)) {
                            logger.warn("관리자가 금지어를 말했으나, 관리자는 필터링 되지 않습니다. \n" +
                                    "서버: " + guild.getName() + "\n" +
                                    "금지어: " + s + "\n" +
                                    "문장: " + message.getContentRaw());

                            return;
                        }
                        if (!guild.getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
                            channel.sendMessage("금지어가 입력되었으나 봇이 삭제할 권한이 없습니다.").queue();

                            return;
                        }
                        logger.warn(author.getAsMention() + "가 금지어를 사용하였습니다.\n" +
                                "서버: " + guild.getName() + "\n" +
                                "금지어: " + s + "\n" +
                                "문장: " + message.getContentRaw());
                        String messagetemp = message.getContentRaw();
                        messagetemp = messagetemp.replaceFirst(s, "||[데이터 말소]||");
                        rawMessage = rawMessage.replaceFirst(s, "||[데이터 말소]||");

                        boolean flag = true;
                        publicflag = true;
                        while (flag) {
                            boolean tempflag = false;
                            for (String s1 : List) {
                                if (rawMessage.contains(s1)) {
                                    rawMessage = rawMessage.replaceFirst(s1, "||[데이터 말소]||");
                                    messagetemp = messagetemp.replaceFirst(s1, "||[데이터 말소]||");
                                    tempflag = true;
                                }
                            }
                            if (!tempflag) {
                                flag = false;
                            }
                        }
                        message.delete().complete();
                        id = channel.sendMessage(messagetemp + "\n " + author.getAsMention() + " 금지어가 포함되어 있어 자동으로 필터링 되어, 필터링 된 문장을 출력합니다.").complete().getId();
                        EmbedBuilder builder = EmbedUtils.getDefaultEmbed()
                                .setTitle("금지어 사용")
                                .setColor(Color.RED)
                                .addField("금지어 사용자", author.getAsMention(), false)
                                .addField("금지어", s, false)
                                .addField("문장", message.getContentRaw(), false);
                        String channelId = SQL.configDownLoad(guild.getId(), SQL.filterlog);
                        if(channelId != null) {
                            if (channelId.equals("error")) {
                                logger.error("링크 필터링 채널 전송중 에러가 발생했습니다!");
                            } else if (!channelId.equals("null")) {
                                Objects.requireNonNull(guild.getTextChannelById(channelId)).sendMessage(builder.build()).queue();
                            }
                        }
                    } catch (Exception e) {

                        StackTraceElement[] eStackTrace = e.getStackTrace();
                        StringBuilder a = new StringBuilder();
                        for (StackTraceElement stackTraceElement : eStackTrace) {
                            a.append(stackTraceElement).append("\n");
                        }
                        logger.warn(a.toString());
                    }
                }
            }
        }
        if(!killPass) {
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
                    time = 3;
                    break;
                case "661656224181518368":
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
                if (Objects.requireNonNull(messages.getMember()).getRoles().contains(role)) {
                    if (publicflag) {
                        channel.deleteMessageById(id).queueAfter(time, TimeUnit.SECONDS);
                        EmbedBuilder embedBuilder = EmbedUtils.getDefaultEmbed()
                                .addField("공개 처형", "당신의 필터링된 메세지도 " + time + "초후 자동으로 삭제됩니다.", true);
                        channel.sendMessage(embedBuilder.build()).complete().delete().queueAfter(time, TimeUnit.SECONDS);
                    } else {
                        messages.delete().queueAfter(time, TimeUnit.SECONDS);
                        EmbedBuilder embedBuilder = EmbedUtils.getDefaultEmbed()
                                .addField("공개 처형", "당신의 메세지는 " + time + "초후 자동으로 삭제됩니다.", true);
                        channel.sendMessage(embedBuilder.build()).complete().delete().queueAfter(time, TimeUnit.SECONDS);
                    }
                }
            } catch (Exception ignored) {

            }
        }
    }

    public static void setCustomFilterWordMap(Map<String, java.util.List<String>> customFilterWordMap) {
        filterListener.customFilterWordMap = customFilterWordMap;
    }
}
