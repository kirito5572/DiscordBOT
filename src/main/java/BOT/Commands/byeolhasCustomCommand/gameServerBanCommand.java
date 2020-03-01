package BOT.Commands.byeolhasCustomCommand;

import BOT.App;
import BOT.Objects.ICommand;
import BOT.Objects.SQL;
import BOT.Objects.getSteamID;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class gameServerBanCommand implements ICommand {
    private final Logger logger = LoggerFactory.getLogger(gameServerBanCommand.class);
    private static final String command = "제재리스트";
    @Override
    public void handle(@NotNull List<String> args, @NotNull GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        Member member = event.getMember();
        boolean isSteam = false;
        boolean isban = true;

        if(event.getGuild().getId().equals(goWorkCommand.serverId)) {
            assert member != null;
            if(!(member.hasPermission(Permission.ADMINISTRATOR) ||
                    member.hasPermission(Permission.VIEW_AUDIT_LOGS))) {
                event.getChannel().sendMessage("당신은 이 명령어를 사용할 권한이 없습니다.").complete().delete().queueAfter(5, TimeUnit.SECONDS);
            }
            if(args.isEmpty()) {
                event.getChannel().sendMessage("인수 부족").queue();
                return;
            }
            if(args.get(0).equals("추가")) {
                String ID;
                String time;
                StringBuilder reason;
                try {
                    ID = args.get(1);

                } catch (Exception e) {
                    channel.sendMessage("Steam ID 또는 Discord ID가 입력되지 않았거나, 인수가 잘못 입력되었습니다.").queue();

                    return;
                }
                try {
                    time = args.get(2);
                } catch (Exception e) {
                    channel.sendMessage("시간이 입력되지 않았거나, 인수가 맞지 않게 입력되었습니다.").queue();

                    return;
                }
                reason = new StringBuilder();
                try {
                    for (int i = 3; i <= args.size(); i++) {
                        reason.append(args.get(i)).append(" ");
                    }
                } catch (Exception e) {
                    if (reason.toString().equals("")) {
                        channel.sendMessage("사유가 입력되지 않았습니다.").queue();

                        return;
                    }
                }
                long time_raw;
                if(time.endsWith("추방")) {
                    time_raw = 60000L;
                    isban = false;
                } else if(time.endsWith("분")) {
                    time_raw = Long.parseLong(time.replace("분", "")) * 60000L;
                } else if(time.endsWith("시간")) {
                    time_raw = Long.parseLong(time.replace("시간", "")) * 3600000L;
                } else if(time.endsWith("일")) {
                    time_raw = Long.parseLong(time.replace("일", "")) * 86400000L;
                } else if(time.endsWith("달")) {
                    time_raw = Long.parseLong(time.replace("달", "")) * 2592000000L;
                } else if(time.endsWith("년")) {
                    time_raw = Long.parseLong(time.replace("년", "")) * 31104000000L;
                } else if(time.endsWith("영구")) {
                    time_raw = Long.parseLong(time.replace("영구", "")) * 1555200000000L;
                } else {
                    event.getChannel().sendMessage("시간 인수가 올바르지 않습니다.").queue();
                    return;
                }
                System.out.println(time);
                System.out.println(time_raw);

                String[] returns = new String[3];
                final String NickName;
                if ((ID.length() == 17) && ID.startsWith("76")) {
                    try {
                        returns = getSteamID.SteamID(ID);
                        isSteam = true;
                    } catch (Exception e) {

                        StackTraceElement[] eStackTrace = e.getStackTrace();
                        StringBuilder a = new StringBuilder();
                        for (StackTraceElement stackTraceElement : eStackTrace) {
                            a.append(stackTraceElement).append("\n");
                        }
                        logger.warn(a.toString());

                        channel.sendMessage("에러가 발생했습니다.").queue();
                        return;
                    }
                    if (returns[0].equals("profile not found")) {
                        event.getChannel().sendMessage("그런 스팀 프로필을 가진 유저는 없습니다.").queue();
                        return;
                    }
                    NickName = returns[0];

                } else {
                    NickName = "닉네임 조회 불가";
                    returns[2] = "aaa";
                }
                banMain(NickName, ID, time, reason, event.getGuild(), event.getAuthor(), isSteam, returns[2], "추가", time_raw);
                try {
                    Connection connection = SQL.getConnection();
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM byeolhaDB.Statistics WHERE userId=" +
                            event.getAuthor().getId() + ";");
                    if(resultSet.next()) {
                        int ban = resultSet.getInt("ban");
                        int kick = resultSet.getInt("kick");
                        if(isban) {
                            statement.execute("UPDATE byeolhaDB.Statistics SET ban=" + ban + 1 + " WHERE userId=" +
                                    event.getAuthor().getId() + ";");
                        } else {
                            statement.execute("UPDATE byeolhaDB.Statistics SET kick=" + kick + 1 + " WHERE userId=" +
                                    event.getAuthor().getId() + ";");
                        }
                    } else {
                        if(isban) {
                            statement.execute("INSERT INTO byeolhaDB.Statistics VALUES (" + event.getAuthor().getId() +
                                    ", " + 0 + ", " + 1 + ")");
                        } else {
                            statement.execute("INSERT INTO byeolhaDB.Statistics VALUES (" + event.getAuthor().getId() +
                                    ", " + 1 + ", " + 0 + ")");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (args.get(0).equals("검색")){
                String ID;
                boolean isCaseID = true;
                try {
                    ID = args.get(1);
                    if(ID.length() > 5) isCaseID = false;

                } catch (Exception e) {
                    channel.sendMessage("Steam ID 또는 Discord ID가 입력되지 않았거나, 인수가 잘못 입력되었습니다.").queue();

                    return;
                }
                try {
                    if(!isCaseID) {
                        String[] data = SQL.SQLdownload(ID);
                        StringBuilder stringBuilder = new StringBuilder();
                        for(String a : data) {
                            stringBuilder.append(a).append(", ");
                        }
                        EmbedBuilder builder = EmbedUtils.defaultEmbed()
                                .setTitle(ID + "로 조회된 제재 이력");
                        if(!data[0].equals("error")) {
                            builder.addField("제재 횟수", String.valueOf(data.length), false)
                                    .addField("Case ID", stringBuilder.toString().substring(0, stringBuilder.toString().length() - 2), false);
                        } else {
                            builder.setDescription("제재 이력 없음");
                        }
                        event.getChannel().sendMessage(builder.build()).queue();
                    } else {
                        String[] data = SQL.SQLdownload(Integer.parseInt(ID));
                        EmbedBuilder builder = EmbedUtils.defaultEmbed()
                                .setTitle(ID + "번 제재 이력");
                        if(!data[0].equals("error")) {
                            builder.addField("ID", data[0], false)
                                    .addField("기간", data[1], true)
                                    .addField("사유", data[2], false)
                                    .addField("담당 관리자",
                                            Objects.requireNonNull(event.getGuild().getMemberById(data[3])).getAsMention(), true)
                                    .addField("제재리스트 등록 시간", data[4], true);
                        } else {
                            builder.setDescription("해당 제재이력은 존재하지 않거나 조회 할 수 없습니다.");
                        }
                        event.getChannel().sendMessage(builder.build()).queue();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else {
            channel.sendMessage("이 명령어는 이 서버에서 지원하지 않습니다.").queue();
        }
    }

    @NotNull
    @Override
    public String getHelp() {
        return "게임 서버에서 밴을 합니다. \n" +
                "사용법:" + App.getPREFIX() + getInvoke() + " SteamID/DiscordID 시간 사유\n";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return command;
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "serverCustom";
    }

    private void banMain(String NickName, String ID, String time, StringBuilder reason, Guild guild, User user,
                         boolean isSteam, String Steam, String which, long time_raw) {
        if(which.equals("추가")) {
            String caseID = SQL.SQLupload(ID, time, reason.toString(), user.getId());
            EmbedBuilder builder = EmbedUtils.defaultEmbed()
                    .setTitle("Case ID: " + caseID)
                    .setColor(Color.RED)
                    .setFooter("제재 해제 시간은 오차가 존재합니다.");
            Date date = new Date();
            date = new Date(date.getTime() + time_raw);
            SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd aa hh:mm:ss");
            if (isSteam) {
                if (Steam.equals("steam")) {
                    builder.addField("제재 대상자", NickName, false)
                            .addField("스팀 ID", ID, false)
                            .addField("기간", time, false)
                            .addField("제재 시작 시간", dayTime.format(new Date()), false);
                    if(time_raw != 0) {
                        builder.addField("제재 해제 시간", dayTime.format(date), false);
                    }
                    builder.addField("사유", reason.toString(), false)
                            .addField("제재 담당자", user.getAsMention(), false);
                } else {
                    builder.addField("제재 대상자", NickName, false)
                            .addField("스팀 ID", ID, false)
                            .addField("기간", time, false)
                            .addField("제재 시작 시간", dayTime.format(new Date()), false);
                    if(time_raw != 0) {
                        builder.addField("제재 해제 시간", dayTime.format(date), false);
                    }
                    builder.addField("사유", reason.toString(), false)
                            .addField("제재 담당자", user.getAsMention(), false)
                            .setDescription("이 유저는 스팀프로필 등록을 하지 않았습니다.");
                }
            } else {
                builder.addField("제재 대상자", NickName, false)
                        .addField("디스코드 ID", ID, false)
                        .addField("기간", time, false)
                        .addField("제재 시작 시간", dayTime.format(new Date()), false);
                if(time_raw != 0) {
                    builder.addField("제재 해제 시간", dayTime.format(date), false);
                }
                builder.addField("사유", reason.toString(), false)
                        .addField("제재 담당자", user.getAsMention(), false);
            }
            Objects.requireNonNull(guild.getTextChannelById("670078075089846292")).sendMessage(builder.build()).queue();
        } else if(which.equals("제거")) {

        }
    }
}
