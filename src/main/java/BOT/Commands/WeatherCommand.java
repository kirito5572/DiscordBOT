package BOT.Commands;

import BOT.Constants;
import BOT.airKoreaList;
import BOT.Objects.ICommand;
import BOT.Objects.getWeather;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class WeatherCommand implements ICommand {
    private boolean listFlag;
    private int location;

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        setListFlag(false);
        String[] listENG = airKoreaList.getLocalListENG();
        String[] listKOR = airKoreaList.getLocalListKOR();
        String[] list = getWeather.getWeather_list();
        TextChannel channel = event.getChannel();
        String joined = String.join("",args);
        for(int i = 0; i < 17; i++) {
            if(joined.equals(listKOR[i])) {
                setListFlag(true);
                setLocation(i);
            }
        }
        if(!isListFlag()) {
            channel.sendMessage("그런 지역은 없습니다.\n").queue();

            return;
        }
        getWeather.get_api(listENG[getLocation()]);
        String[] data = getWeather.getWeather_infor();

        EmbedBuilder builder = EmbedUtils.defaultEmbed()
                .setTitle(listKOR[getLocation()] + "지역의 날씨 정보")
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
        channel.sendMessage(builder.build()).queue();


    }

    @Override
    public String getHelp() {
        return "지역의 날씨 정보를 불러옵니다.\n" +
                "사용법: `" + Constants.PREFIX + getInvoke() +"` (지역명)\n" +
                "지역명양식: `ISO 3166에 등록된 지역명`\n" +
                "`From openweathermap.org`";
    }

    @Override
    public String getInvoke() {
        return "날씨";
    }

    @Override
    public String getSmallHelp() {
        return "날씨는? ";
    }

    public void setListFlag(boolean listFlag) {
        this.listFlag = listFlag;
    }

    public boolean isListFlag() {
        return listFlag;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }
}
