package BOT.Commands;

import BOT.Objects.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;
import java.util.Objects;

public class SayCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(!event.getGuild().getSelfMember().hasPermission(event.getChannel(), Permission.MESSAGE_MANAGE)) {
            event.getChannel().sendMessage("봇에 권한이 없어 명령어를 사용할수 없습니다. \n" +
                    "필요한 권한: 메세지 관리").queue();

            return;
        }
        if(event.getGuild().getId().equals("607390893804093442")) {
            if(!Objects.requireNonNull(event.getMember()).hasPermission(Permission.MANAGE_ROLES)) {
                event.getChannel().sendMessage("당신은 이 명령어를 사용 할 수 없습니다.").queue();
                return;
            }
        }
        if(event.getGuild().getId().equals("617222347425972234")) {
            if(!Objects.requireNonNull(event.getMember()).hasPermission(Permission.MANAGE_ROLES)) {
                event.getChannel().sendMessage("당신은 이 명령어를 사용 할 수 없습니다.").queue();
                return;
            }
        }
        if(event.getGuild().getId().equals("607390893804093442")) {
            if(!Objects.requireNonNull(event.getMember()).hasPermission(Permission.MANAGE_ROLES)) {
                event.getChannel().sendMessage("당신은 이 명령어를 사용 할 수 없습니다.").queue();
                return;
            }
        }
        if(event.getGuild().getId().equals("439780696999985172")) {
            if(!Objects.requireNonNull(event.getMember()).hasPermission(Permission.MANAGE_ROLES)) {
                event.getChannel().sendMessage("당신은 이 명령어를 사용 할 수 없습니다.").queue();
                return;
            }
        }
        String chat = String.join(" ",args);
        List<Message> messages = event.getChannel().getHistory().retrievePast(1).complete();
        messages.get(0).delete().queue();
        event.getChannel().sendMessage(chat).queue();
    }

    @Override
    public String getHelp() {
        return "대신 말을 해줍니다.";
    }

    @Override
    public String getInvoke() {
        return "말";
    }

    @Override
    public String getSmallHelp() {
        return "other";
    }
}
