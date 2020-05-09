package BOT.Commands._OwnerOnlyCommand;

import BOT.Listener.Listener;
import BOT.Objects.ICommand;
import groovy.lang.GroovyShell;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class evalCommand implements ICommand {
    private final GroovyShell engine;
    private final String imports;

    public evalCommand() {
        this.engine = new GroovyShell();
        this.imports = "import java.io.*\n" +
                "import java.lang.*\n" +
                "import java.util.*\n" +
                "import java.util.Arrays.*\n" +
                "import java.util.concurrent.*\n" +
                "import net.dv8tion.jda.api.*\n" +
                "import net.dv8tion.jda.api.entities.*\n" +
                "import net.dv8tion.jda.api.entities.impl.*\n" +
                "import net.dv8tion.jda.api.managers.*\n" +
                "import net.dv8tion.jda.api.managers.impl.*\n" +
                "import net.dv8tion.jda.api.utils.*\n" +
                "import me.duncte123.botcommons.messaging.*\n" +
                "import net.dv8tion.jda.api.events.message.guild.*\n";
    }

    @Override
    public void handle(List<String> args, @NotNull GuildMessageReceivedEvent event) {
        if (!event.getAuthor().getId().equals(Listener.getID1())) {
            return;
        }


        if (args.isEmpty()) {
            event.getChannel().sendMessage("Missing arguments").queue();

            return;
        }
        if(args.get(0).contains("jda.shutdown()")) {
            event.getChannel().sendMessage("봇이 종료됩니다.").queue();
        }

        try {
            engine.setProperty("args", args);
            engine.setProperty("event", event);
            engine.setProperty("message", event.getMessage());
            engine.setProperty("channel", event.getChannel());
            engine.setProperty("jda", event.getJDA());
            engine.setProperty("guild", event.getGuild());
            engine.setProperty("member", event.getMember());

            String script = imports + event.getMessage().getContentRaw().split("\\s+", 2)[1];
            Object out = engine.evaluate(script);

            event.getChannel().sendMessage(out == null ? "에러 없이 실행이 완료되었습니다." : out.toString()).complete();
        }
        catch (Exception e) {
            event.getChannel().sendMessage(e.getMessage()).queue();
        }
        event.getMessage().delete().queue();
    }

    @Override
    public String getHelp() {
        return "간단한 코드 실행";
    }

    @Override
    public String getInvoke() {
        return "eval";
    }

    @Override
    public String getSmallHelp() {
        return "owner";
    }
}
