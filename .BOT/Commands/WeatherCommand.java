package com.kirito5572.commands.main

import com.kirito5572.Constants;
import BOT.Objects.airKoreaList;
import com.kirito5572.objects.main.ICommand;
import BOT.Objects.getWeather;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WeatherCommand implements ICommand {
    private boolean listFlag;
    private String location;

    @Override
    public void handle(@NotNull List<String> args, @NotNull EventPackage event) {
        setListFlag(false);
        String[][] listENG = airKoreaList.getLocal();
        String[] listKOR = airKoreaList.getLocalListKOR();
        String[][] listChangeKOR = airKoreaList.getLocalListChangeKOR();
        String[] listSelectKOR = airKoreaList.getLocalListSelectKOR();
        String[] list = getWeather.getWeather_list();
        TextChannel channel = event.getChannel();
        String joined = String.join("",args);
        for (String s : listKOR) {
            if (joined.equals(s)) {
                setListFlag(true);
                location = s;
            }
        }
        for (String[] s : listChangeKOR) {
            if (joined.equals(s[0])) {
                setListFlag(true);
                location = s[1];
            }
        }
        for (String s : listSelectKOR) {
            if (joined.equals(s)) {
                channel.sendMessage("선택한 지역은 남과 북으로 나눠져 있습니다.").queue();
                return;
            }
        }
        if(!isListFlag()) {
            if(args.isEmpty()) {
                channel.sendMessage("광역시도명을 입력하여주십시오").queue();
            } else {
                channel.sendMessage("그런 지역은 없습니다.\n").queue();
            }
            return;
        }
        for (String[] s : listENG) {
            if(s[0].equals(location)) {
                location = s[1];
                break;
            }
        }
        getWeather.get_api(location);
        String[] data = getWeather.getWeather_infor();

        EmbedBuilder builder = EmbedUtils.getDefaultEmbed()
                .setTitle(joined + "의 날씨 정보")
                .setFooter("Infor from openweathermap.org", "https://openweathermap.org/");
        int j = 0;
        for(int i = 0; i < 10; i++) {

            if(!data[i].equals("null")) {
                builder.addField(
                        (i - j + 1)+ ". " + list[i] + "\n",
                        data[i],
                        false
                );
            } else {
                j++;
            }
        }
        channel.sendMessageEmbeds(builder.build()).queue();


    }

    @NotNull
    @Override
    public String getHelp() {
        return "지역의 날씨 정보를 불러옵니다.\n" +
                "사용법: `" + Constants.PREFIX + getInvoke() +"` (지역명)\n" +
                "지역명양식: `ISO 3166에 등록된 지역명`\n" +
                "`From openweathermap.org`";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "날씨";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "other";
    }

    private void setListFlag(boolean listFlag) {
        this.listFlag = listFlag;
    }

    private boolean isListFlag() {
        return listFlag;
    }
}
