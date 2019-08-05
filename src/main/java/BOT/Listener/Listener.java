package BOT.Listener;

import BOT.App;
import BOT.Constants;
import BOT.Objects.CommandManager;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;

public class Listener extends ListenerAdapter {
    private final CommandManager manager;
    private final Logger logger = LoggerFactory.getLogger(Listener.class);
    public Listener(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void onReady(ReadyEvent event) {
        logger.info(String.format("Logged in as %#s", event.getJDA().getSelfUser()));
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
        }
        if (event.getMessage().getContentRaw().equalsIgnoreCase(App.getPREFIX() + "종료") &&
                (event.getAuthor().getIdLong() == Long.decode(IDreader.toString()) ||
                event.getAuthor().getIdLong() == Long.decode(IDreader1.toString()))) {
            shutdown(event.getJDA(), event);
            return;
        }

        if (!event.getAuthor().isBot() && !event.getMessage().isWebhookMessage() &&
                event.getMessage().getContentRaw().startsWith(Constants.PREFIX)) {
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
            }
            jda.shutdown();
            System.exit(0);
        }).start();
    }
}

