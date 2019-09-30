package BOT.Commands.Moderator;

import BOT.App;
import BOT.Objects.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class pollCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(!(event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getMember().hasPermission(Permission.MESSAGE_MANAGE))) {
            event.getChannel().sendMessage(event.getMember().getAsMention() + ", 당신은 이 명령어를 사용할 권한이 없습니다.").queue();

            return;
        }
        String pollText = event.getMessage().getContentRaw();
        String temp = App.getPREFIX() + getInvoke();
        pollText = pollText.substring(temp.length());

        EmbedBuilder builder = EmbedUtils.defaultEmbed()
                .addField("투표 안건", pollText, false)
                .setTitle("투표")
                .addField("1\u20E3", "찬성", true)
                .addField("2\u20E3", "반대", true)
                .addField("3\u20E3", "기권", true);
        Message message;
        if(event.getGuild().getId().equals("617222347425972234")) {
            message =  event.getChannel().sendMessage(builder.build() + "@everyone").complete();
        } else {
            message = event.getChannel().sendMessage(builder.build()).complete();
        }

        event.getMessage().delete().complete();

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
        return "moderator";
    }
}
