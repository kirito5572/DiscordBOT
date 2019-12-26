package BOT.Listener;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class salListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
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
