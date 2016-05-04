import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import java.io.*;

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
                Console.consoleFile += update + "\n";
                session.getRemote().sendString(String.valueOf(consoleFile));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void compile(){
        try {
            consoleFile = "";
            String sender = "";
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec("sudo ./execute");

            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

            String line=null;

            while((line=input.readLine()) != null) {
                Console.updateConsole(sender, line);
                Thread.sleep(50);
            }

            int exitVal = pr.waitFor();
            System.out.println("Exited with error code "+exitVal);

        } catch(Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
}