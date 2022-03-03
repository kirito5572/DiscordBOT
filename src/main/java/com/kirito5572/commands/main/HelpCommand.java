package com.kirito5572.commands.main;

import com.kirito5572.App;
import com.kirito5572.objects.main.CommandManager;
import com.kirito5572.objects.main.EventPackage;
import com.kirito5572.objects.main.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class HelpCommand implements ICommand {

    @NotNull
    private final CommandManager manager;
    @NotNull
    private final Collection<ICommand> Commands;

    public HelpCommand(@NotNull CommandManager manager) {
        this.manager = manager;
        Commands = manager.getCommands();
    }

    @Override
    public void handle(@NotNull List<String> args, @NotNull EventPackage event) {
        String joined = String.join(" ", args);

        if(joined.equals("")) {
            generateAndSendEmbed(event);
            return;
        }


        ICommand command = manager.getCommand(joined);

        if(command == null) {
            event.getChannel().sendMessage( " `"+joined + "`는 존재하지 않는 명령어 입니다.\n" +
                    "`" + App.getPREFIX() + getInvoke() + "` 를 사용해 명령어 리스트를 확인하세요.").queue();
            return;
        }

        String message = "`" + command.getInvoke() + "` 에 대한 설명\n" + command.getHelp();

        event.getChannel().sendMessage(message).queue();
    }

    private void generateAndSendEmbed(@NotNull EventPackage event) {
        EmbedBuilder builder = EmbedUtils.getDefaultEmbed().setTitle("명령어 리스트:");

        StringBuilder serverCustom = new StringBuilder();
        StringBuilder moderator = new StringBuilder();
        StringBuilder game = new StringBuilder();
        StringBuilder other = new StringBuilder();
        StringBuilder twitch = new StringBuilder();
        builder.appendDescription(App.getPREFIX() + getInvoke() + " <명령어>를 입력하면 명령어별 상세 정보를 볼 수 있습니다.");
        Commands.forEach(iCommand -> {
            if (iCommand.getSmallHelp().equals("serverCustom")) {
                serverCustom.append(iCommand.getInvoke()).append("\n");
            }
            if (iCommand.getSmallHelp().equals("moderator")) {
                moderator.append(iCommand.getInvoke()).append("\n");
            }
            if (iCommand.getSmallHelp().equals("game")) {
                game.append(iCommand.getInvoke()).append("\n");
            }
            if(iCommand.getSmallHelp().equals("twitch")) {
                twitch.append(iCommand.getInvoke()).append("\n");
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
                "게임",
                game.toString(),
                false
        );
        builder.addField(
                "트위치",
                "추가예정",
                //twitch.toString(),
                false
        );
        builder.addField(
                "기타",
                other.toString(),
                false
        );
        event.getChannel().sendMessageEmbeds(builder.build()).queue();
    }

    @NotNull
    @Override
    public String getHelp() {
        return "모르는 명령어는 어디서? 여기서.\n" +
                "명령어: `" + App.getPREFIX() + getInvoke() + " [command]`";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "명령어";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "other";
    }
}
