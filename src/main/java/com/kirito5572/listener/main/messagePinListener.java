//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.kirito5572.listener.main;

import com.kirito5572.objects.main.SQL;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.annotation.Nonnull;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class messagePinListener extends ListenerAdapter {
    public messagePinListener() {
    }

    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if (!event.getAuthor().getId().equals("592987181186940931")) {
            try {
                Statement statement = SQL.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM ritobotDB.Pin WHERE channelId=" + event.getChannel().getId());
                if (resultSet.next()) {
                    Message message;
                    try {
                        message = event.getChannel().retrieveMessageById(resultSet.getString("messageId")).complete();
                    } catch (ErrorResponseException var7) {
                        statement.executeUpdate("DELETE FROM ritobotDB.Pin WHERE channelId=" + event.getChannel().getId());
                        return;
                    }

                    MessageEmbed embed = message.getEmbeds().get(0);
                    message.delete().queue();
                    String messageId = event.getChannel().sendMessageEmbeds(embed, new MessageEmbed[0]).complete().getId();
                    statement.executeUpdate("UPDATE ritobotDB.Pin SET messageId =" + messageId + " WHERE channelId=" + event.getChannel().getId());
                }
            } catch (Exception var8) {
                var8.printStackTrace();
                SQL.reConnection();
            }

        }
    }
}
