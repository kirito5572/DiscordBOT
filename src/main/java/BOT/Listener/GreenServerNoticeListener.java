package BOT.Listener;

import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class GreenServerNoticeListener extends ListenerAdapter {
    private static String message = "";
    private static String lastmessage = "";
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getChannel().getId().equals("600015178821664769")) {
            if(lastmessage.equals(message)) {
                return;
            } else {
                lastmessage = message;
            }
            TextChannel[] channel = new TextChannel[5];
            channel[0] = event.getGuild().getTextChannelById("609055524851286027");
            channel[1] = event.getGuild().getTextChannelById("609055562721787904");
            channel[2] = event.getGuild().getTextChannelById("609624846892269569");
            channel[3] = event.getGuild().getTextChannelById("609662026972659723");
            channel[4] = event.getGuild().getTextChannelById("609057247116525650");
            Member[] member = new Member[5];
            member[0] = event.getGuild().getMemberById("580691748276142100");
            member[1] = event.getGuild().getMemberById("586590053539643408");
            member[2] = event.getGuild().getMemberById("600658772876197888");
            member[3] = event.getGuild().getMemberById("600660530230722560");
            member[4] = event.getGuild().getMemberById("600676751118696448");
            for(int i = 0; i < 5; i++) {
                if(member[i].getOnlineStatus().equals(OnlineStatus.ONLINE)) {
                    //channel[i].sendMessage("+bc 15 <color=#4B89DC>서버 공지사항</color>이 있습니다.\n" +
                            //"<color=#3CB371>디스코드 방</color>을 <color=#f00>확인</color>하여 주시기 바랍니다.").queue();
                }
            }
        }
    }
}