package com.kirito5572.commands.main.gamecommand;

import com.kirito5572.App;
import com.kirito5572.objects.main.EventPackage;
import com.kirito5572.objects.main.ICommand;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class russianRouletteCommand implements ICommand {
    private int shot = 0;
    @Override
    public void handle(@NotNull List<String> args, @NotNull EventPackage event) {
        Random random = new Random();
        if(args.isEmpty()) {
            event.getChannel().sendMessage("사용법: `" + App.getPREFIX() + getInvoke() + "<재장전/발사>`").queue();
            return;
        }
        if (!(args.get(0).equals("재장전") || args.get(0).equals("발사"))) {
            event.getChannel().sendMessage("사용법: `" + App.getPREFIX() + getInvoke() + "<재장전/발사>`").queue();
            return;
        }
        if(shot == 0) {
            if(!args.get(0).equals("재장전")) {
                event.getChannel().sendMessage("재장전을 한번만 해주세요").queue();
                return;
            }
        }
        if(args.get(0).equals("재장전") || args.get(0).equals("reload")) {
            shot = random.nextInt(5) + 1;
            event.getChannel().sendMessage("총알 재장전 완료!.").queue();
        } else if(args.get(0).equals("발사") || args.get(0).equals("shot")) {
            shot--;
            if(shot == 0) {
                event.getChannel().sendMessage(" \uD83D\uDCA5 \uD83D\uDD2B \n" +
                        "\uD83D\uDC80 넌 죽었다!").queue();
                shot = random.nextInt(5) + 1;
            } else {
                event.getChannel().sendMessage(" \uD83D\uDCA5 \uD83D\uDD2B \n" +
                        "넌 살았다!").queue();
            }
        }
    }

    @NotNull
    @Override
    public String getHelp() {
        return "러시안 룰렛! \n" +
                "사용법: `" + App.getPREFIX() + getInvoke() + "<재장전/발사>`";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "러시안룰렛";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "game";
    }
}
