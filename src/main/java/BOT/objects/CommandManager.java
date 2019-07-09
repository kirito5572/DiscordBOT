package BOT.objects;

import BOT.Commands.*;
import BOT.Commands.Moderation.ClearCommand;
import BOT.Commands.Moderation.KickCommand;
import BOT.Commands.Music.*;
import BOT.Constants;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Pattern;

public class CommandManager {

    private final Map<String, ICommand> commands = new HashMap<>();

    public CommandManager() {
        addCommand(new HelpCommand(this));
        addCommand(new PingCommand());
        addCommand(new CatCommand());
        addCommand(new UserInfoCommand());
        addCommand(new ClearCommand());

        addCommand(new KickCommand());

        addCommand(new JoinCommand());
        addCommand(new leaveCommand());
        addCommand(new PlayCommand());
        addCommand(new StopCommand());
        addCommand(new QueueCommand());
        addCommand(new StopClearCommand());
        addCommand(new SkipCommand());
        addCommand(new VolumeCommand());
        addCommand(new NowPlayingCommand());
        addCommand(new QueueDelectCommand());

        addCommand(new WeatherCommand()); //TODO
        addCommand(new AirLocalInforCommand()); //TODO
        addCommand(new AirInforCommand());
        addCommand(new AirkoreaListCommand());
    }

    private void addCommand(ICommand command) {
        if(!commands.containsKey(command.getInvoke())) {
            commands.put(command.getInvoke(), command);
        }
    }

    public Collection<ICommand> getCommands() {
        return commands.values();
    }

    public ICommand getCommand(@NotNull String name) {
        return commands.get(name);
    }

    public void handleCommand(GuildMessageReceivedEvent event) {
        final String[] split = event.getMessage().getContentRaw().replaceFirst(
                "(?i)" + Pattern.quote(Constants.PREFIX), "").split("\\s+");
        final String invoke = split[0].toLowerCase();

        if(commands.containsKey(invoke)) {
            final List<String> args = Arrays.asList(split).subList(1, split.length);

            event.getChannel().sendTyping().queue();
            commands.get(invoke).handle(args, event);
        }
    }
}
