//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.kirito5572.objects.logger;

public class config {
    private final SQL sql;
    private static String[] textLoggingEnable;
    private static String[] channelLoggingEnable;
    private static String[] memberLoggingEnable;

    public config(SQL sql) {
        this.sql = sql;
    }

    public void config_load() {
        textLoggingEnable = this.sql.configDownLoad(7);
        channelLoggingEnable = this.sql.configDownLoad(8);
        memberLoggingEnable = this.sql.configDownLoad(9);
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
