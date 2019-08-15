package BOT.Objects;

public class ONIGIRIList {
    private static String[] List = new String[]{
            "근손실", "존예", "물포", "물고기포"
    };
    private static String[] List_file = new String[]{
            "ONIGIRI1.jpg", "ONIGIRI2.jpg", "ONIGIRI3.gif", "ONIGIRI3.gif"
    };
    private static String[] List_Suffix = new String[] {
            ".jpg", ".jpg", ".gif", ".gif"
    };

    public static String[] getList() {
        return List;
    }
    public static String[] getList_file() {
        return List_file;
    }

    public static String[] getList_Suffix() {
        return List_Suffix;
    }
}
