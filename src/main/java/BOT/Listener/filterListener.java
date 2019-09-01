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

                            return;
                        }
                        logger.warn(author.getAsMention() + "가 금지어를 사용하였습니다.\n" +
                                "금지어: " + message.getContentRaw());
                        String rawMessage = message.getContentRaw();
                        rawMessage = rawMessage.replaceFirst(s,"[데이터 말소]");
                        event.getChannel().sendMessage(rawMessage).queue();
                        message.delete().complete();
                        event.getChannel().sendMessage(author.getAsMention() + " 금지어가 포함되어 있어 자동으로 필터링 되어, 필터링 된 문장을 출력합니다.").queue();
                        if(event.getGuild().getId().equals("617222347425972234")) {
                            event.getGuild().getTextChannelById("617244182653829140").sendMessage(author.getAsMention() + "가 금지어를 사용하였습니다.\n" +
                                    "금지어: " + message.getContentRaw()).queue();
                        } else if (event.getGuild().getId().equals("617757206929997895")) {
                            event.getGuild().getTextChannelById("617760924714926113").sendMessage(author.getAsMention() + "가 금지어를 사용하였습니다.\n" +
                                    "금지어: " + message.getContentRaw()).queue();
                        }
                    } catch (Exception e) {
                        event.getChannel().sendMessage("금지어가 입력되었으나 봇이 삭제할 권한이 없습니다.").queue();
                    }
                }
            }
        }
    }
}
