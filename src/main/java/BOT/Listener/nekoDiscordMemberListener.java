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

public class nekoDiscordMemberListener extends ListenerAdapter {
    private final Logger logger = LoggerFactory.getLogger(nekoDiscordMemberListener.class);
    private static String Chating1;
    private static String Chating2;
    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        Guild guild = event.getJDA().getGuildById("439780696999985172");
        assert guild != null;
        Role role = guild.getRoleById("625549604674600970");  //Chating1
        Role role1 = guild.getRoleById("620787764430110721");  //Chating2
        Member member = event.getMember();
        if(event.getMessageId().equals(Chating1)) {
            assert member != null;
            assert role != null;
            guild.addRoleToMember(member, role).complete();
        }
    }
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
        Guild guild = event.getJDA().getGuildById("439780696999985172");
        assert guild != null;
        Role role = guild.getRoleById("625549604674600970");  //Chating1
        Role role1 = guild.getRoleById("620787764430110721");  //Chating2
        Member member = event.getMember();
        if(event.getMessageId().equals(Chating1)) {
            assert member != null;
            assert role != null;
            guild.removeRoleFromMember(member, role).complete();
        }
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        StringBuilder Chating_temp = new StringBuilder();
        try {
            File file = new File("C:\\디스코드봇 파일들\\네코디코 리스너\\Chating1.txt");
            FileReader fileReader = new FileReader(file);
            int singalCh;
            while ((singalCh = fileReader.read()) != -1) {
                Chating_temp.append((char) singalCh);
            }
        } catch (Exception e) {
            e.printStackTrace();
            StackTraceElement[] eStackTrace = e.getStackTrace();
            StringBuilder a = new StringBuilder();
            for (StackTraceElement stackTraceElement : eStackTrace) {
                a.append(stackTraceElement).append("\n");
            }
            logger.warn(a.toString());
        }
        Chating1 = Chating_temp.toString();
        try {
            File file = new File("C:\\디스코드봇 파일들\\네코디코 리스너\\Chating2.txt");
            FileReader fileReader = new FileReader(file);
            int singalCh;
            while ((singalCh = fileReader.read()) != -1) {
                Chating_temp.append((char) singalCh);
            }
        } catch (Exception e) {
            e.printStackTrace();
            StackTraceElement[] eStackTrace = e.getStackTrace();
            StringBuilder a = new StringBuilder();
            for (StackTraceElement stackTraceElement : eStackTrace) {
                a.append(stackTraceElement).append("\n");
            }
            logger.warn(a.toString());
        }
        Chating2 = Chating_temp.toString();
    }
}
