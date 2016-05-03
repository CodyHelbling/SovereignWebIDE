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
        webSocket("/files", fileManagementHandler.class);
        Commands.main();
        get("/ide", (request, response) -> {

            String content = new String(Files.readAllBytes(Paths.get("./src/main/resources/public/index.html")));
            return content;
        });

        get("/projects", (request, response) -> {

            String content = new String(Files.readAllBytes(Paths.get("./src/main/resources/public/projects.html")));
            return content;
        });

        get("/", (request, response) -> {

            String content = new String(Files.readAllBytes(Paths.get("./src/main/resources/public/landing.html")));
            return content;
        });

        post("/signup", (req, res) -> {
            System.out.println(req.body());
            // Hue, you can plug in your auth and creation stuff here!
            System.out.println("Signing someone up....");
            String response = "";
            String[] s;
            if((s=Authentication.chop(req.body()))!=null){
                if(Authentication.createUser(s[1], s[2], s[0])){
                    System.out.println("success");
                    Users.recent=s[1];
                    //redirect to editor
                    return "true";
                }
            }
            System.out.println("Failiure");
            //complain that username is taken
            return "false";
        });


        post("/login", (req, res) -> {
            System.out.println(req.body());
            // Hue, you can plug in your auth and creation stuff here!
            System.out.println("Logging someone in....");
            String response = "";
            String[] s;
            if((s=Authentication.chop(req.body()))!=null) {
                if (Authentication.logIn(s[2], s[1])) {
                    Users.userUsernameMap.put(Users.current, s[2]);
                    System.out.println("success");
                    Users.recent=s[2];
                    //redirect to editor
                    return "true";
                }
            }
            System.out.println("Failiure");
            response = "false";
            //complain of incorrect credentials
            return response;
        });



        post("/createproject", (req, res) -> {
            System.out.println(req.body());
            System.out.println("Creating Project....");
            String response = "";
            // Do ya thang Hue!!
            String[] s=Authentication.chop(req.body());
            if(s!=null){
                if(Users.createProject(s[1], s[0])){
                    Users.recent=s[1];
                    return "true";
                }
            }

            return "false";
        });

        post("/openproject", (req, res) -> {
            System.out.println(req.body());
            System.out.println("Opening Project....");
            String response = "";
            // Do ya thang Hue!!
            String[] s=Authentication.chop(req.body());
            if(s!=null){
                if(Users.enterProject(s[1], s[0])){
                    Users.recent=s[1];
                    return "true";
                }
            }

            return "false";
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




