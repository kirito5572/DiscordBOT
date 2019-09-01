package BOT.Commands.Moderator;

import BOT.App;
import BOT.Objects.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class pollCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        String pollText = event.getChannel().getMessageById(event.getChannel().getLatestMessageId()).complete().getContentRaw();
        String temp = App.getPREFIX() + getInvoke();
        pollText = pollText.substring(temp.length());

        EmbedBuilder builder = EmbedUtils.defaultEmbed()
                .addField("투표 안건", pollText, false)
                .setTitle("투표")
                .addField("1", "찬성", true)
                .addField("2", "반대", true)
                .addField("3", "기권", true);

        event.getChannel().sendMessage(builder.build()).complete();
        Message message = event.getChannel().getMessageById(event.getChannel().getLatestMessageId()).complete();

        message.addReaction("1\u20E3").complete();
        message.addReaction("2\u20E3").complete();

        System.out.println(pollText);
    }

    @Override
    public String getHelp() {
        return "유저들에게 투표를 하기 위한 명령어 입니다.\n" +
                "중복 투표는 자동 차단됩니다.";
    }

    @Override
    public String getInvoke() {
        return "투표";
    }

    @Override
    public String getSmallHelp() {
        return "투표 하기!";
    }
}
