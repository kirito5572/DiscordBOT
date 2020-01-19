package BOT.Commands;

import BOT.Objects.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

public class TESTCommand implements ICommand {
    @Override
    public void handle(List<String> args, @NotNull GuildMessageReceivedEvent event) {
    }


    @NotNull
    @Override
    public String getHelp() {
        return "디버깅용 테스트 커맨드";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "테스트";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "other";
    }
}
