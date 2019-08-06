package BOT.Commands.Moderator;

import BOT.Constants;
import BOT.Objects.ICommand;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class BanCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        TextChannel channel = event.getChannel();
        Member member = event.getMember();
        Member selfMember = event.getGuild().getSelfMember();
        List<Member> mentionedMembers = event.getMessage().getMentionedMembers();

        if (mentionedMembers.isEmpty() || args.size() < 2) {
            channel.sendMessage("<유저명이 없습니다>").queue();
            return;
        }

        Member target = mentionedMembers.get(0);
        String reason = String.join(" ", args.subList(1, args.size()));

        if (!member.hasPermission(Permission.BAN_MEMBERS) || !member.canInteract(target)) {
            channel.sendMessage("이 명령어를 사용하기 위한 권한이 없습니다.").queue();
            return;
        }

        if (!selfMember.hasPermission(Permission.BAN_MEMBERS) || !selfMember.canInteract(target)) {
            channel.sendMessage("봇이 이 명령어를 사용하기 위한 권한이 없습니다.").queue();
            return;
        }

        event.getGuild().getController().ban(target, 1)
                .reason(String.format("밴 한 사람: %#s, 이유: %s", event.getAuthor(), reason)).queue();

        channel.sendMessage("필요 없는 사람 없애기 성공!").queue();
    }

    @Override
    public String getHelp() {
        return "이서버에서 타노스 시키기.\n" +
                "사용법: `"  + Constants.PREFIX + getInvoke() + " <유저명> <이유>`";
    }

    @Override
    public String getInvoke() {
        return "밴";
    }

    @Override
    public String getSmallHelp() {
        return "넌 이제 필요없다!";
    }
}
