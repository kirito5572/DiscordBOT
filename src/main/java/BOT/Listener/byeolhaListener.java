package BOT.Listener;

import BOT.Commands.byeolhasCustomCommand.goWorkCommand;
import BOT.Objects.SQL;
import BOT.Objects.byeolhaCommandManager;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class byeolhaListener extends ListenerAdapter {
    private final byeolhaCommandManager manager;
    private final Logger logger = LoggerFactory.getLogger(byeolhaListener.class);
    public byeolhaListener(byeolhaCommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        Message message = event.getMessage();
        if(message.isWebhookMessage()) {

            return;
        }
        if(event.getGuild().getId().equals(goWorkCommand.serverId)) {
            if (message.getContentRaw().startsWith("/")) {
                manager.handleCommand(event);
            }
        }
    }

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        TimerTask job = new TimerTask() {
            @Override
            public void run() {
                try {
                    Date date_raw = new Date();
                    Connection connection = SQL.getConnection();
                    Statement statement = connection.createStatement();
                    String executeString = "SELECT * FROM byeolhaDB.attendance_log WHERE renew_times < " + date_raw.getTime() + ";";
                    ResultSet resultSet = statement.executeQuery(executeString);
                    if(resultSet.next()) {
                        if(resultSet.getString("veri_now").equals("1")) {
                            return;
                        }
                        long date = date_raw.getTime() + 600000L;
                        String randomStr = random_String();
                        String userId = resultSet.getString("userId");
                        executeString = "UPDATE byeolhaDB.attendance_log SET veri_now=1 WHERE userId=" + userId + ";";
                        statement.execute(executeString);
                        try {
                            executeString = "INSERT INTO byeolhaDB.cumulative_verification VALUES (" + userId + ", '" +
                                    randomStr + "', " + date + ");";
                            System.out.println(executeString);
                            statement.execute(executeString);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        EmbedBuilder builder = EmbedUtils.defaultEmbed()
                                .setTitle("출근 검증")
                                .addField("아래 5자리 글자를 10분내로 입력하여 주세요.", randomStr, false)
                                .setFooter("별하가 당신을 지켜보고 있습니다. 딴짓을 하는지 안하는지요");
                        PrivateChannel privateChannel = Objects.requireNonNull(Objects.requireNonNull(event.getJDA().getGuildById("576823770329907201"))
                                .getMemberById(userId)).getUser().openPrivateChannel().complete();
                        new Thread(() -> verification(userId, event.getJDA(), randomStr)).start();
                        privateChannel.sendMessage(builder.build()).queue();
                        statement.close();
                    }
                } catch (Exception e) {
                    StackTraceElement[] eStackTrace = e.getStackTrace();
                    StringBuilder a = new StringBuilder();
                    for (StackTraceElement stackTraceElement : eStackTrace) {
                        a.append(stackTraceElement).append("\n");
                    }
                    logger.warn(a.toString());
                }
            }
        };
        Timer jobScheduler = new Timer();
        jobScheduler.scheduleAtFixedRate(job, 0, 1000);
    }

    private String random_String() {
        StringBuilder temp = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 5; i++) {
            int rIndex = rnd.nextInt(3);
            switch (rIndex) {
                case 0:
                    // a-z
                    temp.append((char) (rnd.nextInt(26) + 97));
                    break;
                case 1:
                    // A-Z
                    temp.append((char) (rnd.nextInt(26) + 65));
                    break;
                case 2:
                    // 0-9
                    temp.append((rnd.nextInt(10)));
                    break;
            }
        }
        return temp.toString();
    }

    private void verification(String userId, JDA jda, String string) {
        System.out.println("verification호출");
        Date start_date = new Date();
        while(true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Date date = new Date();
            if((start_date.getTime() + 600000L) < date.getTime()) {
                Objects.requireNonNull(Objects.requireNonNull(jda.getGuildById(goWorkCommand.serverId)).
                        getMemberById(userId)).getUser().openPrivateChannel().complete()
                        .sendMessage("10분동안 응답이 없었습니다. 검증에 실패했습니다.").queue();
                try {
                    Date date_raw = new Date();
                    Connection connection = SQL.getConnection();
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM byeolhaDB.attendance_log WHERE userId=" + userId +";");
                    String startTime;
                    if(resultSet.next()) {
                        startTime = resultSet.getString("start");
                    } else {
                        return;
                    }
                    long time = (date_raw.getTime() - Long.parseLong(startTime)) / 1000;

                    resultSet = statement.executeQuery("SELECT * FROM byeolhaDB.working_log WHERE userId=" + userId + ";");
                    if(resultSet.next()) {
                        time = time + Long.parseLong(resultSet.getString("cumulative_time"));
                        statement.execute("UPDATE byeolhaDB.working_log SET working_log.cumulative_time=" + time + " WHERE userId=" + userId + ";");
                    } else {
                        statement.execute("INSERT INTO byeolhaDB.working_log VALUES (" + userId + ", " + time + ")");
                    }
                    statement.execute("DELETE FROM byeolhaDB.attendance_log WHERE userId=" + userId + ";");
                    statement.execute("DELETE FROM byeolhaDB.cumulative_verification WHERE userId=" + userId + ";");
                    statement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Guild guild = jda.getGuildById("576823770329907201");
                assert guild != null;
                guild.removeRoleFromMember(Objects.requireNonNull(guild.getMemberById(userId)),
                        Objects.requireNonNull(guild.getRoleById("682592172368855107"))).queue();
                Objects.requireNonNull(guild.getMemberById(userId)).getUser().openPrivateChannel().complete()
                        .sendMessage("검증에 실패하여 자동으로 퇴근처리가 되었습니다.").queue();

                break;
            } else {
                PrivateChannel privateChannel = Objects.requireNonNull(Objects.requireNonNull(
                        jda.getGuildById(goWorkCommand.serverId)).
                        getMemberById(userId)).getUser().openPrivateChannel().complete();
                Message message = privateChannel.retrieveMessageById(privateChannel.getLatestMessageId()).complete();
                if(message.getContentRaw().contains(string)) {
                    try {
                        Connection connection = SQL.getConnection();
                        Statement statement = connection.createStatement();
                        statement.execute("UPDATE byeolhaDB.attendance_log SET renew_times=" +
                                (date.getTime() + 3600000L) + " WHERE userId=" + userId + ";");
                        statement.execute("UPDATE byeolhaDB.attendance_log SET veri_now=0 WHERE userId=" + userId + ";");
                        statement.execute("DELETE FROM byeolhaDB.cumulative_verification WHERE userId=" + userId + ";");
                        statement.close();
                        privateChannel.sendMessage("검증에 성공했습니다.").complete();
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if(!message.getAuthor().getId().equals("592987181186940931")) {
                        privateChannel.sendMessage("문자가 틀렸습니다.").complete();
                    }
                }
            }
        }
    }
}

