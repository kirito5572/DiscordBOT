package com.kirito5572.commands.main;

import com.kirito5572.objects.main.EventPackage;
import com.kirito5572.objects.main.ICommand;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class TESTCommand implements ICommand {
    @Override
    public void handle(@NotNull List<String> args, @NotNull EventPackage event) {
        System.out.println(Arrays.toString(event.getGuild().getMembers().toArray()));
        System.out.println(event.getGuild().getMembers().size());
        System.out.println(event.member());
    }


    @NotNull
    @Override
    public String getHelp() {
        return "디버깅용 테스트 커맨드";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "테스트";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "other";
    }
}
