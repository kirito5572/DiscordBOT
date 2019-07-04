package BOT.Commands;

import BOT.Constants;
import BOT.objects.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class CatCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        Member selfMember = event.getGuild().getSelfMember();
        if(!selfMember.hasPermission(Permission.VOICE_CONNECT)) {
            event.getChannel().sendMessage("보이스채널 권한이 없습니다..").queue();
            return;
        }

        WebUtils.ins.scrapeWebPage("https://nekos.life/").async((document) -> {
            String a = document.getElementsByTag("head").first().toString();
            int b = a.indexOf("meta property=\"og:url\" content=\"");
            int c = a.indexOf("<meta property=\"og:image\" content=\"");
            a = a.substring(b+32, c-5);
            EmbedBuilder embed = EmbedUtils.embedImage(a);
            event.getChannel().sendMessage(embed.build()).queue();
        });
    }

    @Override
    public String getHelp() {
        return "랜덤 네코 생성기 \n" +
        "사용법: `" + Constants.PREFIX + getInvoke() +"`";
    }

    @Override
    public String getInvoke() {
        return "네코";
    }

    @Override
    public String getSmallHelp() {
        return "카와이 네코쟝";
    }
}
