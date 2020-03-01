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

import javax.lang.model.element.ElementVisitor;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class goWorkCommand implements ICommand {
    private final Logger logger = LoggerFactory.getLogger(goWorkCommand.class);
    public static String serverId = "576823770329907201";
    public static String roleId = "682592172368855107";
    public static String textChannelId = "678935207310655491";

    @Override
    public void handle(List<String> args, @NotNull GuildMessageReceivedEvent event) {
        try {
            boolean flag = false;
            if (event.getGuild().getId().equals(serverId)) {
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
            System.out.println(flag);
            Role role = event.getGuild().getRoleById(roleId);
            if(event.getMember().getRoles().contains(role)) {
                event.getChannel().sendMessage("당신은 이미 출근한 상태입니다. 퇴근을 하십시오.").complete().delete().queueAfter(5, TimeUnit.SECONDS);
                return;
            }
            if (flag) {
                Date date_raw = new Date();
                SimpleDateFormat format = new SimpleDateFormat("YYYY년 MM월 dd일 HH시 mm분");
                String date = format.format(date_raw);
                Connection connection = BOT.Objects.SQL.getConnection();
                Statement statement = connection.createStatement();
                long renew_time = date_raw.getTime() + 3600000L;
                String executeString = "INSERT INTO byeolhaDB.attendance_log VALUES(" + event.getAuthor().getId() + ", " +
                        date_raw.getTime() + ", " + renew_time + ", 0);";
                System.out.println(executeString);
                statement.execute(executeString);
                statement.close();
                EmbedBuilder builder = EmbedUtils.defaultEmbed()
                        .setColor(Color.GREEN)
                        .setTitle("출근")
                        .addField("출근 시간", date, true)
                        .addField("출근 유저", event.getMember().getAsMention(), true)
                        .setFooter("1시간마다 출근을 검증합니다. 검증에 실패 할경우 자동 퇴근 처리 됩니다.");
                assert role != null;
                event.getMember().getUser().openPrivateChannel().complete().sendMessage("출근 완료").queue();
                event.getGuild().addRoleToMember(event.getMember(), role).complete();
                Objects.requireNonNull(event.getGuild().getTextChannelById(textChannelId)).sendMessage(builder.build()).queue();

            } else {
                event.getChannel().sendMessage("당신은 해당 명령어를 사용할 수 없습니다.").complete().delete().queueAfter(5, TimeUnit.SECONDS);
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

    @NotNull
    @Override
    public String getHelp() {
        return "관리자들이 출근할때 쓰는 명령어랍니다.";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "출근";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "byeolha";
    }
}
