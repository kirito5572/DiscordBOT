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
        if(args.isEmpty()) {
            event.getChannel().sendMessage("SteamID를 입력해주세요").queue();
        }
        String[][] data;
        try {
            data = SQL.SQLdownload(args.get(0));
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        if(data[0][0] == null) {
            event.getChannel().sendMessage("검색된 제재 정보가 없습니다.").queue();
            return;
        }
        EmbedBuilder builder = EmbedUtils.defaultEmbed();
        if(args.get(1).isEmpty()) {
            StringBuilder builder1 = new StringBuilder();
            for (String[] datum : data) {
                if (!(datum[0] == null)) {
                    builder1.append(datum[0]).append("\n");
                }
            }
            builder.setTitle("제재 정보 검색")
                    .appendDescription("자세한 정보는 SteamID 뒤에 caseID를 넣어주세요")
                    .addField("스팀 ID", args.get(0), false)
                    .addField("제재 횟수", args.get(0), false)
                    .addField("caseID", builder1.toString(), false);

            event.getChannel().sendMessage(builder.build()).queue();
        }

    }

    @Override
    public String getHelp() {
        return "제재된 정보를 검색합니다.\n" +
                "사용법: `" + App.getPREFIX() + getInvoke() + "` <SteamID>";
    }

    @Override
    public String getInvoke() {
        return "검색";
    }

    @Override
    public String getSmallHelp() {
        return "serverCustom";
    }
}
