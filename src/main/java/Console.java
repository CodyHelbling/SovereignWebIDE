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
        System.out.print("Before the code line\n");
        try{
            try {
                Runtime rt = Runtime.getRuntime();
                //Process pr = rt.exec("cmd /c dir");
                Process pr = rt.exec("c:\\helloworld.exe");

                BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

                String line=null;

                while((line=input.readLine()) != null) {
                    System.out.println(line);
                }

                int exitVal = pr.waitFor();
                System.out.println("Exited with error code "+exitVal);

            } catch(Exception e) {
                System.out.println(e.toString());
                e.printStackTrace();
            }
        } catch(Exception e){
            System.out.print("Exception" + e);
        }
    }
}