package BOT;

public class airKoreaList {
    public static String[] getInchoenList() {
        return Inchoenlist;
    }
    public static String[] getLocalListENG() {
        return localListENG;
    }
    public static String[] getLocalListKOR() {
        return localListKOR;
    }

    private static final String[] Inchoenlist = {
            "검단", "계산", "고잔", "구월동",
            "논현", "동춘", "부평", "석남",
            "송도", "송림", "송해", "숭의",
            "신흥", "연희", "운서", "원당"
    };
    private static final String[] localListENG = {
            "seoul", "busan", "daegu", "incheon",
            "gwangju", "daejeon", "ulsan", "gyeonggi",
            "gangwon", "chungbuk", "chungnam" , "jeonbuk",
            "jeonnam", "gyeongbuk", "gyeongnam", "jeju",
            "sejong"
    };
    private static final String[] localListKOR = {
            "서울", "부산", "대구", "인천",
            "광주", "대전", "울산", "경기",
            "강원", "충북", "충남", "전북",
            "전남", "경북", "경남", "제주",
            "세종"
    };
}
