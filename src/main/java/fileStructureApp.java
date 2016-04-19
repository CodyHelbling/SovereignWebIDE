/**
 * Created by austin on 3/31/16.
 */
import static spark.Spark.*;

public class fileStructureApp {
    public static void main(String[] args) {
        staticFileLocation("public");
        webSocket("/structure", fileManagementHandler.class);
        init();
    }
}
