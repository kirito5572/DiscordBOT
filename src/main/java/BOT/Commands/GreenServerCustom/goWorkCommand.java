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

public class goWorkCommand implements ICommand {
    private final Logger logger = LoggerFactory.getLogger(goWorkCommand.class);
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
                Role role = event.getGuild().getRoleById("622325436528984084");
                Role roles = event.getGuild().getRoleById("622325520868311041");
                if(event.getMember().getRoles().contains(role)) {
                    event.getChannel().sendMessage("당신은 이미 출근했습니다!").queue();

                    return;
                }
                assert role != null;
                event.getGuild().addRoleToMember(event.getMember(), role).complete();
                assert roles != null;
                event.getGuild().removeRoleFromMember(event.getMember(), roles).complete();
                event.getChannel().sendMessage(event.getMember().getAsMention() + "님, 출근이 확인되었습니다.").queue();
                EmbedBuilder builder = EmbedUtils.defaultEmbed()
                        .setTitle("출근부")
                        .addField("출근 한 사람", event.getAuthor().getAsMention(), false)
                        .addField("출근 시간", time, false);
                Objects.requireNonNull(event.getGuild().getTextChannelById("622076950021799977")).sendMessage(builder.build()).queue();

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

    @Override
    public String getHelp() {
        return "관리자들이 출근할때 쓰는 명령어랍니다.";
    }

    @Override
    public String getInvoke() {
        return "출근";
    }

    @Override
    public String getSmallHelp() {
        return "serverCustom";
    }
}
