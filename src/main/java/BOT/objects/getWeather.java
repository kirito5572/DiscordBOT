package BOT.Objects;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class getWeather {
    private static final Logger logger = LoggerFactory.getLogger(getWeather.class);
    private static final SimpleDateFormat clock_aa = new SimpleDateFormat("a");
    private static final SimpleDateFormat clock_am = new SimpleDateFormat("K시 mm분 ss초(z)");
    private static final SimpleDateFormat clock_pm = new SimpleDateFormat("h시 mm분 ss초(z)");
    private static final String[] weather_infor = new String[12];
    private static final String[] weather_list = new String[] {
            "날씨 상태", "현재 온도", "대기압", "습도", "풍속",
            "바람의 방향", "3시간 강수량", "3시간 적설량",
            "일출 시간", "일몰 시간"
    };

    public static void get_api(String city_name) {

        try {

            StringBuilder TOKEN = new StringBuilder();
            try {
                File file = new File("C:\\DiscordServerBotSecrets\\rito-bot\\weather_key.txt");
                FileReader fileReader = new FileReader(file);
                int singalCh;
                while((singalCh = fileReader.read()) != -1) {
                    TOKEN.append((char) singalCh);
                }
            } catch (Exception e) {

                StackTraceElement[] eStackTrace = e.getStackTrace();
                StringBuilder a = new StringBuilder();
                for (StackTraceElement stackTraceElement : eStackTrace) {
                    a.append(stackTraceElement).append("\n");
                }
                logger.warn(a.toString());
            }

            String url = "https://api.openweathermap.org/data/2.5/weather?q="+ city_name + "&appid=" + TOKEN + "&lang=kr" + "&units=metric";
            URL get_url = new URL(url);

            BufferedReader bf;
            String line;
            String result = "";

            bf = new BufferedReader(new InputStreamReader(get_url.openStream()));

            while((line=bf.readLine()) != null) {
                result = result.concat(line);
            }
            /*
            {
              "coord": {
                "lon": 126.42,
                "lat": 37.45
              },
              "weather": [
                {
                  "id": 802,
                  "main": "Clouds",
                  "description": "구름조금",
                  "icon": "03n"
                }
              ],
              "base": "stations",
              "main": {
                "temp": -1.45,
                "feels_like": -6.46,
                "temp_min": -4,
                "temp_max": 1,
                "pressure": 1021,
                "humidity": 64
              },
              "visibility": 10000,
              "wind": {
                "speed": 3.1,
                "deg": 310
              },
              "clouds": {
                "all": 28
              },
              "dt": 1579473024,
              "sys": {
                "type": 1,
                "id": 8093,
                "country": "KR",
                "sunrise": 1579473965,
                "sunset": 1579509817
              },
              "timezone": 32400,
              "id": 1843561,
              "name": "Incheon",
              "cod": 200
            }
             */
            bf.close();
            JsonParser parser = new JsonParser();
            JsonObject element = parser.parse(result).getAsJsonObject();
            JsonArray parse_weather = element.get("weather").getAsJsonArray();
            JsonObject parse_main = element.get("main").getAsJsonObject();
            JsonObject parse_wind = element.get("wind").getAsJsonObject();
            JsonObject parse_rain = element.get("rain").getAsJsonObject();
            JsonObject parse_snow = element.get("snow").getAsJsonObject();
            JsonObject parse_sys = element.get("sys").getAsJsonObject();

            weather_infor[0] = parse_weather.get(0).getAsJsonObject().get("description").getAsString();   //날씨 상태
            weather_infor[1] = parse_main.get("temp").getAsString() + "Cº";      // 온도

            weather_infor[2] = parse_main.get("pressure").getAsString() + "hPa";      // 대기압

            weather_infor[3] = parse_main.get("humidity").getAsString() + "%";      // 습도

            weather_infor[4] = parse_wind.get("speed").getAsString() + "m/s";      // 풍속

            weather_infor[5] = parse_wind.get("deg").getAsString() + "º";      // 풍향

            if(weather_infor[0].equals("비")) {
                weather_infor[6] = parse_rain.get("3h").getAsString() + "mm";      // 3시간 강수량
            } else {
                weather_infor[6] = "null";
            }
            if(weather_infor[0].equals("눈")) {
                weather_infor[7] = parse_snow.get("3h").getAsString() + "cm";      // 3시간 적설량
            } else {
                weather_infor[7] = "null";
            }

            Date time;
            weather_infor[8] = parse_sys.get("sunrise").getAsString();      // 일출시간
            time = new Date(Long.parseLong(weather_infor[8]) * 1000);
            weather_infor[8] = formatDate(time);

            weather_infor[9] = parse_sys.get("sunset").getAsString();      // 일몰시간
            time = new Date(Long.parseLong(weather_infor[9]) * 1000);
            weather_infor[9] = formatDate(time);

        } catch (Exception e) {

            StackTraceElement[] eStackTrace = e.getStackTrace();
            StringBuilder a = new StringBuilder();
            for (StackTraceElement stackTraceElement : eStackTrace) {
                a.append(stackTraceElement).append("\n");
            }
            logger.warn(a.toString());
        }

    }
    @NotNull
    private static String formatDate(Date date) {
        String flag = clock_aa.format(date);
        if(flag.equals("오전")) {
            return "오전 " + clock_am.format(date);
        } else {
            return "오후 " + clock_pm.format(date);
        }
    }

    @NotNull
    public static String[] getWeather_infor() {
        return weather_infor;
    }

    @NotNull
    public static String[] getWeather_list() {
        return weather_list;
    }
}