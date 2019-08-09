package BOT.Listener;

import BOT.Objects.ONIGIRIList;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.File;
import java.net.URL;

public class ONIGIRIListener extends ListenerAdapter {
    private String[] List = ONIGIRIList.getList();
    private String[] list_File = ONIGIRIList.getList_file();
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getGuild().getId().equals("453817631603032065")) {
            for(int i = 0; i < List.length; i++) {
                if (event.getMessage().getContentRaw().equals(List[i])) {
                    URL certification_Img_URL1 = getClass().getClassLoader().getResource(list_File[i]);
                    File file1 = new File(certification_Img_URL1.getPath());
                    event.getChannel().sendFile(file1).complete();
                }
            }
        }
    }
}
