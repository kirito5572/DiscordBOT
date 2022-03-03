package com.kirito5572.commands.main.moderator;

import com.kirito5572.App;
import com.kirito5572.objects.main.EventPackage;
import com.kirito5572.objects.main.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class pollCommand implements ICommand {
    @Override
    public void handle(@NotNull List<String> args, @NotNull EventPackage event) {
        if(!(Objects.requireNonNull(event.getMember()).hasPermission(Permission.ADMINISTRATOR) || event.getMember().hasPermission(Permission.MESSAGE_MANAGE))) {
            if(!event.getAuthor().getId().equals("278086240195182592")) {
                event.getChannel().sendMessage(event.getMember().getAsMention() + ", 당신은 이 명령어를 사용할 권한이 없습니다.").queue();

                return;
            }
        }
        String pollText = event.getMessage().getContentRaw();
        String temp = App.getPREFIX() + getInvoke();
        pollText = pollText.substring(temp.length());

        EmbedBuilder builder = EmbedUtils.getDefaultEmbed()
                .addField("투표 안건", pollText, false)
                .setTitle("투표")
                .addField("1\u20E3", "찬성", true)
                .addField("2\u20E3", "반대", true)
                .addField("3\u20E3", "기권", true);
        Message message;
        if(event.getGuild().getId().equals("617222347425972234")) {
            message =  event.getChannel().sendMessage(builder.build() + "@everyone").complete();
        } else {
            message = event.getChannel().sendMessageEmbeds(builder.build()).complete();
        }

        event.getMessage().delete().complete();

        message.addReaction("1\u20E3").complete();
        message.addReaction("2\u20E3").complete();
        message.addReaction("3\u20E3").complete();
    }

    @NotNull
    @Override
    public String getHelp() {
        return "유저들에게 투표를 하기 위한 명령어 입니다.\n" +
                "사용법: `" + App.getPREFIX() + getInvoke() + " <투표 내용> `";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "투표";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "moderator";
    }
}
