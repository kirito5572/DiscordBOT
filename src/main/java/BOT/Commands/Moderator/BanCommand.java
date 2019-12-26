package BOT.Commands.Moderator;

import BOT.App;
import BOT.Objects.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BanCommand implements ICommand {
    @Override
    public void handle(@NotNull List<String> args, @NotNull GuildMessageReceivedEvent event) {

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

        assert member != null;
        if (!member.hasPermission(Permission.BAN_MEMBERS) || !member.canInteract(target)) {
            channel.sendMessage("이 명령어를 사용하기 위한 권한이 없습니다.").queue();
            return;
        }

        if (!selfMember.hasPermission(Permission.BAN_MEMBERS) || !selfMember.canInteract(target)) {
            channel.sendMessage("봇이 이 명령어를 사용하기 위한 권한이 없습니다.").queue();
            return;
        }

        event.getGuild().ban(target, 1)
                .reason(String.format("밴 한 사람: %#s, 이유: %s", event.getAuthor(), reason)).queue();

        channel.sendMessage("필요 없는 사람 없애기 성공!").queue();
    }

    @NotNull
    @Override
    public String getHelp() {
        return "이서버에서 타노스 시키기.\n" +
                "사용법: `"  + App.getPREFIX() + getInvoke() + " <유저명> <이유>`";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "moderator";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "넌 이제 필요없다!";
    }
}
