//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.kirito5572.objects.logger;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SQL {
    private static final Logger logger = LoggerFactory.getLogger(SQL.class);
    private static Connection connection;
    private static Connection loggingConnection;
    private static Statement loggingStatement;
    private static ResultSet resultSet6;
    private static String driverName;
    private static String url;
    private static String user;
    private static String password;
    public static final int textLogging = 7;
    public static final int channelLogging = 8;
    public static final int memberLogging = 9;
    public static final int botChannel = 10;
    public static final int textLogChannel = 11;
    public static final int channelLogChannel = 12;

    public SQL() {
        StringBuilder SQLPassword = new StringBuilder();

        int var7;
        File file = new File("C:\\DiscordServerBotSecrets\\rito-bot\\SQLPassword.txt");
        try (FileReader fileReader = new FileReader(file)){
            int signalCh;
            while((signalCh = fileReader.read()) != -1) {
                SQLPassword.append((char)signalCh);
            }
        } catch (Exception var12) {
            StackTraceElement[] eStackTrace = var12.getStackTrace();
            StringBuilder a = new StringBuilder();
            int var6 = eStackTrace.length;

            for(var7 = 0; var7 < var6; ++var7) {
                StackTraceElement stackTraceElement = eStackTrace[var7];
                a.append(stackTraceElement).append("\n");
            }

            logger.warn(a.toString());
        }

        StringBuilder endPoint = new StringBuilder();
        file = new File("C:\\DiscordServerBotSecrets\\rito-bot\\endPoint.txt");
        try (FileReader fileReader = new FileReader(file)){
            int signalCh;
            while((signalCh = fileReader.read()) != -1) {
                endPoint.append((char)signalCh);
            }
        } catch (Exception var11) {
            StackTraceElement[] eStackTrace = var11.getStackTrace();
            StringBuilder a = new StringBuilder();
            var7 = eStackTrace.length;

            for(int var22 = 0; var22 < var7; ++var22) {
                StackTraceElement stackTraceElement = eStackTrace[var22];
                a.append(stackTraceElement).append("\n");
            }

            logger.warn(a.toString());
        }

        driverName = "com.mysql.cj.jdbc.Driver";
        url = "jdbc:mysql://" + endPoint + "/ritobotDB?serverTimezone=UTC";
        user = "ritobot";
        password = SQLPassword.toString();

        try {
            connection = DriverManager.getConnection(url, user, password);
            loggingConnection = DriverManager.getConnection(url, user, password);
        } catch (SQLException var10) {
            var10.printStackTrace();
            reConnection();
        }

    }

    public String configDownLoad_channel(String guildId, int option) {
        String return_data = "error";
        String queryString;
        switch (option) {
            case 9 -> {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    queryString = "SELECT * FROM ritobot_config.log_channel WHERE guildId=" + guildId;
                    loggingStatement = connection.createStatement();
                    resultSet6 = loggingStatement.executeQuery(queryString);
                    if (resultSet6.next()) {
                        return_data = resultSet6.getString("memberLog");
                    }

                    resultSet6.close();
                } catch (Exception var5) {
                    var5.printStackTrace();
                    reConnection();
                    return_data = "error";
                }
            }
            case 10 -> {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    queryString = "SELECT * FROM ritobot_config.bot_channel WHERE guildId=" + guildId;
                    loggingStatement = connection.createStatement();
                    resultSet6 = loggingStatement.executeQuery(queryString);
                    if (resultSet6.next() && resultSet6.getString("disable").equals("0")) {
                        return_data = resultSet6.getString("channelId");
                    }

                    resultSet6.close();
                } catch (Exception var8) {
                    var8.printStackTrace();
                    reConnection();
                    return_data = "error";
                }
            }
            case 11 -> {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    queryString = "SELECT * FROM ritobot_config.log_channel WHERE guildId=" + guildId;
                    loggingStatement = connection.createStatement();
                    resultSet6 = loggingStatement.executeQuery(queryString);
                    if (resultSet6.next()) {
                        return_data = resultSet6.getString("messageLog");
                    }

                    resultSet6.close();
                } catch (Exception var7) {
                    var7.printStackTrace();
                    reConnection();
                    return_data = "error";
                }
            }
            case 12 -> {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    queryString = "SELECT * FROM ritobot_config.log_channel WHERE guildId=" + guildId;
                    loggingStatement = connection.createStatement();
                    resultSet6 = loggingStatement.executeQuery(queryString);
                    if (resultSet6.next()) {
                        return_data = resultSet6.getString("channelLog");
                    }

                    resultSet6.close();
                } catch (Exception var6) {
                    var6.printStackTrace();
                    reConnection();
                    return_data = "error";
                }
            }
        }

        return return_data;
    }

    public boolean loggingMessageUpLoad(String guildId, String messageId, String contentRaw, String authorId) {
        String queryString = "INSERT INTO messageLogging VALUE (" + guildId + "," + messageId + ", '" + contentRaw + "'," + authorId + ");";

        try {
            Class.forName(driverName);
            System.out.println(queryString);
            PreparedStatement preparedStatement = loggingConnection.prepareStatement("INSERT INTO ritobotDB.messageLogging VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, guildId);
            preparedStatement.setString(2, messageId);
            preparedStatement.setString(3, contentRaw);
            preparedStatement.setString(4, authorId);
            preparedStatement.execute();
            preparedStatement.close();
            return true;
        } catch (Exception var14) {
            StackTraceElement[] eStackTrace = var14.getStackTrace();
            StringBuilder a = new StringBuilder();

            for (StackTraceElement stackTraceElement : eStackTrace) {
                a.append(stackTraceElement).append("\n");
            }

            logger.warn(a.toString());

            try {
                loggingConnection = DriverManager.getConnection(url, user, password);
            } catch (SQLException var13) {
                var13.printStackTrace();
            }

            return false;
        }
    }

    public boolean loggingMessageUpdate(String guildId, String messageId, String contentRaw) {
        try {
            Class.forName(driverName);
            PreparedStatement preparedStatement = loggingConnection.prepareStatement("UPDATE ritobotDB.messageLogging SET ContentRaw = ? WHERE GuildId =? AND MessageId = ?");
            preparedStatement.setString(1, contentRaw);
            preparedStatement.setString(2, guildId);
            preparedStatement.setString(3, messageId);
            preparedStatement.execute();
            preparedStatement.close();
            return true;
        } catch (Exception var12) {
            StackTraceElement[] eStackTrace = var12.getStackTrace();
            StringBuilder a = new StringBuilder();
            int var8 = eStackTrace.length;

            for (StackTraceElement stackTraceElement : eStackTrace) {
                a.append(stackTraceElement).append("\n");
            }

            logger.warn(a.toString());

            try {
                loggingConnection = DriverManager.getConnection(url, user, password);
            } catch (SQLException var11) {
                var11.printStackTrace();
            }

            return false;
        }
    }

    @NotNull
    public String[] loggingMessageDownLoad(String guildId, String messageId) {
        String[] data = new String[2];
        String queryString = "SELECT * FROM ritobotDB.messageLogging WHERE MessageId=" + messageId + " AND GuildId=" + guildId + ";";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            loggingStatement = loggingConnection.createStatement();

            for(ResultSet loggingResultSet = loggingStatement.executeQuery(queryString); loggingResultSet.next(); data[1] = loggingResultSet.getString("Author")) {
                data[0] = loggingResultSet.getString("ContentRaw");
            }
        } catch (Exception var14) {
            StackTraceElement[] eStackTrace = var14.getStackTrace();
            StringBuilder a = new StringBuilder();
            int var9 = eStackTrace.length;

            for (StackTraceElement stackTraceElement : eStackTrace) {
                a.append(stackTraceElement).append("\n");
            }

            logger.warn(a.toString());

            try {
                loggingConnection = DriverManager.getConnection(url, user, password);
            } catch (SQLException var13) {
                var13.printStackTrace();
            }
        }

        try {
            loggingStatement.close();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

        return data;
    }

    @NotNull
    public String[] configDownLoad(int option) {
        String[] return_data = new String[]{"error"};

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            int i = 0;
            String queryString;
            Statement statement;
            String[] newArray;
            label43:
            switch(option) {
                case 7:
                    queryString = "SELECT * FROM ritobot_config.logging_enable WHERE text_logging = 1;";
                    statement = connection.createStatement();
                    resultSet6 = statement.executeQuery(queryString);
                    return_data = new String[resultSet6.getFetchSize()];

                    while(true) {
                        if (!resultSet6.next()) {
                            break label43;
                        }

                        if (i == 0) {
                            return_data = new String[]{resultSet6.getString("guildId")};
                            ++i;
                        } else {
                            newArray = Arrays.copyOf(return_data, return_data.length + 1);
                            newArray[return_data.length] = resultSet6.getString("guildId");
                            return_data = newArray;
                        }
                    }
                case 8:
                    queryString = "SELECT * FROM ritobot_config.logging_enable WHERE channel_logging = 1;";
                    statement = connection.createStatement();
                    resultSet6 = statement.executeQuery(queryString);
                    return_data = new String[resultSet6.getFetchSize()];

                    while(true) {
                        if (!resultSet6.next()) {
                            break label43;
                        }

                        if (i == 0) {
                            return_data = new String[]{resultSet6.getString("guildId")};
                            ++i;
                        } else {
                            newArray = Arrays.copyOf(return_data, return_data.length + 1);
                            newArray[return_data.length] = resultSet6.getString("guildId");
                            return_data = newArray;
                        }
                    }
                case 9:
                    queryString = "SELECT * FROM ritobot_config.logging_enable WHERE member_logging = 1;";
                    statement = connection.createStatement();
                    resultSet6 = statement.executeQuery(queryString);
                    return_data = new String[resultSet6.getFetchSize()];

                    while(resultSet6.next()) {
                        if (i == 0) {
                            return_data = new String[]{resultSet6.getString("guildId")};
                            ++i;
                        } else {
                            newArray = Arrays.copyOf(return_data, return_data.length + 1);
                            newArray[return_data.length] = resultSet6.getString("guildId");
                            return_data = newArray;
                        }
                    }
            }

            resultSet6.close();
        } catch (Exception var7) {
            var7.printStackTrace();
            reConnection();
            return_data = new String[]{"error"};
        }

        return return_data;
    }

    public Connection getConnection() {
        return connection;
    }

    public static void reConnection() {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException var1) {
            var1.printStackTrace();
            reConnection();
        }

    }

    public void setLoggingConnection(Connection loggingConnection) {
        SQL.loggingConnection = loggingConnection;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
