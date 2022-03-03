//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.kirito5572.commands.main.moderator;

import com.kirito5572.objects.main.CommandList;
import com.kirito5572.objects.main.EventPackage;
import com.kirito5572.objects.main.ICommand;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.dv8tion.jda.api.interactions.commands.Command;
import org.jetbrains.annotations.NotNull;

public class SlashSetupCommand implements ICommand {
    final List<SlashSetupCommand.SlashData> commandDataList = new ArrayList<>();

    public SlashSetupCommand() {
        String[][] commandData = CommandList.CommandData;

        for (String[] command : commandData) {
            SlashData slashData = new SlashData();
            slashData.invoke = command[0];
            slashData.description = command[1];
            this.commandDataList.add(slashData);
        }

    }

    public void handle(List<String> args, @NotNull EventPackage event) {
        boolean nothing = true;
        List<Command> commandList = event.getGuild().retrieveCommands().complete();
        boolean noRefresh = false;
        boolean needUpdate = false;

        for(SlashData slashData : commandDataList) {
            for (Command command : commandList) {
                if (command.getName().equals(slashData.invoke)) {
                    if (command.getDescription().equals(slashData.description)) {
                        noRefresh = true;
                    } else {
                        needUpdate = true;
                    }

                    nothing = false;
                    break;
                }
            }

            if (!noRefresh) {
                if (!needUpdate) {
                    event.getGuild().upsertCommand(slashData.invoke, slashData.description).complete();
                    event.getTextChannel().sendMessage("명령어 **" + slashData.invoke + "** 가 추가됨").complete();
                } else {
                    event.getGuild().upsertCommand(slashData.invoke, slashData.description).complete();
                    event.getTextChannel().sendMessage("명령어 **" + slashData.invoke + "** 가 갱신됨").complete();
                }
            }
        }

        if (!nothing) {
            event.getTextChannel().sendMessage(event.getMember().getAsMention() + ", 모든 명령어 처리가 완료되었습니다.").queue();
        } else {
            event.getTextChannel().sendMessage(event.getMember().getAsMention() + ", 신규 또는 갱신할 명령어가 없습니다.").queue();
        }

    }

    public String getHelp() {
        return "슬래시를 통한 명령어 사용을 위한 셋팅 및 명령어 업데이트 명령어 입니다.";
    }

    public String getInvoke() {
        return "setupslash";
    }

    public String getSmallHelp() {
        return "moderator";
    }

    public static class SlashData {
        public String invoke;
        public String description;

        public SlashData() {
        }
    }
}
