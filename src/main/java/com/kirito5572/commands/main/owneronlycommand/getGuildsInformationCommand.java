package com.kirito5572.commands.main.owneronlycommand;

import com.kirito5572.listener.main.Listener;
import com.kirito5572.objects.main.EventPackage;
import com.kirito5572.objects.main.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class getGuildsInformationCommand implements ICommand {
    @Override
    public void handle(@NotNull List<String> args, @NotNull EventPackage event) {
        if (Objects.requireNonNull(event.member()).getId().equals(Listener.getID1())) {
            event.message().delete().queue();
            StringBuilder stringBuilder = new StringBuilder();

            for (Guild guild : event.getJDA().getGuilds()) {
                try {
                    guild.retrieveInvites().queue(invites ->
                        stringBuilder.append(guild.getName()).append("(").append(invites.get(0)).append(")").append("\n")
                    );

                } catch (Exception e) {
                    e.printStackTrace();
                    stringBuilder.append("\n");
                }
            }
            EmbedBuilder builder = EmbedUtils.getDefaultEmbed()
                    .setTitle("접속된 서버 정보")
                    .setDescription(stringBuilder.toString());

            event.getChannel().sendMessageEmbeds(builder.build()).queue();
        } else {
            event.getChannel().sendMessage("그런 명령어는 존재하지 않습니다.").queue();
        }
    }

    @NotNull
    @Override
    public String getHelp() {
        return "주인 전용";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "봇서버리스트";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "Owner";
    }
}
