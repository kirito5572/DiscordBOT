package BOT.Commands;

import BOT.App;
import BOT.Objects.ICommand;
import BOT.Objects.greenCommandManager;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.Collection;
import java.util.List;

public class greenHelpCommand implements ICommand {

    private greenCommandManager greenCommandManager;

    private int page = 0;
    private int x = 0;
    private int i = 0;
    private int j = 0;
    private Collection<ICommand> Commands;

    public greenHelpCommand(greenCommandManager manager) {
        this.greenCommandManager = manager;
        Commands = manager.getCommands();
    }

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        String joined = String.join(" ", args);

        if (args.contains("1") || args.contains("2") || args.isEmpty() || args.contains("3") || args.contains("4") || args.contains("5")) {
            if(!joined.equals("")) {
                page = Integer.parseInt(joined);
            }
            generateAndSendEmbed(event);
            return;
        }


        ICommand command = greenCommandManager.getCommand(joined);

        if(command == null) {
            event.getChannel().sendMessage( "`"+joined + "`는 존재하지 않는 명령어 입니다.\n" +
                    "`" + App.getPREFIX() + getInvoke() + "` 를 사용해 명령어 리스트를 확인하세요.").queue();
            return;
        }

        String message = "`" + command.getInvoke() + "`에 대한 설명\n" + command.getHelp();

        event.getChannel().sendMessage(message).queue();
    }

    private void generateAndSendEmbed(GuildMessageReceivedEvent event) {
        EmbedBuilder builder = EmbedUtils.defaultEmbed().setTitle("명령어 리스트:");

        StringBuilder music = new StringBuilder();
        StringBuilder serverCustom = new StringBuilder();
        StringBuilder moderator = new StringBuilder();
        StringBuilder other = new StringBuilder();
        Commands.forEach(iCommand -> {
            if (iCommand.getSmallHelp().equals("music")) {
                music.append(iCommand.getInvoke()).append("\n");
            }
            if (iCommand.getSmallHelp().equals("serverCustom")) {
                serverCustom.append(iCommand.getInvoke()).append("\n");
            }
            if (iCommand.getSmallHelp().equals("moderator")) {
                moderator.append(iCommand.getInvoke()).append("\n");
            }
            if (iCommand.getSmallHelp().equals("other")) {
                other.append(iCommand.getInvoke()).append("\n");
            }
        });
        builder.addField(
                "관리",
                moderator.toString(),
                false
        );
        builder.addField(
                "서버커스텀",
                serverCustom.toString(),
                false
        );
        builder.addField(
                "음악",
                music.toString(),
                false
        );
        builder.addField(
                "기타",
                other.toString(),
                false
        );
        event.getChannel().sendMessage(builder.build()).queue();
    }

    @Override
    public String getHelp() {
        return "모르는 명령어는 어디서? 여기서.\n" +
                "명령어: `" + App.getPREFIX() + getInvoke() + " [command]`";
    }

    @Override
    public String getInvoke() {
        return "명령어";
    }

    @Override
    public String getSmallHelp() {
        return "other";
    }
}
