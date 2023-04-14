package com.kirito5572.commands.main.moderator;

import com.kirito5572.listener.main.Listener;
import com.kirito5572.objects.main.EventPackage;
import com.kirito5572.objects.main.ICommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class OwnerChattingToServer implements ICommand {
    @Override
    public void handle(@NotNull List<String> args, @NotNull EventPackage event) {
        if(event.getAuthor().getId().equals(Listener.getID1())) {
            switch (args.get(0)) {
                case "-channel" -> {
                    List<TextChannel> channels = Objects.requireNonNull(event.getJDA().getGuildById(args.get(1))).getTextChannels();
                    StringBuilder builder = new StringBuilder();
                    for (TextChannel channel : channels) {
                        builder.append(channel.getName()).append("(").append(channel.getId()).append(")").append("\n");
                    }
                    event.getChannel().sendMessage(builder.toString()).queue();
                }
                case "-send" -> {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 3; i < args.size(); i++) {
                        builder.append(args.get(i)).append(" ");
                    }
                    Objects.requireNonNull(Objects.requireNonNull(event.getJDA().getGuildById(args.get(1))).getTextChannelById(args.get(2))).sendMessage(builder.toString()).queue();
                    event.getChannel().sendMessage("전송 완료").queue();
                }
                case "-guilds" -> {
                    List<Guild> guilds = event.getJDA().getGuilds();
                    StringBuilder builder = new StringBuilder();
                    for (Guild guild : guilds) {
                        builder.append(guild.getName()).append("(").append(guild.getId()).append(")").append("\n");
                    }
                    event.getChannel().sendMessage(builder.toString()).queue();
                }
            }
        }
        event.message().delete().queue();
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
