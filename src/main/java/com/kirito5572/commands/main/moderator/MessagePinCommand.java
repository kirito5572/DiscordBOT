package com.kirito5572.commands.main.moderator;

import com.kirito5572.objects.main.EventPackage;
import com.kirito5572.objects.main.ICommand;
import com.kirito5572.objects.main.SQL;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MessagePinCommand implements ICommand {
    @Override
    public void handle(@NotNull List<String> args, @NotNull EventPackage event) {
        Member member = event.member();
        assert member != null;
        if(!(member.hasPermission(Permission.ADMINISTRATOR) || member.hasPermission(Permission.MANAGE_SERVER) ||
                member.hasPermission(Permission.MANAGE_ROLES) || member.hasPermission(Permission.MANAGE_PERMISSIONS) ||
                member.hasPermission(Permission.MANAGE_CHANNEL) || member.hasPermission(Permission.MESSAGE_MANAGE))) {
            event.message().delete().queue();
            return;
        }
        try (Connection connection = SQL.getConnection()){
            try (Statement statement = connection.createStatement()) {
                String channelId = event.getChannel().getId();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM ritobotDB.Pin WHERE channelId=" + channelId);
                if (resultSet.next()) {
                    event.getChannel().deleteMessageById(resultSet.getString("messageId")).queue();
                    statement.executeUpdate("DELETE FROM ritobotDB.Pin WHERE channelId=" + channelId);
                    event.getChannel().sendMessage("핀이 해제되었습니다.").queue(message -> message.delete().queueAfter(10, TimeUnit.SECONDS));
                } else {
                    EmbedBuilder builder = EmbedUtils.getDefaultEmbed()
                            .setTitle("고정된 메세지")
                            .setColor(Color.GREEN)
                            .setDescription(event.message().getContentRaw().substring(2));
                    event.getChannel().sendMessageEmbeds(builder.build()).queue(message -> {
                        try {
                            statement.executeUpdate("INSERT INTO ritobotDB.Pin VALUES (" + channelId + ", " + message.getId() + ")");
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        event.message().delete().queue();
    }

    @Override
    public String getHelp() {
        return "메세지를 고정합니다.";
    }

    @Override
    public String getInvoke() {
        return "핀";
    }

    @Override
    public String getSmallHelp() {
        return "moderator";
    }
}
