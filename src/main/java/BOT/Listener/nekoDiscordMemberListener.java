package BOT.Listener;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.ReadyEvent;
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
        TimerTask job = new TimerTask() {
            @Override
            public void run() {
                Guild guild = event.getJDA().getGuildById("439780696999985172");
                Role role = guild.getRoleById("625549604674600970");  //Chating1
                Role role1 = guild.getRoleById("620787764430110721");  //Chating2
                List<Member> members = guild.getMembers();
                List<User> users = guild.getTextChannelById("616452604506931230").getMessageById(Chating1).complete().getReactions().get(0).getUsers().complete();
                try {
                    for (User user : users) {
                        Member member = guild.getMemberById(user.getId());
                        if (!member.getRoles().contains(role)) {
                            guild.getController().addSingleRoleToMember(member, role).complete();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /*
                try {
                    List<User> users2 = guild.getTextChannelById("616452604506931230").getMessageById(Chating2).complete().getReactions().get(0).getUsers().complete();
                    for (User user : users2) {
                        Member member = guild.getMemberById(user.getId());
                        if (!member.getRoles().contains(role)) {
                            guild.getController().addSingleRoleToMember(member, role1).complete();
                        }
                    }
                } catch (Exception ignored) {

                }

                 */
            }
        };
        Timer jobScheduler = new Timer();
        jobScheduler.scheduleAtFixedRate(job, 20, 2000);

    }
}
