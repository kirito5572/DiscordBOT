//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.kirito5572.listener.main;

import java.io.File;
import java.io.FileReader;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CroissantRoleListener extends ListenerAdapter {
    private final Logger logger = LoggerFactory.getLogger(CroissantRoleListener.class);
    private static final File[] files = new File[]{new File("C:\\디스코드봇 파일들\\크루와상 리스너\\R6.txt"), new File("C:\\디스코드봇 파일들\\크루와상 리스너\\PUBG.txt"), new File("C:\\디스코드봇 파일들\\크루와상 리스너\\WAR.txt"), new File("C:\\디스코드봇 파일들\\크루와상 리스너\\STAR1.txt"), new File("C:\\디스코드봇 파일들\\크루와상 리스너\\WarGaming.txt"), new File("C:\\디스코드봇 파일들\\크루와상 리스너\\Nexon.txt"), new File("C:\\디스코드봇 파일들\\크루와상 리스너\\LoL.txt"), new File("C:\\디스코드봇 파일들\\크루와상 리스너\\OverWatch.txt"), new File("C:\\디스코드봇 파일들\\크루와상 리스너\\GTA.txt"), new File("C:\\디스코드봇 파일들\\크루와상 리스너\\BattleField.txt"), new File("C:\\디스코드봇 파일들\\크루와상 리스너\\타르코프.txt"), new File("C:\\디스코드봇 파일들\\크루와상 리스너\\발로란트.txt")};
    private static final String[] role_id = new String[]{"655340527017000971", "655340598076899338", "655340619371380756", "655340731065565225", "655340810451025930", "655340864641695745", "655340894916182047", "655340921243697152", "655340955863351357", "655340968563965983", "708316944758996995", "708316991643189368"};
    private static final String[] Chatting;

    public CroissantRoleListener() {
    }

    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        Guild guild = event.getGuild();
        if (guild.getId().equals("508913681279483913")) {
            Member member = event.getMember();

            for(int i = 0; i < files.length; ++i) {
                try {
                    if (event.getMessageId().equals(Chatting[i])) {
                        Role role = event.getGuild().getRoleById(role_id[i]);

                        assert member != null;

                        assert role != null;

                        guild.addRoleToMember(member, role).complete();
                    }
                } catch (Exception var6) {
                    var6.printStackTrace();
                }
            }
        }

    }

    public void onMessageReactionRemove(@NotNull MessageReactionRemoveEvent event) {
        Guild guild = event.getGuild();
        if (guild.getId().equals("508913681279483913")) {
            Member member = event.getMember();

            for(int i = 0; i < files.length; ++i) {
                try {
                    if (event.getMessageId().equals(Chatting[i])) {
                        Role role = event.getGuild().getRoleById(role_id[i]);

                        assert member != null;

                        assert role != null;

                        guild.removeRoleFromMember(member, role).complete();
                    }
                } catch (Exception var6) {
                    var6.printStackTrace();
                }
            }
        }

    }

    public void onReady(@NotNull ReadyEvent event) {
        for(int i = 0; i < files.length; ++i) {
            try {
                StringBuilder Chatting_temp = new StringBuilder();
                File file = files[i];
                FileReader fileReader = new FileReader(file);

                int signalCh;
                while((signalCh = fileReader.read()) != -1) {
                    Chatting_temp.append((char)signalCh);
                }

                Chatting[i] = Chatting_temp.toString();
            } catch (Exception var10) {
                StackTraceElement[] eStackTrace = var10.getStackTrace();
                StringBuilder a = new StringBuilder();
                for (StackTraceElement stackTraceElement : eStackTrace) {
                    a.append(stackTraceElement).append("\n");
                }

                this.logger.warn(a.toString());
            }
        }

    }

    static {
        Chatting = new String[files.length];
    }
}
