package BOT.Commands;

import BOT.App;
import BOT.Objects.ICommand;
import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class UnusedColorCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        if(event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
            List<Member> members = event.getGuild().getMembers();
            channel.sendMessage(event.getMember().getAsMention() + ", 안 쓰는 색 역할 삭제를 시작합니다.").queue();

            boolean delete_flag = true;
            List<Role> foundRole = FinderUtil.findRoles("#",event.getGuild());
            for (Role value : foundRole) {
                boolean role_delete = true;
                for (Member member : members) {
                    if (member.getRoles().contains(value)) {
                        role_delete = false;
                    }
                }
                if (role_delete) {
                    delete_flag = false;
                    channel.sendMessage(value.getAsMention() + " 역할이 삭제되었습니다.").complete();
                    value.delete().complete();
                    System.out.println(value.getAsMention());
                }
            }
            if(delete_flag) {
                channel.sendMessage(event.getMember().getAsMention() + ", 삭제 할 역할이 없습니다.").queue();
                return;
            }
            channel.sendMessage(event.getMember().getAsMention() + " 색 역할 삭제가 완료되었습니다.").queue();
        } else {
            channel.sendMessage("권한이 없습니다.").queue();
        }
    }

    @Override
    public String getHelp() {
        return "사용하지 않는 색 역할을 정리합니다." +
                "사용법: `" + App.getPREFIX() + getInvoke() + "`";
    }

    @Override
    public String getInvoke() {
        return "색정리";
    }

    @Override
    public String getSmallHelp() {
        return "other";
    }
}
