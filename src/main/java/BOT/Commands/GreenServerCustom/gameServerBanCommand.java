package BOT.Commands.GreenServerCustom;

import BOT.App;
import BOT.Objects.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import javax.xml.soap.Text;
import java.awt.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class gameServerBanCommand implements ICommand {
    private static String command = "서버밴";
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();

        if(event.getGuild().getId().equals("600010501266866186")) {
            if(!event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
                channel.sendMessage("당신은 이 명령어를 사용할 권한이 없습니다.").queue();

                return;
            }
            if(args.isEmpty()) {
                event.getChannel().sendMessage("인수 부족 '" + App.getPREFIX() + "명령어" +
                        getInvoke() + "'").queue();
                return;
            }
            String SteamID;
            String time_non;
            String reason;
            try {
                SteamID = args.get(0);

            } catch (Exception e) {
                channel.sendMessage("Steam ID가 입력되지 않았거나, 인수가 잘못 입력되었습니다.").queue();

                return;
            }
            try {
                time_non = args.get(1);
            } catch (Exception e) {
                channel.sendMessage("시간이 입력되지 않았거나, 인수가 맞지 않게 입력되었습니다.").queue();

                return;
            }
            try {
                reason = args.get(2);
            } catch (Exception e) {
                channel.sendMessage("사유가 입력되지 않았습니다.").queue();

                return;
            }

            TextChannel botChannel = event.getGuild().getTextChannelById("609055524851286027");
            TextChannel botChannel1 = event.getGuild().getTextChannelById("609057247116525650");
            TextChannel adminChannel = event.getGuild().getTextChannelById("609781460785692672");
            TextChannel reportChannel = event.getGuild().getTextChannelById("600015587544006679");
            final String[] temp = new String[1];
            WebUtils.ins.scrapeWebPage("https://steamid.io/lookup/" + SteamID).async((document1 -> {
                String time = time_non;
                String a1 = document1.getElementsByTag("body").first().toString();
                String a2 = a1;
                try {
                    int b2 = a2.indexOf("data-clipboard-text=\"");
                    int b1 = a1.indexOf(" <dt class=\"key\">\n" +
                            "       name");
                    a1 = a1.substring(b1 + 75);
                    a2 = a2.substring(b2 + 21);
                    b2 = a2.indexOf("data-clipboard-text=\"");
                    a2 = a2.substring(b2 + 21);
                    b2 = a2.indexOf("data-clipboard-text=\"");
                    a2 = a2.substring(b2 + 21);
                    int c1 = a1.indexOf("</dd>");
                    int c2 = a2.indexOf(" src=");
                    a1 = a1.substring(0, c1 - 7);
                    a2 = a2.substring(0, c2 - 1);
                    System.out.println(a1);
                    System.out.println(a2);
                    temp[0] = a1;
                } catch (Exception e) {
                    e.printStackTrace();
                    channel.sendMessage("봇이 스팀 프로필을 불러오는데 실패하였습니다.").queue();

                }
                String NickName;
                String ID;
                ID = a2;

                NickName = temp[0];

                EmbedBuilder builder = EmbedUtils.defaultEmbed()
                        .setTitle("인 게임 정지 제재")
                        .setColor(Color.RED)
                        .addField("제재 대상자", NickName, false)
                        .addField("스팀 ID", ID, false)
                        .addField("정지 기간", time, false)
                        .addField("위반 규정 조항", reason, false)
                        .addField("제재 담당자", event.getAuthor().getAsMention(), false);

                adminChannel.sendMessage("" + event.getMember().getAsMention() + ", ` " + NickName + " ( " + ID + " )제재 완료\n" +
                        "기간: " + time + "`").queue();

                reportChannel.sendMessage(builder.build()).queue();

                int temp1;
                if (time.contains("m")) {
                    time = time.substring(0,time.indexOf("m"));
                    try {
                        temp1 = Integer.parseInt(time);
                    } catch (Exception e) {
                        channel.sendMessage("time의 숫자가 잘못 입력되었습니다.").queue();

                        return;
                    }
                    time = String.valueOf(temp1);

                } else if(time.contains("h")) {
                    time = time.substring(0,time.indexOf("h"));
                    try {
                        temp1 = Integer.parseInt(time);
                    } catch (Exception e) {
                        channel.sendMessage("time의 숫자가 잘못 입력되었습니다.").queue();

                        return;
                    }
                    time = String.valueOf((temp1 * 60));


                } else if(time.contains("d")) {
                    time = time.substring(0,time.indexOf("d"));
                    try {
                        temp1 = Integer.parseInt(time);
                    } catch (Exception e) {
                        channel.sendMessage("time의 숫자가 잘못 입력되었습니다.").queue();

                        return;
                    }
                    time = String.valueOf((temp1 * 1440));

                } else if(time.contains("M")) {
                    time = time.substring(0,time.indexOf("M"));
                    try {
                        temp1 = Integer.parseInt(time);
                    } catch (Exception e) {
                        channel.sendMessage("time의 숫자가 잘못 입력되었습니다.").queue();

                        return;
                    }
                    time = String.valueOf((temp1 * 43830));

                } else {
                    channel.sendMessage("time의 단위가 잘못되었습니다.").queue();

                    return;
                }

                System.out.println("oban " + NickName + " " + ID + " " + time);
                botChannel.sendMessage("+oban " + NickName + " " + ID + " " + time).queue();
                botChannel1.sendMessage("+oban " + NickName + " " + ID + " " + time).queue();

            }));


        } else {
            channel.sendMessage("이 명령어는 이 서버에서 지원하지 않습니다.").queue();
        }
    }

    @Override
    public String getHelp() {
        return "게임 서버에서 밴을 합니다. \n" +
                "사용법:" + App.getPREFIX() + getInvoke() + " <SteamID/스팀 닉네임> <시간> <사유>";
    }

    @Override
    public String getInvoke() {
        return command;
    }

    @Override
    public String getSmallHelp() {
        return "게임 서버 밴";
    }

    public static String getCommand() {
        return command;
    }
}
