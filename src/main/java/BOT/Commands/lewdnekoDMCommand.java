package BOT.Commands;

import BOT.Objects.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class lewdnekoDMCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        System.out.println("TP");
        try {
            WebUtils.ins.scrapeWebPage("https://nekos.life/lewd").async((document) -> {
                System.out.println("TP1");
                String a = document.toString();
                System.out.println("TP2");
                int b = a.indexOf("img src=\"");
                System.out.println("TP3");
                a = a.substring(b);
                System.out.println("TP4");
                int c = a.indexOf("\" alt=\"neko");
                System.out.println("TP5");
                a = a.substring(9, c);
                EmbedBuilder embed = EmbedUtils.embedImage(a);
                System.out.println("TP6");
                PrivateChannel DM = event.getAuthor().openPrivateChannel().complete();
                System.out.println("TP7");
                DM.sendMessage(embed.build()).complete();
                System.out.println("TP8");
            });
        } catch (Exception e){
            System.out.println("TP9");
            channel.sendMessage("DM을 받을수 있게 설정을 변경하여 주세요.").queue();
        }
    }

    @Override
    public String getHelp() {
        return "lewd 콘텐츠 커맨드이므로 사용에 주의하여 주시기 바랍니다.";
    }

    @Override
    public String getInvoke() {
        return "후방주의네코DM";
    }

    @Override
    public String getSmallHelp() {
        return "lewd 커맨드 #후방주의 #DM";
    }
}
