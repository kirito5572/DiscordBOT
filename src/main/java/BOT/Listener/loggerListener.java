package BOT.Listener;

import BOT.Objects.SQL;
import BOT.Objects.config;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.ReconnectedEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.message.MessageBulkDeleteEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.awt.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class loggerListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        Message message = event.getMessage();
        Guild guild = event.getGuild();
        String messageId = message.getId();
        String messageContent = message.getContentDisplay();
        String authorId = event.getAuthor().getId();
        for(String guild1 : config.getTextLoggingEnable()) {
            if(guild.getId().equals(guild1)) {
                if (event.getAuthor().isBot()) {
                    return;
                }
                if (message.isWebhookMessage() || message.isTTS()) {
                    return;
                }
                while(messageContent.contains("'")) {
                    messageContent = messageContent.replaceFirst("'", "");
                }
                while(messageContent.contains("\"")) {
                    messageContent = messageContent.replaceFirst("\"", "");
                }
                while(messageContent.contains("\\")) {
                    messageContent = messageContent.replace("\\", "");
                }
                if (messageContent.contains("\\'")) {
                    messageContent = messageContent.replace("\\'", "\\\\'");
                }
                final boolean[] temp = {SQL.loggingMessageUpLoad(guild.getId(), messageId, messageContent, authorId)};
                String finalMessageContent = messageContent;
                new Thread(() -> {
                    while (!temp[0]) {
                        temp[0] = SQL.loggingMessageUpLoad(guild.getId(), messageId, finalMessageContent, authorId);
                        try {
                            System.out.println(temp[0]);
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
    }



    @Override
    public void onGuildMessageUpdate(@Nonnull GuildMessageUpdateEvent event) {
        Message message = event.getMessage();
        Guild guild = event.getGuild();
        String messageId = message.getId();
        String messageContent = message.getContentDisplay();
        String authorId = event.getAuthor().getId();
        for(String guild1 : config.getTextLoggingEnable()) {
            if (guild.getId().equals(guild1)) {
                if (message.isWebhookMessage() || message.isTTS()) {
                    return;
                }
                if (event.getAuthor().isBot()) {
                    return;
                }
                while (messageContent.contains("\\")) {
                    messageContent = messageContent.replace("\\", "");
                }
                while (messageContent.contains("'")) {
                    messageContent = messageContent.replaceFirst("'", "");
                }
                while (messageContent.contains("\"")) {
                    messageContent = messageContent.replaceFirst("\"", "");
                }
                String finalMessageContent = messageContent;
                String[] data = SQL.loggingMessageDownLoad(guild.getId(), messageId);
                if (data[0].length() < 2) {
                    return;
                }
                final boolean[] temp = {SQL.loggingMessageUpdate(guild.getId(), messageId, messageContent, authorId)};
                new Thread(() -> {
                    while (!temp[0]) {
                        temp[0] = SQL.loggingMessageUpdate(guild.getId(), messageId, finalMessageContent, authorId);
                        try {
                            System.out.println(temp[0]);
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                Member member = event.getGuild().getMemberById(authorId);
                assert member != null;
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");

                Date time = new Date();

                String time2 = format2.format(time);
                EmbedBuilder builder = EmbedUtils.defaultEmbed()
                        .setTitle("수정된 메세지")
                        .setColor(Color.ORANGE)
                        .setDescription("메세지 수정: " + event.getChannel().getAsMention() + "\n" +
                                "[메세지 이동](" + message.getJumpUrl() + ")")
                        .addField("수정전 내용", data[0], false)
                        .addField("수정후 내용", messageContent, false)
                        .addField("수정 시간", time2, false)
                        .setFooter((member.getEffectiveName() + "(" + member.getNickname() + ")"), member.getUser().getAvatarUrl());
                messageLoggingSend(builder, guild);
            }
        }
    }

    @Override
    public void onMessageBulkDelete(@Nonnull MessageBulkDeleteEvent event) {
        List<String> ids = event.getMessageIds();
        Guild guild = event.getGuild();
        for (String messageId : ids) {
            String[] data = SQL.loggingMessageDownLoad(guild.getId(), messageId);
            if (data[0].length() < 2) {
                return;
            }
        }

    }

    @Override
    public void onGuildMessageDelete(@Nonnull GuildMessageDeleteEvent event) {
        String messageId = event.getMessageId();
        Guild guild = event.getGuild();
        for(String guild1 : config.getTextLoggingEnable()) {
            if (guild.getId().equals(guild1)) {
                String[] data = SQL.loggingMessageDownLoad(guild.getId(), messageId);
                try {
                    if (data[0].length() < 2) {
                        return;
                    }
                } catch (NullPointerException e) {
                    return;
                }
                Member member = event.getGuild().getMemberById(data[1]);
                assert member != null;
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");

                Date time = new Date();

                String time2 = format2.format(time);
                EmbedBuilder builder = EmbedUtils.defaultEmbed()
                        .setTitle("삭제된 메세지")
                        .setColor(Color.RED)
                        .setDescription("메세지 삭제: " + event.getChannel().getAsMention())
                        .addField("내용", data[0], false)
                        .addField("메세지 ID", messageId, false)
                        .addField("삭제 시간", time2, false)
                        .setFooter((member.getEffectiveName() + "(" + member.getNickname() + ")"), member.getUser().getAvatarUrl());
                messageLoggingSend(builder, guild);
            }
        }
    }
    private void messageLoggingSend(EmbedBuilder builder, Guild guild) {
        List<TextChannel> channels = guild.getTextChannelsByName("채팅-로그", false);
        if(!channels.isEmpty()) {
            channels.get(0).sendMessage(builder.build()).queue();
        } else {
            List<TextChannel> channels1 = guild.getTextChannelsByName("Chatting-logs", false);
            if(!channels.isEmpty()) {
                channels1.get(0).sendMessage(builder.build()).queue();
            }
        }

    }

    @Override
    public void onReconnect(@Nonnull ReconnectedEvent event) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            SQL.setLoggingConnection(DriverManager.getConnection(SQL.getUrl(), SQL.getUser(), SQL.getPassword()));
            SQL.setConnection(DriverManager.getConnection(SQL.getUrl(), SQL.getUser(), SQL.getPassword()));
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onGuildJoin(@Nonnull GuildJoinEvent event) {
        String guildId = event.getGuild().getId();
        try {
            Statement statement = SQL.getConnection().createStatement();
            statement.executeQuery("INSERT INTO ritobot_config.color_command_guild VALUES (" + guildId + ", 0)");
            statement.executeQuery("INSERT INTO ritobot_config.filter_guild VALUES (" + guildId + ", 1)");
            statement.executeQuery("INSERT INTO ritobot_config.kill_filter_guild VALUES (" + guildId + ", 1)");
            statement.executeQuery("INSERT INTO ritobot_config.lewdneko_command VALUES (" + guildId + ", 0)");
            statement.executeQuery("INSERT INTO ritobot_config.link_filter_guild VALUES (" + guildId + ", 1)");
            statement.executeQuery("INSERT INTO ritobot_config.logging_enable VALUES (" + guildId + ", 1, 1)");
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Objects.requireNonNull(event.getGuild().getDefaultChannel()).sendMessage("&설정 명령어로 봇 설정 해두시기 바랍니다.\n 설정을 하지 않아 발생한 문제는 제작자가 책임지지 않습니다.").queue();
    }
}
