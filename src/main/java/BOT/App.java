/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package BOT;

import BOT.objects.CommandManager;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Random;

public class App {
    private final Random random = new Random();
    private Date date = new Date();
    private SimpleDateFormat transFormat = new SimpleDateFormat("ss");
    private String time;
    private App() {
        new Thread(() -> {
            while(true) {
                date = new Date();
                time = transFormat.format(date);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        CommandManager commandManager = new CommandManager();
        Listener listener = new Listener(commandManager);
        Logger logger = LoggerFactory.getLogger(App.class);

        WebUtils.setUserAgent("Chrome 75.0.3770.100 kirito's discord bot/kirito5572#6115");

        EmbedUtils.setEmbedBuilder(
                () -> new EmbedBuilder()
                        .setColor(getRandomColor())
                        .setTimestamp(Instant.now())
        );

        try {
            logger.info("Booting");
            JDA jda = new JDABuilder(AccountType.BOT)
                    .setToken(Secrets.TOKEN)
                    .setAutoReconnect(true)
                    .addEventListener(listener)
                    .setGame(Game.of(Game.GameType.STREAMING,"사용법: $help","https://github.com/kirito5572/Discord_BOT"))//streaming("사용법: $help","https://github.com/kirito5572/Discord_BOT"))
                    .build().awaitReady();
            logger.info("Running");
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }


    }

    private Color getRandomColor() {
        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();

        return new Color(r, g, b);
    }

    public static void main(String[] args) {
        new App();
    }
}
