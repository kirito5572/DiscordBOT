package BOT.Commands.Moderator;

import BOT.Objects.ICommand;
import BOT.Objects.SQL;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MessagePinCommand implements ICommand {
    @Override
    public void handle(List<String> args, @NotNull GuildMessageReceivedEvent event) {
        Member member = event.getMember();
        assert member != null;
        if(!(member.hasPermission(Permission.ADMINISTRATOR) || member.hasPermission(Permission.MANAGE_SERVER) ||
                member.hasPermission(Permission.MANAGE_ROLES) || member.hasPermission(Permission.MANAGE_PERMISSIONS) ||
                member.hasPermission(Permission.MANAGE_CHANNEL) || member.hasPermission(Permission.MESSAGE_MANAGE))) {
            event.getMessage().delete().queue();
            return;
        }
        try {
            Connection connection = SQL.getConnection();
            Statement statement = connection.createStatement();
            String channelId = event.getChannel().getId();
            String messageId;
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ritobotDB.Pin WHERE channelId=" + channelId);
            if(resultSet.next()) {
                event.getChannel().deleteMessageById(resultSet.getString("messageId")).queue();
                statement.executeUpdate("DELETE FROM ritobotDB.Pin WHERE channelId=" + channelId);
                event.getChannel().sendMessage("핀이 해제되었습니다.").complete().delete().queueAfter(10, TimeUnit.SECONDS);
            } else {
                EmbedBuilder builder = EmbedUtils.defaultEmbed()
                        .setTitle("고정된 메세지")
                        .setColor(Color.GREEN)
                        .setDescription(event.getMessage().getContentRaw().substring(2));
                messageId = event.getChannel().sendMessage(builder.build()).complete().getId();
                statement.executeUpdate("INSERT INTO ritobotDB.Pin VALUES (" + channelId + ", " + messageId + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        event.getMessage().delete().queue();
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
