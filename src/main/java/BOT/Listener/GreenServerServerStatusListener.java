package BOT.Listener;

import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class GreenServerServerStatusListener extends ListenerAdapter {
    @Override
    public void onReady(ReadyEvent event) {
        TimerTask job = new TimerTask() {
            @Override
            public void run() {
                Guild greenServer;
                TextChannel channel;
                String[] status = new String[5];
                try {
                    greenServer = event.getJDA().getGuildById("600010501266866186");
                    channel = greenServer.getTextChannelById("600015521433518090");
                    status[0] = greenServer.getMemberById("580691748276142100").getOnlineStatus().toString();
                    status[1] = greenServer.getMemberById("586590053539643408").getOnlineStatus().toString();
                    status[2] = greenServer.getMemberById("600658772876197888").getOnlineStatus().toString();
                    status[3] = greenServer.getMemberById("600660530230722560").getOnlineStatus().toString();
                    status[4] = greenServer.getMemberById("600676751118696448").getOnlineStatus().toString();

                } catch (Exception e) {

                    return;
                }

                for(int i = 0; i < 5; i++) {
                    switch (status[i]){
                        case "ONLINE":
                        case "IDLE": status[i] = "ON"; break;
                        case "DO NOT DISTUBE":
                        case "OFFLINE": status[i] =  "OFF"; break;
                        default: status[i] = "UNKNOWN"; break;
                    }
                }

                EmbedBuilder builder = EmbedUtils.defaultEmbed()
                        .setTitle("서버 오픈 상태")
                        .setColor(Color.GREEN)
                        .addField("Green Color 상태", "ON", false)
                        .addField("1서버", status[0], false)
                        .addField("2서버", status[1], false)
                        .addField("3서버", status[2], false)
                        .addField("4서버", status[3], false)
                        .addField("5서버", status[4], false)
                        .setFooter("1분마다 서버 상태가 자동 새로고침됩니다.","https://steamuserimages-a.akamaihd.net/ugc/982233321887038211/EB88C5E32425929921EF653FF5B784715B7D0639/");
                channel.editMessageById("616234404452499476", builder.build()).queue();

            }
        };
        Timer jobScheduler = new Timer();
        jobScheduler.scheduleAtFixedRate(job, 1000, 300000);
    }
}
