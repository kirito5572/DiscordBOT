package BOT.Listener;

import BOT.App;
import BOT.Constants;
import BOT.Objects.greenCommandManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.util.Objects;

public class greenListener extends ListenerAdapter {
    private final greenCommandManager manager;
    private final Logger logger = LoggerFactory.getLogger(Listener.class);

    public greenListener(greenCommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void onReady(ReadyEvent event) {
        logger.info(String.format("로그인 성공: %#s", event.getJDA().getSelfUser()));
        System.out.println(String.format("로그인 성공: %#s", event.getJDA().getSelfUser()));
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        User author = event.getAuthor();
        Message message = event.getMessage();
        String content = message.getContentDisplay();

        if (event.isFromType(ChannelType.TEXT)) {

            Guild guild = event.getGuild();
            TextChannel textChannel = event.getTextChannel();

            logger.info(String.format("\n" +
                    "보낸사람: %#s\n" +
                    "친 내용: %s\n" +
                    "[서버: %s]  " +
                    "[채팅방: %s]", author, content, guild.getName(), textChannel.getName()));
        } else if (event.isFromType(ChannelType.PRIVATE)) {
            logger.info(String.format("[PRIV]<%#s>: %s", author, content));
        }
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Message message = event.getMessage();
        StringBuilder IDreader = new StringBuilder();
        StringBuilder IDreader1 = new StringBuilder();
        try {
            File file = new File("C:\\DiscordServerBotSecrets\\rito-bot\\OWNER_ID.txt");
            File file1 = new File("C:\\DiscordServerBotSecrets\\rito-bot\\OWNER_ID1.txt");
            FileReader fileReader = new FileReader(file);
            FileReader fileReader1 = new FileReader(file1);
            int singalCh, singalCh1;
            while((singalCh = fileReader.read()) != -1) {
                IDreader.append((char) singalCh);
            }
            while((singalCh1 = fileReader1.read()) != -1) {
                IDreader1.append((char) singalCh1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            StackTraceElement[] eStackTrace = e.getStackTrace();
            StringBuilder a = new StringBuilder();
            for (StackTraceElement stackTraceElement : eStackTrace) {
                a.append(stackTraceElement).append("\n");
            }
            logger.warn(a.toString());
        }
        String ID1 = IDreader.toString();
        String ID2 = IDreader1.toString();
        if (event.getMessage().getContentRaw().equalsIgnoreCase(App.getPREFIX() + "종료") &&
                (
                        (event.getAuthor().getIdLong() == Long.decode(ID1)) ||
                                (event.getAuthor().getIdLong() == Long.decode(ID2))
                )) {
            System.out.println(ID1 + ID2);
            shutdown(event.getJDA(), event);
            return;

        } else if(event.getMessage().getContentRaw().equalsIgnoreCase(App.getPREFIX() + "재시작") &&
                (
                        (event.getAuthor().getIdLong() == Long.decode(ID1)) ||
                                (event.getAuthor().getIdLong() == Long.decode(ID2))
                )) {
            restart(event.getJDA(), event);
            return;
        }
        if(event.getAuthor().isBot()) {
            return;

        }
        if(event.getMessage().isWebhookMessage()) {

            return;
        }
        if(event.getGuild().getId().equals("600010501266866186")) {
            if(!event.getChannel().getId().equals("600012818879741963")) {
                if(!Objects.requireNonNull(event.getMember()).hasPermission(Permission.MANAGE_ROLES)) {
                    Role role = event.getGuild().getRoleById("600012069559074822");
                    if(!event.getMember().getRoles().contains(role)) {
                        if (event.getMessage().getContentRaw().startsWith(App.getPREFIX())) {
                            event.getChannel().sendMessage(event.getMember().getAsMention() + " , 명령어는 봇 명령어 채널에서 사용해주세요").queue();

                            message.delete().queue();

                            return;
                        }
                    }
                }
            }
        }



        if (event.getMessage().getContentRaw().startsWith(Constants.PREFIX)) {
            manager.handleCommand(event);
        }
    }
    private void shutdown(JDA jda, GuildMessageReceivedEvent event) {
        new Thread(() -> {
            event.getChannel().sendMessage("봇이 종료됩니다.").queue();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                StackTraceElement[] eStackTrace = e.getStackTrace();
                StringBuilder a = new StringBuilder();
                for (StackTraceElement stackTraceElement : eStackTrace) {
                    a.append(stackTraceElement).append("\n");
                }
                logger.warn(a.toString());
            }
            jda.shutdown();
            System.exit(0);
        }).start();
    }
    private void restart(JDA jda, GuildMessageReceivedEvent event) {
        new Thread(() -> {
            event.getMessage().delete().queue();
            event.getChannel().sendMessage("종료 하는중....").queue();
            if(App.isDEBUG_MODE() || App.isONLINE_DEBUG()) {
                event.getChannel().sendMessage("디버그 모드 재시작입니다...").queue();
            } else {
                if(event.getAuthor().getId().equals("284508374924787713") && !event.getJDA().getSelfUser().getId().equals("607585394237636629")){
                    Objects.requireNonNull(Objects.requireNonNull(event.getJDA().getGuildById("607585394237636629")).getTextChannelById("600010501266866188")).sendMessage(event.getJDA().getSelfUser().getAsMention() + " 업데이트틀 위해 1분간 사용이 불가능합니다.").queue();
                }
            }
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    StackTraceElement[] eStackTrace = e.getStackTrace();
                    StringBuilder a = new StringBuilder();
                    for (StackTraceElement stackTraceElement : eStackTrace) {
                        a.append(stackTraceElement).append("\n");
                    }
                    logger.warn(a.toString());
                }
                jda.shutdown();
                System.exit(0);
            }).start();
        }).start();
    }

}
