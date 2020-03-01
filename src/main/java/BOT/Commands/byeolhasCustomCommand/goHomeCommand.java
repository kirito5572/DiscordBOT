package BOT.Commands.byeolhasCustomCommand;

import BOT.Objects.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class goHomeCommand implements ICommand {
    private final Logger logger = LoggerFactory.getLogger(goHomeCommand.class);
    @Override
    public void handle(List<String> args, @NotNull GuildMessageReceivedEvent event) {
        try {
            boolean flag = false;
            if (event.getGuild().getId().equals(goWorkCommand.serverId)) {
                if (Objects.requireNonNull(event.getMember()).hasPermission(Permission.VIEW_AUDIT_LOGS) ||
                        event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                    flag = true;
                } else {
                    if(event.getMember().getRoles().contains(event.getGuild().getRoleById("675465579229872180"))) {
                        flag = true;
                    }
                }
            } else {
                event.getChannel().sendMessage("이 명령어는 이 서버에서 지원하지 않는 명령어입니다.").queue();

                return;
            }
            Role role = event.getGuild().getRoleById(goWorkCommand.roleId);
            if(!event.getMember().getRoles().contains(role)) {
                event.getChannel().sendMessage("당신은 이미 퇴근한 상태입니다. 출근을 하십시오.").complete().delete().queueAfter(5, TimeUnit.SECONDS);
                return;
            }
            if (flag) {
                Date date_raw = new Date();
                SimpleDateFormat format = new SimpleDateFormat("YYYY년 MM월 dd일 HH시 mm분");
                String date = format.format(date_raw);
                Connection connection = BOT.Objects.SQL.getConnection();
                Statement statement = connection.createStatement();
                String executeString = "SELECT * FROM byeolhaDB.attendance_log WHERE userId=" + event.getAuthor().getId() +";";
                ResultSet resultSet = statement.executeQuery(executeString);
                String startTime;
                if(resultSet.next()) {
                    startTime = resultSet.getString("start");
                } else {
                    return;
                }
                long time = (date_raw.getTime() - Long.parseLong(startTime)) / 1000;
                String hour, min;
                min = String.valueOf((time / 60) % 60);
                hour = String.valueOf((time / 60) / 60);
                executeString = "DELETE FROM byeolhaDB.attendance_log WHERE userId=" + event.getAuthor().getId() +";";
                statement.execute(executeString);
                executeString = "SELECT * FROM byeolhaDB.working_log WHERE userId=" + event.getAuthor().getId() +";";
                resultSet = statement.executeQuery(executeString);
                if(resultSet.next()) {
                    time = time + Long.parseLong(resultSet.getString("cumulative_time"));
                    executeString = "UPDATE byeolhaDB.working_log SET working_log.cumulative_time=" + time + " WHERE userId=" + event.getAuthor().getId() + ";";
                } else {
                    executeString = "INSERT INTO byeolhaDB.working_log VALUES (" + event.getAuthor().getId() + ", " + time + ");";
                }
                statement.execute(executeString);
                statement.close();
                EmbedBuilder builder = EmbedUtils.defaultEmbed()
                        .setColor(Color.RED)
                        .setTitle("퇴근")
                        .setDescription("기록시간: " + hour + "시간" + min + "분")
                        .addField("퇴근 시간", date, true)
                        .addField("퇴근 유저", event.getMember().getAsMention(), true)
                        .setFooter("수고 많으셨습니다.");
                assert role != null;
                event.getMember().getUser().openPrivateChannel().complete().sendMessage("퇴근 완료").queue();
                event.getGuild().removeRoleFromMember(event.getMember(), role).complete();
                Objects.requireNonNull(event.getGuild().getTextChannelById(goWorkCommand.textChannelId)).sendMessage(builder.build()).queue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            StackTraceElement[] eStackTrace = e.getStackTrace();
            StringBuilder a = new StringBuilder();
            for (StackTraceElement stackTraceElement : eStackTrace) {
                a.append(stackTraceElement).append("\n");
            }
            logger.warn(a.toString());
        }
    }

    @NotNull
    @Override
    public String getHelp() {
        return "관리자들이 퇴근할때 쓰는 명령어랍니다.";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "퇴근";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "byeolha";
    }
}
