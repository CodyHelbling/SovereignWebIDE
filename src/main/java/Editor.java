/**
 * Created by cody on 23/03/16.
 */

import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import static spark.Spark.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


@WebSocket
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
        webSocket("/chat", ChatWebSocketHandler.class);
        Commands.main();
        get("/ide", (request, response) -> {


//            try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File("./src/main/resources/public/TEST.txt")))) {
//                bw.write("Hello, This is a test message");
//                bw.close();
//            }catch (FileNotFoundException ex) {
//                System.out.println(ex.toString());
//            }


            String content = new String(Files.readAllBytes(Paths.get("./src/main/resources/public/index.html")));
            return content;
        });

        get("/", (request, response) -> {


//            try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File("./src/main/resources/public/TEST.txt")))) {
//                bw.write("Hello, This is a test message");
//                bw.close();
//            }catch (FileNotFoundException ex) {
//                System.out.println(ex.toString());
//            }


            String content = new String(Files.readAllBytes(Paths.get("./src/main/resources/public/landing.html")));
            return content;
        });

        post("/signup", (req, res) -> {
            System.out.println(req.body());
            // Hue, you can plug in your auth and creation stuff here!
            System.out.println("Signing someone up....");
            String response = "";
            if(Authentication.chop(req.body(), 1)){
                System.out.println("success");
                //redirect to editor
                response = "true";
            }else{
                System.out.println("Failiure");
                //complain that username is taken
                response = "false";
            }
            return response;
        });

        post("/login", (req, res) -> {
            System.out.println(req.body());
            // Hue, you can plug in your auth and creation stuff here!
            System.out.println("Logging someone in....");
            String response = "";
            if(Authentication.chop(req.body(), 2)){
                System.out.println("success");
                response = "true";
                //redirect to editor
            }else{
                System.out.println("Failiure");
                response = "false";
                //complain of incorrect credentials
            }
            return response;
        });



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




