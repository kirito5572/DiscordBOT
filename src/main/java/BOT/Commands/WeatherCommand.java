package BOT.Commands;

import BOT.Constants;
import BOT.objects.ICommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class WeatherCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

    }

    @Override
    public String getHelp() {
        return "지역의 날씨 정보를 불러옵니다.\n" +
                "사용법: `" + Constants.PREFIX + getInvoke() +"` (지역명)\n" +
                "";
    }

    @Override
    public String getInvoke() {
        return "날씨";
    }
}
