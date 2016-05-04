/**
 * Created by Cody on 4/9/16.
 */



import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class JSONTools {
    public static void main(String[] args) {
        JsonObject o = new JsonParser().parse("{\"a\": \"A\"}").getAsJsonObject();
        System.out.println(o);
    }
}
