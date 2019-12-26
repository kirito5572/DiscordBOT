package BOT.Commands.Moderator;

import BOT.Constants;
import BOT.Objects.ICommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class KickCommand implements ICommand {
    @Override
    public void handle(@NotNull List<String> args, @NotNull GuildMessageReceivedEvent event) {

        TextChannel channel = event.getChannel();
        List<Member> mentionedMembers = event.getMessage().getMentionedMembers();


        if (args.isEmpty() || mentionedMembers.isEmpty()) {
            channel.sendMessage("추방할 유저를 입력해주세요").queue();
            return;
        }

        Member target = mentionedMembers.get(0);
        String reason = String.join(" ", args.subList(1, args.size()));



        event.getGuild().kick(target, String.format("킥한사람: %#s, 사유: %s",
                event.getAuthor(), reason)).queue();

        channel.sendMessage("추방 성공!").queue();

    }

    @NotNull
    @Override
    public String getHelp() {
        return "너는 이제 필요 없다!\n" +
                "사용법: `"  + Constants.PREFIX + getInvoke() + " <유저> <이유>`";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "꺼져";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "moderator";
    }
}