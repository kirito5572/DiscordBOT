package BOT.Commands;

import BOT.App;
import BOT.Objects.ICommand;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DICECommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        int sides = 6;
        int dices = 1;
        TextChannel channel = event.getChannel();

        if (!args.isEmpty()) {
            sides = Integer.parseInt(args.get(0));

            if (args.size() > 1) {
                dices = Integer.parseInt(args.get(1));
            }
        }

        if (sides > 100) {
            channel.sendMessage("주사위의 최대 숫자가 100이하여야 합니다.").queue();

            return;
        }

        if (dices > 20) {
            channel.sendMessage("주사위를 던질수 있는 최대 횟수는 20번입니다.").queue();

            return;
        }

        ThreadLocalRandom random = ThreadLocalRandom.current();
        StringBuilder builder = new StringBuilder()
                .append("결과:\n");

        for (int d = 1; d <= dices; d++) {
            builder.append("\uD83C\uDFB2 #")
                    .append(d)
                    .append(": **")
                    .append(random.nextInt(1, sides))
                    .append("**\n");
        }

        channel.sendMessage(builder.toString()).queue();
    }

    @Override
    public String getHelp() {
        return "주사위를 굴립니다.\n" +
                "사용법: `" + App.getPREFIX() + getInvoke() + " [주사위 최대 숫자] [주사위 던질 횟수]`";
    }

    @Override
    public String getInvoke() {
        return "야바위";
    }

    @Override
    public String getSmallHelp() {
        return "주사위 굴리기";
    }
}
