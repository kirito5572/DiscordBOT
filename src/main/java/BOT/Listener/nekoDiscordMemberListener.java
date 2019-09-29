package BOT.Listener;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class nekoDiscordMemberListener extends ListenerAdapter {
    private static String Chating1;
    private static String Chating2;
    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        Guild guild = event.getJDA().getGuildById("439780696999985172");
        Role role = guild.getRoleById("625549604674600970");  //Chating1
        Role role1 = guild.getRoleById("620787764430110721");  //Chating2
        Member member = event.getMember();
        if(event.getMessageId().equals(Chating1)) {
            guild.getController().addSingleRoleToMember(member, role).complete();
        }
    }
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
        Guild guild = event.getJDA().getGuildById("439780696999985172");
        Role role = guild.getRoleById("625549604674600970");  //Chating1
        Role role1 = guild.getRoleById("620787764430110721");  //Chating2
        Member member = event.getMember();
        if(event.getMessageId().equals(Chating1)) {
            guild.getController().removeSingleRoleFromMember(member, role).complete();
        }
    }

    @Override
    public void onReady(ReadyEvent event) {
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
        }
        Chating2 = Chating_temp.toString();
    }
}
