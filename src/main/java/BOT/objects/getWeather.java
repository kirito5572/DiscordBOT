package BOT.objects;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class getWeather {

    private static long time = System.currentTimeMillis();
    private static SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat hhmm = new SimpleDateFormat("HHmm");
    private static String yyyymmddStr = yyyymmdd.format(new Date(time));
    private static String hhmmStr = hhmm.format(new Date(time));


    private static String[] weather_category = new String[90];
    private static String[] fcstValue = new String[90];

    private static String[] weather_POP = new String[8];
    private static String[] weather_PTY = new String[8];
    private static String[] weather_R06 = new String[8];
    private static String[] weather_REH = new String[8];
    private static String[] weather_S06 = new String[8];
    private static String[] weather_SKY = new String[8];
    private static String[] weather_T3H = new String[8];
    private static String[] weather_TMN = new String[8];
    private static String[] weather_TMX = new String[8];
    private static String[] weather_UUU = new String[8];
    private static String[] weather_VVV = new String[8];
    private static String[] weather_WAV = new String[8];
    private static String[] weather_VEC = new String[8];
    private static String[] weather_WSD = new String[8];

    public String[] getWeather_POP() {
        return weather_POP;
    }

    public String[] getWeather_PTY() {
        return weather_PTY;
    }

    public String[] getWeather_R06() {
        return weather_R06;
    }

    public String[] getWeather_REH() {
        return weather_REH;
    }

    public String[] getWeather_S06() {
        return weather_S06;
    }

    public String[] getWeather_SKY() {
        return weather_SKY;
    }

    public String[] getWeather_T3H() {
        return weather_T3H;
    }

    public String[] getWeather_TMN() {
        return weather_TMN;
    }

    public String[] getWeather_TMX() {
        return weather_TMX;
    }

    public String[] getWeather_UUU() {
        return weather_UUU;
    }

    public String[] getWeather_VVV() {
        return weather_VVV;
    }

    public String[] getWeather_WAV() {
        return weather_WAV;
    }

    public String[] getWeather_VEC() {
        return weather_VEC;
    }

    public String[] getWeather_WSD() {
        return weather_WSD;
    }

    void get_API(String stationName) {
        int page = 1;	// 페이지 초기값
        try{

            String weather_url = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastSpaceData";
            String weather_serviceKey = "rd9jDc4rAZCzh815fSejIsMIAOsAt%2Fd3DNWjNCe1XKh7jkHlo9OLJtwHAGfkhlhrjaU%2FFVwBi35i7yY7D3I2vg%3D%3D";
            String base_date = yyyymmddStr;
            String base_time = "";
            String pageNo = "1";
            System.out.println(yyyymmddStr);
            System.out.println(hhmmStr);
            if(Integer.parseInt(hhmmStr) <= 220) {
                base_time = "2300";
                base_date = String.valueOf(Integer.parseInt(base_date) - 1);
            }
            if(Integer.parseInt(hhmmStr) >= 220) {
                base_time = "0200";
            }
            if(Integer.parseInt(hhmmStr) >= 520) {
                base_time = "0500";
            }
            if(Integer.parseInt(hhmmStr) >= 820) {
                base_time = "0800";
            }
            if(Integer.parseInt(hhmmStr) >= 1120) {
                base_time = "1100";
            }
            if(Integer.parseInt(hhmmStr) >= 1420) {
                base_time = "1400";
            }
            if(Integer.parseInt(hhmmStr) >= 1720) {
                base_time = "1700";
            }
            if(Integer.parseInt(hhmmStr) >= 2020) {
                base_time = "2000";
            }
            if(Integer.parseInt(hhmmStr) >= 2320) {
                base_time = "2300";
            }
            //TODO stationName set
            String nx = "54";
            String ny = "125";
            String type = "xml";
            String weather_numOfRows = "90";
            // parsing할 url 지정(API 키 포함해서)
            String weather_get_url = weather_url + "?&ServiceKey=" + weather_serviceKey + "&base_date=" + base_date + "&base_time=" + base_time + "&nx=" + nx + "&ny=" + ny + "&numOfRows=" + weather_numOfRows + "&pageNo=" + pageNo + "_type=" + type;

            DocumentBuilderFactory weather_DB_Factoty = DocumentBuilderFactory.newInstance();
            DocumentBuilder weather_Builder = weather_DB_Factoty.newDocumentBuilder();
            Document weather_doc = weather_Builder.parse(weather_get_url);

            weather_doc.getDocumentElement().normalize();

            NodeList weather_nList = weather_doc.getElementsByTagName("item");

            for(int temp = 0; temp < weather_nList.getLength(); temp++){
                Node weather_nNode = weather_nList.item(temp);
                if(weather_nNode.getNodeType() == Node.ELEMENT_NODE){

                    Element eElement = (Element) weather_nNode;
                    weather_category[temp] = (get_WeatherTagValue("category", eElement));
                    fcstValue[temp] = (get_WeatherTagValue("fcstValue", eElement));
                }	// for end
            }	// if end
            System.out.println();
            new Thread(() -> {
                int a, weather_data = 0;
                for(a = 0; a < 90; a++) {
                    if(weather_category[a].equals("POP")) {
                        weather_data++;
                        if(a < 5) {
                            weather_data = 0;
                        }
                    }
                    try {
                        switch (weather_category[a]) {
                            case "POP":
                                weather_POP[weather_data] = fcstValue[a];
                                break;
                            case "PTY":
                                weather_PTY[weather_data] = fcstValue[a];
                                break;
                            case "R06":
                                weather_R06[weather_data] = fcstValue[a];
                                break;
                            case "REH":
                                weather_REH[weather_data] = fcstValue[a];
                                break;
                            case "S06":
                                weather_S06[weather_data] = fcstValue[a];
                                break;
                            case "SKY":
                                weather_SKY[weather_data] = fcstValue[a];
                                break;
                            case "T3H":
                                weather_T3H[weather_data] = fcstValue[a];
                                break;
                            case "TMN":
                                weather_TMN[weather_data] = fcstValue[a];
                                break;
                            case "TMX":
                                weather_TMX[weather_data] = fcstValue[a];
                                break;
                            case "UUU":
                                weather_UUU[weather_data] = fcstValue[a];
                                break;
                            case "VEC":
                                weather_VEC[weather_data] = fcstValue[a];
                                break;
                            case "VVV":
                                weather_VVV[weather_data] = fcstValue[a];
                                break;
                            case "WSD":
                                weather_WSD[weather_data] = fcstValue[a];
                                break;
                            case "WAV":
                                weather_WAV[weather_data] = fcstValue[a];
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }).start();



        } catch (Exception e){
            e.printStackTrace();
        }	// try~catch end
    }
    private static String get_WeatherTagValue(String weather_tag, Element weather_eElement) {
        NodeList nlList = weather_eElement.getElementsByTagName(weather_tag).item(0).getChildNodes();
        Node nValue = nlList.item(0);
        if(nValue == null)
            return null;
        return nValue.getNodeValue();
    }
}
