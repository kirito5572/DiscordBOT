package BOT.Listener;

import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class GreenAutoBanListener extends ListenerAdapter {
    private final String file_Location = "C:\\Users\\Administrator\\Documents\\SCP Secret Laboratory Dedicated Server\\logger\\";
    private User user;
    @Override
    public void onReady(ReadyEvent event) {
        TimerTask job = new TimerTask() {
            @Override
            public void run() {
                TextChannel channel;
                TextChannel final_channel;
                TextChannel server1;
                TextChannel server5;
                try {
                    Guild guild = event.getJDA().getGuildById("600010501266866186");
                    channel = guild.getTextChannelById("613153421373079552");
                    final_channel = guild.getTextChannelById("600015587544006679");
                    server1 = guild.getTextChannelById("609055524851286027");
                    server5 = guild.getTextChannelById("609057247116525650");
                    Member member = guild.getMemberById("284508374924787713");
                    user = member.getUser();
                } catch (NullPointerException e) {
                    return;
                }
                String name;
                String SteamID;
                String SCPRole;
                String time;
                try{
                    //파일 객체 생성
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("");
                    File file = new File(file_Location + "탈주.txt");
                    String a1 = sdf.format(date);
                    File file1 = new File(file_Location + "재접속.txt");
                    File file2 = new File( file_Location + "마지막 탈주.txt");
                    //입력 스트림 생성
                    FileReader filereader;
                    FileReader fileReader;
                    try {
                        filereader = new FileReader(file);
                    } catch (Exception e) {
                        FileWriter writer;

                        writer = new FileWriter(file, false);
                        writer.write(" ");
                        writer.flush();

                        return;
                    }
                    try {
                        fileReader = new FileReader(file2);
                    } catch (Exception e) {
                        FileWriter writer;

                        writer = new FileWriter(file2, false);
                        writer.write(" ");
                        writer.flush();

                        return;
                    }
                    //입력 버퍼 생성
                    BufferedReader bufReader = new BufferedReader(filereader);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String line = "";
                    String lastline = "";
                    String finalline = "";
                    while((line = bufReader.readLine()) != null) {
                        lastline = line;
                    }
                    bufReader.close();
                    while ((line = bufferedReader.readLine()) != null) {
                        finalline = line;
                    };
                    bufferedReader.close();
                    if(finalline.equals(lastline)) {
                        return;

                    } else {
                        finalline = lastline;
                        FileWriter writer;


                        writer = new FileWriter(file2, false);
                        writer.write(finalline);
                        writer.flush();

                        writer.close();

                        time = finalline.substring(finalline.indexOf("[") + 1, finalline.indexOf("]"));
                        name = finalline.substring(finalline.indexOf("이름:") + 3, finalline.indexOf(" 프로필:"));
                        SteamID = finalline.substring(finalline.indexOf("프로필:") + 4, finalline.indexOf(" 역할:"));
                        SCPRole = finalline.substring(finalline.indexOf("역할:") + 3);

                        EmbedBuilder builder = EmbedUtils.defaultEmbed()
                                .setTitle("SCP 탈주 기록")
                                .setColor(Color.RED)
                                .addField("탈주 시간", time, false)
                                .addField("유저명", name, false)
                                .addField("Steam ID", SteamID, false)
                                .addField("탈주시 역할", SCPRole, false);
                        channel.sendMessage(builder.build()).queue();
                    }

                    SCP_Ban_Timer(name, SteamID, SCPRole, finalline, channel, final_channel, server1, server5);

                    reconnect(file1);

                }catch (FileNotFoundException e) {
                    return;
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        };
        Timer jobScheduler = new Timer();
        jobScheduler.scheduleAtFixedRate(job, 100, 1000);
    }
    private void SCP_Ban_Timer(String name, String SteamID, String SCPRole, String sline, TextChannel channel, TextChannel channel2, TextChannel server1, TextChannel server5) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    File file = new File(file_Location + SteamID + ".txt");
                    String line;
                    String lastline = "";
                    try {
                        FileReader reader = new FileReader(file);
                        BufferedReader bufferedReader = new BufferedReader(reader);
                        while ((line = bufferedReader.readLine()) != null) {
                            lastline = line;
                        }
                    } catch (Exception e) {
                        lastline = "";
                    }
                    if(sline.equals(lastline)) {
                        if(file.exists()) {
                            boolean complete = false;
                            while(!complete) {
                                complete = file.delete();
                            }
                        }
                        EmbedBuilder builder = EmbedUtils.defaultEmbed()
                                .setTitle("SCP 재접속 기록")
                                .setColor(Color.GREEN)
                                .addField("유저명", name, false)
                                .addField("Steam ID", SteamID, false)
                                .addField("재부여된 역할", SCPRole, false);

                        channel.sendMessage(builder.build()).queue();
                    } else {
                        EmbedBuilder builder1 = EmbedUtils.defaultEmbed()
                                .setTitle("SCP 탈주 제재")
                                .setColor(Color.RED)
                                .addField("유저명", name, false)
                                .addField("Steam ID", SteamID, false)
                                .addField("탈주시 역할", SCPRole, false)
                                .addField("처리 담당자:", "SYSTEM", false)
                                .addField("소명 신청 담당자: ", user.getAsMention(), false);
                        channel2.sendMessage(builder1.build()).queue();

                        server1.sendMessage("+oban " + name + " " + SteamID + " 60").queue();
                        server5.sendMessage("+oban " + name + " " + SteamID + " 60").queue();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        timer.schedule(timerTask, 300000);
    }
    private void reconnect(File refile) {
        String line;
        String lastline = "";
        String finalline = "";
        try {
            File file2 = new File(file_Location + "마지막 재접속.txt");
            FileReader filereader = new FileReader(refile);
            FileReader fileReader;
            try {
                fileReader = new FileReader(file2);
            } catch (Exception e) {
                FileWriter writer;

                writer = new FileWriter(file2, false);
                writer.write(" ");
                writer.flush();

                return;
            }
            BufferedReader bufReader = new BufferedReader(filereader);
            while ((line = bufReader.readLine()) != null) {
                lastline = line;
            }
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                finalline = line;
            }
            bufReader.close();
            bufferedReader.close();

            if(lastline.equals(finalline)) {

                return;
            } else {
                finalline = lastline;
                FileWriter writer;


                writer = new FileWriter(file2, false);
                writer.write(finalline);
                writer.flush();

                writer.close();

                String time = finalline.substring(finalline.indexOf("[") + 1, finalline.indexOf("]"));
                String name = finalline.substring(finalline.indexOf("이름:") + 3, finalline.indexOf(" 프로필:"));
                String SteamID = finalline.substring(finalline.indexOf("프로필:") + 4, finalline.indexOf(" 역할:"));
                String SCPRole = finalline.substring(finalline.indexOf("역할:") + 3);

                File file3 = new File(file_Location + SteamID + ".txt");

                FileWriter writer1;

                writer1 = new FileWriter(file3, false);
                writer1.write(finalline);
                writer1.flush();

                writer1.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
