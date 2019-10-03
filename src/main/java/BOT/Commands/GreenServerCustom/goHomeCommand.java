package BOT.Commands.GreenServerCustom;

import BOT.Objects.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class goHomeCommand implements ICommand {
    private final Logger logger = LoggerFactory.getLogger(goHomeCommand.class);
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        try {
            boolean flag = false;
            if (event.getGuild().getId().equals("600010501266866186")) {
                if (Objects.requireNonNull(event.getMember()).hasPermission(Permission.MANAGE_ROLES) ||
                        event.getMember().hasPermission(Permission.MANAGE_CHANNEL) ||
                        event.getMember().hasPermission(Permission.ADMINISTRATOR) ||
                        event.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {

                    flag = true;
                } else {
                    if (event.getMember().getRoles().contains(event.getGuild().getRoleById("600012069559074822"))) {
                        flag = true;
                    }
                }
            } else {
                event.getChannel().sendMessage("이 명령어는 이 서버에서 지원하지 않는 명령어입니다.").queue();

                return;
            }
            if (flag) {
                Date date = new Date();
                SimpleDateFormat format1 = new SimpleDateFormat("MM/dd aa hh:mm");
                String time = format1.format(date);
                Role roles = event.getGuild().getRoleById("622325436528984084");
                if(!event.getMember().getRoles().contains(roles)) {
                    event.getChannel().sendMessage("먼저 출근을 하십시오").queue();

                    return;
                }
                Role role = event.getGuild().getRoleById("622325520868311041");
                if(event.getMember().getRoles().contains(role)) {
                    event.getChannel().sendMessage("당신은 이미 퇴근했습니다!").queue();

                    return;
                }
                assert roles != null;
                event.getGuild().removeRoleFromMember(event.getMember(), roles).complete();
                assert role != null;
                event.getGuild().addRoleToMember(event.getMember(), role).complete();
                event.getChannel().sendMessage(event.getMember().getAsMention() + "님, 퇴근이 확인되었습니다.").queue();
                EmbedBuilder builder = EmbedUtils.defaultEmbed()
                        .setTitle("퇴근부")
                        .addField("퇴근 한 사람", event.getAuthor().getAsMention(), false)
                        .addField("퇴근 시간", time, false);
                Objects.requireNonNull(event.getGuild().getTextChannelById("622076950021799977")).sendMessage(builder.build()).queue();
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

    @Override
    public String getHelp() {
        return "관리자들이 퇴근할때 쓰는 명령어랍니다.";
    }

    @Override
    public String getInvoke() {
        return "퇴근";
    }

    @Override
    public String getSmallHelp() {
        return "serverCustom";
    }
}
