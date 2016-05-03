import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

/**
 * Created by Tyler Songstad on 4/25/2016..
 */
@WebSocket
public class Console {
    static Map<Session, String> userUsernameMap = new HashMap<>();
    static int nextUserNumber = 1;

    public static void main(String[] args) {
        init();
    }

    static String consoleFile = "";

    public static void updateConsole(String sender, String update) {
        userUsernameMap.keySet().stream().filter(Session::isOpen).forEach(session -> {
            try {
                Console.consoleFile = update;
                System.out.print("Console: " + consoleFile + "\n");
                System.out.print("actually runing update console in the loop");
                session.getRemote().sendString(String.valueOf(consoleFile));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void compile(){
        try{
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec("java -jar map.jar time.rel test.txt debug");
        } catch(Exception e){
            System.out.print("Exception" + e);
        }
    }
}