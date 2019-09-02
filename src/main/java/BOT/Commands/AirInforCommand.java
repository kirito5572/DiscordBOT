
package BOT.Commands;

import BOT.Constants;
import BOT.Objects.airKoreaList;
import BOT.Objects.ICommand;
import BOT.Objects.getAirData;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

        import java.util.List;

public class AirInforCommand implements ICommand {
    private boolean listFlag;

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        setListFlag(false);
        String[] rank = new String[8];
        String[] list = airKoreaList.getInchoenList();
        TextChannel channel = event.getChannel();
        String joined = String.join("", args);
        Member selfMember = event.getGuild().getSelfMember();
        if(!selfMember.hasPermission(Permission.MESSAGE_WRITE)) {
            channel.sendMessage("메세지를 보낼권한이 없습니다.").queue();

            return;
        }
        getAirData airData = new getAirData();
        for(int i = 0; i < 16; i++) {
            if(joined.equals(list[i])) {
                setListFlag(true);
            }
        }
        if(!isListFlag()) {
            if(!joined.equals("지역")) {
                channel.sendMessage("그런 측정소는 없습니다.\n" +
                        "측정소 리스트: `" + Constants.PREFIX + getInvoke() + "지역`").queue();
            }
            return;
        }
        airData.get_API(joined);
        String[] data = airData.getAirkorea_data();
        String[] air_list = airData.getAirkorea_List();
        for(int i = 0; i < 7; i++) {
            switch (data[i + 8]) {
                case "0": {
                    data[i + 8] = "좋음";
                    break;
                }
                case "1": {
                    data[i + 8] = "보통";
                    break;
                }
                case "2": {
                    data[i + 8] = "나쁨";
                    break;
                }
                case "3": {
                    data[i + 8] = "매우 나쁨";
                    break;
                }
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            rank[i] = data[i + 8];
            System.out.println(data[i+1] + "/" + data[i+8] + "/" +rank[i]);
        }
        EmbedBuilder builder = EmbedUtils.defaultEmbed()
                .setTitle("공기질 측정표(airkorea 제공)");
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

        channel.sendMessage(builder.build()).queue();
    }

    @Override
    public String getHelp() {
        return "입력된 측정소의 대기질 측정값을 읽어옵니다.(인천만 지원)\n" +
                "사용법: `" + Constants.PREFIX + getInvoke() + "` (측정소명)\n" +
                "측정소명: `" + Constants.PREFIX + getInvoke() + "지역` \n" +
                "`From airkorea.or.kr` `API from data.go.kr`";
    }

    @Override
    public String getInvoke() {
        return "공기질상세";
    }

    @Override
    public String getSmallHelp() {
        return "공기질은?";
    }

    public void setListFlag(boolean listflag) {
        this.listFlag = listflag;
    }

    public boolean isListFlag() {
        return listFlag;
    }
}