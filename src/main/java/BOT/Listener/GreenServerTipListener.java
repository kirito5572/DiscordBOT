package BOT.Listener;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Timer;
import java.util.TimerTask;

public class GreenServerTipListener extends ListenerAdapter {
    @Override
    public void onReady(ReadyEvent event) {
        final Message[] lastMessage = {null};
        final int[] time = {0};
        final int[] text = {0};
        final String[] content = {
                "~을 누른뒤 .명령어, .HELP를 입력할시 사용가능한 명령어 리스트가 나옵니다",
                "MTF를 체포탈출 시킬시 카오스가 되고. 카오스를 체포탈출 시킬시 MTF부관이 됩니다",
                "카오스와 SCP의 티밍은 금지됩니다.",
                "서버 이용규정을 숙지하지 않아 발생하는 문제는 본인에게 있습니다.",
                "디스코드 방에 참여해주세요. [주소]",
                "존댓말과 존칭어 사용을 생활화 합시다",
                "5분이상 존버 혹은 욕설 사용시 무통보 킥 조치 될수 있습니다.",
                "방송실에서 현재 남은인원과 상황을 확인할수 있습니다",
                "SCP진영에서 탈주가 일어날시 강력 처벌 받습니다, 좀비도요!",
                "관리자가 가끔 [튜토리얼] 상태로 있을시.... 그냥 지켜만 봐주세요",
                "서버장은 탈주중이라 일을못합니다",
                "서버가 가득찬 상태로 지속적으로 접속을 시도할시 IP밴 되실수있습니다.",
        };
        final boolean[] flag = {false};
        TimerTask job = new TimerTask() {
            @Override
            public void run() {
                Guild greenServer;
                TextChannel channel;
                Message message;
                time[0]++;
                try {
                    greenServer = event.getJDA().getGuildById("600010501266866186");
                    channel = greenServer.getTextChannelById("609055524851286027");
                    message = channel.getMessageById(channel.getLatestMessageId()).complete();
                } catch (Exception e) {

                    return;
                }
                if(!message.equals(lastMessage[0])) {
                    lastMessage[0] = message;
                    if(message.getContentRaw().contains("라운드 시작")) {
                        time[0] = 0;
                        flag[0] = true;
                    }
                    if(message.getContentRaw().contains("라운드가 끝났습니다.")) {
                        time[0] = 0;
                        flag[0] = false;
                    }
                    if(message.getContentRaw().contains("- SCP:SL server connection lost.")) {
                        time[0] = 0;
                        flag[0] = false;
                    }
                    if(message.getContentRaw().contains("라운드 재시작")) {
                        time[0] = 0;
                        flag[0] = false;
                    }
                    if(message.getContentRaw().contains("서버가 유저를 기달리고 있습니다")) {
                        time[0] = 0;
                        flag[0] = false;
                    }
                }
                if(flag[0] && (time[0] == 75)) {
                    channel.sendMessage("+bc 8 " + content[text[0]]).queue();
                    text[0]++;
                    time[0] = 0;
                    if(text[0] == content.length) {
                        text[0] = 0;
                    }
                }
            }
        };
        Timer jobScheduler = new Timer();
        jobScheduler.scheduleAtFixedRate(job, 100, 1000);
    }
}
