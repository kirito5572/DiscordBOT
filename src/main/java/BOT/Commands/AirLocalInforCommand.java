package BOT.Commands;

import BOT.Constants;
import BOT.airKoreaList;
import BOT.objects.ICommand;
import BOT.objects.getAirLocalData;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class AirLocalInforCommand implements ICommand {
    private boolean listFlag;
    private int location;
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        setListFlag(false);
        String[] listENG = airKoreaList.getLocalListENG();
        String[] listKOR = airKoreaList.getLocalListKOR();
        TextChannel channel = event.getChannel();
        String joined = String.join("",args);
        getAirLocalData airData = new getAirLocalData();
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
        airData.get_API(listENG[getLocation()]);
        String[] data = airData.getAirkorea_data();
        String[] air_list = airData.getItemCode();
        EmbedBuilder builder = EmbedUtils.defaultEmbed()
                .setTitle(listKOR[getLocation()]+ "지역의 공기질 측정표");
        builder.addField(
                "1. " + air_list[6] + "\n",
                data[6],
                false
        );
        for(int i = 0; i < 6; i++) {
            builder.addField(
                    String.format(
                            i + ". %s\n",
                            air_list[i]),
                    String.format(
                            "%s \n",
                            data[i]),
                    false
            );
        }

        channel.sendMessage(builder.build()).queue();
    }

    @Override
    public String getHelp() {
        return "시도별 대기정보 평균값을 조회합니다.\n" +
                "사용법: `" + Constants.PREFIX + getInvoke() + "` (측정소명)\n" +
                "`From airkorea.or.kr` `API from data.go.kr`";
    }

    @Override
    public String getInvoke() {
        return "공기질";
    }

    @Override
    public String getSmallHelp() {
        return "지역의 공기질은 과연?";
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
