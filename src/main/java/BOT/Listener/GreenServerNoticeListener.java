package BOT.Listener;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Objects;

public class GreenServerNoticeListener extends ListenerAdapter {
    @NotNull
    private static String lastmessage = "";
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(event.getChannel().getId().equals("600015178821664769")) {
            String message = "";
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
                assert member[i] != null;
                if(member[i].getOnlineStatus().equals(OnlineStatus.ONLINE)) {
                    assert channel[i] != null;
                    channel[i].sendMessage("+bc 15 <color=#4B89DC>서버 공지사항</color>이 있습니다.\n" +
                            "<color=#3CB371>디스코드 방</color>을 <color=#f00>확인</color>하여 주시기 바랍니다.").queue();
                }
            }
        }
    }

    @Override
    public void onGuildMemberLeave(@Nonnull GuildMemberLeaveEvent event) {
        if (event.getGuild().getId().equals("600010501266866186")) {
            for(Role role :event.getMember().getRoles()){
                if (role.getId().equals("655729827965566998")) {
                    Objects.requireNonNull(event.getGuild().getTextChannelById("600010501266866188"))
                            .sendMessage("<@368688044934561792>, <@284508374924787713> SCP 탈주 사태가 발생했습니다. \n" +
                                    "모든 MTF는 해당 개체를 빠르게 재 격리 하여 주시기 바랍니다.\n" +
                                    "격리 대상 SCP 개체는 " + event.getMember().getNickname() + " 입니다.").queue();
                }
            }
        }
    }
}