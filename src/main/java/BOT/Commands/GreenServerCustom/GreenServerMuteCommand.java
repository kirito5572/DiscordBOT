package BOT.Commands.GreenServerCustom;

import BOT.App;
import BOT.Objects.ICommand;
import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;
import java.util.stream.Collectors;

public class GreenServerMuteCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        if(event.getGuild().getId().equals("600010501266866186")) {
            if(args.isEmpty()) {
                event.getChannel().sendMessage("인수 부족 '" + App.getPREFIX() + "help" +
                        getInvoke() + "'").queue();
                return;
            }

            String joined = String.join(" ", args);
            List<User> foundUsers = FinderUtil.findUsers(joined, event.getGuild().getJDA());

            if(foundUsers.isEmpty()) {
                List<Member> foundMember = FinderUtil.findMembers(joined, event.getGuild());
                if(foundMember.isEmpty()) {
                    event.getChannel().sendMessage("'" + joined + "' 라는 유저는 없습니다.").queue();
                    return;
                }

                foundUsers = foundMember.stream().map(Member::getUser).collect(Collectors.toList());
            }
            User user = foundUsers.get(0);
            Member member = event.getGuild().getMember(user);
            Role role;
            try {
                role = event.getGuild().getRolesByName("채팅 금지", true).get(0);
            } catch (Exception e) {
                channel.sendMessage("채팅 금지 역할을 먼저 생성해주세요.").queue();

                return;
            }

            event.getGuild().getController().addSingleRoleToMember(member, role).queue();
        } else {
            channel.sendMessage("이 명령어는 이 서버에서 지원하지 않습니다.").complete();
        }
    }

    @Override
    public String getHelp() {
        return "채팅 금지를 먹입니다.";
    }

    @Override
    public String getInvoke() {
        return "채팅금지";
    }

    @Override
    public String getSmallHelp() {
        return "채팅 금지를 먹입니다.";
    }
}
