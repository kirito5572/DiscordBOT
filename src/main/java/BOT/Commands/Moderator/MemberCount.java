package BOT.Commands.Moderator;

import BOT.App;
import BOT.Objects.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;
import java.util.Objects;

import static BOT.Listener.MemberCountListener.count;

public class MemberCount implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(!Objects.requireNonNull(event.getMember()).hasPermission(Permission.MANAGE_CHANNEL)) {
            event.getChannel().sendMessage("이 명령어를 사용할 권한이 없습니다.").queue();

            return;
        }
        if(!event.getGuild().getSelfMember().hasPermission(Permission.MANAGE_CHANNEL)) {
            event.getChannel().sendMessage("봇이 이 명령어를 사용 할 권한이 없습니다.").queue();

            return;
        }
        String joined = String.join(" ",args);
        Guild guild = event.getGuild();
        String categoryName = "\uD83D\uDCCB서버 상태\uD83D\uDCCB";
        Category category;
        switch (joined) {
            case "시작":
                try {
                    category = guild.getCategoriesByName(categoryName, true).get(0);

                    if (category != null) {
                        event.getChannel().sendMessage("이미 멤버 카운팅이 시작되었습니다. \n" +
                                App.getPREFIX() + getInvoke() + " 새로고침").queue();
                    }
                    return;
                } catch (Exception ignored) {

                }

                guild.createCategory(categoryName)
                        .setPosition(0)
                        .complete();
                category = guild.getCategoriesByName(categoryName, true).get(0);
                String memberCountName = "총 멤버 수";
                category.createVoiceChannel(memberCountName + " : " + guild.getMembers().size()).complete();

                int numOfBot = 0;
                int numOfUser = 0;
                for (int i = 0; i < guild.getMembers().size(); i++) {
                    if (guild.getMembers().get(i).getUser().isBot()) {
                        numOfBot++;
                    } else {
                        numOfUser++;
                    }
                }

                String botCountName = "봇 수";
                category.createVoiceChannel(botCountName + " : " + numOfBot).complete();
                String userCountName = "유저 수";
                category.createVoiceChannel(userCountName + " : " + numOfUser).complete();
                String channelCountName = "채널 수";
                category.createVoiceChannel(channelCountName + " : " + (guild.getChannels().size() - guild.getCategories().size())).complete();
                String roleCountName = "역할 갯수";
                category.createVoiceChannel(roleCountName + " : " + guild.getRoles().size()).complete();

                event.getChannel().sendMessage("멤버 카운팅이 시작되었습니다.").queue();
                break;
            case "종료":
                try {
                    category = guild.getCategoriesByName(categoryName, true).get(0);
                    for (int i = 0; i < category.getChannels().size(); i++) {
                        category.getChannels().get(i).delete().complete();
                    }
                    category.delete().complete();
                    event.getChannel().sendMessage("멤버 카운팅이 종료되었습니다..").queue();
                } catch (Exception e) {
                    event.getChannel().sendMessage("멤버 카운팅을 하고 있지 않습니다.").queue();
                }
                break;
            case "새로고침":
                count(guild);
                event.getChannel().sendMessage("새로고침이 완료되었습니다.").queue();
                break;
            default:
                event.getChannel().sendMessage("그런 인수는 없습니다.\n" +
                        App.getPREFIX() + getInvoke() + " [시작 / 종료 / 새로고침]").queue();
                break;
        }
    }

    @Override
    public String getHelp() {
        return "멤버 카운터 [시작/정지/새로고침]";
    }

    @Override
    public String getInvoke() {
        return "멤버카운팅";
    }

    @Override
    public String getSmallHelp() {
        return "moderator";
    }
}
