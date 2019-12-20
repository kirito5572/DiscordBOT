package BOT.Objects;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class config {
    private static String[] colorRoleById;
    private static String[] colorGuildById;
    private static String[] lewdNekoDisable;
    private static String[] linkFilterDisable;
    private static String[] filterDisable;
    private static String[] killfilterDiable;

    private static String[] textLoggingEnable;

    private final Logger logger = LoggerFactory.getLogger(config.class);
    public config() {
        StringBuilder textconfig = new StringBuilder();
        try {
            File file = new File("C:\\DiscordServerBotSecrets\\rito-bot\\textlogging_config.txt");
            FileReader fileReader = new FileReader(file);
            int singalCh;
            while ((singalCh = fileReader.read()) != -1) {
                textconfig.append((char) singalCh);
            }
        } catch(Exception e) {
            StackTraceElement[] eStackTrace = e.getStackTrace();
            StringBuilder a = new StringBuilder();
            for (StackTraceElement stackTraceElement : eStackTrace) {
                a.append(stackTraceElement).append("\n");
            }
            logger.warn(a.toString());
        }

        String textconfigtemp = textconfig.toString();


        while(textconfigtemp.contains("\r")) {
            textconfigtemp = textconfigtemp.replaceFirst("\r", "");
        }
        logger.info("미리 구성된 설정 불러오는중...");
        System.out.println("미리 구성된 설정 불러오는중...");

        String[] textconfigs = textconfigtemp.split("\n");
        if(!textconfigs[0].contains("#rito-bot textlogging")) {
            logger.error("text logging config error!");
            System.exit(0);
        }
        textLoggingEnable = Arrays.copyOfRange(textconfigs, 1, textconfigs.length);

        colorGuildById = SQL.configDownLoad(SQL.color_guild);
        lewdNekoDisable = SQL.configDownLoad(SQL.lewdneko);
        linkFilterDisable = SQL.configDownLoad(SQL.link_filter);
        filterDisable = SQL.configDownLoad(SQL.filter);
        killfilterDiable = SQL.configDownLoad(SQL.kill_filter);
        colorRoleById = SQL.configDownLoad(SQL.color_role);
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

    public static String[] getTextLoggingEnable() {
        return textLoggingEnable;
    }

    public static void setTextLoggingEnable(String textLoggingEnable) {
        String[] output = new String[config.textLoggingEnable.length + 1];
        System.arraycopy(config.textLoggingEnable,0,output,0,config.textLoggingEnable.length);
        output[output.length -1] = textLoggingEnable;

        config.textLoggingEnable = output;


    }
}
