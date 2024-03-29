package com.kirito5572.commands.main;

import com.kirito5572.objects.main.EventPackage;
import com.kirito5572.objects.main.ICommand;
import com.kirito5572.objects.main.config;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.kirito5572.listener.main.ONIGIRIListener.convertInputStreamToFile;

public class lewdnekoCommand implements ICommand {
    private final Logger logger = LoggerFactory.getLogger(lewdnekoCommand.class);
    @Override
    public void handle(@NotNull List<String> args, @NotNull EventPackage event) {
        int num = 1;
        try {
            num = Integer.parseInt(args.get(0));
        } catch (Exception ignored) {

        }
        if(num > 10) {
            event.getChannel().sendMessage("최대 전송 가능량은 10개입니다.").queue();

            return;
        }

        for(int i = 0; i < num; i++ ) {
            WebUtils.ins.scrapeWebPage("https://nekos.life/lewd").async((document) -> {
                String a = document.toString();
                int b = a.indexOf("img src=\"");
                a = a.substring(b);
                int c = a.indexOf("\" alt=\"neko");
                a = a.substring(9, c);
                EmbedBuilder embed = EmbedUtils.embedImage(a);
                boolean sendFlag = true;
                String[] lewdNekoDisable = config.getLewdNekoDisable();
                for (String s : lewdNekoDisable) {
                    if (event.getGuild().getId().equals(s)) {
                        sendFlag = false;
                    }
                }
                if (sendFlag) {
                    event.getAuthor().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessageEmbeds(embed.build()).queue());
                } else {
                    InputStream inputStream = this.getClass().getResourceAsStream("/" + "haha1.jpg");
                    File file;
                    try {
                        file = convertInputStreamToFile(inputStream, ".jpg");
                    } catch (IOException e) {

                        StackTraceElement[] eStackTrace = e.getStackTrace();
                        StringBuilder ab = new StringBuilder();
                        for (StackTraceElement stackTraceElement : eStackTrace) {
                            ab.append(stackTraceElement).append("\n");
                        }
                        logger.warn(ab.toString());

                        event.getChannel().sendMessage("에러가 발생했습니다.").queue();

                        return;
                    }
                    event.getChannel().sendMessage("당신의 흑심! 너굴맨이 처리했으니까 안심하라구!").addFile(file).queue();
                }

            });
        }
    }

    @NotNull
    @Override
    public String getHelp() {
        return "lewd 콘텐츠 커맨드이므로 사용에 주의하여 주시기 바랍니다.";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "후방주의네코";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "other";
    }
}
