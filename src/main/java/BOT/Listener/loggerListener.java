package BOT.Listener;

import BOT.Objects.SQL;
import BOT.Objects.config;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.List;

public class loggerListener extends ListenerAdapter {
    /*
    private String[] TextLoggingEnable;
    private String[] ChannelLoggingEnable;
    private List<Guild> guilds;
    public void onReady(@Nonnull ReadyEvent event) {
        guilds = event.getJDA().getGuilds();
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        Message message = event.getMessage();
        Guild guild = event.getGuild();
        String messageId = message.getId();
        String messageContent = message.getContentRaw();
        for(String guild1 : config.getTextLoggingEnable()) {
            if(guild.getId().equals(guild1)) {
                SQL.loggingMessageUpLoad(guild.getId(), messageId, messageContent);
            }
        }
    }

    @Override
    public void onMessageDelete(@Nonnull MessageDeleteEvent event) {
        String messageId = event.getMessageId();
        Guild guild = event.getGuild();

        for(String guild1 : config.getTextLoggingEnable()) {
            if(guild.getId().equals(guild1)) {
                String[] data = SQL.loggingMessageDownLoad(guild1, messageId);
                EmbedBuilder builder = EmbedUtils.defaultEmbed()
                        .setTitle("삭제된 메세지")
                        .addField("작성자", data[1], false)
                        .addField("내용", data[0], false)
                        .addField("메세지 ID", messageId, false);
                messageLoggingSend(builder, guild);
            }
        }


    }
    private void messageLoggingSend(EmbedBuilder builder, Guild guild) {
        List<TextChannel> channels = guild.getTextChannelsByName("채팅-로그", false);
        if(!channels.isEmpty()) {
            channels.get(0).sendMessage(builder.build()).queue();
        }

    }
     */
}
