package BOT.objects;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class getWeather {
    private static final SimpleDateFormat clock_aa = new SimpleDateFormat("a");
    private static final SimpleDateFormat clock_am = new SimpleDateFormat("K시 mm분 ss초(z)");
    private static final SimpleDateFormat clock_pm = new SimpleDateFormat("h시 mm분 ss초(z)");
    private static String[] weather_infor = new String[12];
    private static String[] weather_list = new String[] {
            "날씨 상태", "현재 온도", "대기압", "습도", "최저 온도(현재)",
            "최고 온도(현재)", "풍속", "바람의 방향", "3시간 강수량", "3시간 적설량",
            "일출 시간", "일몰 시간"
    };

    public static void get_api(String city_name) {

        try {

            String TOKEN = "";
            try {
                File file = new File("D:\\DiscordServerBotSecrets\\ritobot\\weather_key.txt");
                FileReader fileReader = new FileReader(file);
                int singalCh = 0;
                while((singalCh = fileReader.read()) != -1) {
                    TOKEN = TOKEN + (char)singalCh;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            String url = "https://api.openweathermap.org/data/2.5/weather?q="+ city_name + "&appid=" + TOKEN + "&lang=kr" + "&units=metric";
            System.out.println(url);
            URL get_url = new URL(url);

            BufferedReader bf;
            String line = "";
            String result = "";

            bf = new BufferedReader(new InputStreamReader(get_url.openStream()));

            while((line=bf.readLine()) != null) {
                result = result.concat(line);
            }

            bf.close();
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(result);
            JSONArray parse_weather = (JSONArray) obj.get("weather");
            JSONObject parse_main = (JSONObject) obj.get("main");
            JSONObject parse_wind = (JSONObject) obj.get("wind");
            JSONObject parse_rain = (JSONObject) obj.get("rain");
            JSONObject parse_snow = (JSONObject) obj.get("snow");
            JSONObject parse_sys = (JSONObject) obj.get("sys");

            weather_infor[0] = parse_weather.get(0).toString();   //날씨 상태
            weather_infor[0] = weather_infor[0].substring(weather_infor[0].indexOf("\"description\":\"") + 15,weather_infor[0].indexOf("\",\"main\""));
            weather_infor[1] = parse_main.get("temp").toString() + "Cº";      // 온도

            weather_infor[2] = parse_main.get("pressure").toString() + "hPa";      // 대기압

            weather_infor[3] = parse_main.get("humidity").toString() + "%";      // 습도

            weather_infor[4] = parse_main.get("temp_min").toString() + "Cº";;      // 순간최저온도

            weather_infor[5] = parse_main.get("temp_max").toString()+ "Cº";      // 순간최대온도

            weather_infor[6] = parse_wind.get("speed").toString() + "m/s";      // 풍속

            weather_infor[7] = parse_wind.get("deg").toString() + "";      // 풍향

            if(weather_infor[0].equals("비")) {
                weather_infor[8] = parse_rain.get("3h").toString() + "mm";      // 3시간 강수량
            } else {
                weather_infor[8] = "null";
            }
            if(weather_infor[0].equals("눈")) {
                weather_infor[9] = parse_snow.get("3h").toString() + "cm";      // 3시간 적설량
            } else {
                weather_infor[9] = "null";
            }

            Date time;
            weather_infor[10] = parse_sys.get("sunrise").toString();      // 일출시간
            time = new Date(Long.parseLong(weather_infor[10]) * 1000);
            weather_infor[10] = formatDate(time);

            weather_infor[11] = parse_sys.get("sunset").toString();      // 일몰시간
            time = new Date(Long.parseLong(weather_infor[11]) * 1000);
            weather_infor[11] = formatDate(time);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static String formatDate(Date date) {
        String flag = clock_aa.format(date);
        if(flag.equals("AM")) {
            return "오전 " + clock_am.format(date);
        } else {
            return "오후 " + clock_pm.format(date);
        }
    }

    public static String[] getWeather_infor() {
        return weather_infor;
    }

    public static String[] getWeather_list() {
        return weather_list;
    }
}
