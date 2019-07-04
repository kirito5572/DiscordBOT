package BOT.Commands.Moderation;

import BOT.Constants;
import BOT.objects.ICommand;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class ClearCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        Member selfMember = event.getGuild().getSelfMember();
        if(!selfMember.hasPermission(Permission.MESSAGE_MANAGE)) {
            channel.sendMessage("메세지를 삭제할 권한이 없습니다.").queue();
        }
        String joined = String.join("", args);
        if(joined.equals("")) {
            channel.sendMessage("`" + Constants.PREFIX + getInvoke() +
                    "` 뒤에 숫자를 입력해주세요").queue();
        }
        if(Integer.parseInt(joined) < 1) {
            channel.sendMessage("1보다 큰 숫자를 입력해주세요").queue();
        } else if(Integer.parseInt(joined) > 100) {
            channel.sendMessage("100보다 작은 숫자를 입력해주세요").queue();
        }
        List<Message> messages = event.getChannel().getHistory().retrievePast(Integer.parseInt(joined)).complete();
        event.getChannel().deleteMessages(messages).queue();

        channel.sendMessage(joined + "개의 채팅 삭제 완료").queue();
    }

    @Override
    public String getHelp() {
        return "더러운 채팅들을 깔끔하게!." +
                "사용법: `" + Constants.PREFIX + getInvoke() + "` (숫자)";
    }

    @Override
    public String getInvoke() {
        return "청소";
    }

    @Override
    public String getSmallHelp() {
        return "더러운거 청소하기";
    }
}
