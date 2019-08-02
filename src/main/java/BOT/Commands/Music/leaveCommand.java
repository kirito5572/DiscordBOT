package BOT.Commands.Music;

import BOT.App;
import BOT.Music.GuildMusicManager;
import BOT.Music.PlayerManager;
import BOT.Objects.ICommand;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;

import java.util.List;

public class leaveCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        AudioManager audioManager = event.getGuild().getAudioManager();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
        Member selfMember = event.getGuild().getSelfMember();
        if(!selfMember.hasPermission(Permission.VOICE_CONNECT)) {
            channel.sendMessage("보이스채널 권한이 없습니다..").queue();
            return;
        }
        if(!audioManager.isConnected()) {
            channel.sendMessage("나갈 보이스 채널이 없습니다.").queue();
            return;
        }
        /*VoiceChannel voiceChannel = audioManager.getConnectedChannel();

        if(!voiceChannel.getMembers().contains(event.getMember())) {
            channel.sendMessage("봇과 같은 보이스 채널에 있어야 합니다.").queue();
            return;
        }*/

        audioManager.closeAudioConnection();
        musicManager.scheduler.getQueue().clear();

        channel.sendMessage("보이스채널을 떠납니다.").queue();
    }

    @Override
    public String getHelp() {
        return "노래를 정지하고 나갑니다" +
                "사용법 : '" + App.getPREFIX() + getInvoke() + "'";
    }

    @Override
    public String getInvoke() {
        return "퇴장";
    }

    @Override
    public String getSmallHelp() {
        return "다 듣고 내보내기";
    }
}
