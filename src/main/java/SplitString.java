import java.util.Arrays;

/**
 * Created by Cody on 4/5/16.
 */
public class SplitString {
    // public static final String PLAYER = "1||1||Abdul-Jabbar||Karim||1996||1974";
    // public static final String file = "lineone\nlinetwo\n";
    // public static final String file2 = Editor.file;

    public static String[] main(String file) {
        // String[] data = PLAYER.split("\\|\\|");
        // String[] data2 = file.split("\\n");
        String[] data3 = file.split("\\n");

        // System.out.println(Arrays.toString(data));
        // System.out.println(Arrays.toString(data2));
        System.out.println(Arrays.toString(data3));
        return data3;
    }


    public static String[] get_command(String command_and_name) {
        String[] data = command_and_name.split(":");
        System.out.println(data[0]);
        System.out.println(data[1]);
        return data;
    }
}
