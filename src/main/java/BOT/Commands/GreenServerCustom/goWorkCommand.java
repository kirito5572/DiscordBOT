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
        boolean flag = false;
        if(event.getGuild().getId().equals("600010501266866186")) {
            if(event.getMember().hasPermission(Permission.MANAGE_ROLES, Permission.MANAGE_CHANNEL, Permission.ADMINISTRATOR, Permission.MESSAGE_MANAGE)) {
                flag = true;
            } else {
                if(event.getMember().getRoles().contains(event.getGuild().getRoleById("600012069559074822"))) {
                    flag = true;
                }
            }
        } else {
            event.getChannel().sendMessage("이 명령어는 이 서버에서 지원하지 않는 명령어입니다.").queue();

            return;
        }
        if(flag) {
            Date date = new Date();
            SimpleDateFormat format1 = new SimpleDateFormat( "MM/dd aa hh:mm");
            String time = format1.format(date);
            event.getChannel().sendMessage(event.getMember().getAsMention() + "님, 출근이 확인되었습니다.").queue();
            EmbedBuilder builder = EmbedUtils.defaultEmbed()
                    .setTitle("출근부")
                    .addField("출근 시간", time, false);
            event.getChannel().sendMessage(builder.build()).queue();
        }
    }

    @Override
    public String getHelp() {
        return null;
    }

    @Override
    public String getInvoke() {
        return null;
    }

    @Override
    public String getSmallHelp() {
        return "serverCustom";
    }
}
