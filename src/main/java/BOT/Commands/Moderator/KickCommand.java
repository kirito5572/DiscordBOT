package BOT.Commands.Moderator;

import BOT.Constants;
import BOT.Objects.ICommand;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class KickCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        TextChannel channel = event.getChannel();
        Member member = event.getMember();
        Member selfMember = event.getGuild().getSelfMember();
        List<Member> mentionedMembers = event.getMessage().getMentionedMembers();

        if (args.isEmpty() || mentionedMembers.isEmpty()) {
            channel.sendMessage("추방할 유저를 입력해주세요").queue();
            return;
        }

        Member target = mentionedMembers.get(0);
        String reason = String.join(" ", args.subList(1, args.size()));

        if (!member.hasPermission(Permission.KICK_MEMBERS) || !member.canInteract(target)) {
            channel.sendMessage("이 명령어를 사용할 권한이 없습니다.").queue();
            return;
        }


        if (!selfMember.hasPermission(Permission.KICK_MEMBERS) || !selfMember.canInteract(target)) {
            channel.sendMessage("봇이 이 명령어를 사용할 권한이 없습니다.").queue();
            return;
        }


        event.getGuild().getController().kick(target, String.format("킥한사람: %#s, 사유: %s",
                event.getAuthor(), reason)).queue();

        channel.sendMessage("추방 성공!").queue();

    }

    @Override
    public String getHelp() {
        return "너는 이제 필요 없다!\n" +
                "사용법: `"  + Constants.PREFIX + getInvoke() + " <유저> <이유>`";
    }

    @Override
    public String getInvoke() {
        return "꺼져";
    }

    @Override
    public String getSmallHelp() {
        return "서버에서 밖으로 던져버리기";
    }
}