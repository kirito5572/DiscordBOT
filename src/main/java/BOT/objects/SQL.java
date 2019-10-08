package BOT.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SQL {
    private static final Logger logger = LoggerFactory.getLogger(SQL.class);
    private static int caseID;
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
            StackTraceElement[] eStackTrace = e.getStackTrace();
            StringBuilder a = new StringBuilder();
            for (StackTraceElement stackTraceElement : eStackTrace) {
                a.append(stackTraceElement).append("\n");
            }
            logger.warn(a.toString());
        }
        String driverName = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://" + endPoint.toString() + "/ritobotDB?serverTimezone=UTC";
        String user = "admin";
        String password = SQLPassword.toString();

        try {
            Class.forName(driverName);

            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            StackTraceElement[] eStackTrace = e.getStackTrace();
            StringBuilder a = new StringBuilder();
            for (StackTraceElement stackTraceElement : eStackTrace) {
                a.append(stackTraceElement).append("\n");
            }
            logger.warn(a.toString());
        }
    }
    public static void SQLupload(String SteamID, String time, String reason, String confirmUser) {
        caseIDup();

        Date date = new Date();
        SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String DBWriteTime = dayTime.format(date);
        String queryString = "INSERT INTO Sanction_Infor VALUE (\"" + caseID + "\",\""+ SteamID + "\", \"" + DBWriteTime + "\", \"" + time + "\", \"" + reason + "\", \"" + confirmUser + "\" );";


        System.out.println(queryString);
        try {
            statement = connection.createStatement();
            statement.executeUpdate(queryString);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
            StackTraceElement[] eStackTrace = e.getStackTrace();
            StringBuilder a = new StringBuilder();
            for (StackTraceElement stackTraceElement : eStackTrace) {
                a.append(stackTraceElement).append("\n");
            }
            logger.warn(a.toString());
        }
    }
    public static String[][] SQLdownload(String SteamID) throws SQLException {
        String[][] data = new String[10][7];
        for (int i = 0; i < 10; i++) {
            for(int j = 0; j < 7; j++) {
                data[i][j] = null;
            }
        }

        String queryString = "SELECT * FROM Sanction_Infor WHERE SteamID =\"" + SteamID +"\";";


        statement = connection.createStatement();
        resultSet = statement.executeQuery(queryString);
        statement.close();
        int i = 0;
        while (resultSet.next()) {
            data[i][0] = resultSet.getString("caseID");
            data[i][1] = resultSet.getString("SteamID");
            data[i][2] = resultSet.getString("sanctionTime");
            data[i][3] = resultSet.getString("endTime");
            data[i][4] = resultSet.getString("reason");
            data[i][5] = resultSet.getString("sendServer");
            data[i][6] = resultSet.getString("serverID");
        }
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
            e.printStackTrace();
            StackTraceElement[] eStackTrace = e.getStackTrace();
            StringBuilder a = new StringBuilder();
            for (StackTraceElement stackTraceElement : eStackTrace) {
                a.append(stackTraceElement).append("\n");
            }
            logger.warn(a.toString());
        }
    }
}
