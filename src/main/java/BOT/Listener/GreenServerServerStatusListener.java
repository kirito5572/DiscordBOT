package BOT.Listener;

import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

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
                try {
                    greenServer = event.getJDA().getGuildById("600010501266866186");
                    channel = greenServer.getTextChannelById("600015521433518090");

                } catch (Exception e) {

                    return;
                }
                String[] status = new String[5];
                status[0] = greenServer.getMemberById("580691748276142100").getOnlineStatus().toString();
                status[1] = greenServer.getMemberById("586590053539643408").getOnlineStatus().toString();
                status[2] = greenServer.getMemberById("600658772876197888").getOnlineStatus().toString();
                status[3] = greenServer.getMemberById("600660530230722560").getOnlineStatus().toString();
                status[4] = greenServer.getMemberById("600676751118696448").getOnlineStatus().toString();

                for(int i = 0; i < 5; i++) {
                    switch (status[i]){
                        case "online":
                        case "idle": status[i] = "ON"; break;
                        case "dnd":
                        case "offline": status[i] =  "OFF"; break;
                        default: status[i] = "UNKNOWN"; break;
                    }
                }

                EmbedBuilder builder = EmbedUtils.defaultEmbed()
                        .setTitle("서버 오픈 상태")
                        .addField("1서버", status[0], true)
                        .addField("2서버", status[1], true)
                        .addField("3서버", status[2], true)
                        .addField("4서버", status[3], true)
                        .addField("5서버", status[4], true);

                channel.editMessageById("613705525200420875", builder.build()).queue();
            }
        };
        Timer jobScheduler = new Timer();
        jobScheduler.scheduleAtFixedRate(job, 1000, 9000);
    }
}
