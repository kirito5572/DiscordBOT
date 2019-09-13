package BOT.Commands.GreenServerCustom;

import BOT.Objects.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class goWorkCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        try {
            boolean flag = false;
            if (event.getGuild().getId().equals("600010501266866186")) {
                if (event.getMember().hasPermission(Permission.MANAGE_ROLES) ||
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
                event.getChannel().sendMessage(event.getMember().getAsMention() + "님, 출근이 확인되었습니다.").queue();
                EmbedBuilder builder = EmbedUtils.defaultEmbed()
                        .setTitle("출근부")
                        .addField("출근 한 사람", event.getAuthor().getAsMention(), false)
                        .addField("출근 시간", time, false);
                event.getGuild().getTextChannelById("622076950021799977").sendMessage(builder.build()).queue();

            }
        } catch (Exception e) {
            e.printStackTrace();
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
