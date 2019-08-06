package BOT.Objects;

import BOT.App;
import BOT.Commands.*;
import BOT.Commands.GreenServerCustom.certificationCommand;
import BOT.Commands.Moderator.ClearCommand;
import BOT.Commands.Moderator.KickCommand;
import BOT.Commands.Moderator.MuteCommand;
import BOT.Commands.Moderator.giveroleCommand;
import BOT.Commands.Music.*;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Pattern;

public class CommandManager {

    private final Map<String, ICommand> commands = new HashMap<>();

    public CommandManager() {
        addCommand(new HelpCommand(this));
        addCommand(new PingCommand());
        addCommand(new UserInfoCommand());
        addCommand(new ColorInfoCommand());
        addCommand(new ColorCommand());

        addCommand(new ClearCommand());
        addCommand(new giveroleCommand());
        addCommand(new SayCommand());
        addCommand(new UnusedColorCommand());
        addCommand(new JoinCommand());
        //------------------------------------------------------------------//
        addCommand(new QueueDelectCommand());
        addCommand(new QueueCommand());
        addCommand(new StopClearCommand());
        addCommand(new PlayCommand());
        addCommand(new leaveCommand());

        addCommand(new NowPlayingCommand());
        addCommand(new SkipCommand());
        addCommand(new StopCommand());
        addCommand(new VolumeCommand());
        addCommand(new VersionCommand());
        //------------------------------------------------------------------//
        addCommand(new SearchCommand());
        addCommand(new upTimeCommand());
        addCommand(new AirInforCommand());
        addCommand(new AirkoreaListCommand());
        addCommand(new AirLocalInforCommand());

        addCommand(new CatCommand());
        addCommand(new KickCommand());
        addCommand(new MuteCommand());
        addCommand(new certificationCommand());
        //------------------------------------------------------------------//
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
                "(?i)" + Pattern.quote(App.getPREFIX()), "").split("\\s+");
        final String invoke = split[0].toLowerCase();

        if(commands.containsKey(invoke)) {
            final List<String> args = Arrays.asList(split).subList(1, split.length);

            event.getChannel().sendTyping().queue();
            commands.get(invoke).handle(args, event);
        }
    }
}
