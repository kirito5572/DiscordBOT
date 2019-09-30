package BOT.Commands.GreenServerCustom;

import BOT.App;
import BOT.Objects.ICommand;
import BOT.Objects.SQL;
import BOT.Objects.getSteamID;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class gameServerBanCommand implements ICommand {
    private static String command = "서버밴";
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        Member member = event.getMember();

        if(event.getGuild().getId().equals("600010501266866186")) {
            assert member != null;
            if(!member.hasPermission(Permission.MANAGE_ROLES)) {
                if(!Objects.requireNonNull(event.getMember()).getRoles().contains(event.getGuild().getRoleById("600012069559074822"))) {
                    channel.sendMessage(event.getMember().getAsMention() + ", 당신은 이 명령어를 사용할 권한이 없습니다.").queue();

                    return;
                }
            }
            if(args.isEmpty()) {
                event.getChannel().sendMessage("인수 부족 '" + App.getPREFIX() + "명령어" +
                        getInvoke() + "'").queue();
                return;
            }
            String SteamID;
            String time_non;
            StringBuilder reason;
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
            reason = new StringBuilder();
            try {
                for(int i = 2; i <= args.size(); i++) {
                    reason.append(args.get(i));
                }
            } catch (Exception e) {
                if(reason.toString().equals("")) {
                    channel.sendMessage("사유가 입력되지 않았습니다.").queue();

                    return;
                }
            }

            TextChannel botChannel = event.getGuild().getTextChannelById("609055524851286027");
            TextChannel botChannel1 = event.getGuild().getTextChannelById("609057247116525650");
            TextChannel adminChannel = event.getGuild().getTextChannelById("609781460785692672");
            TextChannel reportChannel = event.getGuild().getTextChannelById("600015587544006679");

            String time = time_non;
            String timeString;
            String[] returns = getSteamID.SteamID(SteamID);
            if(returns[0].equals("profile not found")) {
                event.getChannel().sendMessage("그런 스팀 프로필을 가진 유저는 없습니다.").queue();
                return;
            }
            final String[] NickName = {returns[0]};
            String ID = returns[1];

            int temp1;
            if (time.contains("m")) {
                time = time.substring(0,time.indexOf("m"));
                try {
                    temp1 = Integer.parseInt(time);
                    timeString = temp1 + "분";
                } catch (Exception e) {
                    channel.sendMessage("time의 숫자가 잘못 입력되었습니다.").queue();

                    return;
                }
                time = String.valueOf(temp1);

            } else if(time.contains("h")) {
                time = time.substring(0,time.indexOf("h"));
                try {
                    temp1 = Integer.parseInt(time);
                    timeString = temp1 + "시간";
                } catch (Exception e) {
                    channel.sendMessage("time의 숫자가 잘못 입력되었습니다.").queue();

                    return;
                }
                time = String.valueOf((temp1 * 60));


            } else if(time.contains("d")) {
                time = time.substring(0,time.indexOf("d"));
                try {
                    temp1 = Integer.parseInt(time);
                    timeString = temp1 + "일";
                } catch (Exception e) {
                    channel.sendMessage("time의 숫자가 잘못 입력되었습니다.").queue();

                    return;
                }
                time = String.valueOf((temp1 * 1440));

            } else if(time.contains("M")) {
                time = time.substring(0,time.indexOf("M"));
                try {
                    temp1 = Integer.parseInt(time);
                    timeString = temp1 + "달";
                } catch (Exception e) {
                    channel.sendMessage("time의 숫자가 잘못 입력되었습니다.").queue();

                    return;
                }
                time = String.valueOf((temp1 * 43830));

            } else if(time.contains("y")) {
                time = time.substring(0,time.indexOf("y"));
                try {
                    temp1 = Integer.parseInt(time);
                    timeString = temp1 + "년";
                } catch (Exception e) {
                    channel.sendMessage("time의 숫자가 잘못 입력되었습니다.").queue();

                    return;
                }
                time = String.valueOf((temp1 * 525960));
            } else if(time.contains("영구")) {
                time = "99999999";
                timeString = "영구적용";
            } else {
                channel.sendMessage("시간 단위가 틀렸거나, 스팀 닉네임의 띄워쓰기를 삭제하여 주세요.\n" +
                        "사용법: `" + App.getPREFIX() + "명령어/도움말/help" +getInvoke() + "`").queue();

                return;
            }
            for(; NickName[0].contains(" ");) {
                NickName[0] = NickName[0].replaceFirst(" ", "");
            }
            for(;ID.contains(" ");) {
                ID = ID.replaceFirst("\n", "");
            }
            System.out.println(NickName[0] + ID);
            AtomicBoolean returnflag = new AtomicBoolean(false);
            AtomicBoolean steam = new AtomicBoolean(true);
            if(NickName[0].equals("")) {
                steam.set(false);
                String finalTime = time;
                WebUtils.ins.scrapeWebPage("https://steamid.io/lookup/" + SteamID).async((document1 -> {
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
                        for(; a1.contains(" ");) {
                            a1 = a1.replaceFirst(" ", "");
                        }
                        NickName[0] = a1;
                        banMain(NickName, a2, finalTime, reason, timeString, steam,
                                adminChannel, reportChannel, botChannel, botChannel1,
                                event);
                    } catch (Exception e) {
                        e.printStackTrace();
                        channel.sendMessage("봇이 스팀 프로필을 불러오는데 실패하였습니다.").queue();
                        returnflag.set(true);
                    }
                }));
            } else if(NickName[0].equals(" ")) {
                steam.set(false);
                String finalTime1 = time;
                WebUtils.ins.scrapeWebPage("https://steamid.io/lookup/" + SteamID).async((document1 -> {
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
                        for(; a1.contains(" ");) {
                            a1 = a1.replaceFirst(" ", "");
                        }
                        NickName[0] = a1;
                        banMain(NickName, a2, finalTime1, reason, timeString, steam,
                                adminChannel, reportChannel, botChannel, botChannel1,
                                event);
                    } catch (Exception e) {
                        e.printStackTrace();
                        channel.sendMessage("봇이 스팀 프로필을 불러오는데 실패하였습니다.").queue();
                        returnflag.set(true);
                    }
                }));
            }
            if(returnflag.get()) {
                return;
            }
            if(steam.get()) {
                banMain(NickName, ID, time, reason, timeString, steam,
                        adminChannel, reportChannel, botChannel, botChannel1,
                        event);
            }
        } else {
            channel.sendMessage("이 명령어는 이 서버에서 지원하지 않습니다.").queue();
        }
    }

    @Override
    public String getHelp() {
        return "게임 서버에서 밴을 합니다. \n" +
                "사용법:" + App.getPREFIX() + getInvoke() + " <SteamID> <시간> <사유>\n" +
                "시간 가능 인수: 1m(1min)/1h(1Hour)/1d(1day)/1M(1month)/1y(1year)/영구(99999999min)";
    }

    @Override
    public String getInvoke() {
        return command;
    }

    @Override
    public String getSmallHelp() {
        return "serverCustom";
    }

    public static String getCommand() {
        return command;
    }
    private void banMain(String[] NickName, String ID, String time, StringBuilder reason, String timeString, AtomicBoolean steam,
                         TextChannel adminChannel, TextChannel reportChannel, TextChannel botChannel, TextChannel botChannel1,
                         GuildMessageReceivedEvent event) {
        String text = "+oban " + NickName[0] + " " + ID + " " + time + " " + reason.toString();
        System.out.println(text);
        SQL.SQLupload(ID, timeString, reason.toString(), event.getAuthor().getName());

        EmbedBuilder builder = EmbedUtils.defaultEmbed()
                .setTitle("인 게임 정지 제재")
                .setColor(Color.RED)
                .addField("제재 대상자", NickName[0], false)
                .addField("스팀 ID", ID, false)
                .addField("정지 기간", timeString, false)
                .addField("위반 규정 조항", reason.toString(), false)
                .addField("제재 담당자", event.getAuthor().getAsMention(), false);
        if (steam.get()) {
            adminChannel.sendMessage("" + Objects.requireNonNull(event.getMember()).getAsMention() + ", ` " + NickName[0] + " ( " + ID + " )제재 완료\n" +
                    "기간: " + time + "`").queue();
        } else {
            adminChannel.sendMessage("" + Objects.requireNonNull(event.getMember()).getAsMention() + ", ` " + NickName[0] + " ( " + ID + " )제재 완료\n" +
                    "기간: " + time + "`\n" +
                    "**중요 이 유저는 스팀 프로필이 설정되지 않았습니다**").queue();
            event.getChannel().sendMessage("**중요 이 유저는 스팀 프로필이 설정되지 않았습니다**").queue();
        }


        reportChannel.sendMessage(builder.build()).queue();

        //event.getGuild().getTextChannelById("600012818879741963").sendMessage("$$정보 " + ID + " " + time_non + " " + reason.toString()).queue();
        if (Objects.requireNonNull(event.getGuild().getMemberById("580691748276142100")).getOnlineStatus().equals(OnlineStatus.ONLINE) ||
                Objects.requireNonNull(event.getGuild().getMemberById("580691748276142100")).getOnlineStatus().equals(OnlineStatus.IDLE)) {
            botChannel.sendMessage(text).queue();
        } else {
            event.getChannel().sendMessage("1서버가 종료상태이므로, 1,2,3,4 서버에 밴이 적용되지 않습니다.").queue();
        }
        if (Objects.requireNonNull(event.getGuild().getMemberById("600676751118696448")).getOnlineStatus().equals(OnlineStatus.ONLINE) ||
                Objects.requireNonNull(event.getGuild().getMemberById("600676751118696448")).getOnlineStatus().equals(OnlineStatus.IDLE)) {

            botChannel1.sendMessage(text).queue();
        } else {
            event.getChannel().sendMessage("5서버가 종료상태이므로, 5서버에 밴이 적용되지 않습니다.").queue();
        }
        event.getChannel().sendMessage("밴 적용 완료").queue();
    }
}
