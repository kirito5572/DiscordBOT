//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.kirito5572;

import com.kirito5572.listener.logger.LoggerListener;
import com.kirito5572.listener.main.CroissantRoleListener;
import com.kirito5572.listener.main.Listener;
import com.kirito5572.listener.main.MemberCountListener;
import com.kirito5572.listener.main.MuteListener;
import com.kirito5572.listener.main.ONIGIRIListener;
import com.kirito5572.listener.main.activityChangeListener;
import com.kirito5572.listener.main.filterListener;
import com.kirito5572.listener.main.messagePinListener;
import com.kirito5572.listener.main.nekoDiscordMemberListener;
import com.kirito5572.objects.main.CommandManager;
import com.kirito5572.objects.main.SQL;
import com.kirito5572.objects.main.config;
import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import javax.security.auth.login.LoginException;
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
        com.kirito5572.objects.logger.SQL loggerSQL = new com.kirito5572.objects.logger.SQL();
        config.config_load();
        com.kirito5572.objects.logger.config config = new com.kirito5572.objects.logger.config(loggerSQL);
        (new Thread(() -> {
            config.config_load();

            try {
                Thread.sleep(10000L);
            } catch (InterruptedException var2) {
                var2.printStackTrace();
            }

        })).start();
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
        activityChangeListener activityChangeListener = new activityChangeListener();
        CroissantRoleListener croissantRoleListener = new CroissantRoleListener();
        messagePinListener messagePinListener = new messagePinListener();
        com.kirito5572.listener.logger.Listener loggerModuleListener = new com.kirito5572.listener.logger.Listener();
        LoggerListener loggerListener = new LoggerListener(loggerSQL);
        Logger logger = LoggerFactory.getLogger(App.class);
        StringBuilder TOKENReader = new StringBuilder();

        StackTraceElement[] eStackTrace;
        int var23;
        try {
            File file = new File("C:\\DiscordServerBotSecrets\\rito-bot\\TOKEN.txt");
            FileReader fileReader = new FileReader(file);

            int signalCh;
            while((signalCh = fileReader.read()) != -1) {
                TOKENReader.append((char)signalCh);
            }
        } catch (Exception var30) {
            eStackTrace = var30.getStackTrace();
            StringBuilder a = new StringBuilder();
            int var22 = eStackTrace.length;

            for(var23 = 0; var23 < var22; ++var23) {
                StackTraceElement stackTraceElement = eStackTrace[var23];
                a.append(stackTraceElement).append("\n");
            }

            logger.warn(a.toString());
        }


        String TOKEN = TOKENReader.toString();
        PREFIX = "&";
        WebUtils.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) kirito's discord bot/kirito5572#5572");
        EmbedUtils.setEmbedBuilder(() -> (new EmbedBuilder()).setColor(this.getRandomColor()).setFooter("Made By kirito5572#5572", null));

        try {
            logger.info("부팅");
            JDA jda = JDABuilder.createDefault(TOKEN).setAutoReconnect(true).addEventListeners(memberCountListener, listener, filterlistener, onigiriListener, nekoDiscordMemberListener, muteListener, activityChangeListener, croissantRoleListener, messagePinListener, loggerModuleListener, loggerListener).setEnabledIntents(GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS)).setChunkingFilter(ChunkingFilter.ALL).build().awaitReady();
            logger.info("부팅완료");

            try {
                textChannel = Objects.requireNonNull(Objects.requireNonNull(jda.getGuildById("665581943382999048")).getTextChannelById("665581943382999051"));
            } catch (Exception var27) {
                var27.printStackTrace();
            }
        } catch (InterruptedException | LoginException e) {
            eStackTrace = e.getStackTrace();
            StringBuilder a = new StringBuilder();
            StackTraceElement[] var43 = eStackTrace;
            int var44 = eStackTrace.length;

            for(int var45 = 0; var45 < var44; ++var45) {
                StackTraceElement stackTraceElement = var43[var45];
                a.append(stackTraceElement).append("\n");
            }

            logger.warn(a.toString());
        }

    }

    @NotNull
    private Color getRandomColor() {
        float r = this.random.nextFloat();
        float g = this.random.nextFloat();
        float b = this.random.nextFloat();
        return new Color(r, g, b);
    }

    public static String getPREFIX() {
        return PREFIX;
    }

    public static boolean isDEBUG_MODE() {
        return false;
    }

    public static boolean isONLINE_DEBUG() {
        return false;
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
