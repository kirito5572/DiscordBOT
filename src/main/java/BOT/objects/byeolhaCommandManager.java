package BOT.Objects;

import BOT.App;
import BOT.Commands.byeolhasCustomCommand.cumulativeTimeCommand;
import BOT.Commands.byeolhasCustomCommand.gameServerBanCommand;
import BOT.Commands.byeolhasCustomCommand.goHomeCommand;
import BOT.Commands.byeolhasCustomCommand.goWorkCommand;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Pattern;

public class byeolhaCommandManager {
    private final Logger logger = LoggerFactory.getLogger(byeolhaCommandManager.class);

    private final Map<String, BOT.Objects.ICommand> commands = new HashMap<>();

    public byeolhaCommandManager() {
        addCommand(new cumulativeTimeCommand());
        addCommand(new goHomeCommand());
        addCommand(new goWorkCommand());
        addCommand(new gameServerBanCommand());
    }

    private void addCommand(@NotNull BOT.Objects.ICommand command) {
        if(!commands.containsKey(command.getInvoke())) {
            commands.put(command.getInvoke(), command);
        }
        sleep();
    }

    private void sleep() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {

            StackTraceElement[] eStackTrace = e.getStackTrace();
            StringBuilder a = new StringBuilder();
            for (StackTraceElement stackTraceElement : eStackTrace) {
                a.append(stackTraceElement).append("\n");
            }
            logger.warn(a.toString());
        }
    }

    @NotNull
    public Collection<BOT.Objects.ICommand> getCommands() {
        return commands.values();
    }

    public BOT.Objects.ICommand getCommand(String name) {
        return commands.get(name);
    }

    public void handleCommand(@NotNull GuildMessageReceivedEvent event) {
        final TextChannel channel = event.getChannel();
        final String[] split = event.getMessage().getContentRaw().replaceFirst(
                "(?i)" + Pattern.quote("/"), "").split("\\s+");
        final String invoke = split[0].toLowerCase();

        if(commands.containsKey(invoke)) {
            final List<String> args = Arrays.asList(split).subList(1, split.length);

            channel.sendTyping().queue();
            commands.get(invoke).handle(args, event);
        }
    }
}
