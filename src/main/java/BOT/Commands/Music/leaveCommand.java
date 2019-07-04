package BOT.Commands.Music;

import BOT.Constants;
import BOT.objects.ICommand;
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
        channel.sendMessage("보이스채널을 떠납니다.").queue();
    }

    @Override
    public String getHelp() {
        return "노래 다들었어 이제 그럼 가" +
                "사용법 : '" + Constants.PREFIX + getInvoke() + "'";
    }

    @Override
    public String getInvoke() {
        return "퇴장";
    }

    @Override
    public String getSmallHelp() {
        return "다들었음 내보내";
    }
}
