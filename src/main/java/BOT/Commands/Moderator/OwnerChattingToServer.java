package BOT.Commands.Moderator;

import BOT.Listener.Listener;
import BOT.Objects.ICommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class OwnerChattingToServer implements ICommand {
    @Override
    public void handle(@NotNull List<String> args, @NotNull GuildMessageReceivedEvent event) {
        if(event.getAuthor().getId().equals(Listener.getID1())) {
            if (args.get(0).equals("-channel")) {
                List<TextChannel> channels = Objects.requireNonNull(event.getJDA().getGuildById(args.get(1))).getTextChannels();
                StringBuilder builder = new StringBuilder();
                for (TextChannel channel : channels) {
                    builder.append(channel.getName()).append("(").append(channel.getId()).append(")").append("\n");
                }
                event.getChannel().sendMessage(builder.toString()).queue();
            } else if (args.get(0).equals("-send")){
                StringBuilder builder = new StringBuilder();
                for (int i = 3; i < args.size(); i++) {
                    builder.append(args.get(i)).append(" ");
                }
                Objects.requireNonNull(Objects.requireNonNull(event.getJDA().getGuildById(args.get(1))).getTextChannelById(args.get(2))).sendMessage(builder.toString()).complete();
                event.getChannel().sendMessage("전송 완료").queue();
            } else if (args.get(0).equals("-guilds")) {
                List<Guild> guilds = event.getJDA().getGuilds();
                StringBuilder builder = new StringBuilder();
                for(Guild guild : guilds) {
                    builder.append(guild.getName()).append("(").append(guild.getId()).append(")").append("\n");
                }
                event.getChannel().sendMessage(builder.toString()).queue();
            }
        }
        event.getMessage().delete().queue();
    }

    @NotNull
    @Override
    public String getHelp() {
        return "쓰지마, 봇 주인 전용 커맨드임";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "채팅";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "owners";
    }
}
