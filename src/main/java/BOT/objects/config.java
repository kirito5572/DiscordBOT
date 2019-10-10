package BOT.Objects;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;

public class config {
    private static String[] colorRoleById;
    private static String[] colorGuildById;
    private static String[] lewdNekoDisable;
    private static String[] linkFilterDisable;
    private static String[] filterDisable;
    private static String[] killfilterDiable;

    private final Logger logger = LoggerFactory.getLogger(config.class);
    public config() {
        StringBuilder config = new StringBuilder();
        try {
            File file = new File("C:\\DiscordServerBotSecrets\\rito-bot\\bot_config.txt");
            FileReader fileReader = new FileReader(file);
            int singalCh;
            while ((singalCh = fileReader.read()) != -1) {
                config.append((char) singalCh);
            }
        } catch(Exception e) {
            StackTraceElement[] eStackTrace = e.getStackTrace();
            StringBuilder a = new StringBuilder();
            for (StackTraceElement stackTraceElement : eStackTrace) {
                a.append(stackTraceElement).append("\n");
            }
            logger.warn(a.toString());
        }
        String configtemp = config.toString();
        while(configtemp.contains("\r")) {
            configtemp = configtemp.replaceFirst("\r", "");
        }
        String[] configs = configtemp.split("#");
        logger.info("미리 구성된 설정 불러오는중...");
        if(!configs[1].contains("rito-bot config")) {
            logger.error("config error!");
            System.exit(0);
        }
        if(!configs[2].contains("Color RolebyId")) {
            logger.error("color role config error!");
            System.exit(0);
        }
        colorRoleById = configs[2].substring(15).split("\n");
        if(!configs[3].contains("Color GuildID")) {
            logger.error("color guild config error!");
            System.exit(0);
        }
        colorGuildById = configs[3].substring(14).split("\n");
        if(!configs[4].contains("lewdNekoDisable")) {
            logger.error("lewdNeko config error!");
            System.exit(0);
        }
        lewdNekoDisable = configs[4].substring(16).split("\n");
        if(!configs[5].contains("linkfilter disable")) {
            logger.error("linkfilter config error!");
            System.exit(0);
        }
        linkFilterDisable = configs[5].substring(19).split("\n");
        if(!configs[6].contains("filter disable")) {
            logger.error("filter config error!");
            System.exit(0);
        }
        filterDisable = configs[6].substring(14).split("\n");
        if(!configs[7].contains("killfilter disable")) {
            logger.error("killfilter config error!");
            System.exit(0);
        }
        killfilterDiable = configs[7].substring(19).split("\n");
    }

    public static String[] getColorGuildById() {
        return colorGuildById;
    }

    public static String[] getColorRoleById() {
        return colorRoleById;
    }

    public static String[] getFilterDisable() {
        return filterDisable;
    }

    public static String[] getKillfilterDiable() {
        return killfilterDiable;
    }

    public static String[] getLewdNekoDisable() {
        return lewdNekoDisable;
    }

    public static String[] getLinkFilterDisable() {
        return linkFilterDisable;
    }
}
