package BOT.Listener;

import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class GreenAutoBanListener extends ListenerAdapter {
    @Override
    public void onReady(ReadyEvent event) {
        Guild guild = event.getJDA().getGuildById("508913681279483913"); //600010501266866186
        TextChannel channel = guild.getTextChannelById("593991995433680924");
        //TextChannel fianl_channel = guild.getTextChannelById("600015587544006679");
        TimerTask job = new TimerTask() {
            @Override
            public void run() {
                String name;
                String SteamID;
                String SCPRole;
                try{
                    //파일 객체 생성
                    Date date = new Date();
                    Date date1 = new Date(date.getTime() - 299000L);
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:MM:ss");
                    date = new Date(date.getTime() - 300000L);
                    String a = sdf.format(date);
                    File file = new File("C:\\디스코드 유저 제재기록\\[" + a + "]탈주.txt");
                    String a1 = sdf.format(date);
                    try {
                        File file1 = new File("C:\\디스코드 유저 제재기록\\[" + a1 + "]재접속.txt");
                        file1.delete();
                    } catch (Exception ignored) {

                    }
                    //입력 스트림 생성
                    FileReader filereader = new FileReader(file);
                    //입력 버퍼 생성
                    BufferedReader bufReader = new BufferedReader(filereader);
                    String line = "";
                    String lastline = "";
                    while((line = bufReader.readLine()) != null){
                        lastline = line;
                    }
                    bufReader.close();
                    file.delete();

                    name = lastline.substring(lastline.indexOf("이름:") + 3, lastline.indexOf(" 프로필:"));
                    SteamID = lastline.substring(lastline.indexOf("프로필:") + 4, lastline.indexOf(" 역할:"));
                    SCPRole = lastline.substring(lastline.indexOf("역할:") + 3);

                    EmbedBuilder builder = EmbedUtils.defaultEmbed()
                            .setTitle("SCP 탈주 기록")
                            .setColor(Color.RED)
                            .addField("유저명", name, false)
                            .addField("Steam ID", SteamID, false)
                            .addField("탈주시 역할", SCPRole, false);
                    channel.sendMessage(builder.build()).queue();

                    EmbedBuilder builder1 = EmbedUtils.defaultEmbed()
                            .setTitle("SCP 탈주 기록")
                            .setColor(Color.RED)
                            .addField("유저명", name, false)
                            .addField("Steam ID", SteamID, false)
                            .addField("탈주시 역할", SCPRole, false)
                            .addField("처리 담당자:", "SYSTEM AUTO BAN", false)
                            .setFooter("해당 탈주의 대한 이의제기 담당자는 kirito5572입니다. ","");
                    //.readLine()은 끝에 개행문자를 읽지 않는다.
                }catch (FileNotFoundException e) {
                    return;
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        };
        Timer jobScheduler = new Timer();
        jobScheduler.scheduleAtFixedRate(job, 1000, 1000);


    }
}
