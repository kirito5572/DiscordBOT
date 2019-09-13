package BOT.Commands.Moderator;

import BOT.App;
import BOT.Constants;
import BOT.Objects.ICommand;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;
import java.util.stream.Collectors;

public class UnbanCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        TextChannel channel = event.getChannel();

        if (!event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
            channel.sendMessage("이 명령어를 사용할 권한이 없습니다.").queue();
            return;
        }

        if (!event.getGuild().getSelfMember().hasPermission(Permission.BAN_MEMBERS)) {
            channel.sendMessage("봇이 이 명령어를 사용할 권한이 없습니다.").queue();
            return;
        }

        if (args.isEmpty()) {
            channel.sendMessage("사용법: `" + Constants.PREFIX + getInvoke() + " <유저명/유저 ID/@멘션>`").queue();
            return;
        }

        String argsJoined = String.join(" ", args);

        event.getGuild().getBanList().queue((bans) -> {

            List<User> goodUsers = bans.stream().filter((ban) -> isCorrectUser(ban, argsJoined))
                    .map(Guild.Ban::getUser).collect(Collectors.toList());

            if (goodUsers.isEmpty()) {
                channel.sendMessage("해당 유저는 밴 되지 않았습니다.").queue();
                return;
            }

            User target = goodUsers.get(0);

            String mod = String.format("%#s", event.getAuthor());
            String bannedUser = String.format("%#s", target);

            event.getGuild().getController().unban(target)
                    .reason("언밴한 유저: " + mod).queue();

            channel.sendMessage("유저 " + bannedUser + " 가 언밴되었습니다.").queue();

        });
    }

    @Override
    public String getHelp() {
        return "이 서버에서 유저의 밴을 취소합니다.\n" +
                "사용법: `" + App.getPREFIX() + getInvoke() + " <유저명/유저 ID/@멘션>";
    }

    @Override
    public String getInvoke() {
        return "언밴";
    }

    @Override
    public String getSmallHelp() {
        return "moderator";
    }

    private boolean isCorrectUser(Guild.Ban ban, String arg) {
        User bannedUser = ban.getUser();

        return bannedUser.getName().equalsIgnoreCase(arg) || bannedUser.getId().equals(arg)
                || String.format("%#s", bannedUser).equalsIgnoreCase(arg);
    }
}