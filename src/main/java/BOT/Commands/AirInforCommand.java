
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
        String temp = airData.get_StationName(args.get(0), args.get(1));
        if(temp.equals("error1")) {
            channel.sendMessage("해당 시도에 그런 읍면동이 존재하지 않습니다.").queue();

            return;
        } else if(temp.equals("error1234")) {
            channel.sendMessage("봇 제작에게 getAireDate의 if 판독문을 추가해주라고 말해주세요.").queue();

            return;
        }
        airData.get_API(temp);
        String[] data = airData.getAirkorea_data();
        String[] air_list = airData.getAirkorea_List();
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
                e.printStackTrace();
            }
            rank[i] = data[i + 8];
        }
        EmbedBuilder builder = EmbedUtils.defaultEmbed()
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

        channel.sendMessage(builder.build()).queue();
    }

    @Override
    public String getHelp() {
        return "입력된 광역시도의 읍면동의 미세먼지 값을 불러옵니다.\n" +
                "사용법: `" + Constants.PREFIX + getInvoke() + "` (광역시도) (읍면동)\n" +
                "에시: `" + Constants.PREFIX + getInvoke() + "인천광역시 송림동` \n" +
                "`From airkorea.or.kr` `API from data.go.kr`\n" +
                "이 값은 오차가 있을수도 있습니다. 항상 정확한것은 아닙니다.";
    }

    @Override
    public String getInvoke() {
        return "공기질상세";
    }

    @Override
    public String getSmallHelp() {
        return "other";
    }

    public void setListFlag(boolean listflag) {
        this.listFlag = listflag;
    }

    public boolean isListFlag() {
        return listFlag;
    }
}