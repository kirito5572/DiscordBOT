package BOT;/*
 * This Java source file was generated by the Gradle 'init' task.
 */

import BOT.Listener.*;
import BOT.Objects.CommandManager;
import BOT.Objects.SQL;
import BOT.Objects.config;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

public class App {
    private static final boolean DEBUG_MODE = false;
    private static final boolean ONLINE_DEBUG = false;
    private static Date date;
    private static String Time;
    private static String PREFIX;
    private final Random random = new Random();
    public static TextChannel textChannel;

    public App() {
        new SQL();
        config.config_load();
        date = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd aa hh:mm:ss z");
        Time = format1.format(date);
        CommandManager commandManager = new CommandManager();
        MemberCountListener memberCountListener = new MemberCountListener();
        Listener listener = new Listener(commandManager);
        filterListener filterlistener = new filterListener();
        ONIGIRIListener onigiriListener = new ONIGIRIListener();
        MuteListener muteListener = new MuteListener();
        nekoDiscordMemberListener nekoDiscordMemberListener = new nekoDiscordMemberListener();
        SteamServerStatusListener steamServerStatusListener = new SteamServerStatusListener();
        activityChangeListener activityChangeListener = new activityChangeListener();
        CroissantRoleListener croissantRoleListener = new CroissantRoleListener();
        messagePinListener messagePinListener = new messagePinListener();
        Logger logger = LoggerFactory.getLogger(App.class);

        StringBuilder TOKENreader = new StringBuilder();
        try {
            File file = new File("C:\\DiscordServerBotSecrets\\rito-bot\\TOKEN.txt");
            FileReader fileReader = new FileReader(file);
            int singalCh;
            while ((singalCh = fileReader.read()) != -1) {
                TOKENreader.append((char) singalCh);
            }
        } catch (Exception e) {

            StackTraceElement[] eStackTrace = e.getStackTrace();
            StringBuilder a = new StringBuilder();
            for (StackTraceElement stackTraceElement : eStackTrace) {
                a.append(stackTraceElement).append("\n");
            }
            logger.warn(a.toString());
        }
        StringBuilder TOKENreader_DEBUG = new StringBuilder();
        try {
            File file = new File("C:\\DiscordServerBotSecrets\\rito-bot\\TOKEN_DEBUG.txt");
            FileReader fileReader = new FileReader(file);
            int singalCh;
            while ((singalCh = fileReader.read()) != -1) {
                TOKENreader_DEBUG.append((char) singalCh);
            }
        } catch (Exception e) {

            StackTraceElement[] eStackTrace = e.getStackTrace();
            StringBuilder a = new StringBuilder();
            for (StackTraceElement stackTraceElement : eStackTrace) {
                a.append(stackTraceElement).append("\n");
            }
            logger.warn(a.toString());
        }

        String TOKEN;
        if (DEBUG_MODE) {
            TOKEN = TOKENreader_DEBUG.toString();
            PREFIX = Constants.PREFIX_DEBUG;
        } else if (!DEBUG_MODE && ONLINE_DEBUG) {
            TOKEN = TOKENreader.toString();
            PREFIX = Constants.PREFIX_DEBUG;
        } else {
            TOKEN = TOKENreader.toString();
            PREFIX = Constants.PREFIX;
        }

        WebUtils.setUserAgent("Chrome 75.0.3770.100 LiODi's discord bot/LiODi5572#5572");

        EmbedUtils.setEmbedBuilder(
                () -> new EmbedBuilder()
                        .setColor(getRandomColor())
                        .setFooter("Made By LiODi5572#5572", null)
        );

        try {
            logger.info("부팅");
            JDA jda = JDABuilder.createDefault(TOKEN)
                    .setAutoReconnect(true)
                    .addEventListeners(memberCountListener, listener, filterlistener, onigiriListener, nekoDiscordMemberListener,
                            muteListener, activityChangeListener, croissantRoleListener,
                            messagePinListener)
                    .setEnabledIntents(GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS))
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .build().awaitReady();
            logger.info("부팅완료");
            try {
                textChannel = Objects.requireNonNull(Objects.requireNonNull(jda.getGuildById("665581943382999048")).getTextChannelById("665581943382999051"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (LoginException | InterruptedException e) {

            StackTraceElement[] eStackTrace = e.getStackTrace();
            StringBuilder a = new StringBuilder();
            for (StackTraceElement stackTraceElement : eStackTrace) {
                a.append(stackTraceElement).append("\n");
            }
            logger.warn(a.toString());
        }
    }

    @NotNull
    private Color getRandomColor() {
        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();

        return new Color(r, g, b);
    }

    public static String getPREFIX() {
        return PREFIX;
    }

    public static boolean isDEBUG_MODE() {
        return DEBUG_MODE;
    }

    public static boolean isONLINE_DEBUG() {
        return ONLINE_DEBUG;
    }

    public static void main(String[] args) {
        new App();
    }

    public static String getTime() {
        return Time;
    }

    public static Date getDate() {
        return date;
    }
}
