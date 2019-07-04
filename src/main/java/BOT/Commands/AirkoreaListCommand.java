package BOT.Commands;

import BOT.Constants;
import BOT.objects.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class AirkoreaListCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        String[] Inchoenlist = BOT.airKoreaList.getInchoenList();
        StringBuilder InchoenlistSt = new StringBuilder();
        Member selfMember = event.getGuild().getSelfMember();
        if(!selfMember.hasPermission(Permission.MESSAGE_WRITE)) {
            channel.sendMessage("메세지를 보낼권한이 없습니다.").queue();

            return;
        }
        EmbedBuilder builder = EmbedUtils.defaultEmbed()
                .setTitle("측정소 목록");
        for (String s : Inchoenlist) {
            InchoenlistSt.append(s);
            InchoenlistSt.append(", ");
        }

        builder.addField("인천", InchoenlistSt.toString(),false);
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

    @Override
    public String getSmallHelp() {
        return "지역 목록 부르기";
    }
}
