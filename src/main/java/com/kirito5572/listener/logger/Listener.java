//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.kirito5572.listener.logger;

import com.kirito5572.App;
import java.io.File;
import java.io.FileReader;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Listener extends ListenerAdapter {
    private final Logger logger = LoggerFactory.getLogger(Listener.class);

    public Listener() {
    }

    public void onReady(@NotNull ReadyEvent event) {
        this.logger.info("logger_core 로그인 성공: " + event.getJDA().getSelfUser().getAsTag());
        System.out.println("logger_core 로그인 성공: " + event.getJDA().getSelfUser().getAsTag());
    }

    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        StringBuilder IDReader = new StringBuilder();
        StringBuilder IDReader1 = new StringBuilder();

        int signalCh;
        int signalCh1;
        try {
            File file = new File("C:\\DiscordServerBotSecrets\\rito-bot\\OWNER_ID.txt");
            File file1 = new File("C:\\DiscordServerBotSecrets\\rito-bot\\OWNER_ID1.txt");
            FileReader fileReader = new FileReader(file);
            FileReader fileReader1 = new FileReader(file1);

            while((signalCh = fileReader.read()) != -1) {
                IDReader.append((char)signalCh);
            }

            while((signalCh1 = fileReader1.read()) != -1) {
                IDReader1.append((char)signalCh1);
            }
        } catch (Exception var11) {
            StackTraceElement[] eStackTrace = var11.getStackTrace();
            StringBuilder a = new StringBuilder();
            signalCh = eStackTrace.length;

            for(signalCh1 = 0; signalCh1 < signalCh; ++signalCh1) {
                StackTraceElement stackTraceElement = eStackTrace[signalCh1];
                a.append(stackTraceElement).append("\n");
            }

            this.logger.warn(a.toString());
        }

        String ID1 = IDReader.toString();
        String ID2 = IDReader1.toString();
        if (event.getMessage().getContentRaw().equalsIgnoreCase(App.getPREFIX() + "종료 logger") && (event.getAuthor().getIdLong() == Long.decode(ID1) || event.getAuthor().getIdLong() == Long.decode(ID2))) {
            this.shutdown(event.getJDA(), event);
        }

    }

    private void shutdown(@NotNull JDA jda, @NotNull GuildMessageReceivedEvent event) {
        (new Thread(() -> {
            event.getChannel().sendMessage("logger_core 종료 완료").queue();

            try {
                Thread.sleep(500L);
            } catch (InterruptedException var10) {
                StackTraceElement[] eStackTrace = var10.getStackTrace();
                StringBuilder a = new StringBuilder();

                for (StackTraceElement stackTraceElement : eStackTrace) {
                    a.append(stackTraceElement).append("\n");
                }

                this.logger.warn(a.toString());
            }

            jda.shutdown();
            System.exit(0);
        })).start();
    }
}
