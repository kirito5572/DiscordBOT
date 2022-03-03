package com.kirito5572.listener.main;

import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import com.kirito5572.objects.main.SQL;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class MuteListener extends ListenerAdapter {
    private final Logger logger = LoggerFactory.getLogger(MuteListener.class);
    @Override
    public void onReady(@NotNull ReadyEvent event) {
        TimerTask job = new TimerTask() {
            @Override
            public void run() {
                String discord_ID;
                String guildId;
                Date date = new Date();
                String time;
                time = String.valueOf(date.getTime());
                time = time.substring(0, time.length() - 4);
                time += "0000";
                try {
                    PreparedStatement preparedStatement = SQL.getConnection().prepareStatement("SELECT * FROM ritobotDB.mute_Data_List WHERE endTime > ? ");
                    preparedStatement.setString(1, time);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if(resultSet.next()) {
                        discord_ID = resultSet.getString("userId");
                        guildId = resultSet.getString("guildId");
                    } else {
                        return;
                    }
                    preparedStatement = SQL.getConnection().prepareStatement("DELETE FROM ritobotDB.mute_Data_List WHERE guildId = ? AND userId = ?");
                    preparedStatement.setString(1, guildId);
                    preparedStatement.setString(2, discord_ID);
                    preparedStatement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                    SQL.reConnection();
                    return;
                }
                Member member;
                User user;
                Guild guild;
                try {
                    guild = event.getJDA().getGuildById(guildId);
                    List<User> foundUsers = FinderUtil.findUsers(discord_ID, event.getJDA());

                    if (foundUsers.isEmpty()) {
                        assert guild != null;
                        List<Member> foundMember = FinderUtil.findMembers(discord_ID, guild);
                        if (foundMember.isEmpty()) {

                            return;
                        }
                        foundUsers = foundMember.stream().map(Member::getUser).collect(Collectors.toList());
                    }
                    user = foundUsers.get(0);
                    assert guild != null;
                    member = guild.getMember(user);

                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                Role role;
                try {
                    PreparedStatement preparedStatement = SQL.getConnection().prepareStatement("SELECT * FROM ritobot_config.mute_role_data WHERE guildId=?");
                    preparedStatement.setString(1, guild.getId());
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if(resultSet.next()) {
                        role = guild.getRoleById(resultSet.getString("roleId"));
                    } else {
                        throw new UnsupportedOperationException("채팅 금지 역할이 생성되어있지 않다고 인식함");
                    }
                } catch (Exception e) {
                    logger.warn(e.getMessage());
                    return;
                }
                if(role == null) {
                    return;
                }
                assert member != null;
                guild.removeRoleFromMember(member, role).complete();
                EmbedBuilder builder = EmbedUtils.getDefaultEmbed()
                        .setTitle("채팅 금지 제재 해제")
                        .addField("유저명", user.getName(), false)
                        .addField("멘션명", member.getAsMention(), false)
                        .setColor(Color.GREEN);
                Objects.requireNonNull(Objects.requireNonNull(event.getJDA().getGuildById(guildId))
                        .getTextChannelById(SQL.configDownLoad(Objects.requireNonNull(event.getJDA().getGuildById(guildId)).getId(),
                                SQL.filterlog)))
                        .sendMessageEmbeds(builder.build()).complete();
            }
        };
        Timer jobScheduler = new Timer();
        jobScheduler.scheduleAtFixedRate(job, 1000, 10000);
    }
}

