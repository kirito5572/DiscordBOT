package com.kirito5572.commands.main.owneronlycommand;

import com.kirito5572.listener.main.Listener;
import com.kirito5572.objects.main.EventPackage;
import com.kirito5572.objects.main.ICommand;
import groovy.lang.GroovyShell;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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
                "import java.sql.*\n" +
                "import net.dv8tion.jda.api.*\n" +
                "import net.dv8tion.jda.api.entities.*\n" +
                "import net.dv8tion.jda.api.entities.impl.*\n" +
                "import net.dv8tion.jda.api.managers.*\n" +
                "import net.dv8tion.jda.api.managers.impl.*\n" +
                "import net.dv8tion.jda.api.utils.*\n" +
                "import me.duncte123.botcommons.messaging.*\n" +
                "import net.dv8tion.jda.api.events.message.guild.*\n" +
                "import net.dv8tion.jda.api.exceptions.*\n" +
                "import net.dv8tion.jda.api.audio.*\n" +
                "import net.dv8tion.jda.api.events.*\n" +
                "import net.dv8tion.jda.api.events.channel.category.update.*\n" +
                "import net.dv8tion.jda.api.events.channel.category.*\n" +
                "import net.dv8tion.jda.api.events.channel.priv.*\n" +
                "import net.dv8tion.jda.api.events.channel.store.update.*\n" +
                "import net.dv8tion.jda.api.events.channel.store.*\n" +
                "import net.dv8tion.jda.api.events.channel.text.update.*\n" +
                "import net.dv8tion.jda.api.events.channel.text.*\n" +
                "import net.dv8tion.jda.api.events.channel.voice.update.*\n" +
                "import net.dv8tion.jda.api.events.channel.voice.*\n" +
                "import net.dv8tion.jda.api.events.emote.update.*\n" +
                "import net.dv8tion.jda.api.events.emote.*\n" +
                "import net.dv8tion.jda.api.events.guild.update.*\n" +
                "import net.dv8tion.jda.api.events.guild.voice.*\n" +
                "import net.dv8tion.jda.api.events.guild.member.*\n" +
                "import net.dv8tion.jda.api.events.guild.invite.*\n" +
                "import net.dv8tion.jda.api.events.guild.override.*\n" +
                "import net.dv8tion.jda.api.events.guild.*\n" +
                "import net.dv8tion.jda.api.events.http.*\n" +
                "import net.dv8tion.jda.api.events.message.guild.*\n" +
                "import net.dv8tion.jda.api.events.message.guild.react.*\n" +
                "import net.dv8tion.jda.api.events.message.react.*\n" +
                "import net.dv8tion.jda.api.events.message.priv.react.*\n" +
                "import net.dv8tion.jda.api.events.message.priv.*\n" +
                "import net.dv8tion.jda.api.events.message.*\n" +
                "import net.dv8tion.jda.api.events.role.*\n" +
                "import net.dv8tion.jda.api.events.role.update.*\n" +
                "import net.dv8tion.jda.api.events.self.*\n" +
                "import net.dv8tion.jda.api.events.role.*\n" +
                "import net.dv8tion.jda.api.events.role.update.*\n" +
                "import net.dv8tion.jda.api.events.*\n" +
                "import net.dv8tion.jda.api.managers.*\n" +
                "import me.duncte123.botcommons.*\n" +
                "import me.duncte123.botcommons.text.*\n" +
                "import me.duncte123.botcommons.commands.*\n" +
                "import me.duncte123.botcommons.config.*\n" +
                "import me.duncte123.botcommons.messaging.*\n" +
                "import me.duncte123.botcommons.web.*\n" +
                "import BOT.Listener.*\n" +
                "import BOT.Commands.*\n" +
                "import BOT.Commands._OwnerOnlyCommand.*\n" +
                "import BOT.Commands.GameCommand.*;\n" +
                "import BOT.Commands.Moderator.*\n" +
                "import BOT.Commands.ONIGIRIServerCustom.*\n" +
                "import BOT.Commands.SharkyCustomCommand.*\n" +
                "import BOT.Commands.TwitchCommand.*\n" +
                "import BOT.Objects.*\n" +
                "import BOT.*\n" +
                "import com.google.gson.*\n" +
                "import org.hyperic.sigar.*\n";
    }

    @Override
    public void handle(@NotNull List<String> args, @NotNull EventPackage event) {
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
        } catch (Exception e) {
            event.getChannel().sendMessage(e.getMessage()).queue();
        }
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
