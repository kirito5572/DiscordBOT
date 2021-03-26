package BOT.Commands;

import BOT.Constants;
import BOT.Objects.airKoreaList;
import BOT.Objects.ICommand;
import BOT.Objects.getAirLocalData;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AirLocalInforCommand implements ICommand {
    private boolean listFlag;
    private String location;

    @Override
    public void handle(@NotNull List<String> args, @NotNull GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        Member selfMember = event.getGuild().getSelfMember();
        if (!selfMember.hasPermission(Permission.MESSAGE_WRITE)) {
            channel.sendMessage("메세지를 보낼권한이 없습니다.").queue();

            return;
        }
        setListFlag(false);
        String[][] listENG = airKoreaList.getLocal();
        String[] listKOR = airKoreaList.getLocalListKOR();
        String joined = String.join("", args);
        getAirLocalData airData = new getAirLocalData();
        for (String s : listKOR) {
            if (joined.equals(s)) {
                setListFlag(true);
                location = s;
            }
        }
        for (String[] s : listENG) {
            if(s[0].equals(location)) {
                location = s[1];
                break;
            }
        }
        if (!isListFlag()) {
            channel.sendMessage("그런 지역은 없습니다.\n").queue();

            return;
        }
        airData.get_API(location);
        String[] data = airData.getAirkorea_data();
        String[] air_list = airData.getItemCode();
        EmbedBuilder builder = EmbedUtils.getDefaultEmbed()
                .setTitle(location + "지역의 공기질 측정표");
        builder.addField(
                "1. " + air_list[6] + "\n",
                data[6],
                false
        );
        for (int i = 0; i < 6; i++) {
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

    @NotNull
    @Override
    public String getHelp() {
        return "시도별 대기정보 평균값을 조회합니다.\n" +
                "사용법: `" + Constants.PREFIX + getInvoke() + "` (측정소명)\n" +
                "`From airkorea.or.kr` `API from data.go.kr`";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "공기질";
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