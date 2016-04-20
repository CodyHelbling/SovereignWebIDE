/**
 * Created by cody on 23/03/16.
 * Test
 */

import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.JSONObject;

import static j2html.TagCreator.*;
import static spark.Spark.*;
import java.text.*;
import java.util.*;


@WebSocket
public class Editor {
    static Map<Session, String> userUsernameMap = new HashMap<>();
    static int nextUserNumber = 1;
    static String file = "";


    public static void main(String[] args) {
        staticFileLocation("public"); //index.html is served at localhost:4567 (default port)
        port(4567);
        webSocket("/editor", EditorHandler.class);
        webSocket("/chat", ChatWebSocketHandler.class);
        Commands.main();
        init();
    }


    public static void updateEditors(String sender, String update) {
        userUsernameMap.keySet().stream().filter(Session::isOpen).forEach(session -> {
            try {
                //file.add(update);
                Editor.file = update;
                System.out.print("Editor: " + file + "\n");
                session.getRemote().sendString(String.valueOf(file));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
