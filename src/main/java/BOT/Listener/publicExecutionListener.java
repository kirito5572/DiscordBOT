package BOT.Listener;

import BOT.Objects.CommandManager;
import BOT.Objects.FilterList;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class publicExecutionListener extends ListenerAdapter {
    private final CommandManager manager;
    private final Logger logger = LoggerFactory.getLogger(Listener.class);
    public publicExecutionListener(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();
        Message message = event.getMessage();

        String[] List = FilterList.getList();
        Logger logger = LoggerFactory.getLogger(filterListener.class);
        Role role;
        try {
            role = event.getJDA().getRolesByName("공개 처형", true).get(0);
        } catch (Exception ignored) {
            return;
        }
        int time;
        if(event.getGuild().getId().equals("453817631603032065")) {
            time = 10;
        } else if (event.getGuild().getId().equals("600010501266866186")) {
            time = 5;
        } else {
            time = 7;
        }
        if(event.getMessage().getMember().getRoles().contains(role)) {
            event.getMessage().delete().queueAfter(time, TimeUnit.SECONDS);
            EmbedBuilder embedBuilder = EmbedUtils.defaultEmbed()
                    .addField("공개 처형", "당신의 메세지는 " + time + "초후 자동으로 삭제됩니다.",true);
            event.getChannel().sendMessage(embedBuilder.build()).complete();
            event.getChannel().getMessageById(event.getChannel().getLatestMessageId()).complete().delete().queue();

        }
    }
}
