package BOT.Commands;

import BOT.App;
import BOT.Objects.ICommand;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class UnusedColorCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        if(event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
            Role role;
            List<Member> members = event.getGuild().getMembers();
            List<Role> roleList = event.getGuild().getRoles();
            StringBuilder b;
            //TODO 코드 최적화
            channel.sendMessage(event.getMember().getAsMention() + ", 안 쓰는 색 역할 삭제중입니다....").queue();

            for (int i = 0; i < 16777215; i++) {
                b = new StringBuilder(Integer.toHexString(i));
                for (; b.length() <= 5; ) {
                    b.insert(0, "0");
                }
                try {
                    role = event.getGuild().getRolesByName("#" + b.toString(), true).get(0);
                    if (roleList.contains(role)) {
                        boolean role_delete = true;
                        for (Member member : members) {
                            if (member.getRoles().contains(role)) {
                                role_delete = false;
                            }
                        }
                        if (role_delete) {
                            role.delete().queue();
                            channel.sendMessage("#" + b.toString() + " 역할이 삭제되었습니다.").queue();
                        }
                    }
                } catch (Exception ignored) {
                }
            }
        } else {
            channel.sendMessage("").queue();
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
        return "색 역할 정리";
    }
}
