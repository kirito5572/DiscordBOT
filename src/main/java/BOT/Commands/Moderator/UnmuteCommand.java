package BOT.Commands.Moderator;

import BOT.App;
import BOT.Objects.ICommand;
import BOT.Objects.SQL;
import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UnmuteCommand implements ICommand {
    private final Logger logger = LoggerFactory.getLogger(UnmuteCommand.class);
    @Override
    public void handle(List<String> args, @Nonnull GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        if(!(Objects.requireNonNull(event.getMember()).hasPermission(Permission.MANAGE_ROLES) ||
                event.getMember().hasPermission(Permission.ADMINISTRATOR))) {
            channel.sendMessage(event.getMember().getAsMention() + ", 당신은 이 명령어를 사용할 권한이 없습니다.").queue();
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

        Role role;
        try {
            PreparedStatement preparedStatement = SQL.getConnection().prepareStatement("SELECT * FROM ritobot_config.mute_role_data WHERE guildId=?");
            preparedStatement.setString(1, event.getGuild().getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                role = event.getGuild().getRoleById(resultSet.getString("roleId"));
            } else {
                throw new UnsupportedOperationException("채팅 금지 역할이 생성되어있지 않다고 인식함");
            }

        } catch (Exception e) {
            return;
        }
        assert member != null;
        if(!member.getRoles().contains(role)) {
            return;
        }
        EmbedBuilder builder;
        try {
            Thread.sleep(1);
            builder = EmbedUtils.defaultEmbed()
                    .setTitle("채팅 금지 제재 해제")
                    .addField("유저명", user.getName(), false)
                    .addField("멘션명", member.getAsMention(), false)
                    .setColor(Color.RED);
            Thread.sleep(1);
        } catch (Exception e) {
            channel.sendMessage("메세지를 보내기 전에 문제가 발생했습니다.").complete();


            StackTraceElement[] eStackTrace = e.getStackTrace();
            StringBuilder a = new StringBuilder();
            for (StackTraceElement stackTraceElement : eStackTrace) {
                a.append(stackTraceElement).append("\n");
            }
            logger.warn(a.toString());

            return;
        }
        if(role != null) {
            try {
                PreparedStatement preparedStatement = SQL.getConnection().prepareStatement("DELETE FROM ritobotDB.mute_Data_List WHERE guildId = ? AND userId = ?");
                preparedStatement.setString(1, event.getGuild().getId());
                preparedStatement.setString(2, member.getId());
                preparedStatement.execute();
            } catch (Exception e) {
                e.printStackTrace();
                event.getChannel().sendMessage("에러가 발생했습니다.").queue();
                return;
            }

            event.getGuild().removeRoleFromMember(member, role).queue();
            Objects.requireNonNull(event.getGuild().getTextChannelById(SQL.configDownLoad(event.getGuild().getId(), SQL.filterlog))).sendMessage(builder.build()).complete();
            builder = EmbedUtils.defaultEmbed()
                    .setTitle("채팅 금지 제재 해제")
                    .addField("멘션명", member.getAsMention(), false);
            event.getChannel().sendMessage(builder.build()).queue();
        } else {
            event.getChannel().sendMessage("에러가 발생했습니다.\n" +
                    "발생 위치: MuteCommand / 실처리부").queue();
        }
    }

    @Override
    public String getHelp() {
        return "뮤트를 해제합니다";
    }

    @Override
    public String getInvoke() {
        return "채금해제";
    }

    @Override
    public String getSmallHelp() {
        return "moderator";
    }
}
