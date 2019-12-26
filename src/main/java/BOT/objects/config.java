package BOT.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class config {
    private static String[] colorRoleById;
    private static String[] colorGuildById;
    private static String[] lewdNekoDisable;
    private static String[] linkFilterDisable;
    private static String[] filterDisable;
    private static String[] killfilterDiable;

    private static String[] textLoggingEnable;
    private static String[] channelLoggingEnable;
    private static String[] memberLoggingEnable;

    private static final Logger logger = LoggerFactory.getLogger(config.class);
    public static void config_load() {
        logger.info("설정 로딩중...");
        System.out.println("설정 로딩중...");

        colorGuildById = SQL.configDownLoad(SQL.color_guild);
        lewdNekoDisable = SQL.configDownLoad(SQL.lewdneko);
        linkFilterDisable = SQL.configDownLoad(SQL.link_filter);
        filterDisable = SQL.configDownLoad(SQL.filter);
        killfilterDiable = SQL.configDownLoad(SQL.kill_filter);
        colorRoleById = SQL.configDownLoad(SQL.color_role);
        textLoggingEnable = SQL.configDownLoad(SQL.textLogging);
        channelLoggingEnable = SQL.configDownLoad(SQL.channelLogging);
        memberLoggingEnable = SQL.configDownLoad(SQL.memberLogging);
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

    public static String[] getChannelLoggingEnable() {
        return channelLoggingEnable;
    }

    public static String[] getMemberLoggingEnable() {
        return memberLoggingEnable;
    }

}
