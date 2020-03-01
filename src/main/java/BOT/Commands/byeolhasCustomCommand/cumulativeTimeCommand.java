package BOT.Commands.byeolhasCustomCommand;

import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class cumulativeTimeCommand implements BOT.Objects.ICommand {
    final Logger logger = LoggerFactory.getLogger(cumulativeTimeCommand.class);
    @Override
    public void handle(List<String> args, @Nonnull GuildMessageReceivedEvent event) {
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
            Member member;
            if(args.isEmpty()) {
                member = event.getMember();
            } else {
                    List<Member> foundMember = FinderUtil.findMembers(args.get(0), event.getGuild());
                    if(foundMember == null) {
                        event.getChannel().sendMessage("'" + args.get(0) + "' 라는 유저는 없습니다.").queue();
                        return;
                    }
                    if(foundMember.isEmpty()) {
                        event.getChannel().sendMessage("'" + args.get(0) + "' 라는 유저는 없습니다.").queue();
                        return;
                    }

                    member = foundMember.get(0);
            }
            if (flag) {
                Connection connection = BOT.Objects.SQL.getConnection();
                Statement statement = connection.createStatement();
                String executeString = "SELECT * FROM byeolhaDB.working_log WHERE userId=" + member.getId() + ";";
                ResultSet resultSet = statement.executeQuery(executeString);
                EmbedBuilder builder = EmbedUtils.defaultEmbed();
                if(resultSet.next()) {
                    String time = resultSet.getString("cumulative_time");
                    time = ((Integer.parseInt(time) / 60) / 60) + "시간" + ((Integer.parseInt(time) / 60) % 60) + "분";
                    builder.setColor(Color.ORANGE)
                            .setTitle("검색 결과")
                            .setDescription("현재 출근중인 경우 해당 시간은 포함되지 않습니다.")
                            .addField("누적 출근 시간", time, true)
                            .addField("검색 대상 유저", member.getAsMention(), true)
                            .setFooter("별하가 당신의 출 퇴근 현황을 지켜보고 있습니다. 퇴근 할때 퇴근을 꼭합시다.");
                }
                statement.close();
                event.getChannel().sendMessage(builder.build()).queue();

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
        return "관리자들이 출근 시간을 검색 할때 쓰는 명령어랍니다.";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "출근시간검색";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "byeolha";
    }
}
