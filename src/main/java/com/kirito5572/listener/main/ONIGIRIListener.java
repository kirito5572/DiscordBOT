package com.kirito5572.listener.main;

import com.kirito5572.objects.main.ONIGIRIList;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Objects;

public class ONIGIRIListener extends ListenerAdapter {
    private final Logger logger = LoggerFactory.getLogger(ONIGIRIListener.class);
    private final String[] List = ONIGIRIList.getList();
    private final String[] list_File = ONIGIRIList.getList_file();
    private final String[] list_Suffix = ONIGIRIList.getList_Suffix();
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Guild guild;
        try {
            guild = event.getGuild();
        } catch (Exception ignored) {
            return;
        }
        if(guild.getId().equals("453817631603032065")) { //453817631603032065
            if(event.getAuthor().isBot()) {

                return;
            }
            if(Objects.requireNonNull(event.getGuild().getMemberById("556449367863590923")).getOnlineStatus().toString().equals("OFFLINE")) {
                for (int i = 0; i < List.length; i++) {
                    if (event.getMessage().getContentRaw().equals(List[i])) {
                        InputStream inputStream = this.getClass().getResourceAsStream("/" + list_File[i]);
                        File file;
                        try {
                            file = convertInputStreamToFile(inputStream, list_Suffix[i]);
                        } catch (IOException e) {

                            StackTraceElement[] eStackTrace = e.getStackTrace();
                            StringBuilder a = new StringBuilder();
                            for (StackTraceElement stackTraceElement : eStackTrace) {
                                a.append(stackTraceElement).append("\n");
                            }
                            logger.warn(a.toString());

                            event.getChannel().sendMessage("에러가 발생했습니다.").queue();

                            return;
                        }
                        try {
                            event.getChannel().sendFile(file).queue();
                        } catch (Exception e) {

                            StackTraceElement[] eStackTrace = e.getStackTrace();
                            StringBuilder a = new StringBuilder();
                            for (StackTraceElement stackTraceElement : eStackTrace) {
                                a.append(stackTraceElement).append("\n");
                            }
                            logger.warn(a.toString());
                        }
                    }
                }
            }
        }
    }
    @NotNull
    public static File convertInputStreamToFile(@NotNull InputStream is, String suffix) throws IOException {
        File file = File.createTempFile("C://temp", suffix);

        OutputStream outputStream = new FileOutputStream(file);

        IOUtils.copy(is, outputStream);

        outputStream.close();

        return file;
    }
}
