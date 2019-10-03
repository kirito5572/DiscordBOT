package BOT.Objects;

import me.duncte123.botcommons.web.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class getSteamID {
    private static final Logger logger = LoggerFactory.getLogger(getSteamID.class);
    public static String[] SteamID(String ID) {
        String[] returns = new String[2];
        final boolean[] flag = {true};
        try {
            WebUtils.ins.scrapeWebPage("https://steamcommunity.com/profiles/" + ID + "/?xml=1").async((document -> {
                String a1 = document.getElementsByTag("profile").first().toString();
                String a2 = a1;
                try {
                    a1 = a1.substring(a1.indexOf("<steamid64>") + 14, a1.indexOf("</steamid64>") - 2);
                    a2 = a2.substring(a2.indexOf("<steamid>"));
                    a2 = a2.substring(a2.indexOf("![CDATA[") + 8, a2.indexOf("]]>"));
                    returns[0] = a2;
                    returns[1] = a1;

                } catch (Exception e) {
                    e.printStackTrace();
                    StackTraceElement[] eStackTrace = e.getStackTrace();
                    StringBuilder a = new StringBuilder();
                    for (StackTraceElement stackTraceElement : eStackTrace) {
                        a.append(stackTraceElement).append("\n");
                    }
                    logger.warn(a.toString());

                    returns[0] = "error";
                }
                flag[0] = false;
            }));
            while (flag[0]) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    StackTraceElement[] eStackTrace = e.getStackTrace();
                    StringBuilder a = new StringBuilder();
                    for (StackTraceElement stackTraceElement : eStackTrace) {
                        a.append(stackTraceElement).append("\n");
                    }
                    logger.warn(a.toString());
                }
            }
            return returns;
        } catch (Exception e) {
            return new String[] {
                    "profile not found"
            };
        }
    }
}