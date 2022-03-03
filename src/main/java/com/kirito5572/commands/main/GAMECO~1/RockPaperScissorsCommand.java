package com.kirito5572.commands.main.gamecommand;

import com.kirito5572.App;
import com.kirito5572.objects.main.EventPackage;
import com.kirito5572.objects.main.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RockPaperScissorsCommand implements ICommand {
    @Override
    public void handle(@NotNull List<String> args, @NotNull EventPackage event) {
        event.getMessage().delete().queue();
        String message;
        String myMessage;
        boolean botWin = false;
        boolean userWin = false;
        switch (args.get(0)) {
            case "가위":
                message = "가위";
                break;
            case "바위":
                message = "바위";
                break;
            case "보":
                message = "보";
                break;
            default:
                event.getChannel().sendMessage("가위/바위/보중 하나를 입력하세요").complete().delete().queueAfter(7, TimeUnit.SECONDS);
                return;
        }
        Random random = new Random();
        switch (random.nextInt(3)) {
            case 0:
                myMessage = "가위";
                break;
            case 1:
                myMessage = "바위";
                break;
            case 2:
                myMessage = "보";
                break;
            default: event.getChannel().sendMessage("에러가 발생했습니다.").complete().delete().queueAfter(7, TimeUnit.SECONDS);
                return;
        }
        switch (message) {
            case "가위":
                switch (myMessage) {
                    case "가위":
                        botWin = false;
                        userWin = false;
                        break;
                    case "바위":
                        botWin = true;
                        userWin = false;
                        break;
                    case "보":
                        botWin = false;
                        userWin = true;
                        break;
                }
                break;
            case "바위":
                switch (myMessage) {
                    case "가위":
                        botWin = false;
                        userWin = true;
                        break;
                    case "바위":
                        botWin = false;
                        userWin = false;
                        break;
                    case "보":
                        botWin = true;
                        userWin = false;
                        break;
                }
                break;
            case "보":
                switch (myMessage) {
                    case "가위":
                        botWin = true;
                        userWin = false;
                        break;
                    case "바위":
                        botWin = false;
                        userWin = true;
                        break;
                    case "보":
                        botWin = false;
                        userWin = false;
                        break;
                }
                break;
            default: return;
        }
        EmbedBuilder builder = EmbedUtils.getDefaultEmbed();
        if(botWin) {
            builder.setTitle("패배")
                    .setColor(Color.RED)
                    .addField("당신", message, false)
                    .addField("봇", myMessage, false);
        } else if(userWin) {
            builder.setTitle("승리")
                    .setColor(Color.GREEN)
                    .addField("당신", message, false)
                    .addField("봇", myMessage, false);
        } else {
            builder.setTitle("무승부")
                    .setColor(Color.GRAY)
                    .addField("당신", message, false)
                    .addField("봇", myMessage, false);
        }
    }

    @NotNull
    @Override
    public String getHelp() {
        return "봇과 가위바위보를 합니다. \n" +
                "사용법: `" + App.getPREFIX() + getInvoke() + " <가위/바위/보>`";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "가위바위보";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "game";
    }
}
