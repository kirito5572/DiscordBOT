package BOT.Listener;

import BOT.Objects.SQL;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GreenAutoDBWriteListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getAuthor().getId().equals("593447324127068173")) {
            if(event.getChannel().getId().equals("600015587544006679")) {
                MessageEmbed embedMessage = event.getMessage().getEmbeds().get(0);
                String name = "";
                String SteamID = "";
                String reason = "";
                String time = "";
                if(embedMessage.getFields().get(0).getName().equals("이름")) {
                    name = embedMessage.getFields().get(0).getValue();
                }
                if(embedMessage.getFields().get(1).getName().equals("스팀 ID")) {
                    SteamID = embedMessage.getFields().get(1).getValue();
                }
                if(embedMessage.getFields().get(2).getName().equals("위반 규정 조항")) {
                    reason = embedMessage.getFields().get(2).getValue();
                }
                if(embedMessage.getFields().get(3).getName().equals("정지 기간")) {
                    time = embedMessage.getFields().get(3).getValue();
                }
                if(!(name.equals("") || SteamID.equals("") || reason.equals("") || time.equals(""))) {
                    SQL.SQLupload(SteamID, time, reason, event.getAuthor().getName());
                }
            }
        }
    }
}
