package BOT.Commands.GameCommand;

import BOT.App;
import BOT.Objects.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;
import java.util.Random;

public class russianRouletteCommand implements ICommand {
    private int shot = 0;
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        Random random = new Random();
        if(args.get(0).equals("재장전") || args.get(0).equals("reload")) {
            shot = random.nextInt(5) + 1;
            event.getChannel().sendMessage("총알 재장전 완료!.").queue();
        } else if(args.get(0).equals("발사") || args.get(0).equals("shot")) {
            shot--;
            if(shot == 0) {
                event.getChannel().sendMessage(" \uD83D\uDCA5 \uD83D\uDD2B \n" +
                        "\uD83D\uDC80 넌 죽었다!").queue();
                shot = random.nextInt(5) + 1;
            } else {
                event.getChannel().sendMessage(" \uD83D\uDCA5 \uD83D\uDD2B \n" +
                        "넌 살았다!").queue();
            }
        }
    }

    @Override
    public String getHelp() {
        return "러시안 룰렛! \n" +
                "사용법: `" + App.getPREFIX() + getInvoke() + "<재장전/시작>";
    }

    @Override
    public String getInvoke() {
        return "러시안룰렛";
    }

    @Override
    public String getSmallHelp() {
        return "game";
    }
}
