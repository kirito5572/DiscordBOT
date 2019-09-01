package BOT.Commands.Moderator;

import BOT.App;
import BOT.Objects.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class pollCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            event.getChannel().sendMessage(event.getMember().getAsMention() + ", 당신은 이 명령어를 사용할 권한이 없습니다.").queue();

            return;
        }

        String ids = event.getChannel().getLatestMessageId();
        Message message1 = event.getChannel().getMessageById(ids).complete();
        String pollText = message1.getContentRaw();
        String temp = App.getPREFIX() + getInvoke();
        pollText = pollText.substring(temp.length());

        EmbedBuilder builder = EmbedUtils.defaultEmbed()
                .addField("투표 안건", pollText, false)
                .setTitle("투표")
                .addField("1\u20E3", "찬성", true)
                .addField("2\u20E3", "반대", true)
                .addField("3\u20E3", "기권", true);

        String id = event.getChannel().sendMessage(builder.build()).complete().getId();

        Message message = event.getChannel().getMessageById(id).complete();
        message1.delete().queue();

        message.addReaction("1\u20E3").complete();
        message.addReaction("2\u20E3").complete();
        message.addReaction("3\u20E3").complete();
    }

    @Override
    public String getHelp() {
        return "유저들에게 투표를 하기 위한 명령어 입니다.\n" +
                "사용법: `" + App.getPREFIX() + getInvoke() + " <투표 내용> `";
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
