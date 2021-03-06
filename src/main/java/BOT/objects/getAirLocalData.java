package BOT.Objects;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileReader;

public class getAirLocalData {
    private final Logger logger = LoggerFactory.getLogger(getAirLocalData.class);
    private static final String[] airkorea_data = new String[7];

    private static final String[] itemCode = new String[] {
            "PM10", "PM25", "O3", "SO2", "CO", "NO2", "측정시간"
    };

    @NotNull
    public String[] getAirkorea_data() {
        return airkorea_data;
    }
    @NotNull
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

            StringBuilder TOKEN = new StringBuilder();
            try {
                File file = new File("C:\\DiscordServerBotSecrets\\rito-bot\\airkoreaAPIKEY.txt");
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

            StackTraceElement[] eStackTrace = e.getStackTrace();
            StringBuilder a = new StringBuilder();
            for (StackTraceElement stackTraceElement : eStackTrace) {
                a.append(stackTraceElement).append("\n");
            }
            logger.warn(a.toString());
        }	// try~catch end
    }
}