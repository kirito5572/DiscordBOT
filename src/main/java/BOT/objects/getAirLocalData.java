package BOT.Objects;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileReader;

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

            String TOKEN = "";
            try {
                File file = new File("C:\\DiscordServerBotSecrets\\rito-bot\\airkoreaAPIKEY.txt");
                FileReader fileReader = new FileReader(file);
                int singalCh = 0;
                while((singalCh = fileReader.read()) != -1) {
                    TOKEN = TOKEN + (char)singalCh;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            DocumentBuilderFactory airkorea_DB_Factoty = DocumentBuilderFactory.newInstance();
            DocumentBuilder airkorea_Builder = airkorea_DB_Factoty.newDocumentBuilder();
            for(int i = 0; i < 6; i++) {
                Document airkorea_doc = airkorea_Builder.parse(
                        airkorea_url +
                                "?serviceKey=" + TOKEN +
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
                    airkorea_data[i] = BOT.Objects.getAirData.get_AirKoreaTagValue(stationName,eElement);
                    airkorea_data[6] = BOT.Objects.getAirData.get_AirKoreaTagValue("dataTime",eElement);
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }	// try~catch end
    }
}