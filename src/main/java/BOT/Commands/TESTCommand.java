package BOT.Commands;

import BOT.Objects.ICommand;
import com.github.natanbc.reliqua.request.PendingRequest;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jsoup.nodes.Document;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TESTCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage("디버깅용 테스트 명령어 입니다.").queue();
        WebUtils.ins.scrapeWebPage("https://crowbar.steamstat.us/Barney").async((document -> {
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(document.body().toString().substring(6, document.body().toString().length() - 7));
            long time = element.getAsJsonObject().get("time").getAsLong();
            Date date = new Date(time * 1000);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss z");
            String CM = element.getAsJsonObject().get("services").getAsJsonObject().get("cms").getAsJsonObject().get("status").getAsString();
            String CM_1 = element.getAsJsonObject().get("services").getAsJsonObject().get("cms").getAsJsonObject().get("title").getAsString();
            String message = "!here 스팀 서버의 상태가 변경되었습니다.\n" +
                    "시간: " + dateFormat.format(date) + "\n" +
                    "내용: Steam Connection Manager 서버가 다운되었습니다. (" + CM_1 + ")";
            event.getChannel().sendMessage(message).queue();
        }));
    }


    @Override
    public String getHelp() {
        return "디버깅용 테스트 커맨드";
    }

    @Override
    public String getInvoke() {
        return "테스트";
    }

    @Override
    public String getSmallHelp() {
        return "other";
    }
}
