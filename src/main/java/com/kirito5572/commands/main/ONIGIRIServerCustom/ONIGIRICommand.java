package com.kirito5572.commands.main.ONIGIRIServerCustom;

import com.kirito5572.objects.main.EventPackage;
import com.kirito5572.objects.main.ICommand;
import com.kirito5572.objects.main.ONIGIRIList;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ONIGIRICommand implements ICommand {
    @Override
    public void handle(@NotNull List<String> args, @NotNull EventPackage event) {
        if(event.getGuild().getId().equals("453817631603032065")) {
            String[] list = ONIGIRIList.getList();
            EmbedBuilder embedBuilder = EmbedUtils.getDefaultEmbed();
            for (String s : list) {
                embedBuilder.addField(s, "", false);
            }
            event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
        } else {
            event.getChannel().sendMessage("이 명령어는 이 서버에서 사용할 수 없습니다.").queue();
        }
    }

    @NotNull
    @Override
    public String getHelp() {
        return "짤방 시동어를 알려줍니다.";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "짤방";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "serverCustom";
    }
}
