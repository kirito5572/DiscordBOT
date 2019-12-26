package BOT.Objects;

import org.jetbrains.annotations.NotNull;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class linkConfirm {
    private static String link;
    public static boolean isLink(@NotNull String rawMessage, String s) {
        if(rawMessage.startsWith("http://")) {
            rawMessage = rawMessage.replaceFirst("http://", "");
        }
        if(rawMessage.startsWith("https://")) {
            rawMessage = rawMessage.replaceFirst("https://", "");
        }
        Pattern p = Pattern.compile("[A-Za-z0-9_]*" + s);
        Matcher m = p.matcher(rawMessage);
        if(rawMessage.contains(" ")) {
            p = Pattern.compile("\\s[A-Za-z0-9_]*" + s);
            m = p.matcher(rawMessage);
        }
        boolean hangulflag = false;
        if(!m.find()) {
            p = Pattern.compile("[가-힣][A-Za-z0-9_]*" + s);
            m = p.matcher(rawMessage);
            hangulflag = true;
        }
        int i = 0;
        while (m.find()) {
            try {
                System.out.println(m.group());
                String text = m.group(i);
                if(text.contains(" ")) {
                    text = text.replaceFirst(" ", "");
                }
                if(hangulflag) {
                    text = text.substring(1);
                }
                System.out.println("http://" + text);
                URL url = new URL("http://" + text);
                link = text;
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                if (!(connection.getResponseCode() == HttpURLConnection.HTTP_OK || connection.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED || connection.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM
                || connection.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP || connection.getResponseCode() == HttpURLConnection.HTTP_NO_CONTENT)) {
                    i++;
                    if(i < m.groupCount()) {
                        return false;
                    }
                } else {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static String getLink() {
        return link;
    }
}
