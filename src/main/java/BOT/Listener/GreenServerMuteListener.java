package BOT.Listener;

import BOT.App;
import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

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
                StringBuilder text_sb = new StringBuilder();
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

                System.out.println(discord_ID);
                System.out.println(discord_Name);
                System.out.println(Roles);


            }
        };
        Timer jobScheduler = new Timer();
        jobScheduler.scheduleAtFixedRate(job, 1000, 9000);
    }
}

