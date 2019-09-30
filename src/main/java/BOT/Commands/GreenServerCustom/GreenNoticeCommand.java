package BOT.Commands.GreenServerCustom;

import BOT.App;
import BOT.Objects.ICommand;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class GreenNoticeCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        Member member = event.getMember();
        TextChannel channel = event.getChannel();
        TextChannel[] servers = new TextChannel[5];
        Member[] serverBots = new Member[5];
        assert member != null;
        if(!member.hasPermission(Permission.MESSAGE_MANAGE)) {
            channel.sendMessage("당신은 이 명령어를 사용할 권한이 없습니다.").queue();

            return;
        }
        servers[0] = event.getGuild().getTextChannelById("609055524851286027");
        servers[1] = event.getGuild().getTextChannelById("609055562721787904");
        servers[2] = event.getGuild().getTextChannelById("609624846892269569");
        servers[3] = event.getGuild().getTextChannelById("609662026972659723");
        servers[4] = event.getGuild().getTextChannelById("609057247116525650");
        serverBots[0] = event.getGuild().getMemberById("580691748276142100");
        serverBots[1] = event.getGuild().getMemberById("586590053539643408");
        serverBots[2] = event.getGuild().getMemberById("600658772876197888");
        serverBots[3] = event.getGuild().getMemberById("600660530230722560");
        serverBots[4] = event.getGuild().getMemberById("600676751118696448");
        StringBuilder builder = new StringBuilder();
        for(int i = 1; i < args.size(); i++) {
            builder.append(args.get(i)).append(" ");
        }
        String message = "+bc " + args.get(0) + " " + builder.toString();

        for(int i = 0; i < 5; i++) {
            assert serverBots[i] != null;
            if(serverBots[i].getOnlineStatus().equals(OnlineStatus.ONLINE)) {
                assert servers[i] != null;
                servers[i].sendMessage(message).queue();
            }
        }

        channel.sendMessage("전송이 완료되었습니다.").queue();


    }

    @Override
    public String getHelp() {
        return "각 서버로 공지를 보냅니다.\n" +
                "사용법: `" + App.getPREFIX() + getInvoke() + " <시간> <보낼 문장>`";
    }

    @Override
    public String getInvoke() {
        return "서버공지";
    }

    @Override
    public String getSmallHelp() {
        return "serverCustom";
    }
}
