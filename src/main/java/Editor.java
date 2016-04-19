/**
 * Created by cody on 23/03/16.
 */

import org.eclipse.jetty.websocket.api.*;
import static spark.Spark.*;
import java.text.*;
import java.util.*;

/**
 * Contains methods that:
 *   - Configure the server
 *   - Define variables used for differentiating users
 *   - Update all clients, with updated editor string.
 *
 */
public class Editor {
    static Map<Session, String> userUsernameMap = new HashMap<>();
    static int nextUserNumber = 1;
    static String file = "";

    /**
     * Configures the server, port, static files, and webSocket.
     * @param args
     */
    public static void main(String[] args) {
        staticFileLocation("public"); //index.html is served at localhost:4567 (default port)
        port(4568);
        webSocket("/editor", EditorHandler.class);
        Commands.main();
        init();
    }


    /**
     * Updates clients involved in the same webSocket Session.
     * @param sender - user who made a change in the editor.
     * @param update - the update made to the editor, currently the contains the entire file.
     */
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
