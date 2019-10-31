package BOT.Commands;

import BOT.Objects.ICommand;
import BOT.Objects.SQL;
import BOT.Objects.getYoutubeSearch;
import com.github.natanbc.reliqua.request.PendingRequest;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jsoup.nodes.Document;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TESTCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
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
