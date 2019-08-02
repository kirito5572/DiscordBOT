package BOT.Listener;

import BOT.Objects.CommandManager;
import BOT.Objects.FilterList;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class filterListener extends ListenerAdapter {
    private final CommandManager manager;
    private final Logger logger = LoggerFactory.getLogger(Listener.class);
    public filterListener(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();
        Message message = event.getMessage();

        String[] List = FilterList.getList();

        for (String s : List) {
            if (message.getContentRaw().contains(s)) {
                try {
                    message.delete().queue();
                    event.getChannel().sendMessage(author.getAsMention() + " 금지어가 포함되어 있어 자동으로 필터링 되었습니다.").queue();
                } catch (Exception e) {
                    event.getChannel().sendMessage("금지어가 입력되었으나 봇이 삭제할 권한이 없습니다.").queue();
                }
            }
        }

    }
}
