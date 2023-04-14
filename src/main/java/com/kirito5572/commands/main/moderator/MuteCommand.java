package com.kirito5572.commands.main.moderator;

import com.kirito5572.App;
import com.kirito5572.objects.main.EventPackage;
import com.kirito5572.objects.main.ICommand;
import com.kirito5572.objects.main.SQL;
import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MuteCommand implements ICommand {
    private final Logger logger = LoggerFactory.getLogger(MuteCommand.class);
    @Override
    public void handle(@NotNull List<String> args, @NotNull EventPackage event) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd kk:mm");
        String time_st;
        TextChannel channel = event.textChannel();
        if(!(Objects.requireNonNull(event.member()).hasPermission(Permission.MANAGE_ROLES) ||
                event.member().hasPermission(Permission.ADMINISTRATOR))) {
            channel.sendMessage(event.member().getAsMention() + ", 당신은 이 명령어를 사용할 권한이 없습니다.").queue();
            return;
        }
        if(args.isEmpty()) {
            event.getChannel().sendMessage("인수 부족 '" + App.getPREFIX() + "help" +
                    getInvoke() + "'").queue();
            return;
        }
        String users;
        String time;
        try {
            users = args.get(0);
        } catch (Exception e) {
            channel.sendMessage("유저 명이 없습니다.").queue();

            return;
        }
        try {
            time = args.get(1);
        } catch (Exception e) {
            channel.sendMessage("시간이 없습니다.").queue();

            return;
        }

        List<User> foundUsers = FinderUtil.findUsers(users, event.getGuild().getJDA());

        if(foundUsers.isEmpty()) {
            List<Member> foundMember = FinderUtil.findMembers(users, event.getGuild());
            if(foundMember.isEmpty()) {
                event.getChannel().sendMessage("'" + users + "' 라는 유저는 없습니다.").queue();
                return;
            }

            foundUsers = foundMember.stream().map(Member::getUser).collect(Collectors.toList());
        }
        User user = foundUsers.get(0);
        Member member = event.getGuild().getMember(user);

        assert member != null;
        if(member.hasPermission(Permission.MANAGE_ROLES) || member.hasPermission(Permission.MESSAGE_MANAGE) ||
                member.hasPermission(Permission.MANAGE_PERMISSIONS) || member.hasPermission(Permission.MANAGE_SERVER) ||
                member.hasPermission(Permission.ADMINISTRATOR)) {
            channel.sendMessage("이 사람에게는 채팅금지를 먹일 수 없습니다.").queue();

            return;
        }
        Role role;
        try (PreparedStatement preparedStatement = SQL.getConnection().prepareStatement("SELECT * FROM ritobot_config.mute_role_data WHERE guildId=?")){
            preparedStatement.setString(1, event.getGuild().getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                role = event.getGuild().getRoleById(resultSet.getString("roleId"));
            } else {
                throw new UnsupportedOperationException("채팅 금지 역할이 생성되어있지 않다고 인식함");
            }

        } catch (Exception e) {
            role = event.getGuild().createRole()
                    .setName("채팅 금지")
                    .setPermissions(0L)
                    .setMentionable(false)
                    .setHoisted(true).complete();
            try (PreparedStatement preparedStatement = SQL.getConnection().prepareStatement("INSERT INTO ritobot_config.mute_role_data VALUES (?, ?)")){
                preparedStatement.setString(1, event.getGuild().getId());
                preparedStatement.setString(2, role.getId());
            } catch (Exception e1) {
                e.printStackTrace();
            }
        }
        long temp;
        Date date;
        if (time.contains("m")) {
            time = time.substring(0,time.indexOf("m"));
            try {
                temp = Integer.parseInt(time);
            } catch (Exception e) {
                channel.sendMessage("time의 숫자가 잘못 입력되었습니다.").queue();

                return;
            }
            date = new Date();
            date.setTime(date.getTime() + temp * 60000L);
            time = String.valueOf(date.getTime());

        } else if(time.contains("h")) {
            time = time.substring(0,time.indexOf("h"));
            try {
                temp = Integer.parseInt(time);
            } catch (Exception e) {
                channel.sendMessage("time의 숫자가 잘못 입력되었습니다.").queue();

                return;
            }

            date = new Date();
            date.setTime(date.getTime() + temp * 3600000L);
            time = String.valueOf(date.getTime());

        } else if(time.contains("d")) {
            time = time.substring(0,time.indexOf("d"));
            try {
                temp = Integer.parseInt(time);
            } catch (Exception e) {
                channel.sendMessage("time의 숫자가 잘못 입력되었습니다.").queue();

                return;
            }
            date = new Date();
            date.setTime(date.getTime() + temp * 86400000L);
            time = String.valueOf(date.getTime());

        } else if(time.contains("M")) {
            time = time.substring(0,time.indexOf("M"));
            try {
                temp = Integer.parseInt(time);
            } catch (Exception e) {
                channel.sendMessage("time의 숫자가 잘못 입력되었습니다.").queue();

                return;
            }
            date = new Date();
            date.setTime(date.getTime() + temp * 2629800000L);
            time = String.valueOf(date.getTime());
        } else {
            channel.sendMessage("time의 단위가 잘못되었습니다.").queue();

            return;
        }
        EmbedBuilder builder;
        try {
            time_st = sdf.format(date);
            Thread.sleep(1);
            builder = EmbedUtils.getDefaultEmbed()
                    .setTitle("채팅 금지 제재")
                    .addField("유저명", user.getName(), false)
                    .addField("멘션명", member.getAsMention(), false)
                    .addField("제재 해제 시간", time_st, false)
                    .setColor(Color.RED);
            Thread.sleep(1);
        } catch (Exception e) {
            channel.sendMessage("메세지를 보내기 전에 문제가 발생했습니다.").queue();


            StackTraceElement[] eStackTrace = e.getStackTrace();
            StringBuilder a = new StringBuilder();
            for (StackTraceElement stackTraceElement : eStackTrace) {
                a.append(stackTraceElement).append("\n");
            }
            logger.warn(a.toString());

            return;
        }
        if(role != null) {
            time = time.substring(0, time.length() - 4);
            time += "0000";
            try (PreparedStatement preparedStatement = SQL.getConnection().prepareStatement("INSERT INTO ritobotDB.mute_Data_List VALUES (?, ?, ?, ?)")) {
                preparedStatement.setString(1, event.getGuild().getId());
                preparedStatement.setString(2, member.getId());
                preparedStatement.setString(3, sdf.format(new Date()));
                preparedStatement.setString(4, time);
                preparedStatement.execute();
            } catch (Exception e) {
                e.printStackTrace();
                event.getChannel().sendMessage("에러가 발생했습니다.").queue();
                return;
            }

            event.getGuild().addRoleToMember(member, role).queue();
            Objects.requireNonNull(event.getGuild().getTextChannelById(SQL.configDownLoad(event.getGuild().getId(), SQL.filterlog))).sendMessageEmbeds(builder.build()).queue();
            builder = EmbedUtils.getDefaultEmbed()
                    .setTitle("채팅 금지 제재")
                    .addField("멘션명", member.getAsMention(), false);
            event.getChannel().sendMessageEmbeds(builder.build()).queue();
        } else {
            event.getChannel().sendMessage("에러가 발생했습니다.\n" +
                    "발생 위치: MuteCommand / 실처리부").queue();
        }
    }

    @NotNull
    @Override
    public String getHelp() {
        return "채팅 금지를 먹입니다.\n" +
                "사용법: `" + App.getPREFIX() + getInvoke() + "<유저명/@멘션/ID> <시간>`";

    }

    @NotNull
    @Override
    public String getInvoke() {
        return "채금";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "moderator";
    }

}
