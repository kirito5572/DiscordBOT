package BOT.Commands;

import BOT.Objects.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ColorInfoCommand implements ICommand {
    @Override
    public void handle(List<String> args, @NotNull GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        EmbedBuilder builder = EmbedUtils.getDefaultEmbed()
                .setTitle("색 목록");
        String[][] color = ColorCommand.getColor();
        for(int i = 0; i < 17; i++) {
            builder.addField(color[i][0], color[i][1] + color[i][3],false);
        }
        channel.sendMessage(builder.build()).queue();
    }

    @NotNull
    @Override
    public String getHelp() {
        return "TEST";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "색정보";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "other";
    }
}
