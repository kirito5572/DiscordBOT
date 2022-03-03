package com.kirito5572.commands.main

import com.kirito5572.objects.main.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class CallCommand implements ICommand {
    @Override
    public void handle(@NotNull List<String> args, @NotNull EventPackage event) {
        try {
            event.getChannel().sendMessage(Objects.requireNonNull(event.getGuild().getMemberById("284508374924787713")).getAsMention()).queue();
        } catch (Exception e) {
            event.getChannel().sendMessage("error:java.lang.NullPointerException: null\n해당 서버에 봇 제작자가 없는것 같아요. <@284508374924787713> 이 곳으로 1대1 멘션 부탁드립니다.").queue();
            return;
        }
        event.getChannel().sendMessage("무슨 오류가 발생했는지 상세하게 적어주시면 확인후에, 결과를 알려드리도록 하겠습니다.").queue();
    }

    @NotNull
    @Override
    public String getHelp() {
        return "봇 제작자를 호출 하는 명령어 입니다.";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "제작자";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "other";
    }
}
