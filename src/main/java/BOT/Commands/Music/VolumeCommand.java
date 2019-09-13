package BOT.Commands.Music;

import BOT.App;
import BOT.Music.PlayerManager;
import BOT.Objects.ICommand;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.GuildVoiceState;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;

import java.util.List;

public class VolumeCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        PlayerManager manager = PlayerManager.getInstance();
        AudioManager audioManager = event.getGuild().getAudioManager();
        String joined = String.join("", args);

        Member selfMember = event.getGuild().getSelfMember();
        if(!selfMember.hasPermission(Permission.VOICE_CONNECT)) {
            channel.sendMessage("보이스채널 권한이 없습니다..").queue();
            return;
        }
        if(!audioManager.isConnected()) {
            channel.sendMessage("봇을 먼저 보이스채널에 들어오게 하세요.").queue();
            return;
        }

        GuildVoiceState memberVoiceState = event.getMember().getVoiceState();

        if(!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("먼저 보이스 채널에 들어오세요").queue();
            return;
        }
        if(Integer.parseInt(joined) < 10) {
            channel.sendMessage("최소 볼륨은 10입니다. 10보다 큰 수를 입력해주세요.").queue();

            return;
        } else if(Integer.parseInt(joined) > 100) {
            channel.sendMessage("최대 볼륨은 100입니다. 100보다 작은 수를 입력해주세요.").queue();

            return;
        }

        manager.getGuildMusicManager(event.getGuild()).player.setVolume(Integer.parseInt(joined));

        channel.sendMessage("볼륨이 " + joined + "으로 변경되었습니다.").queue();
    }

    @Override
    public String getHelp() {
        return "노래 소리 조절" +
                "사용법: `" + App.getPREFIX() + getInvoke() + "`(숫자)";
    }

    @Override
    public String getInvoke() {
        return "볼륨";
    }

    @Override
    public String getSmallHelp() {
        return "music";
    }
}
