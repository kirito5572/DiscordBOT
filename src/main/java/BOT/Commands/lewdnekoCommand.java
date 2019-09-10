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
        WebUtils.ins.scrapeWebPage("https://nekos.life/lewd").async((document) -> {
            String a = document.toString();
            int b = a.indexOf("img src=\"");
            a = a.substring(b);
            int c = a.indexOf("\" alt=\"neko");
            a = a.substring(9, c);
            EmbedBuilder embed = EmbedUtils.embedImage(a);
            if(event.getChannel().isNSFW()) {
                event.getChannel().sendMessage(embed.build()).queue();
            } else {
                if(!event.getGuild().getId().equals("600010501266866186")) {
                    event.getAuthor().openPrivateChannel().complete().sendMessage(embed.build()).queue();
                } else {
                    event.getChannel().sendMessage("당신의 흑심! 너굴맨이 처리했으니까 안심하라구!").queue();
                }
            }
        });
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
