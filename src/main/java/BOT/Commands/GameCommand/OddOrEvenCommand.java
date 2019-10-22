package BOT.Commands.GameCommand;

import BOT.Objects.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;
import java.util.Random;

public class OddOrEvenCommand implements ICommand {
    private Random random = new Random();
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        event.getMessage().delete().queue();
        event.getChannel().sendMessage("홀수 또는 짝수를 선택해주세요").queue();
        boolean even = random.nextBoolean();
        try {
            for (int i = 0; i < 21; i++) {
                Thread.sleep(500);
                try {
                    String message = event.getChannel().retrieveMessageById(event.getChannel().getLatestMessageId()).complete().getContentRaw();
                    if(message.equals("홀수")) {
                        if(even) {
                            event.getChannel().sendMessage("성공!").queue();
                        } else {
                            event.getChannel().sendMessage("실패!").queue();
                        }
                    } else if (message.equals("짝수")) {
                        if(even) {
                            event.getChannel().sendMessage("실패").queue();
                        } else {
                            event.getChannel().sendMessage("성공!").queue();
                        }
                    }

                } catch (Exception ignored) {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getHelp() {
        return "홀짝 게임 입니다.";
    }

    @Override
    public String getInvoke() {
        return "홀짝";
    }

    @Override
    public String getSmallHelp() {
        return "game";
    }
}
