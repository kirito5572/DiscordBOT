package BOT.Commands.Music;

import BOT.Constants;
import BOT.Music.GuildMusicManager;
import BOT.Music.PlayerManager;
import BOT.objects.ICommand;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class QueueDelectCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        new Thread(() -> {
            TextChannel channel = event.getChannel();
            PlayerManager playerManager = PlayerManager.getInstance();
            GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
            BlockingQueue<AudioTrack> queue = musicManager.scheduler.getQueue();

            String joined = String.join("", args);


            if(queue.isEmpty()) {
                channel.sendMessage("재생목록이 비었습니다.").queue();

                return;
            }

            System.out.println(queue.size());
            channel.sendMessage("재생목록을 비우는 중입니다.").queue();
            String a;
            if(joined.equals("")) {
                musicManager.scheduler.getQueue().clear();
                a = channel.getLatestMessageId();
                channel.editMessageById(a,"재생목록을 초기화 했습니다.").queue();
            } else {
                for(int i = 0; i < Integer.parseInt(joined); i++) {
                    musicManager.scheduler.nextTrack();
                    a = channel.getLatestMessageId();
                    channel.editMessageById(a,"재생목록에서"+ joined +"개의 노래를 삭제했습니다.").queue();
                }
            }

        }).start();
    }

    @Override
    public String getHelp() {
        return "입력된 숫자만큼 재생목록을 비웁니다.\n" +
                "숫자를 입력하지 않은경우 전체가 지워집니다.\n" +
                "사용법: '" + Constants.PREFIX + getInvoke() + "'(숫자)";
    }

    @Override
    public String getInvoke() {
        return "qc";
    }
}
