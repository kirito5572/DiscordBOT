package BOT.Commands;

import BOT.Constants;
import BOT.objects.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class AirkoreaListCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        String[] Inchoenlist = BOT.airKoreaList.getInchoenList();
        StringBuilder InchoenlistSt = new StringBuilder();
        EmbedBuilder builder = EmbedUtils.defaultEmbed()
                .setTitle("측정소 목록");
        for (String s : Inchoenlist) {
            InchoenlistSt.append(s);
            InchoenlistSt.append(", ");
        }

        builder.addField("인천", InchoenlistSt.toString(),false);
        TextChannel channel = event.getChannel();
        channel.sendMessage(builder.build()).queue();
    }

    @Override
    public String getHelp() {
        return Constants.PREFIX + getInvoke();
    }

    @Override
    public String getInvoke() {
        return "공기질상세지역";
    }
}
