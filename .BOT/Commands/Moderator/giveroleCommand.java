package BOT.Commands.Moderator;

import com.kirito5572.App;
import com.kirito5572.objects.main.ICommand;
import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class giveroleCommand implements ICommand {
    @NotNull
    private String colorCode = "";
    @NotNull
    private String permission_Int = "";
    @NotNull
    private String pos = "";
    private boolean hoisted = false;

    @Override
    public void handle(@NotNull List<String> args, @NotNull EventPackage event) {
        colorCode = "";
        permission_Int = "";
        pos = "";
        hoisted = false;
        if(Objects.requireNonNull(event.getMember()).hasPermission(Permission.ADMINISTRATOR)) {
            String rolename;
            String username;


            try {
                rolename = args.get(0);
            } catch (Exception e) {
                event.getChannel().sendMessage("역할명을 입력해주세요").queue();
                return;
            }
            try {
                username = args.get(1);
            } catch (Exception e) {
                event.getChannel().sendMessage("유저명을 입력해주세요.").queue();
                return;
            }

            String temp;

            try {
                temp = args.get(2);
                convert(temp);
            } catch (Exception ignored) {

            } try {
                temp = args.get(3);
                convert(temp);
            } catch (Exception ignored) {

            } try {
                temp = args.get(4);
                convert(temp);
            } catch (Exception ignored) {

            } try {
                temp = args.get(5);
                convert(temp);
            } catch (Exception ignored) {

            }

            List<User> foundUsers = FinderUtil.findUsers(username, event.getGuild().getJDA());
            if(foundUsers.isEmpty()) {
                List<Member> foundMember = FinderUtil.findMembers(username, event.getGuild());
                if(foundMember.isEmpty()) {
                    event.getChannel().sendMessage("'" + username + "' 이라는 유저는 없습니다.").queue();
                    return;
            }

                foundUsers = foundMember.stream().map(Member::getUser).collect(Collectors.toList());
            }
            User user = foundUsers.get(0);
            Member member = event.getGuild().getMember(user);
            Role rolea;
            assert member != null;
            if(event.getGuild().getRolesByName(rolename, false).size() > 0) {
                rolea = event.getGuild().getRolesByName(rolename, false).get(0);
                try {
                    event.getGuild().addRoleToMember(member, rolea).complete();
                } catch (HierarchyException e) {
                    e.printStackTrace();
                    event.getChannel().sendMessage("봇의 역할보다 상위에 있는 역할은 지급할 수 없습니다.").queue();
                    return;
                }
                return;
            }
            if(!colorCode.equals("") && !permission_Int.equals("")) {
                rolea = event.getGuild().createRole()
                        .setName(rolename)
                        .setColor(Integer.parseInt(colorCode, 16))
                        .setPermissions(Long.parseLong(permission_Int))
                        .setHoisted(hoisted)
                        .complete();
            } else if(!colorCode.equals("")) {
                rolea = event.getGuild().createRole()
                        .setName(rolename)
                        .setColor(Integer.parseInt(colorCode, 16))
                        .setHoisted(hoisted)
                        .complete();
            } else if(!permission_Int.equals("")) {
                rolea = event.getGuild().createRole()
                    .setName(rolename)
                    .setPermissions(Long.parseLong(permission_Int))
                    .setHoisted(hoisted)
                    .complete();
            } else {
                rolea = event.getGuild().createRole()
                    .setName(rolename)
                    .setHoisted(hoisted)
                    .complete();
            }

            if(!pos.equals("")) {
                event.getGuild().modifyRolePositions().selectPosition(rolea).moveUp(Integer.parseInt(pos)).complete();
            }
            assert member != null;
            event.getGuild().addRoleToMember(member, rolea).complete();

            event.getChannel().sendMessage("[" + rolename + "] 역할이 [" + user.getName() + "]에게 부여되었습니다..").queue();
        } else {
            event.getChannel().sendMessage("당신은 명령어를 사용할 권한이 없습니다.").queue();
        }
    }

    @NotNull
    @Override
    public String getHelp() {
        return "역할이 없는 사람에게 역할을 만들어서 줍니다.(관리자 전용)\n" +
                "사용법: `" + App.getPREFIX() + getInvoke() + "` [역할명] [유저명] \n" +
                "옵션: [#RGB코드] \n예시 `" + App.getPREFIX() + getInvoke() + "[역할명] [유저명] #FF0000`\n" +
                "[^권한 등급(숫자)] \n예시 `" + App.getPREFIX() + getInvoke() + "[역할명] [유저명] ^8`\n" +
                "권한 등급 계산기: `https://discordapi.com/permissions.html` \n" +
                "[$역할 위치] \n예시 `" + App.getPREFIX() + getInvoke() + "[역할명] [유저명] $15`\n" +
                "[역할 분리 표시] \n예시 `" + App.getPREFIX() + getInvoke() + "[역할명] [유저명] true`";
    }
    @NotNull
    @Override
    public String getInvoke() {
        return "역할";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "moderator";
    }

    private void convert(@NotNull String str) {
        if(str.contains("#")) {
            colorCode = str.substring(1);
        } else if(str.contains("^")) {
            permission_Int = str.substring(1);
        } else if(str.contains("$")) {
            pos = str.substring(1);
        } else if(str.contains("true")) {
            hoisted = true;
        } else if(str.contains("false")) {
            hoisted = false;
        }
    }
}
