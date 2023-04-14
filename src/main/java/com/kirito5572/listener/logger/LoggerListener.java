//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.kirito5572.listener.logger;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.StorageClass;
import com.kirito5572.objects.logger.SQL;
import com.kirito5572.objects.logger.config;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import javax.annotation.Nonnull;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.ResumedEvent;
import net.dv8tion.jda.api.events.channel.category.CategoryCreateEvent;
import net.dv8tion.jda.api.events.channel.category.CategoryDeleteEvent;
import net.dv8tion.jda.api.events.channel.category.update.CategoryUpdateNameEvent;
import net.dv8tion.jda.api.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.text.TextChannelDeleteEvent;
import net.dv8tion.jda.api.events.channel.text.update.TextChannelUpdateNSFWEvent;
import net.dv8tion.jda.api.events.channel.text.update.TextChannelUpdateNameEvent;
import net.dv8tion.jda.api.events.channel.text.update.TextChannelUpdateSlowmodeEvent;
import net.dv8tion.jda.api.events.channel.text.update.TextChannelUpdateTopicEvent;
import net.dv8tion.jda.api.events.channel.voice.VoiceChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.voice.VoiceChannelDeleteEvent;
import net.dv8tion.jda.api.events.channel.voice.update.VoiceChannelUpdateBitrateEvent;
import net.dv8tion.jda.api.events.channel.voice.update.VoiceChannelUpdateNameEvent;
import net.dv8tion.jda.api.events.channel.voice.update.VoiceChannelUpdateUserLimitEvent;
import net.dv8tion.jda.api.events.emote.EmoteAddedEvent;
import net.dv8tion.jda.api.events.emote.EmoteRemovedEvent;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildUnbanEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.guild.override.PermissionOverrideCreateEvent;
import net.dv8tion.jda.api.events.guild.override.PermissionOverrideDeleteEvent;
import net.dv8tion.jda.api.events.guild.override.PermissionOverrideUpdateEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceGuildMuteEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.events.role.RoleDeleteEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdateColorEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdateHoistedEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdateMentionableEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdateNameEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdatePermissionsEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.AttachmentOption;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerListener extends ListenerAdapter {
    private final Logger logger = LoggerFactory.getLogger(LoggerListener.class);
    private final SQL sql;

    public LoggerListener(SQL sql) {
        this.sql = sql;
    }

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
                List<Message.Attachment> files = message.getAttachments();
                if(!files.isEmpty()) {
                    int i = 0;
                    for(Message.Attachment attachment : files) {
                        if(attachment.isImage()) {
                            i++;
                            File file = attachment.downloadToFile().join();
                            S3UploadObject(file, messageId + "_" + i);
                            if(!file.delete()) {
                                logger.warn("파일 삭제 실패");
                            }
                        }
                    }
                }
                final boolean[] temp = {sql.loggingMessageUpLoad(guild.getId(), messageId, messageContent, authorId)};
                String finalMessageContent = messageContent;
                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        temp[0] = sql.loggingMessageUpLoad(guild.getId(), messageId, finalMessageContent, authorId);
                        if(temp[0]) {
                            timer.cancel();
                        }
                    }
                };
                if(!temp[0]) {
                    timer.scheduleAtFixedRate(timerTask, 0, 1000);
                }
            }
        }
    }


    public void onGuildMessageUpdate(@Nonnull GuildMessageUpdateEvent event) {
        Message message = event.getMessage();
        final Guild guild = event.getGuild();
        final String messageId = message.getId();
        final String messageContent = message.getContentDisplay();
        String authorId = event.getAuthor().getId();
        String[] var7 = config.getTextLoggingEnable();

        for (String guild1 : var7) {
            if (guild.getId().equals(guild1)) {
                if (message.isWebhookMessage() || message.isTTS()) {
                    return;
                }

                if (event.getAuthor().isBot()) {
                    return;
                }

                String[] data = this.sql.loggingMessageDownLoad(guild.getId(), messageId);
                if (data[0] == null) {
                    return;
                }

                if (data[0].length() < 2) {
                    return;
                }

                final boolean[] temp = new boolean[]{this.sql.loggingMessageUpdate(guild.getId(), messageId, messageContent)};
                final Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    public void run() {
                        temp[0] = LoggerListener.this.sql.loggingMessageUpdate(guild.getId(), messageId, messageContent);
                        if (temp[0]) {
                            timer.cancel();
                        }

                    }
                };
                timer.scheduleAtFixedRate(timerTask, 0L, 1000L);
                Member member = event.getGuild().getMemberById(authorId);

                assert member != null;

                SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
                Date time = new Date();
                String time2 = format2.format(time);
                EmbedBuilder var10000 = EmbedUtils.getDefaultEmbed().setTitle("수정된 메세지").setColor(Color.ORANGE);
                String var10001 = event.getChannel().getAsMention();
                EmbedBuilder builder = var10000.setDescription("메세지 수정: " + var10001 + "\n[메세지 이동](" + message.getJumpUrl() + ")");

                try {
                    builder.addField("수정전 내용", data[0], false);
                } catch (Exception var22) {
                    var22.printStackTrace();
                    builder.addField("수정전 내용", "1024자 이상이라서 표현할 수 없습니다.", false);
                }

                try {
                    builder.addField("수정후 내용", messageContent, false);
                } catch (Exception var21) {
                    var21.printStackTrace();
                    builder.addField("수정후 내용", "1024자 이상이라서 표현할 수 없습니다.", false);
                }

                builder.addField("수정 시간", time2, false).setFooter(member.getEffectiveName() + "(" + member.getEffectiveName() + ")", member.getUser().getAvatarUrl());
                this.messageLoggingSend(builder, guild);
            }
        }

    }

    public void onGuildMessageDelete(@Nonnull GuildMessageDeleteEvent event) {
        String messageId = event.getMessageId();
        Guild guild = event.getGuild();
        String[] var4 = config.getTextLoggingEnable();

        for (String guild1 : var4) {
            if (guild.getId().equals(guild1)) {
                File file = this.S3DownloadObject(messageId + "_1");
                String[] data = this.sql.loggingMessageDownLoad(guild.getId(), messageId);

                try {
                    if (data[0].length() < 2 && file == null) {
                        return;
                    }
                } catch (NullPointerException var15) {
                    return;
                }

                Member member = event.getGuild().getMemberById(data[1]);

                assert member != null;

                SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
                Date time = new Date();
                String time2 = format2.format(time);
                if (file != null && data[0].length() < 2) {
                    data[0] = "사진 파일만 있는 메세지";
                }

                if (data[0].length() > 1024) {
                    data[0] = "1024자 초과로 인한 처리 불가";
                }

                EmbedBuilder builder = EmbedUtils.getDefaultEmbed().setTitle("삭제된 메세지").setColor(Color.RED).setDescription("메세지 삭제: " + event.getChannel().getAsMention()).addField("내용", data[0], false).addField("메세지 ID", messageId, false).addField("삭제 시간", time2, false).setFooter(member.getEffectiveName() + "(" + member.getEffectiveName() + ")", member.getUser().getAvatarUrl());
                this.messageLoggingSend(builder, guild);
                if (file != null) {
                    this.messageLoggingSend(file, guild);
                }

                if (file != null && !file.delete()) {
                    this.logger.warn("파일이 삭제되지 않음");
                }
            }
        }

    }

    private void messageLoggingSend(@NotNull File file, @NotNull Guild guild) {
        String channelId = this.sql.configDownLoad_channel(guild.getId(), 11);
        boolean a = false;
        if (!channelId.equals("error")) {
            try {
                Objects.requireNonNull(guild.getTextChannelById(channelId)).sendFile(file, new AttachmentOption[0]).queue();
                a = true;
            } catch (Exception var7) {
                var7.printStackTrace();
                a = false;
            }
        }

        if (!a) {
            List<TextChannel> channels = guild.getTextChannelsByName("채팅-로그", false);
            if (!channels.isEmpty()) {
                channels.get(0).sendFile(file, new AttachmentOption[0]).queue();
            } else {
                List<TextChannel> channels1 = guild.getTextChannelsByName("chat-logs", false);
                if (!channels.isEmpty()) {
                    channels1.get(0).sendFile(file, new AttachmentOption[0]).queue();
                }
            }
        }

    }

    private void messageLoggingSend(@NotNull EmbedBuilder builder, @NotNull Guild guild) {
        String channelId = this.sql.configDownLoad_channel(guild.getId(), 11);
        boolean a = false;
        if (!channelId.equals("error")) {
            try {
                Objects.requireNonNull(guild.getTextChannelById(channelId)).sendMessageEmbeds(builder.build(), new MessageEmbed[0]).queue();
                a = true;
            } catch (Exception var7) {
                var7.printStackTrace();
                a = false;
            }
        }

        if (!a) {
            List<TextChannel> channels = guild.getTextChannelsByName("채팅-로그", false);
            if (!channels.isEmpty()) {
                channels.get(0).sendMessageEmbeds(builder.build(), new MessageEmbed[0]).queue();
            } else {
                List<TextChannel> channels1 = guild.getTextChannelsByName("chat-logs", false);
                if (!channels.isEmpty()) {
                    channels1.get(0).sendMessageEmbeds(builder.build(), new MessageEmbed[0]).queue();
                }
            }
        }

    }

    private void channelLoggingSend(@NotNull EmbedBuilder builder, @NotNull Guild guild) {
        String channelId = this.sql.configDownLoad_channel(guild.getId(), 11);
        boolean a = false;
        if (!channelId.equals("error")) {
            try {
                Objects.requireNonNull(guild.getTextChannelById(channelId)).sendMessageEmbeds(builder.build(), new MessageEmbed[0]).queue();
                a = true;
            } catch (Exception var7) {
                var7.printStackTrace();
                a = false;
            }
        }

        if (!a) {
            List<TextChannel> channels = guild.getTextChannelsByName("채널-로그", false);
            if (!channels.isEmpty()) {
                channels.get(0).sendMessageEmbeds(builder.build(), new MessageEmbed[0]).queue();
            } else {
                List<TextChannel> channels1 = guild.getTextChannelsByName("channel-logs", false);
                if (!channels.isEmpty()) {
                    channels1.get(0).sendMessageEmbeds(builder.build(), new MessageEmbed[0]).queue();
                }
            }
        }

    }

    private void memberLoggingSend(@NotNull EmbedBuilder builder, @NotNull Guild guild) {
        String channelId = this.sql.configDownLoad_channel(guild.getId(), 11);
        boolean a = false;
        if (!channelId.equals("error")) {
            try {
                Objects.requireNonNull(guild.getTextChannelById(channelId)).sendMessageEmbeds(builder.build(), new MessageEmbed[0]).queue();
                a = true;
            } catch (Exception var7) {
                var7.printStackTrace();
                a = false;
            }
        }

        if (!a) {
            List<TextChannel> channels = guild.getTextChannelsByName("멤버-로그", false);
            if (!channels.isEmpty()) {
                channels.get(0).sendMessageEmbeds(builder.build(), new MessageEmbed[0]).queue();
            } else {
                List<TextChannel> channels1 = guild.getTextChannelsByName("member-logs", false);
                if (!channels.isEmpty()) {
                    channels1.get(0).sendMessageEmbeds(builder.build(), new MessageEmbed[0]).queue();
                }
            }
        }

    }

    public void onResumed(@NotNull ResumedEvent event) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.sql.setLoggingConnection(DriverManager.getConnection(this.sql.getUrl(), this.sql.getUser(), this.sql.getPassword()));
            SQL.reConnection();
        } catch (ClassNotFoundException | SQLException var3) {
            var3.printStackTrace();
        }

    }

    public void onGuildJoin(@Nonnull GuildJoinEvent event) {
        String guildId = event.getGuild().getId();

        try {
            PreparedStatement statement = this.sql.getConnection().prepareStatement("INSERT INTO ritobot_config.color_command_guild VALUES (?, ?)");
            statement.setString(1, guildId);
            statement.setInt(2, 1);
            statement.execute();
            statement.close();
            statement = this.sql.getConnection().prepareStatement("INSERT INTO ritobot_config.filter_guild VALUES (?, ?)");
            statement.setString(1, guildId);
            statement.setInt(2, 0);
            statement.execute();
            statement.close();
            statement = this.sql.getConnection().prepareStatement("INSERT INTO ritobot_config.kill_filter_guild VALUES (?, ?)");
            statement.setString(1, guildId);
            statement.setInt(2, 0);
            statement.execute();
            statement.close();
            statement = this.sql.getConnection().prepareStatement("INSERT INTO ritobot_config.lewdneko_command VALUES (?, ?)");
            statement.setString(1, guildId);
            statement.setInt(2, 0);
            statement.execute();
            statement.close();
            statement = this.sql.getConnection().prepareStatement("INSERT INTO ritobot_config.link_filter_guild VALUES (?, ?)");
            statement.setString(1, guildId);
            statement.setInt(2, 0);
            statement.execute();
            statement.close();
            statement = this.sql.getConnection().prepareStatement("INSERT INTO ritobot_config.logging_enable VALUES (?, ?, ?, ?)");
            statement.setString(1, guildId);
            statement.setInt(2, 1);
            statement.setInt(3, 1);
            statement.setInt(4, 1);
            statement.execute();
            statement.close();
            statement = this.sql.getConnection().prepareStatement("INSERT INTO ritobot_config.notice VALUES (?, ?, ?)");
            statement.setString(1, guildId);
            statement.setInt(2, 0);
            statement.setString(3, "0");
            statement.execute();
            statement.close();
            statement = this.sql.getConnection().prepareStatement("INSERT INTO ritobot_config.filter_output_channel VALUES (?, ?, ?)");
            statement.setString(1, guildId);
            statement.setInt(2, 0);
            statement.setString(3, "1");
            statement.execute();
            statement.close();
            statement = this.sql.getConnection().prepareStatement("INSERT INTO ritobot_config.bot_channel VALUES (?, ?, ?)");
            statement.setString(1, guildId);
            statement.setInt(2, 0);
            statement.setInt(3, 1);
            statement.execute();
            statement.close();
            statement = this.sql.getConnection().prepareStatement("INSERT INTO ritobot_config.custom_Filter VALUES (?, ?, ?)");
            statement.setString(1, guildId);
            statement.setInt(2, 1);
            statement.setString(3, "{\"data\": [\"none\"]}");
            statement.execute();
            statement.close();
            statement = this.sql.getConnection().prepareStatement("INSERT INTO ritobot_config.log_channel VALUES (?, ?, ?, ?)");
            statement.setString(1, guildId);
            statement.setInt(2, 0);
            statement.setInt(3, 0);
            statement.setInt(3, 0);
            statement.execute();
            statement.close();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        Objects.requireNonNull(event.getGuild().getDefaultChannel()).sendMessage("&설정 명령어로 봇 설정 해두시기 바랍니다.\n 설정을 하지 않아 발생한 문제는 제작자가 책임지지 않습니다.").queue();
    }

    public void onTextChannelUpdateName(@Nonnull TextChannelUpdateNameEvent event) {
        Guild guild = event.getGuild();
        String[] var3 = config.getChannelLoggingEnable();
        

        for (String guild1 : var3) {
            if (guild.getId().equals(guild1)) {
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
                Date time = new Date();
                String time2 = format2.format(time);
                EmbedBuilder builder = EmbedUtils.getDefaultEmbed().setTitle("텍스트 채널 이름 변경").setColor(Color.GREEN).addField("이전 이름", event.getOldName(), false).addField("변경된 이름", event.getNewName(), false).addField("변경 시간", time2, false);
                this.channelLoggingSend(builder, guild);
            }
        }

    }

    public void onTextChannelUpdateTopic(@Nonnull TextChannelUpdateTopicEvent event) {
        Guild guild = event.getGuild();
        String[] var3 = config.getChannelLoggingEnable();
        

        for (String guild1 : var3) {
            if (guild.getId().equals(guild1)) {
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
                Date time = new Date();
                String time2 = format2.format(time);
                String topic = event.getOldTopic();
                if (topic == null) {
                    topic = "없음";
                }

                EmbedBuilder builder = EmbedUtils.getDefaultEmbed().setTitle("텍스트 채널 토픽 변경").setColor(Color.GREEN).addField("채널명", event.getChannel().getName(), false).addField("이전 토픽", topic, false).addField("변경된 토픽", event.getNewTopic(), false).addField("변경 시간", time2, false);
                this.channelLoggingSend(builder, guild);
            }
        }

    }

    public void onPermissionOverrideCreate(@Nonnull PermissionOverrideCreateEvent event) {
        Guild guild = event.getGuild();
        String[] var3 = config.getChannelLoggingEnable();
        

        for (String guildId : var3) {
            if (guild.getId().equals(guildId)) {
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
                Date time = new Date();
                String time2 = format2.format(time);
                EmbedBuilder builder = EmbedUtils.getDefaultEmbed();
                StringBuilder stringBuilder = new StringBuilder();

                for (Permission permission : event.getPermissionOverride().getAllowed()) {
                    stringBuilder.append("➕").append(permission.getName()).append("\n");
                }

                switch (event.getChannelType()) {
                    case CATEGORY -> {
                        if (event.getPermissionOverride().isRoleOverride()) {
                            builder.setTitle("카테고리 권한 오버라이딩 생성").setColor(Color.GREEN).addField("카테고리명", event.getCategory().getName(), false).addField("변경된 권한(" + Objects.requireNonNull(event.getPermissionOverride().getRole()).getAsMention() + ")", stringBuilder.toString(), false).addField("변경 시간", time2, false);
                        } else if (event.getPermissionOverride().isMemberOverride()) {
                            builder.setTitle("카테고리 권한 오버라이딩 생성").setColor(Color.GREEN).addField("카테고리명", event.getCategory().getName(), false).addField("변경된 권한(" + Objects.requireNonNull(event.getPermissionOverride().getMember()).getAsMention() + ")", stringBuilder.toString(), false).addField("변경 시간", time2, false);
                        }
                    }
                    case TEXT -> {
                        if (event.getPermissionOverride().isRoleOverride()) {
                            builder.setTitle("텍스트 채널 권한 오버라이딩 생성").setColor(Color.GREEN).addField("채널명", event.getCategory().getName(), false).addField("변경된 권한(" + Objects.requireNonNull(event.getPermissionOverride().getRole()).getAsMention() + ")", stringBuilder.toString(), false).addField("변경 시간", time2, false);
                        } else if (event.getPermissionOverride().isMemberOverride()) {
                            builder.setTitle("텍스트 채널 권한 오버라이딩 생성").setColor(Color.GREEN).addField("채널명", event.getCategory().getName(), false).addField("변경된 권한(" + Objects.requireNonNull(event.getPermissionOverride().getMember()).getAsMention() + ")", stringBuilder.toString(), false).addField("변경 시간", time2, false);
                        }
                    }
                    case VOICE -> {
                        if (event.getPermissionOverride().isRoleOverride()) {
                            builder.setTitle("보이스 채널 권한 오버라이딩 생성").setColor(Color.GREEN).addField("채널명", event.getCategory().getName(), false).addField("변경된 권한(" + Objects.requireNonNull(event.getPermissionOverride().getRole()).getAsMention() + ")", stringBuilder.toString(), false).addField("변경 시간", time2, false);
                        } else if (event.getPermissionOverride().isMemberOverride()) {
                            builder.setTitle("보이스 채널 권한 오버라이딩 생성").setColor(Color.GREEN).addField("채널명", event.getCategory().getName(), false).addField("변경된 권한(" + Objects.requireNonNull(event.getPermissionOverride().getMember()).getAsMention() + ")", stringBuilder.toString(), false).addField("변경 시간", time2, false);
                        }
                    }
                }

                this.channelLoggingSend(builder, guild);
            }
        }

    }

    public void onPermissionOverrideUpdate(@Nonnull PermissionOverrideUpdateEvent event) {
        super.onPermissionOverrideUpdate(event);
    }

    public void onPermissionOverrideDelete(@Nonnull PermissionOverrideDeleteEvent event) {
        Guild guild = event.getGuild();
        String[] var3 = config.getChannelLoggingEnable();
        

        for (String guildId : var3) {
            if (guild.getId().equals(guildId)) {
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
                Date time = new Date();
                String time2 = format2.format(time);
                EmbedBuilder builder = EmbedUtils.getDefaultEmbed();
                switch (event.getChannelType()) {
                    case CATEGORY -> {
                        if (event.getPermissionOverride().isRoleOverride()) {
                            builder.setTitle("카테고리 권한 오버라이딩 삭제").setColor(Color.GREEN).addField("카테고리명", event.getCategory().getName(), false).addField("변경 시간", time2, false);
                        } else if (event.getPermissionOverride().isMemberOverride()) {
                            builder.setTitle("카테고리 권한 오버라이딩 삭제").setColor(Color.GREEN).addField("카테고리명", event.getCategory().getName(), false).addField("변경 시간", time2, false);
                        }
                    }
                    case TEXT -> {
                        if (event.getPermissionOverride().isRoleOverride()) {
                            builder.setTitle("텍스트 채널 권한 오버라이딩 삭제").setColor(Color.GREEN).addField("채널명", event.getCategory().getName(), false).addField("변경 시간", time2, false);
                        } else if (event.getPermissionOverride().isMemberOverride()) {
                            builder.setTitle("텍스트 채널 권한 오버라이딩 삭제").setColor(Color.GREEN).addField("채널명", event.getCategory().getName(), false).addField("변경 시간", time2, false);
                        }
                    }
                    case VOICE -> {
                        if (event.getPermissionOverride().isRoleOverride()) {
                            builder.setTitle("보이스 채널 권한 오버라이딩 삭제").setColor(Color.GREEN).addField("채널명", event.getCategory().getName(), false).addField("변경 시간", time2, false);
                        } else if (event.getPermissionOverride().isMemberOverride()) {
                            builder.setTitle("보이스 채널 권한 오버라이딩 삭제").setColor(Color.GREEN).addField("채널명", event.getCategory().getName(), false).addField("변경 시간", time2, false);
                        }
                    }
                }

                this.channelLoggingSend(builder, guild);
            }
        }

    }

    public void onTextChannelUpdateNSFW(@Nonnull TextChannelUpdateNSFWEvent event) {
        Guild guild = event.getGuild();
        String[] var3 = config.getChannelLoggingEnable();
        

        for (String guild1 : var3) {
            if (guild.getId().equals(guild1)) {
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
                Date time = new Date();
                String time2 = format2.format(time);
                EmbedBuilder builder;
                if (event.getOldNSFW()) {
                    builder = EmbedUtils.getDefaultEmbed().setTitle("후방 주의 채널 해제").setColor(Color.GREEN).addField("채널명", event.getChannel().getAsMention(), false).addField("변경 시간", time2, false);
                } else {
                    builder = EmbedUtils.getDefaultEmbed().setTitle("후방 주의 채널 지정").setColor(Color.RED).addField("채널명", event.getChannel().getAsMention(), false).addField("변경 시간", time2, false);
                }

                this.channelLoggingSend(builder, guild);
            }
        }

    }

    public void onTextChannelUpdateSlowmode(@Nonnull TextChannelUpdateSlowmodeEvent event) {
        Guild guild = event.getGuild();
        String[] var3 = config.getChannelLoggingEnable();
        

        for (String guild1 : var3) {
            if (guild.getId().equals(guild1)) {
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
                Date time = new Date();
                String time2 = format2.format(time);
                EmbedBuilder builder = EmbedUtils.getDefaultEmbed().setTitle("텍스트 채널 슬로우 모드 지정").setColor(Color.YELLOW).addField("채널명", event.getChannel().getAsMention(), false).addField("이전 슬로우 모드 시간", String.valueOf(event.getOldValue()), false).addField("현재 슬로우 모드 시간", String.valueOf(event.getNewSlowmode()), false).addField("변경 시간", time2, false);
                this.channelLoggingSend(builder, guild);
            }
        }

    }

    public void onTextChannelCreate(@Nonnull TextChannelCreateEvent event) {
        Guild guild = event.getGuild();
        String[] var3 = config.getChannelLoggingEnable();
        

        for (String guild1 : var3) {
            if (guild.getId().equals(guild1)) {
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
                Date time = new Date();
                String time2 = format2.format(time);
                EmbedBuilder builder = EmbedUtils.getDefaultEmbed().setTitle("텍스트 채널 생성").setColor(Color.GREEN).addField("채널명", event.getChannel().getAsMention(), false).addField("변경 시간", time2, false);
                this.channelLoggingSend(builder, guild);
            }
        }

    }

    public void onTextChannelDelete(@Nonnull TextChannelDeleteEvent event) {
        Guild guild = event.getGuild();
        String[] var3 = config.getChannelLoggingEnable();
        

        for (String guild1 : var3) {
            if (guild.getId().equals(guild1)) {
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
                Date time = new Date();
                String time2 = format2.format(time);
                EmbedBuilder builder = EmbedUtils.getDefaultEmbed().setTitle("텍스트 채널 삭제").setColor(Color.RED).addField("채널명", event.getChannel().getName(), false).addField("변경 시간", time2, false);
                this.channelLoggingSend(builder, guild);
            }
        }

    }

    public void onVoiceChannelCreate(@Nonnull VoiceChannelCreateEvent event) {
        Guild guild = event.getGuild();
        String[] var3 = config.getChannelLoggingEnable();
        

        for (String guild1 : var3) {
            if (guild.getId().equals(guild1)) {
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
                Date time = new Date();
                String time2 = format2.format(time);
                EmbedBuilder builder = EmbedUtils.getDefaultEmbed().setTitle("보이스 채널 생성").setColor(Color.GREEN).addField("채널명", event.getChannel().getName(), false).addField("변경 시간", time2, false);
                this.channelLoggingSend(builder, guild);
            }
        }

    }

    public void onVoiceChannelDelete(@Nonnull VoiceChannelDeleteEvent event) {
        Guild guild = event.getGuild();
        String[] var3 = config.getChannelLoggingEnable();
        

        for (String guild1 : var3) {
            if (guild.getId().equals(guild1)) {
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
                Date time = new Date();
                String time2 = format2.format(time);
                EmbedBuilder builder = EmbedUtils.getDefaultEmbed().setTitle("보이스 채널 삭제").setColor(Color.RED).addField("채널명", event.getChannel().getName(), false).addField("변경 시간", time2, false);
                this.channelLoggingSend(builder, guild);
            }
        }

    }

    public void onVoiceChannelUpdateName(@Nonnull VoiceChannelUpdateNameEvent event) {
        Guild guild = event.getGuild();
        String[] var3 = config.getChannelLoggingEnable();
        

        for (String guild1 : var3) {
            if (guild.getId().equals(guild1)) {
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
                Date time = new Date();
                String time2 = format2.format(time);
                EmbedBuilder builder = EmbedUtils.getDefaultEmbed().setTitle("보이스 채널 이름 변경").setColor(Color.YELLOW).addField("이전 이름", event.getOldName(), false).addField("변경된 이름", event.getNewName(), false).addField("변경 시간", time2, false);
                this.channelLoggingSend(builder, guild);
            }
        }

    }

    public void onVoiceChannelUpdateUserLimit(@Nonnull VoiceChannelUpdateUserLimitEvent event) {
        Guild guild = event.getGuild();
        String[] var3 = config.getChannelLoggingEnable();
        

        for (String guild1 : var3) {
            if (guild.getId().equals(guild1)) {
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
                Date time = new Date();
                String time2 = format2.format(time);
                EmbedBuilder builder = EmbedUtils.getDefaultEmbed().setTitle("보이스 채널 유저 제한 수 변경").setColor(Color.YELLOW).addField("채널명", event.getChannel().getName(), false).addField("이전 제한 수", String.valueOf(event.getOldUserLimit()), false).addField("변경 제한 수", String.valueOf(event.getNewUserLimit()), false).addField("변경 시간", time2, false);
                this.channelLoggingSend(builder, guild);
            }
        }

    }

    public void onVoiceChannelUpdateBitrate(@Nonnull VoiceChannelUpdateBitrateEvent event) {
        Guild guild = event.getGuild();
        String[] var3 = config.getChannelLoggingEnable();
        

        for (String guild1 : var3) {
            if (guild.getId().equals(guild1)) {
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
                Date time = new Date();
                String time2 = format2.format(time);
                EmbedBuilder builder = EmbedUtils.getDefaultEmbed().setTitle("보이스 채널 비트레이트 변경").setColor(Color.YELLOW).addField("채널명", event.getChannel().getName(), false).addField("이전 비트레이트", String.valueOf(event.getOldBitrate()), false).addField("변경 비트레이트", String.valueOf(event.getNewBitrate()), false).addField("변경 시간", time2, false);
                this.channelLoggingSend(builder, guild);
            }
        }

    }

    public void onCategoryDelete(@Nonnull CategoryDeleteEvent event) {
        Guild guild = event.getGuild();
        String[] var3 = config.getChannelLoggingEnable();

        for (String guild1 : var3) {
            if (guild.getId().equals(guild1)) {
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
                Date time = new Date();
                String time2 = format2.format(time);
                EmbedBuilder builder = EmbedUtils.getDefaultEmbed().setTitle("카테고리 삭제").setColor(Color.RED).addField("카테고리명", event.getCategory().getName(), false).addField("변경 시간", time2, false);
                this.channelLoggingSend(builder, guild);
            }
        }

    }

    public void onCategoryUpdateName(@Nonnull CategoryUpdateNameEvent event) {
        Guild guild = event.getGuild();
        String[] var3 = config.getChannelLoggingEnable();
        

        for (String guild1 : var3) {
            if (guild.getId().equals(guild1)) {
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
                Date time = new Date();
                String time2 = format2.format(time);
                EmbedBuilder builder = EmbedUtils.getDefaultEmbed().setTitle("카테고리 이름 변경").setColor(Color.ORANGE).addField("이전 이름", event.getOldName(), false).addField("변경된 이름", event.getNewName(), false).addField("변경 시간", time2, false);
                this.channelLoggingSend(builder, guild);
            }
        }

    }

    public void onCategoryCreate(@Nonnull CategoryCreateEvent event) {
        Guild guild = event.getGuild();
        String[] var3 = config.getChannelLoggingEnable();

        for (String guild1 : var3) {
            if (guild.getId().equals(guild1)) {
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
                Date time = new Date();
                String time2 = format2.format(time);
                EmbedBuilder builder = EmbedUtils.getDefaultEmbed().setTitle("카테고리 생성").setColor(Color.GREEN).addField("카테고리명", event.getCategory().getName(), false).addField("변경 시간", time2, false);
                this.channelLoggingSend(builder, guild);
            }
        }

    }

    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        Guild guild = event.getGuild();
        String[] var3 = config.getMemberLoggingEnable();
        

        for (String guild1 : var3) {
            if (guild.getId().equals(guild1)) {
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
                Date time = new Date();
                String time2 = format2.format(time);
                EmbedBuilder builder = EmbedUtils.getDefaultEmbed().setTitle("유저 입장").setDescription(event.getMember().getAsMention() + "유저가 서버에 들어왔습니다.").setColor(Color.GREEN).addField("유저명", event.getMember().getEffectiveName() + "(" + event.getMember().getUser().getAsTag() + ") ", false).addField("유저 가입일", event.getMember().getTimeCreated().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.systemDefault())), false).addField("입장 시간", time2, false);
                this.memberLoggingSend(builder, guild);
            }
        }

    }

    public void onGuildMemberRemove(@Nonnull GuildMemberRemoveEvent event) {
        Guild guild = event.getGuild();
        String[] var3 = config.getMemberLoggingEnable();
        

        for (String guild1 : var3) {
            if (guild.getId().equals(guild1)) {
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
                Date time = new Date();
                StringBuilder stringBuilder = new StringBuilder();

                for (Role role : Objects.requireNonNull(event.getMember()).getRoles()) {
                    stringBuilder.append(role.getName()).append("\n");
                }

                String time2 = format2.format(time);
                EmbedBuilder builder = EmbedUtils.getDefaultEmbed().setTitle("유저 퇴장").setDescription(event.getMember().getAsMention() + "유저가 서버에서 나갔습니다.").setColor(Color.RED).addField("유저명", event.getMember().getEffectiveName() + "(" + event.getMember().getUser().getAsTag() + ") ", false).addField("유저 가입일", event.getMember().getTimeCreated().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.systemDefault())), false).addField("유저 서버 입장일", event.getMember().getTimeJoined().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.systemDefault())), false).addField("역할", stringBuilder.toString(), false).addField("퇴장 시간", time2, false);
                this.memberLoggingSend(builder, guild);
            }
        }

    }

    public void onGuildMemberRoleAdd(@Nonnull GuildMemberRoleAddEvent event) {
        Guild guild = event.getGuild();
        String[] var3 = config.getMemberLoggingEnable();
        

        for (String guild1 : var3) {
            if (guild.getId().equals(guild1)) {
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
                Date time = new Date();
                StringBuilder stringBuilder = new StringBuilder();

                for (Role role : event.getRoles()) {
                    stringBuilder.append(role.getAsMention()).append("\n");
                }

                String time2 = format2.format(time);
                EmbedBuilder builder = EmbedUtils.getDefaultEmbed().setTitle("유저 역할 추가").setDescription("대상유저:" + event.getMember().getAsMention()).setColor(Color.GREEN).addField("유저명", event.getMember().getEffectiveName() + "(" + event.getMember().getUser().getAsTag() + ") ", false).addField("추가된 역할", stringBuilder.toString(), false).addField("시간", time2, false);
                this.memberLoggingSend(builder, guild);
            }
        }

    }

    public void onGuildMemberRoleRemove(@Nonnull GuildMemberRoleRemoveEvent event) {
        Guild guild = event.getGuild();
        String[] var3 = config.getMemberLoggingEnable();
        

        for (String guild1 : var3) {
            if (guild.getId().equals(guild1)) {
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
                Date time = new Date();
                StringBuilder stringBuilder = new StringBuilder();

                for (Role role : event.getRoles()) {
                    stringBuilder.append(role.getAsMention()).append("\n");
                }

                String time2 = format2.format(time);
                EmbedBuilder builder = EmbedUtils.getDefaultEmbed().setTitle("유저 역할 삭제").setDescription("대상유저:" + event.getMember().getAsMention()).setColor(Color.RED).addField("유저명", event.getMember().getEffectiveName() + "(" + event.getMember().getUser().getAsTag() + ") ", false).addField("삭제된 역할", stringBuilder.toString(), false).addField("시간", time2, false);
                this.memberLoggingSend(builder, guild);
            }
        }

    }

    public void onGuildMemberUpdateNickname(@Nonnull GuildMemberUpdateNicknameEvent event) {
        Guild guild = event.getGuild();
        String[] var3 = config.getMemberLoggingEnable();
        

        for (String guild1 : var3) {
            if (guild.getId().equals(guild1)) {
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
                Date time = new Date();
                String time2 = format2.format(time);
                String nickname = event.getOldNickname();
                if (nickname == null) {
                    nickname = "없음";
                }

                EmbedBuilder builder = EmbedUtils.getDefaultEmbed().setTitle("유저 닉네임 변경").setDescription("대상유저:" + event.getMember().getAsMention()).setColor(Color.GREEN).addField("이전 이름", nickname, false).addField("현재 이름", event.getNewNickname(), false).addField("시간", time2, false);
                this.memberLoggingSend(builder, guild);
            }
        }

    }

    public void onGuildBan(@Nonnull GuildBanEvent event) {
        Guild guild = event.getGuild();
        String[] var3 = config.getMemberLoggingEnable();
        

        for (String guild1 : var3) {
            if (guild.getId().equals(guild1)) {
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
                Date time = new Date();
                String time2 = format2.format(time);
                EmbedBuilder builder = EmbedUtils.getDefaultEmbed().setTitle("유저 밴").setDescription("대상유저:" + event.getUser().getAsTag()).setColor(Color.RED).addField("시간", time2, false);
                this.memberLoggingSend(builder, guild);
            }
        }

    }

    public void onGuildUnban(@Nonnull GuildUnbanEvent event) {
        Guild guild = event.getGuild();
        String[] var3 = config.getMemberLoggingEnable();
        

        for (String guild1 : var3) {
            if (guild.getId().equals(guild1)) {
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
                Date time = new Date();
                String time2 = format2.format(time);
                EmbedBuilder builder = EmbedUtils.getDefaultEmbed().setTitle("유저 밴 헤제").setDescription("대상유저:" + event.getUser().getAsTag()).setColor(Color.GREEN).addField("시간", time2, false);
                this.memberLoggingSend(builder, guild);
            }
        }

    }

    public void onGuildVoiceGuildMute(@Nonnull GuildVoiceGuildMuteEvent event) {
        Guild guild = event.getGuild();
        String[] var3 = config.getMemberLoggingEnable();
        

        for (String guild1 : var3) {
            if (guild.getId().equals(guild1)) {
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
                Date time = new Date();
                String time2 = format2.format(time);
                EmbedBuilder builder = EmbedUtils.getDefaultEmbed();
                EmbedBuilder var10000;
                String var10001;
                if (event.isGuildMuted()) {
                    var10000 = builder.setTitle("유저 강제 뮤트");
                    var10001 = event.getMember().getEffectiveName();
                    var10000.setDescription("대상유저:" + var10001 + "(" + event.getMember().getAsMention() + ")").setColor(Color.RED).addField("시간", time2, false);
                } else {
                    var10000 = builder.setTitle("유저 강제 뮤트 해제");
                    var10001 = event.getMember().getEffectiveName();
                    var10000.setDescription("대상유저:" + var10001 + "(" + event.getMember().getAsMention() + ")").setColor(Color.RED).addField("시간", time2, false);
                }

                this.memberLoggingSend(builder, guild);
            }
        }

    }

    public void onRoleCreate(@Nonnull RoleCreateEvent event) {
        Guild guild = event.getGuild();
        String[] var3 = config.getMemberLoggingEnable();
        

        for (String guild1 : var3) {
            if (guild.getId().equals(guild1)) {
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
                Date time = new Date();
                Role role = event.getRole();
                String time2 = format2.format(time);
                StringBuilder stringBuilder = new StringBuilder();

                for (Permission permission : role.getPermissions()) {
                    stringBuilder.append(permission.getName()).append("\n");
                }

                EmbedBuilder var10000 = EmbedUtils.getDefaultEmbed().setTitle("역할 생성").setColor(role.getColor());
                String var10002 = role.getName();
                EmbedBuilder builder = var10000.addField("역할명", var10002 + "(" + role.getAsMention() + ")", false).addField("권한", stringBuilder.toString(), false).addField("멘션 가능", role.isMentionable() ? "예" : "아니오", true).addField("유저 분리 표시", role.isHoisted() ? "예" : "아니오", true).addField("시간", time2, false);
                this.memberLoggingSend(builder, guild);
            }
        }

    }

    public void onRoleDelete(@Nonnull RoleDeleteEvent event) {
        Guild guild = event.getGuild();
        String[] var3 = config.getMemberLoggingEnable();
        

        for (String guild1 : var3) {
            if (guild.getId().equals(guild1)) {
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
                Date time = new Date();
                Role role = event.getRole();
                String time2 = format2.format(time);
                StringBuilder stringBuilder = new StringBuilder();

                for (Permission permission : role.getPermissions()) {
                    stringBuilder.append(permission.getName()).append("\n");
                }

                EmbedBuilder builder = EmbedUtils.getDefaultEmbed().setTitle("역할 삭제").setColor(role.getColor()).addField("역할명", role.getName(), false).addField("권한", stringBuilder.toString(), false).addField("멘션 가능", role.isMentionable() ? "예" : "아니오", true).addField("유저 분리 표시", role.isHoisted() ? "예" : "아니오", true).addField("시간", time2, false);
                this.memberLoggingSend(builder, guild);
            }
        }

    }

    private void onRoleUpdate(@Nonnull Role role, @Nonnull Guild guild) {
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
        Date time = new Date();
        String time2 = format2.format(time);
        StringBuilder stringBuilder = new StringBuilder();

        for (Permission permission : role.getPermissions()) {
            stringBuilder.append(permission.getName()).append("\n");
        }

        EmbedBuilder var10000 = EmbedUtils.getDefaultEmbed().setTitle("역할 변경").setColor(role.getColor());
        String var10002 = role.getName();
        EmbedBuilder builder = var10000.addField("역할명", var10002 + "(" + role.getAsMention() + ")", false).addField("권한", stringBuilder.toString(), false).addField("멘션 가능", role.isMentionable() ? "예" : "아니오", true).addField("유저 분리 표시", role.isHoisted() ? "예" : "아니오", true).addField("시간", time2, false);
        this.memberLoggingSend(builder, guild);
    }

    public void onRoleUpdateColor(@Nonnull RoleUpdateColorEvent event) {
        Guild guild = event.getGuild();
        String[] var3 = config.getMemberLoggingEnable();
        

        for (String guild1 : var3) {
            if (guild.getId().equals(guild1)) {
                this.onRoleUpdate(event.getRole(), guild);
            }
        }

    }

    public void onRoleUpdateHoisted(@Nonnull RoleUpdateHoistedEvent event) {
        Guild guild = event.getGuild();
        String[] var3 = config.getMemberLoggingEnable();
        

        for (String guild1 : var3) {
            if (guild.getId().equals(guild1)) {
                this.onRoleUpdate(event.getRole(), guild);
            }
        }

    }

    public void onRoleUpdateMentionable(@Nonnull RoleUpdateMentionableEvent event) {
        Guild guild = event.getGuild();
        String[] var3 = config.getMemberLoggingEnable();
        

        for (String guild1 : var3) {
            if (guild.getId().equals(guild1)) {
                this.onRoleUpdate(event.getRole(), guild);
            }
        }

    }

    public void onRoleUpdateName(@Nonnull RoleUpdateNameEvent event) {
        Guild guild = event.getGuild();
        String[] var3 = config.getMemberLoggingEnable();
        

        for (String guild1 : var3) {
            if (guild.getId().equals(guild1)) {
                this.onRoleUpdate(event.getRole(), guild);
            }
        }

    }

    public void onRoleUpdatePermissions(@Nonnull RoleUpdatePermissionsEvent event) {
        Guild guild = event.getGuild();
        String[] var3 = config.getMemberLoggingEnable();
        

        for (String guild1 : var3) {
            if (guild.getId().equals(guild1)) {
                this.onRoleUpdate(event.getRole(), guild);
            }
        }

    }

    public void onEmoteAdded(@Nonnull EmoteAddedEvent event) {
        Guild guild = event.getGuild();
        Emote emote = event.getEmote();
        String[] var4 = config.getChannelLoggingEnable();

        for (String guild1 : var4) {
            if (guild.getId().equals(guild1)) {
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
                Date time = new Date();
                String time2 = format2.format(time);
                EmbedBuilder builder = EmbedUtils.getDefaultEmbed().setTitle("서버 이모지 추가").setColor(Color.GREEN).addField("이모지명", emote.getName(), false).setDescription("[이모지 보기](" + emote.getImageUrl() + ")").addField("변경 시간", time2, false);
                this.channelLoggingSend(builder, guild);
            }
        }

    }

    public void onEmoteRemoved(@Nonnull EmoteRemovedEvent event) {
        Guild guild = event.getGuild();
        Emote emote = event.getEmote();
        String[] var4 = config.getChannelLoggingEnable();

        for (String guild1 : var4) {
            if (guild.getId().equals(guild1)) {
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
                Date time = new Date();
                String time2 = format2.format(time);
                EmbedBuilder builder = EmbedUtils.getDefaultEmbed().setTitle("서버 이모지 제거").setColor(Color.GREEN).addField("이모지명", emote.getName(), false).setDescription("[이모지 보기](" + emote.getImageUrl() + ")").addField("변경 시간", time2, false);
                this.channelLoggingSend(builder, guild);
            }
        }

    }

    private void S3UploadObject(File file, String messageId) {
        Regions clientRegion = Regions.AP_NORTHEAST_2;
        String bucketName = "ritobot-logger";

        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(clientRegion).withCredentials(new EnvironmentVariableCredentialsProvider()).build();
            PutObjectRequest request = new PutObjectRequest(bucketName, messageId, file);
            ObjectMetadata metadata = new ObjectMetadata();
            request.setMetadata(metadata);
            request.setStorageClass(StorageClass.StandardInfrequentAccess);
            s3Client.putObject(request);
        } catch (SdkClientException var8) {
            var8.printStackTrace();
        }

    }

    private File S3DownloadObject(String messageId) {
        Regions clientRegion = Regions.AP_NORTHEAST_2;
        String bucketName = "ritobot-logger";

        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(clientRegion).withCredentials(new EnvironmentVariableCredentialsProvider()).build();
            GetObjectRequest request = new GetObjectRequest(bucketName, messageId);
            S3Object object = s3Client.getObject(request);
            ObjectMetadata metadata = object.getObjectMetadata();
            InputStream inputStream = object.getObjectContent();
            Path path = Files.createTempFile(messageId, "." + metadata.getContentType().split("/")[1]);

            try {
                FileOutputStream out = new FileOutputStream(path.toFile());

                try {
                    byte[] buffer = new byte[1024];

                    int len;
                    while((len = inputStream.read(buffer)) != -1) {
                        out.write(buffer, 0, len);
                    }
                } catch (Throwable var14) {
                    try {
                        out.close();
                    } catch (Throwable var13) {
                        var14.addSuppressed(var13);
                    }

                    throw var14;
                }

                out.close();
            } catch (Exception var15) {
                return null;
            }

            return path.toFile();
        } catch (IOException | SdkClientException var16) {
            return null;
        }
    }
}
