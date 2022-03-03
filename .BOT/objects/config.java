package BOT.Objects;

import com.kirito5572.listener.main.Listener;
import BOT.Listener.filterListener;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class config {
    private static String[] colorRoleById;
    private static String[] colorGuildById;
    private static String[] lewdNekoDisable;
    private static String[] linkFilterDisable;
    private static String[] filterDisable;
    private static String[] killfilterDisable;
    private static String[] customFilterDisable;

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
        killfilterDisable = SQL.configDownLoad(SQL.kill_filter);
        customFilterDisable = SQL.configDownLoad(SQL.customFilter);
        colorRoleById = SQL.configDownLoad(SQL.color_role);
        textLoggingEnable = SQL.configDownLoad(SQL.textLogging);
        channelLoggingEnable = SQL.configDownLoad(SQL.channelLogging);
        memberLoggingEnable = SQL.configDownLoad(SQL.memberLogging);
        Listener.setBotChannel(SQL.configDownLoad(SQL.botchannel, true));
        Map<String, List<String>> stringListMap = new HashMap<>();
        for(Map.Entry<String, String> entry : SQL.configDownLoad(SQL.customFilter, true).entrySet()) {
            List<String> arrayList = new ArrayList<>();
            SQL.Data a = new Gson().fromJson(new JsonParser().parse(entry.getValue()), SQL.Data.class);
            for(JsonElement jsonElement : a.data) {
                arrayList.add(jsonElement.getAsString());
            }
            stringListMap.put(entry.getKey(), arrayList);
        }
        filterListener.setCustomFilterWordMap(stringListMap);
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
        return killfilterDisable;
    }

    public static String[] getCustomFilterDisable() {
        return customFilterDisable;
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
