package BOT.Commands;

import BOT.App;
import BOT.Listener.Listener;
import BOT.Objects.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class SayCommand implements ICommand {
    @Override
    public void handle(@NotNull List<String> args, @NotNull GuildMessageReceivedEvent event) {
        if(!event.getGuild().getSelfMember().hasPermission(event.getChannel(), Permission.MESSAGE_MANAGE)) {
            event.getChannel().sendMessage("봇에 권한이 없어 명령어를 사용할수 없습니다. \n" +
                    "필요한 권한: 메세지 관리").queue();

            return;
        }
        if(!event.getAuthor().getId().equals(Listener.getID1())) {
            if (event.getGuild().getId().equals("670665835618107393")) {
                if (!Objects.requireNonNull(event.getMember()).hasPermission(Permission.ADMINISTRATOR)) {
                    event.getChannel().sendMessage(event.getMember().getAsMention() + ", 당신은 이 명령어를 사용 할 수 없습니다.").complete().delete().queueAfter(5, TimeUnit.SECONDS);
                    return;
                }
            }
            if (event.getGuild().getId().equals("607390893804093442")) {
                if (!Objects.requireNonNull(event.getMember()).hasPermission(Permission.MANAGE_ROLES)) {
                    event.getChannel().sendMessage("당신은 이 명령어를 사용 할 수 없습니다.").queue();
                    return;
                }
            }
            if (event.getGuild().getId().equals("617222347425972234")) {
                if (!Objects.requireNonNull(event.getMember()).hasPermission(Permission.MANAGE_ROLES)) {
                    event.getChannel().sendMessage("당신은 이 명령어를 사용 할 수 없습니다.").queue();
                    return;
                }
            }
            if (event.getGuild().getId().equals("607390893804093442")) {
                if (!Objects.requireNonNull(event.getMember()).hasPermission(Permission.MANAGE_ROLES)) {
                    event.getChannel().sendMessage("당신은 이 명령어를 사용 할 수 없습니다.").queue();
                    return;
                }
            }
            if (event.getGuild().getId().equals("439780696999985172")) {
                if (!Objects.requireNonNull(event.getMember()).hasPermission(Permission.MANAGE_ROLES)) {
                    event.getChannel().sendMessage("당신은 이 명령어를 사용 할 수 없습니다.").queue();
                    return;
                }
            }
            if (event.getGuild().getId().equals("659364876384469022")) {
                if (!Objects.requireNonNull(event.getMember()).hasPermission(Permission.MANAGE_ROLES)) {
                    event.getChannel().sendMessage("당신은 이 명령어를 사용 할 수 없습니다.").queue();
                    return;
                }
            }
        }
        if(!event.getMessage().getAttachments().isEmpty()) {
            try {
                String message = event.getMessage().getContentRaw().substring(2);
                if(message.length() > 1) {
                    event.getChannel().sendFile(event.getMessage().getAttachments().get(0).downloadToFile().get()).append(message).queue();
                    App.textChannel.sendMessage("말 커맨드 사용 로그\n" +
                            "메세지 보낸사람: " + event.getMember() + "\n" +
                            "보낸 서버: " + event.getGuild().getId()).complete().getChannel()
                            .sendFile(event.getMessage().getAttachments().get(0).downloadToFile().get()).append(message).queue();
                } else {
                    event.getChannel().sendFile(event.getMessage().getAttachments().get(0).downloadToFile().get()).queue();
                    App.textChannel.sendMessage("말 커맨드 사용 로그\n" +
                            "메세지 보낸사람: " + event.getMember() + "\n" +
                            "보낸 서버: " + event.getGuild().getId()).complete().getChannel()
                            .sendFile(event.getMessage().getAttachments().get(0).downloadToFile().get()).queue();
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            String chat = event.getMessage().getContentRaw().substring(2);
            event.getChannel().sendMessage(chat).queue();
            App.textChannel.sendMessage("말 커맨드 사용 로그\n" +
                    "메세지 보낸사람: " + event.getMember() + "\n" +
                    "보낸 서버: " + event.getGuild().getId()).complete().getChannel()
                    .sendMessage(chat).queue();
        }
        event.getMessage().delete().queue();
    }

    @NotNull
    @Override
    public String getHelp() {
        return "대신 말을 해줍니다.";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "말";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "other";
    }
}
