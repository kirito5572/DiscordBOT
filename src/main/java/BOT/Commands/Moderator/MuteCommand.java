package BOT.Commands.Moderator;

import BOT.Constants;
import BOT.Objects.ICommand;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;

public class MuteCommand implements ICommand {
    @Override
    public void handle(List<String> args, @NotNull GuildMessageReceivedEvent event) {
        //TODO muteCommand
        Role muteRole = event.getGuild().createRole()
                .setName("채팅금지")
                .setColor(Color.RED)
                .setHoisted(true)
                .setPermissions(66560L)
                .complete();

    }

    @NotNull
    @Override
    public String getHelp() {
        return "누가 남에게 민폐를 끼치나요? 그럼 그 사람을 제재해봅시다." +
                "사용법: `" + Constants.PREFIX + getInvoke() + "`(@유저)";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "채금";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "moderator";
    }
}