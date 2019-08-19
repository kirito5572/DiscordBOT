package BOT.Listener;

import BOT.App;
import BOT.Objects.CommandManager;
import BOT.Objects.FilterList;
import net.dv8tion.jda.core.Permission;
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
        Logger logger = LoggerFactory.getLogger(filterListener.class);
        if(event.getGuild().getId().equals("453817631603032065")) {
            return;

        }
        if(!(event.getGuild().getId().equals("600010501266866186"))) {
            for (String s : List) {
                if (message.getContentRaw().contains(s)) {
                    try {
                        if(event.getGuild().getSelfMember().getUser().getId().equals(event.getMember().getUser().getId())) {
                            return;
                        }
                        if(message.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                            logger.warn("관리자가 금지어를 말했으나, 관리자는 필터링 되지 않습니다.");
                            System.out.println("관리자가 금지어를 말했으나, 관리자는 필터링 되지 않습니다." +
                                    "금지어: " + message.getContentRaw());

                            return;
                        }
                        logger.warn(author.getAsMention() + "가 금지어를 사용하였습니다.\n" +
                                "금지어: " + message.getContentRaw());
                        System.out.println(author.getAsMention() + "가 금지어를 사용하였습니다.\n" +
                                "금지어: " + message.getContentRaw());
                        message.delete().complete();
                        event.getChannel().sendMessage(author.getAsMention() + " 금지어가 포함되어 있어 자동으로 필터링 되었습니다.").queue();

                    } catch (Exception e) {
                        event.getChannel().sendMessage("금지어가 입력되었으나 봇이 삭제할 권한이 없습니다.").queue();
                    }
                }
            }
        }
    }
}
