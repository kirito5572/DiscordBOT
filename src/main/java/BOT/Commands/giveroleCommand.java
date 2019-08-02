package BOT.Commands;

import BOT.App;
import BOT.Objects.ICommand;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class giveroleCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
            List<Member> member_list = event.getGuild().getMembers();

            Role for_role = event.getGuild().getRoleById(600006248200405023L);
            for (Member member : member_list) {
                if (member.getRoles().toString().equals("[]")) {
                    event.getGuild().getController().addSingleRoleToMember(member, for_role).queue();
                }
            }
            System.out.println(for_role);
            event.getChannel().sendMessage("현장 직원이 파견되어 왔습니다.").queue();
        } else {
            event.getChannel().sendMessage("당신은 권한이 없습니다.").queue();
        }
    }

    @Override
    public String getHelp() {
        return "역할이 없는 사람에게 역할을 만들어서 줍니다다.(관리자 전용)\n" +
                "사용법: `" + App.getPREFIX() + getInvoke() + "`";
    }

    @Override
    public String getInvoke() {
        return "역할 [유저명] [역할]";
    }

    @Override
    public String getSmallHelp() {
        return "역할 부여";
    }
}
