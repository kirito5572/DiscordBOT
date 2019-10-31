package BOT.Commands.GreenServerCustom;

import BOT.App;
import BOT.Objects.ICommand;
import BOT.Objects.SQL;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.sql.SQLException;
import java.util.List;

public class GreenSearchCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(!event.getGuild().getId().equals("600010501266866186")) {
            event.getChannel().sendMessage("이 명령어는 해당 서버에서 사용 할 수 없습니다.").queue();

            return;
        }
        if(args.isEmpty()) {
            event.getChannel().sendMessage("SteamID를 입력해주세요").queue();
            return;
        }
        EmbedBuilder embedBuilder = EmbedUtils.defaultEmbed();
        if(args.size() != 1) {
            if(args.get(1).startsWith("-c")) {
                String[] data = new String[0];
                try {
                    data = SQL.SQLdownload(Integer.parseInt(args.get(2)));
                } catch (SQLException | ClassNotFoundException | InterruptedException e) {
                    e.printStackTrace();
                }
                embedBuilder.setTitle("검색된 case ID 제재 기록 정보")
                        .addField("case ID", args.get(2), false)
                        .addField("SteamID", data[0], false)
                        .addField("DB 등록 시간", data[1], false)
                        .addField("제재 기간", data[2], false)
                        .addField("사유", data[3], false)
                        .addField("제재자", data[4], false);
            } else {
                event.getChannel().sendMessage("-c 인수가 잘못 입력되었습니다.").queue();
            }
        } else {
            String[] data;
            try {
                data = SQL.SQLdownload(args.get(0));
            } catch (SQLException | ClassNotFoundException | InterruptedException e) {
                e.printStackTrace();
                return;
            }
            if(data[0].equals("error")) {
                event.getChannel().sendMessage("DB에 기록된 제재 내역이 존재 하지 않습니다.").queue();
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            for(String a : data) {
                stringBuilder.append(a).append(", ");
            }
            embedBuilder.setTitle("검색된 제재 기록 정보")
                    .addField("SteamID", args.get(0), false)
                    .addField("검색된 제재 기록 건수", String.valueOf(data.length), false)
                    .addField("case ID", stringBuilder.toString().substring(0, stringBuilder.toString().length() - 2), false);
        }

        event.getChannel().sendMessage(embedBuilder.build()).queue();
    }

    @Override
    public String getHelp() {
        return "제재된 정보를 검색합니다.\n" +
                "사용법: `" + App.getPREFIX() + getInvoke() + "` <SteamID> -c <caseID>";
    }

    @Override
    public String getInvoke() {
        return "제재검색";
    }

    @Override
    public String getSmallHelp() {
        return "serverCustom";
    }
}
