package BOT.Listener;

import BOT.App;
import BOT.Objects.ONIGIRIList;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.URL;

public class ONIGIRIListener extends ListenerAdapter {
    private String[] List = ONIGIRIList.getList();
    private String[] list_File = ONIGIRIList.getList_file();
    private String[] list_Suffix = ONIGIRIList.getList_Suffix();
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getGuild().getId().equals("453817631603032065")) { //453817631603032065
            if(event.getAuthor().isBot()) {

                return;
            }
            if(event.getGuild().getMemberById("556449367863590923").getOnlineStatus().toString().equals("OFFLINE")) {
                for (int i = 0; i < List.length; i++) {
                    if (event.getMessage().getContentRaw().equals(List[i])) {
                        InputStream inputStream = this.getClass().getResourceAsStream("/" + list_File[i]);
                        File file;
                        try {
                            file = convertInputStreamToFile(inputStream, list_Suffix[i]);
                        } catch (IOException e) {
                            e.printStackTrace();

                            event.getChannel().sendMessage("에러가 발생했습니다.").queue();

                            return;
                        }
                        try {
                            event.getChannel().sendFile(file).complete();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    public static File convertInputStreamToFile(InputStream is, String suffix) throws IOException {
        File file = File.createTempFile("C://temp", suffix);

        OutputStream outputStream = new FileOutputStream(file);

        IOUtils.copy(is, outputStream);

        outputStream.close();

        return file;
    }
}
