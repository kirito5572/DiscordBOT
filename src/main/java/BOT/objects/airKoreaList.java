package BOT.Objects;

public class airKoreaList {
    public static String[] getLocalListENG() {
        return localListENG;
    }
    public static String[] getLocalListKOR() {
        return localListKOR;
    }

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