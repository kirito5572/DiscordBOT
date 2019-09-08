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
        final Message[] lastMessage = {null, null};
        final int[] time = {0, 0};
        final int[] text = {0, 0};
        final String[] content = {
                "~을 누른뒤 <color=#f00>.HELP 또는 .명령어</color> 를 입력할시 사용가능한 명령어 리스트가 나옵니다.",
                "<color=#0096FF>MTF</color>를 체포탈출 시킬시 <color=#008f1e>혼돈의 반란</color>이 되고\n <color=#008f1e>혼돈의 반란</color>을 체포탈출 시킬시 <color=#0096FF>MTF 부관</color>이 됩니다.",
                "<color=#008f1e>혼돈의 반란</color>과 <color=#f00>SCP</color>의 티밍은 금지됩니다.",
                "서버 이용규정을 숙지하지 않아 발생하는 문제는 이용자 본인에게 있습니다.",
                "디스코드 서버에 참여해주세요! [ https://discord.gg/FqfTD2F ]",
                "경어 사용을 생활화 합시다.",
                "5분이상 한 장소에 계시거나, 욕설 사용시 <color=#f00>무통보</color> 킥 조치 될수 있습니다.",
                "방송실에서 현재상황과 남은인원을 확인하실수 있습니다.",
                "<color=#f00>SCP</color>진영으로 탈주시 <color=#f00>중징계</color>를 받습니다, <color=#f00>좀비</color>도요!",
                "관리자가 가끔 <color=#98FB98>튜토리얼</color> 상태로 있을시.... 그냥 지켜만 봐주세요. ",
                "서버가 가득찬 상태로 지속적으로 접속을 시도할시 <color=#f00>IP밴</color> 되실수있습니다.",
                "~을 누른뒤 <color=#0096FF>.레일건</color> 을 입력할시 레일건의 사용여부를 알수있습니다.",
                "~을 누른뒤 .채팅 [하고싶은 말] 을 입력할시 <color=#f00>SCP</color>끼리 의사소통을 할 수 있습니다",
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
        TimerTask job1 = new TimerTask() {
            @Override
            public void run() {
                Guild greenServer;
                TextChannel channel;
                Message message;
                time[1]++;
                try {
                    greenServer = event.getJDA().getGuildById("600010501266866186");
                    channel = greenServer.getTextChannelById("609055562721787904");
                    message = channel.getMessageById(channel.getLatestMessageId()).complete();
                } catch (Exception e) {

                    return;
                }
                if(!message.equals(lastMessage[1])) {
                    lastMessage[1] = message;
                    if(message.getContentRaw().contains("라운드 시작")) {
                        time[1] = 0;
                        flag[1] = true;
                    }
                    if(message.getContentRaw().contains("라운드가 끝났습니다.")) {
                        time[1] = 0;
                        flag[1] = false;
                    }
                    if(message.getContentRaw().contains("- SCP:SL server connection lost.")) {
                        time[1] = 0;
                        flag[1] = false;
                    }
                    if(message.getContentRaw().contains("라운드 재시작")) {
                        time[1] = 0;
                        flag[1] = false;
                    }
                    if(message.getContentRaw().contains("서버가 유저를 기달리고 있습니다")) {
                        time[1] = 0;
                        flag[1] = false;
                    }
                }
                if(flag[1] && (time[1] == 75)) {
                    channel.sendMessage("+bc 8 " + content[text[0]]).queue();
                    text[1]++;
                    time[1] = 0;
                    if(text[1] == content.length) {
                        text[1] = 0;
                    }
                }
            }
        };
        Timer jobScheduler = new Timer();
        Timer jobScheduler1 = new Timer();
        jobScheduler.scheduleAtFixedRate(job, 100, 1000);
        jobScheduler1.scheduleAtFixedRate(job1, 100, 1000);
    }
}
