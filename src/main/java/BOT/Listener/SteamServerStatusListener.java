package BOT.Listener;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class SteamServerStatusListener extends ListenerAdapter {
    private static boolean steamdown = false;
    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        TimerTask job = new TimerTask() {
            @Override
            public void run() {
                WebUtils.ins.scrapeWebPage("https://crowbar.steamstat.us/Barney").async((document -> {
                    JsonParser parser = new JsonParser();
                    JsonElement element = parser.parse(document.body().toString().substring(6, document.body().toString().length() - 7));
                    long time = element.getAsJsonObject().get("time").getAsLong();
                    Date date = new Date(time * 1000);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss z");
                    String CM = element.getAsJsonObject().get("services").getAsJsonObject().get("cms").getAsJsonObject().get("status").getAsString();
                    String CM_1 = element.getAsJsonObject().get("services").getAsJsonObject().get("cms").getAsJsonObject().get("title").getAsString();
                    String message;
                    if((!CM.equals("good")) && (!steamdown)) {
                        steamdown = true;
                        message = "@here 스팀 서버의 상태가 변경되었습니다.\n" +
                            "시간: " + dateFormat.format(date) + "\n" +
                            "내용: Steam Connection Manager 서버가 다운되었습니다. (" + CM_1 + ")";
                        send_message(message, event);
                    } else if (steamdown && CM.equals("good")){
                        steamdown = false;
                        message = "@here 스팀 서버의 상태가 변경되었습니다.\n" +
                                "시간: " + dateFormat.format(date) + "\n" +
                                "내용: Steam Connection Manager 서버가 복구되었습니다. (" + CM_1 + ")";
                        send_message(message, event);
                    }
                }));
            }
        };
        Timer jobScheduler = new Timer();
        jobScheduler.scheduleAtFixedRate(job, 1000, 30000);
    }
    private void send_message(@NotNull String message, @NotNull ReadyEvent event) {
        Objects.requireNonNull(Objects.requireNonNull(event.getJDA().getGuildById("617222347425972234")).getTextChannelById("628768703152128000")).sendMessage(message).queue();
    }
}
