package BOT.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class SQL {
    private static final Logger logger = LoggerFactory.getLogger(SQL.class);
    private static int caseID;
    private static Connection connection;
    private static Connection loggingConnection;
    private static Statement statement;
    private static Statement loggingStatement;
    private static ResultSet resultSet;
    private static ResultSet resultSet6;
    private static ResultSet loggingResultSet;
    private static String driverName;
    private static String url;
    private static String user;
    private static String password;

    public SQL() {
        //init
        StringBuilder caseIDBuilder = new StringBuilder();
        try {
            File file = new File("C:\\DiscordServerBotSecrets\\rito-bot\\caseID.txt");
            FileReader fileReader = new FileReader(file);
            int singalCh;
            while((singalCh = fileReader.read()) != -1) {
                caseIDBuilder.append((char) singalCh);
            }
        } catch (Exception e) {

            StackTraceElement[] eStackTrace = e.getStackTrace();
            StringBuilder a = new StringBuilder();
            for (StackTraceElement stackTraceElement : eStackTrace) {
                a.append(stackTraceElement).append("\n");
            }
            logger.warn(a.toString());
        }
        caseID = Integer.parseInt(caseIDBuilder.toString());
        StringBuilder SQLPassword = new StringBuilder();
        try {
            File file = new File("C:\\DiscordServerBotSecrets\\rito-bot\\SQLPassword.txt");
            FileReader fileReader = new FileReader(file);
            int singalCh;
            while((singalCh = fileReader.read()) != -1) {
                SQLPassword.append((char) singalCh);
            }
        } catch (Exception e) {

            StackTraceElement[] eStackTrace = e.getStackTrace();
            StringBuilder a = new StringBuilder();
            for (StackTraceElement stackTraceElement : eStackTrace) {
                a.append(stackTraceElement).append("\n");
            }
            logger.warn(a.toString());
        }
        StringBuilder endPoint = new StringBuilder();
        try {
            File file = new File("C:\\DiscordServerBotSecrets\\rito-bot\\endPoint.txt");
            FileReader fileReader = new FileReader(file);
            int singalCh;
            while((singalCh = fileReader.read()) != -1) {
                endPoint.append((char) singalCh);
            }
        } catch (Exception e) {

            StackTraceElement[] eStackTrace = e.getStackTrace();
            StringBuilder a = new StringBuilder();
            for (StackTraceElement stackTraceElement : eStackTrace) {
                a.append(stackTraceElement).append("\n");
            }
            logger.warn(a.toString());
        }
        driverName = "com.mysql.cj.jdbc.Driver";
        url = "jdbc:mysql://" + endPoint.toString() + "/ritobotDB?serverTimezone=UTC";
        user = "admin";
        password = SQLPassword.toString();
        System.out.println(url);
        System.out.println(password);
        try {
            connection = DriverManager.getConnection(url, user, password);
            loggingConnection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void SQLupload(String SteamID, String time, String reason, String confirmUser) {
        caseIDup();

        Date date = new Date();
        SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd aa hh:mm:ss");
        String DBWriteTime = dayTime.format(date);
        String queryString = "INSERT INTO Sanction_Infor VALUE (\''" + caseID + "\',\'"+ SteamID + "\', \'" + DBWriteTime + "\', \'" + time + "\', \'" + reason + "\', \'" + confirmUser + "\' );";


        System.out.println(queryString);
        try {
            Class.forName(driverName);

            statement = connection.createStatement();
            statement.executeUpdate(queryString);
            statement.close();
        } catch (Exception e) {

            StackTraceElement[] eStackTrace = e.getStackTrace();
            StringBuilder a = new StringBuilder();
            for (StackTraceElement stackTraceElement : eStackTrace) {
                a.append(stackTraceElement).append("\n");
            }
            logger.warn(a.toString());
        }
    }
    public static String[] SQLdownload(String SteamID) throws SQLException, ClassNotFoundException, InterruptedException {
        String[] data = new String[20];
        for (int i = 0; i < 20; i++) {
            data[i] = null;
        }

        String queryString = "SELECT * FROM Sanction_Infor WHERE SteamID =\''" + SteamID +"\';";

        Class.forName(driverName);
        new Thread(() -> {
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(queryString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(500);
        int i = 0;
        while (resultSet.next()) {
            data[i] = resultSet.getString("caseID");
            i++;
        }
        statement.close();
        if(i > 0) {
            return Arrays.copyOfRange(data, 0, i);
        } else {
            return new String[] {
                    "error"
            };
        }
    }
    public static String[] SQLdownload(int caseID) throws SQLException, ClassNotFoundException, InterruptedException {
        String[] data = new String[5];
        for (int i = 0; i < 5; i++) {
            data[i] = null;
        }

        String queryString = "SELECT * FROM Sanction_Infor WHERE caseID =\'" + caseID +"\';";

        Class.forName(driverName);
        new Thread(() -> {
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(queryString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(500);
        if(resultSet.next()) {
            data[0] = resultSet.getString("SteamID");
            data[1] = resultSet.getString("DBWriteTime");
            data[2] = resultSet.getString("time");
            data[3] = resultSet.getString("reason");
            data[4] = resultSet.getString("confirmUser");
        }
        statement.close();

        return data;
    }
    private static void caseIDup() {
        caseID++;
        try {
            String message = String.valueOf(caseID);

            File file = new File("C:\\DiscordServerBotSecrets\\rito-bot\\caseID.txt");
            FileWriter writer;


            writer = new FileWriter(file, false);
            writer.write(message);
            writer.flush();

            if (writer != null) writer.close();
        } catch (IOException e) {

            StackTraceElement[] eStackTrace = e.getStackTrace();
            StringBuilder a = new StringBuilder();
            for (StackTraceElement stackTraceElement : eStackTrace) {
                a.append(stackTraceElement).append("\n");
            }
            logger.warn(a.toString());
        }
    }
    public static boolean loggingMessageUpLoad(String guildId, String messageId, String contentRaw, String authorId) {
        String queryString = "INSERT INTO messageLogging VALUE (" + guildId + ","+ messageId + ", \'" + contentRaw + "\'," + authorId +");";
        System.out.println(queryString);
        try {
            Class.forName(driverName);

            loggingStatement = loggingConnection.createStatement();
            loggingStatement.executeUpdate(queryString);
            loggingStatement.close();
        } catch (Exception e) {
            StackTraceElement[] eStackTrace = e.getStackTrace();
            StringBuilder a = new StringBuilder();
            for (StackTraceElement stackTraceElement : eStackTrace) {
                a.append(stackTraceElement).append("\n");
            }
            logger.warn(a.toString());
            try {
                loggingConnection = DriverManager.getConnection(url, user, password);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }
        return true;
    }

    public static boolean loggingMessageUpdate(String guildId, String messageId, String contentRaw, String authorId) {
        String queryString = "UPDATE messageLogging SET ContentRaw = \'" + contentRaw +"\' WHERE GuildId = \'" + guildId + "\' AND MessageId = \'" + messageId + "\';";
        System.out.println(queryString);
        try {
            Class.forName(driverName);

            loggingStatement = loggingConnection.createStatement();
            loggingStatement.executeUpdate(queryString);
            loggingStatement.close();
        } catch (Exception e) {
            StackTraceElement[] eStackTrace = e.getStackTrace();
            StringBuilder a = new StringBuilder();
            for (StackTraceElement stackTraceElement : eStackTrace) {
                a.append(stackTraceElement).append("\n");
            }
            logger.warn(a.toString());
            try {
                loggingConnection = DriverManager.getConnection(url, user, password);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }
        return true;
    }

    public static String[] loggingMessageDownLoad(String guildId, String messageId) {
        String[] data = new String[2];
        String queryString = "SELECT * FROM messageLogging WHERE MessageId=" + messageId + " AND GuildId=" + guildId + ";";
        System.out.println(queryString);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            loggingStatement = loggingConnection.createStatement();
            loggingResultSet = loggingStatement.executeQuery(queryString);
            while (loggingResultSet.next()) {
                data[0] = loggingResultSet.getString("ContentRaw");
                data[1] = loggingResultSet.getString("Author");
            }
        } catch (Exception e) {
            StackTraceElement[] eStackTrace = e.getStackTrace();
            StringBuilder a = new StringBuilder();
            for (StackTraceElement stackTraceElement : eStackTrace) {
                a.append(stackTraceElement).append("\n");
            }
            logger.warn(a.toString());
            try {
                loggingConnection = DriverManager.getConnection(url, user, password);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        try {
            loggingStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return data;
    }
    public static final int color_guild = 1;
    public static final int filter = 2;
    public static final int link_filter = 3;
    public static final int kill_filter = 4;
    public static final int lewdneko =5;
    public static final int color_role = 6;
    public static final int textLogging = 7;
    public static final int channelLogging = 8;

    public static String[] configDownLoad(String guildId) {

        String[] return_data = new String[] {
                "0", "0", "0", "0", "0", "0", "0", "1"
        };
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String queryString;
            queryString = "SELECT * FROM ritobot_config.color_command_guild WHERE guildId =" + guildId;
            statement = connection.createStatement();
            resultSet6 = statement.executeQuery(queryString);
            while (resultSet6.next()) {
                return_data[0] = resultSet6.getString("disable");
            }
            resultSet6.close();
            queryString = "SELECT * FROM ritobot_config.filter_guild WHERE guildId =" + guildId;
            resultSet6 = statement.executeQuery(queryString);
            while (resultSet6.next()) {
                return_data[1] = resultSet6.getString("disable");
            }
            queryString = "SELECT * FROM ritobot_config.link_filter_guild WHERE guildId =" + guildId;
            resultSet6 = statement.executeQuery(queryString);
            while (resultSet6.next()) {
                return_data[2] = resultSet6.getString("disable");
            }
            queryString = "SELECT * FROM ritobot_config.kill_filter_guild WHERE guildId =" + guildId;
            resultSet6 = statement.executeQuery(queryString);
            while (resultSet6.next()) {
                return_data[3] = resultSet6.getString("disable");
            }
            queryString = "SELECT * FROM ritobot_config.lewdneko_command WHERE guildId =" + guildId;
            resultSet6 = statement.executeQuery(queryString);
            while (resultSet6.next()) {
                return_data[4] = resultSet6.getString("disable");
            }
            queryString = "SELECT * FROM ritobot_config.logging_enable WHERE guildId =" + guildId;
            resultSet6 = statement.executeQuery(queryString);
            while (resultSet6.next()) {
                return_data[5] = resultSet6.getString("text_logging");
                return_data[6] = resultSet6.getString("channel_logging");
            }
            queryString = "SELECT * FROM ritobot_config.color_command_role WHERE guildId =" + guildId;
            resultSet6 = statement.executeQuery(queryString);
            if (resultSet6.next()) {
                return_data[7] = "0";
            } else {
                return_data[7] = "1";
            }

            resultSet6.close();
        } catch (Exception e) {
            e.printStackTrace();
            return_data = new String[] {"error"};
        }
        return return_data;
    }

    public static void configSetup(String guildId, String disable, String roleId, boolean insert) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String queryString = "";
            if (guildId.length() < 15) {
                return;
            }
            if (insert) {
                queryString = "INSERT INTO ritobot_config.color_command_role VALUES (" + guildId + ", " + roleId + ")";
            } else {
                queryString = "DELETE FROM ritobot_config.color_command_role WHERE guildId =" + guildId + " AND roleId =" + roleId;
            }
            System.out.println(queryString);
            statement = connection.createStatement();
            statement.executeUpdate(queryString);
            resultSet6.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void configSetup(String guildId, int option, String disable) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String queryString = "";
            if (guildId.length() < 15) {
                return;
            }
            switch (option) {
                case color_guild:
                    queryString = "UPDATE ritobot_config.color_command_guild SET disable=" + disable + " WHERE guildId =" + guildId;
                    break;
                case filter:
                    queryString = "UPDATE ritobot_config.filter_guild SET disable=" + disable + " WHERE guildId =" + guildId;
                    break;
                case kill_filter:
                    queryString = "UPDATE ritobot_config.kill_filter_guild SET disable=" + disable + " WHERE guildId =" + guildId;
                    break;
                case lewdneko:
                    queryString = "UPDATE ritobot_config.lewdneko_command SET disable=" + disable + " WHERE guildId =" + guildId;
                    break;
                case link_filter:
                    queryString = "UPDATE ritobot_config.link_filter_guild SET disable=" + disable + " WHERE guildId =" + guildId;
                    break;
                case color_role:
                    if (disable.equals("1")) {
                        queryString = "DELETE FROM ritobot_config.color_command_role WHERE guildId =" + guildId;
                    }
                    break;
                case textLogging:
                    queryString = "UPDATE ritobot_config.logging_enable SET text_logging=" + (disable.equals("1") ? "0" : "1") + " WHERE guildId =" + guildId;
                    break;
                case channelLogging:
                    queryString = "UPDATE ritobot_config.logging_enable SET channel_logging=" + (disable.equals("1") ? "0" : "1") + " WHERE guildId =" + guildId;
                    break;
            }
            System.out.println(queryString);
            statement = connection.createStatement();
            statement.executeUpdate(queryString);
            resultSet6.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String[] configDownLoad_role(String guildId) {
        String[] return_data = new String[] {"error"};
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String queryString;
            int i = 0;
            queryString = "SELECT * FROM ritobot_config.color_command_role WHERE guildId=" + guildId;
            statement = connection.createStatement();
            resultSet6 = statement.executeQuery(queryString);
            return_data = new String[resultSet6.getFetchSize()];
            while (resultSet6.next()) {
                if (i == 0) {
                    return_data = new String[] {
                            resultSet6.getString("roleId")};
                    i++;
                } else {
                    String[] newArray = Arrays.copyOf(return_data, return_data.length + 1);
                    newArray[return_data.length] = resultSet6.getString("roleId");
                    return_data = newArray;
                }
            }
            resultSet6.close();
        } catch (Exception e) {
            e.printStackTrace();
            return_data = new String [] {"error"};
        }
        return return_data;
    }

    public static String[] configDownLoad(int option) {
        String[] return_data = new String[] {"error"};
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String queryString;
            int i = 0;
            switch (option) {
                case color_guild:
                    queryString = "SELECT * FROM ritobot_config.color_command_guild WHERE disable = 1";
                    statement = connection.createStatement();
                    resultSet6 = statement.executeQuery(queryString);
                    while (resultSet6.next()) {
                        if (i == 0) {
                            return_data = new String[] {
                                    resultSet6.getString("guildId")};
                            i++;
                        } else {
                            String[] newArray = Arrays.copyOf(return_data, return_data.length + 1);
                            newArray[return_data.length] = resultSet6.getString("guildId");
                            return_data = newArray;
                        }
                    }
                    break;
                case filter:
                    queryString = "SELECT * FROM ritobot_config.filter_guild WHERE disable = 1";
                    statement = connection.createStatement();
                    resultSet6 = statement.executeQuery(queryString);
                    return_data = new String[resultSet6.getFetchSize()];
                    while (resultSet6.next()) {
                        if (i == 0) {
                            return_data = new String[] {
                                    resultSet6.getString("guildId")};
                            i++;
                        } else {
                            String[] newArray = Arrays.copyOf(return_data, return_data.length + 1);
                            newArray[return_data.length] = resultSet6.getString("guildId");
                            return_data = newArray;
                        }
                    }
                    break;
                case link_filter:
                    queryString = "SELECT * FROM ritobot_config.link_filter_guild WHERE disable = 1";
                    statement = connection.createStatement();
                    resultSet6 = statement.executeQuery(queryString);
                    return_data = new String[resultSet6.getFetchSize()];
                    while (resultSet6.next()) {
                        if (i == 0) {
                            return_data = new String[] {
                                    resultSet6.getString("guildId")};
                            i++;
                        } else {
                            String[] newArray = Arrays.copyOf(return_data, return_data.length + 1);
                            newArray[return_data.length] = resultSet6.getString("guildId");
                            return_data = newArray;
                        }
                    }
                    break;
                case kill_filter:
                    queryString = "SELECT * FROM ritobot_config.kill_filter_guild WHERE disable = 1";
                    statement = connection.createStatement();
                    resultSet6 = statement.executeQuery(queryString);
                    return_data = new String[resultSet6.getFetchSize()];
                    while (resultSet6.next()) {
                        if (i == 0) {
                            return_data = new String[] {
                                    resultSet6.getString("guildId")};
                            i++;
                        } else {
                            String[] newArray = Arrays.copyOf(return_data, return_data.length + 1);
                            newArray[return_data.length] = resultSet6.getString("guildId");
                            return_data = newArray;
                        }
                    }
                    break;
                case lewdneko:
                    queryString = "SELECT * FROM ritobot_config.lewdneko_command WHERE disable = 1";
                    statement = connection.createStatement();
                    resultSet6 = statement.executeQuery(queryString);
                    return_data = new String[resultSet6.getFetchSize()];
                    while (resultSet6.next()) {
                        if (i == 0) {
                            return_data = new String[] {
                                    resultSet6.getString("guildId")};
                            i++;
                        } else {
                            String[] newArray = Arrays.copyOf(return_data, return_data.length + 1);
                            newArray[return_data.length] = resultSet6.getString("guildId");
                            return_data = newArray;
                        }
                    }
                    break;
                case color_role:
                    queryString = "SELECT * FROM ritobot_config.color_command_role;";
                    statement = connection.createStatement();
                    resultSet6 = statement.executeQuery(queryString);
                    return_data = new String[resultSet6.getFetchSize()];
                    while (resultSet6.next()) {
                        if (i == 0) {
                            return_data = new String[] {
                                    resultSet6.getString("roleId")};
                            i++;
                        } else {
                            String[] newArray = Arrays.copyOf(return_data, return_data.length + 1);
                            newArray[return_data.length] = resultSet6.getString("roleId");
                            return_data = newArray;
                        }
                    }
                    break;
                case textLogging:
                    queryString = "SELECT * FROM ritobot_config.logging_enable WHERE text_logging = 1;";
                    statement = connection.createStatement();
                    resultSet6 = statement.executeQuery(queryString);
                    return_data = new String[resultSet6.getFetchSize()];
                    while (resultSet6.next()) {
                        if (i == 0) {
                            return_data = new String[] {
                                    resultSet6.getString("guildId")};
                            i++;
                        } else {
                            String[] newArray = Arrays.copyOf(return_data, return_data.length + 1);
                            newArray[return_data.length] = resultSet6.getString("guildId");
                            return_data = newArray;
                        }
                    }
                    break;
                case channelLogging:
                    queryString = "SELECT * FROM ritobot_config.logging_enable WHERE channel_logging = 1;";
                    statement = connection.createStatement();
                    resultSet6 = statement.executeQuery(queryString);
                    return_data = new String[resultSet6.getFetchSize()];
                    while (resultSet6.next()) {
                        if (i == 0) {
                            return_data = new String[] {
                                    resultSet6.getString("guildId")};
                            i++;
                        } else {
                            String[] newArray = Arrays.copyOf(return_data, return_data.length + 1);
                            newArray[return_data.length] = resultSet6.getString("guildId");
                            return_data = newArray;
                        }
                    }
                    break;
            }
            resultSet6.close();
        } catch (Exception e) {
            e.printStackTrace();
            return_data = new String [] {"error"};
        }
        return return_data;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        SQL.connection = connection;
    }

    public static void setLoggingConnection(Connection loggingConnection) {
        SQL.loggingConnection = loggingConnection;
    }

    public static String getUrl() {
        return url;
    }

    public static String getUser() {
        return user;
    }

    public static String getPassword() {
        return password;
    }
}
