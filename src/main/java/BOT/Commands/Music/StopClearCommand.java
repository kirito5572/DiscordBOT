package BOT.Commands.Music;

import BOT.Constants;
import BOT.Music.GuildMusicManager;
import BOT.Music.PlayerManager;
import BOT.objects.ICommand;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;

import java.util.List;

public class StopClearCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        PlayerManager playerManager = PlayerManager.getInstance();
        AudioManager audioManager = event.getGuild().getAudioManager();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
        if(!audioManager.isConnected()) {
            event.getChannel().sendMessage("음성 채널에 연결되어있지 않아 사용이 불가능합니다.").queue();
        }

        musicManager.scheduler.getQueue().clear();
        musicManager.player.stopTrack();
        musicManager.player.setPaused(false);

        Member selfMember = event.getGuild().getSelfMember();
        if(!selfMember.hasPermission(Permission.VOICE_CONNECT)) {
            event.getChannel().sendMessage("보이스채널 권한이 없습니다.").queue();
            return;
        }

        event.getChannel().sendMessage("노래 재생을 멈추고 재생목록을 비웁니다.").queue();

    }

    @Override
    public String getHelp() {
        return "이제 그만 부르고 재생목록 비우고 나갈게" +
                "사용법:`" + Constants.PREFIX + getInvoke() + "`";
    }

    @Override
    public String getInvoke() {
        return "정지";
    }

    @Override
    public String getSmallHelp() {
        return "노래 정지시키기(재생목록 초기화 O)";
    }
}
