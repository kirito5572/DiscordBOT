package BOT.Listener;

import BOT.Objects.CommandManager;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class salListener extends ListenerAdapter {
    private final CommandManager manager;
    private final Logger logger = LoggerFactory.getLogger(Listener.class);
    public salListener(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();
        Message message = event.getMessage();
        if(!author.isBot()) {
            if (message.getContentRaw().contains("살쨩")) {
                String id = event.getChannel().sendMessage("살쨩 아냐!").complete().getId();
                event.getChannel().deleteMessageById(id).queueAfter(3, TimeUnit.SECONDS);
            }
            if (message.getContentRaw().contains("살짱")) {
                String id = event.getChannel().sendMessage("살짱 아냐!").complete().getId();
                event.getChannel().deleteMessageById(id).queueAfter(3, TimeUnit.SECONDS);
            }
        }
    }
}
