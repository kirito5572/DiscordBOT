package BOT.Commands;

import BOT.Objects.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class lewdnekoCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        if(event.getChannel().isNSFW()) {
            WebUtils.ins.scrapeWebPage("https://nekos.life/lewd").async((document) -> {
                String a = document.toString();
                int b = a.indexOf("img src=\"");
                a = a.substring(b);
                int c = a.indexOf("\" alt=\"neko");
                a = a.substring(9, c);
                EmbedBuilder embed = EmbedUtils.embedImage(a);
                event.getChannel().sendMessage(embed.build()).queue();
            });
        } else {
            channel.sendMessage("이 명령어는 후방주의 채널에서만 사용이 가능합니다.").queue();
        }
    }

    @Override
    public String getHelp() {
        return "lewd 콘텐츠 커맨드이므로 사용에 주의하여 주시기 바랍니다.";
    }

    @Override
    public String getInvoke() {
        return "후방주의네코";
    }

    @Override
    public String getSmallHelp() {
        return "lewd 커맨드 #후방주의";
    }
}