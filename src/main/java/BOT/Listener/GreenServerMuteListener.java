package BOT.Listener;

import BOT.App;
import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class GreenServerMuteListener extends ListenerAdapter {
    @Override
    public void onReady(ReadyEvent event) {
        TimerTask job = new TimerTask() {
            @Override
            public void run() {
                Guild guild = event.getJDA().getGuildById("600010501266866186");
                TextChannel channel = guild.getTextChannelById("609781460785692672");
                File file;
                Date date = new Date();
                String time;
                time = String.valueOf(date.getTime());
                time = time.substring(0, time.length() - 4);
                time += "0000";
                try {
                    System.out.println(time + "txt");
                    file = new File("C:\\디스코드 유저 제재기록\\" + time + ".txt");
                } catch (Exception e) {

                    return;
                }
                FileReader reader;

                try {
                    reader = new FileReader(file);
                } catch (FileNotFoundException e) {

                    return;
                }
                String text;
                try {
                    char[] char_array = new char[2048];
                    reader.read(char_array);
                    text = String.valueOf(char_array).trim();
                } catch (IOException e) {
                    e.printStackTrace();

                    return;
                }
                try {
                    reader.close();
                    if(!file.delete()) {
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();

                    return;
                }
                String discord_ID = text.substring(text.indexOf("Discord ID = ") + 13, text.indexOf("Discord name = ") - 1);
                String discord_Name = text.substring(text.indexOf("Discord name = ") + 15, text.indexOf("Roles = ") - 1);
                String Roles = text.substring(text.indexOf("Roles = ") + 8);

                List<User> foundUsers = FinderUtil.findUsers(discord_ID, event.getJDA());

                if(foundUsers.isEmpty()) {
                    List<Member> foundMember = FinderUtil.findMembers(discord_ID, guild);
                    if(foundMember.isEmpty()) {

                        return;
                    }
                    foundUsers = foundMember.stream().map(Member::getUser).collect(Collectors.toList());
                }
                User user = foundUsers.get(0);
                Member member = guild.getMember(user);

                Role roles = guild.getRolesByName("채팅 금지", true).get(0);

                guild.getController().removeSingleRoleFromMember(member, roles).complete();

                String Roletemp = Roles.substring(Roles.indexOf("^"));
                for(;;) {
                    try {
                        String Role = Roletemp.substring(1, Roletemp.indexOf("$"));
                        Role giveRole = guild.getRolesByName(Role, true).get(0);
                        System.out.println("role" + Role);

                        guild.getController().addSingleRoleToMember(member, giveRole).complete();
                        try {
                            Roletemp = Roletemp.substring(Roletemp.indexOf("$") + 2);
                            System.out.println(Roletemp);
                        } catch (Exception e) {
                                StringBuilder rolesb = new StringBuilder();
                                for (int i = 0; i < member.getRoles().size(); i++) {
                                    rolesb.append(member.getRoles().get(i).getAsMention()).append("\n");
                                }
                                EmbedBuilder builder = EmbedUtils.defaultEmbed()
                                        .setTitle("채팅 금지 제재 해제")
                                        .addField("채팅 정지 당시 유저명", discord_Name, false)
                                        .addField("현재 유저명", user.getName(), false)
                                        .addField("멘션명", member.getAsMention(), false)
                                        .addField("복구되는 역할", rolesb.toString(), false)
                                        .setColor(Color.GREEN);
                                channel.sendMessage(builder.build()).queue();

                                break;
                        }
                    }catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            }
        };
        Timer jobScheduler = new Timer();
        jobScheduler.scheduleAtFixedRate(job, 1000, 9000);
    }
}

