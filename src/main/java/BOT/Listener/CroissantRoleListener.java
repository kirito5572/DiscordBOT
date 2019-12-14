package BOT.Listener;

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

import java.io.File;
import java.io.FileReader;

public class CroissantRoleListener extends ListenerAdapter {
    private final Logger logger = LoggerFactory.getLogger(CroissantRoleListener.class);
    private static File[] files = new File[] {
            new File("C:\\디스코드봇 파일들\\크루와상 리스너\\R6.txt"),
            new File("C:\\디스코드봇 파일들\\크루와상 리스너\\PUBG.txt"),
            new File("C:\\디스코드봇 파일들\\크루와상 리스너\\WAR.txt"),
            new File("C:\\디스코드봇 파일들\\크루와상 리스너\\STAR1.txt"),
            new File("C:\\디스코드봇 파일들\\크루와상 리스너\\WarGaming.txt"),
            new File("C:\\디스코드봇 파일들\\크루와상 리스너\\Nexon.txt"),
            new File("C:\\디스코드봇 파일들\\크루와상 리스너\\LoL.txt"),
            new File("C:\\디스코드봇 파일들\\크루와상 리스너\\OverWatch.txt"),
            new File("C:\\디스코드봇 파일들\\크루와상 리스너\\GTA.txt"),
            new File("C:\\디스코드봇 파일들\\크루와상 리스너\\BattleField.txt"),
    };
    private static String[] role_id = new String[] {
            "655340527017000971",
            "655340598076899338",
            "655340619371380756",
            "655340731065565225",
            "655340810451025930",
            "655340864641695745",
            "655340894916182047",
            "655340921243697152",
            "655340955863351357",
            "655340968563965983"
    };
    private static String[] Chating = new String[files.length];
    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        Guild guild = event.getGuild();
        if(guild.getId().equals("508913681279483913")) {
            Member member = event.getMember();
            for (int i = 0; i < files.length; i++) {
                try {
                    if (event.getMessageId().equals(Chating[i])) {
                        Role role = event.getGuild().getRoleById(role_id[i]);
                        assert member != null;
                        assert role != null;
                        guild.addRoleToMember(member, role).complete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
        Guild guild = event.getGuild();
        if(guild.getId().equals("508913681279483913")) {
            Member member = event.getMember();
            for (int i = 0; i < files.length; i++) {
                try {
                    if (event.getMessageId().equals(Chating[i])) {
                        Role role = event.getGuild().getRoleById(role_id[i]);
                        guild.removeRoleFromMember(member, role).complete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        for(int i = 0; i < files.length; i++) {
            try {
                StringBuilder Chating_temp = new StringBuilder();
                File file = files[i];
                FileReader fileReader = new FileReader(file);
                int singalCh;
                while ((singalCh = fileReader.read()) != -1) {
                    Chating_temp.append((char) singalCh);
                }
                Chating[i] = Chating_temp.toString();
            } catch (Exception e) {
                StackTraceElement[] eStackTrace = e.getStackTrace();
                StringBuilder a = new StringBuilder();
                for (StackTraceElement stackTraceElement : eStackTrace) {
                    a.append(stackTraceElement).append("\n");
                }
                logger.warn(a.toString());
            }
        }
    }
}
