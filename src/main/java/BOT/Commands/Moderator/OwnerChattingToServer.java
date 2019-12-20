package BOT.Commands.Moderator;

import BOT.Listener.Listener;
import BOT.Objects.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class OwnerChattingToServer implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(event.getAuthor().getId().equals(Listener.getID1())) {
            if (args.get(0).equals("-channelload")) {
                List<TextChannel> channels = event.getJDA().getGuildById(args.get(1)).getTextChannels();
                for (TextChannel channel : channels) {
                    event.getChannel().sendMessage(channel.getName() + " " + String.valueOf(channel.getId())).queue();
                }
            } else if (args.get(0).equals("-send")){
                StringBuilder builder = new StringBuilder();
                for (int i = 3; i < args.size(); i++) {
                    builder.append(args.get(i)).append(" ");
                }
                event.getJDA().getGuildById(args.get(1)).getTextChannelById(args.get(2)).sendMessage(builder.toString()).complete();
                event.getChannel().sendMessage("전송 완료").queue();
            }
        }
    }

    @Override
    public String getHelp() {
        return "쓰지마, 봇 주인 전용 커맨드임";
    }

    @Override
    public String getInvoke() {
        return "대신채팅";
    }

    @Override
    public String getSmallHelp() {
        return "봇 주인 전용 커맨드";
    }
}
