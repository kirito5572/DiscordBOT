package BOT.objects;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class getAirData {
    private static long time = System.currentTimeMillis();
    private static String[] airkorea_data = new String[13];
    private static String[] airkorea_List = new String[6];
    {
        airkorea_List[0] = "측정시간";
        airkorea_List[1] = "SO2";
        airkorea_List[2] = "CO(일산화탄소)";
        airkorea_List[3] = "O3(오존)";
        airkorea_List[4] = "NO2(이산화질소)";
        airkorea_List[5] = "PM10(미세먼지)";
        airkorea_List[6] = "PM2.5(초미세먼지)";
    }

    public String[] getAirkorea_data() {
        return airkorea_data;
    }
    public String[] getAirkorea_List() {
        return airkorea_List;
    }


    public void get_API(String stationName) {
        try{
            String airkorea_url = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty";
            String dataTerm = "DAILY";
            String numOfRows = "10";
            String pageNo = "1";
            String ver = "1.3";
            String airkorea_serviceKey = "rd9jDc4rAZCzh815fSejIsMIAOsAt%2Fd3DNWjNCe1XKh7jkHlo9OLJtwHAGfkhlhrjaU%2FFVwBi35i7yY7D3I2vg%3D%3D";


            DocumentBuilderFactory airkorea_DB_Factoty = DocumentBuilderFactory.newInstance();
            DocumentBuilder airkorea_Builder = airkorea_DB_Factoty.newDocumentBuilder();
            Document airkorea_doc = airkorea_Builder.parse(airkorea_url + "?&stationName=" + stationName + "&dataTerm=" + dataTerm + "&numOfRows" + numOfRows + "&pageNo=" + pageNo + "&ver=" + ver + "&serviceKey=" + airkorea_serviceKey);

            // root tag
            airkorea_doc.getDocumentElement().normalize();
            // 파싱할 tag
            NodeList airkorea_nList = airkorea_doc.getElementsByTagName("item");

            Node nNode = airkorea_nList.item(0);
            String airkorea_dataTime = "";
            String airkorea_so2V = "";
            String airkorea_coV = "";
            String airkorea_o3V = "";
            String airkorea_no2V = "";
            String airkorea_pm10V = "";
            String airkorea_pm25V = "";
            String airkorea_khaiV = "";
            String airkorea_khaiG = "";
            String airkorea_so2G = "";
            String airkorea_coG = "";
            String airkorea_o3G = "";
            String airkorea_no2G = "";
            String airkorea_pm10G = "";
            String airkorea_pm25G = "";
            if(nNode.getNodeType() == Node.ELEMENT_NODE){

                Element eElement = (Element) nNode;
                airkorea_dataTime = (get_AirKoreaTagValue("dataTime", eElement));
                airkorea_so2V = (get_AirKoreaTagValue("so2Value", eElement));
                airkorea_coV = (get_AirKoreaTagValue("coValue", eElement));
                airkorea_o3V = (get_AirKoreaTagValue("o3Value", eElement));
                airkorea_no2V = (get_AirKoreaTagValue("no2Value", eElement));
                airkorea_pm10V = (get_AirKoreaTagValue("pm10Value", eElement));
                airkorea_pm25V = (get_AirKoreaTagValue("pm25Value", eElement));
                airkorea_khaiV = (get_AirKoreaTagValue("khaiValue", eElement));
                airkorea_khaiG = (get_AirKoreaTagValue("khaiGrade", eElement));
                airkorea_so2G = (get_AirKoreaTagValue("so2Grade", eElement));
                airkorea_coG = (get_AirKoreaTagValue("coGrade", eElement));
                airkorea_o3G = (get_AirKoreaTagValue("o3Grade", eElement));
                airkorea_no2G = (get_AirKoreaTagValue("no2Grade", eElement));
                airkorea_pm10G = (get_AirKoreaTagValue("pm10Grade", eElement));
                airkorea_pm25G = (get_AirKoreaTagValue("pm25Grade", eElement));
            }	// if end

            airkorea_data[0] = airkorea_dataTime;
            airkorea_data[1] = airkorea_so2V;
            airkorea_data[2] = airkorea_coV;
            airkorea_data[3] = airkorea_o3V;
            airkorea_data[4] = airkorea_no2V;
            airkorea_data[5] = airkorea_pm10V;
            airkorea_data[6] = airkorea_pm25V;
            airkorea_data[7] = airkorea_khaiV;
            airkorea_data[8] = airkorea_so2G;
            airkorea_data[9] = airkorea_coG;
            airkorea_data[10] = airkorea_o3G;
            airkorea_data[11] = airkorea_no2G;
            airkorea_data[12] = airkorea_pm10G;
            airkorea_data[13] = airkorea_pm25G;
            airkorea_data[14] = airkorea_khaiG;

        } catch (Exception e){
            e.printStackTrace();
        }	// try~catch end
    }
    public static String get_AirKoreaTagValue(String airkorea_tag, Element airkorea_eElement) {
        NodeList nlList = airkorea_eElement.getElementsByTagName(airkorea_tag).item(0).getChildNodes();
        Node nValue = nlList.item(0);
        if(nValue == null)
            return null;
        return nValue.getNodeValue();
    }
}