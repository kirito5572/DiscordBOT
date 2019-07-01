package BOT.objects;

import BOT.Secrets;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class getAirLocalData {
    private static String[] airkorea_data = new String[7];

    private static String[] itemCode = new String[] {
            "PM10", "PM25", "O3", "SO2", "CO", "NO2", "측정시간"
    };

    public String[] getAirkorea_data() {
        return airkorea_data;
    }
    public String[] getItemCode() {
        return itemCode;
    }

    public void get_API(String stationName) {
        try{
            String airkorea_url = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureLIst";
            String dataGubun = "HOUR";
            String numOfRows = "1";
            String pageNo = "1";
            String searchCondition = "WEEK";

            DocumentBuilderFactory airkorea_DB_Factoty = DocumentBuilderFactory.newInstance();
            DocumentBuilder airkorea_Builder = airkorea_DB_Factoty.newDocumentBuilder();
            for(int i = 0; i < 6; i++) {
                Document airkorea_doc = airkorea_Builder.parse(
                        airkorea_url +
                                "?serviceKey=" + Secrets.airkorea_serviceKey +
                                "&numOfRows=" + numOfRows +
                                "&pageNo=" + pageNo +
                                "&itemCode=" + itemCode[i] +
                                "&dataGubun=" + dataGubun +
                                "&searchCondition=" + searchCondition
                );
                airkorea_doc.getDocumentElement().normalize();
                NodeList airkorea_nList = airkorea_doc.getElementsByTagName("item");
                Node nNode = airkorea_nList.item(0);
                if(nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;
                    airkorea_data[i] = BOT.objects.getAirData.get_AirKoreaTagValue(stationName,eElement);
                    airkorea_data[6] = BOT.objects.getAirData.get_AirKoreaTagValue("dataTime",eElement);
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }	// try~catch end
    }
}
