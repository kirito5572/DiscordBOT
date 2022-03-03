package com.kirito5572.commands.main;

import com.kirito5572.Constants;
import com.kirito5572.objects.main.EventPackage;
import com.kirito5572.objects.main.ICommand;
import com.kirito5572.objects.main.getAirData;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AirInformationCommand implements ICommand {

    @Override
    public void handle(@NotNull List<String> args, @NotNull EventPackage event) {
        Logger logger = LoggerFactory.getLogger(AirInformationCommand.class);
        String[] rank = new String[8];
        TextChannel channel = event.getTextChannel();
        Member selfMember = event.getGuild().getSelfMember();
        if(!selfMember.hasPermission(Permission.MESSAGE_WRITE)) {
            channel.sendMessage("메세지를 보낼권한이 없습니다.").queue();

            return;
        }
        getAirData airData = new getAirData();
        String temp = airData.get_StationName(args.get(0), args.get(1));
        assert temp != null;
        if(temp.equals("error1")) {
            channel.sendMessage("해당 시도에 그런 읍면동이 존재하지 않습니다.").queue();

            return;
        } else if(temp.equals("error1234")) {
            channel.sendMessage("봇 제작에게 getAirData의 if 판독문을 추가해주라고 말해주세요.").queue();

            return;
        }
        airData.get_API(temp);
        String[] data = airData.getAirKorea_data();
        String[] air_list = airData.getAirKorea_List();
        for(int i = 0; i < 16; i++) {
            if(data[i] == null) {
                data[i] = "정보 없음";
            }
            if(i < 8) {
               if(data[i].equals("-")) {
                   data[i] = "정보 없음";
               }
            }
        }
        for(int i = 0; i < 7; i++) {
            switch (data[i + 8]) {
                case "1": {
                    data[i + 8] = "좋음";
                    break;
                }
                case "2": {
                    data[i + 8] = "보통";
                    break;
                }
                case "3": {
                    data[i + 8] = "나쁨";
                    break;
                }
                case "4": {
                    data[i + 8] = "매우 나쁨";
                    break;
                }
            }
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
            rank[i] = data[i + 8];
        }
        EmbedBuilder builder = EmbedUtils.getDefaultEmbed()
                .setTitle("공기질 측정표(" + data[15] + ")");
        builder.addField(
                "1. " + air_list[0] + "\n",
                data[0],
                false
        );
        for(int i = 1; i < 8; i++) {
            builder.addField(
                    String.format(
                            (i + 1) + ". %s\n",
                            air_list[i]),
                    String.format(
                            "%s(%s)\n",
                            data[i],
                            rank[i - 1]),
                    false
            );
        }

        channel.sendMessageEmbeds(builder.build()).queue();
    }

    @NotNull
    @Override
    public String getHelp() {
        return "입력된 광역시도의 읍면동의 미세먼지 값을 불러옵니다.\n" +
                "사용법: `" + Constants.PREFIX + getInvoke() + "` (광역시도) (읍면동)\n" +
                "에시: `" + Constants.PREFIX + getInvoke() + "인천광역시 송림동` \n" +
                "`From airkorea.or.kr` `API from data.go.kr`\n" +
                "이 값은 오차가 있을수도 있습니다. 항상 정확한것은 아닙니다.";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "공기질상세";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "other";
    }

}