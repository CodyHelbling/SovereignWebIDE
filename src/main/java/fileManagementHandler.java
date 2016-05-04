/**
 * Created by austin on 3/24/16.
 */

import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.json.*;
import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Add Two Numbers!</h1>
 * The AddNum program implements an application that
 * simply adds two given integer numbers and Prints
 * the output on the screen.
 * <p>
 * <b>Note:</b> Giving proper comments in your program makes it more
 * user friendly and it is assumed as a high quality code.
 *
 * @author  Zara Ali
 * @version 1.0
 * @since   2014-03-31
 */

@WebSocket
public class fileManagementHandler {
    private FileManager manager;
    private String username;
    static Map<Session, String> userUsernameMap = new HashMap<>();
    public static String message;
    public static String pName;
    private static String path;
    public String getCurrOpenPath() {return path;}
    private boolean dataExist;
    @OnWebSocketConnect
    public void creator(Session user) throws Exception {
        FileManager.createProjectSpace();
        username = Chat.currentUserName;
        userUsernameMap.put(user, username);
        System.out.println("at connect..." + message);
        if (pName != null) {
            System.out.println("fileManagementHandler: ...looking for project to open");
            if (manager == null) {
                manager = new FileManager();
                manager.projectName = pName;
                dataExist = manager.checkForData(pName);
            } else {
                dataExist = manager.checkForData(pName);
                System.err.println("fileManagementHandler: Found data!");
            }
            if (!dataExist && message != null && message.equals("new")) {
                System.out.println("Trying to update new..." + message + " " + pName);
                updateFileStructureAll(message, pName);
                message = "nil";
            } else if (dataExist) {
                try {
                    //if (manager != null) {
                    user.getRemote().sendString(String.valueOf(
                            new JSONObject().put("targetID", "fileManagementStart").put("htmlContent", manager.readFileStructureData())));
                    //}
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            System.err.println("fileManagementHandler: No project to open.");
        }
    }
    @OnWebSocketClose
    public void destructor(int statusCode, String reason){
        //FileManager.deleteProjectSpace(Paths.get("/home/austin/sQuire"));
       // System.out.println("Destructor: websocket closed with:"); // + closeReason + " " + closeCode);
    }
    public void update(String t, String data){
        message = t;
        pName = data;
    }
    private void updateFileStructureAll(String target, String htmlData) {
        System.out.println("update: " + target +" "+ htmlData);
        userUsernameMap.keySet().stream().filter(Session::isOpen).forEach(session -> {
            try {
                session.getRemote().sendString(String.valueOf(new JSONObject().put("targetID", target).put("htmlContent", htmlData)));
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
    }

    @OnWebSocketMessage
    public void decodeFileOpCmd(String command_file_name) {
        String[] htmlData = new String[3];
        System.out.println(command_file_name);
        String[] decoded_command = SplitString.get_command(command_file_name);
        if(decoded_command.length > 2)
            htmlData = decoded_command[2].split("~", 3);
        if(decoded_command[0].equals("newProject")){
            manager = new FileManager();
            manager.projectName = decoded_command[1];
        }
        /*
        else if(decoded_command[0].equals("readProject")) {
            System.out.println("Trying to read project: " + decoded_command[0]);
            updateFileStructureAll("fileManagementStart", manager.readFileStructureData());
        }
        */
        else if (decoded_command[0].equals("addFolder")) {
            System.out.println("Trying to create folder: " + decoded_command[1]);
            try {
                FileManager.createDirectory(decoded_command[1]);
                updateFileStructureAll(htmlData[0], htmlData[1]);
                manager.saveFileStructureData(htmlData[2]);
            } catch (NullPointerException e) {
                System.err.println(e);
            }
        }
        else if (decoded_command[0].equals("deleteFolder")) {
            System.out.println("Trying to delete: " + decoded_command[1]);
            updateFileStructureAll(htmlData[0],  htmlData[1]);
            manager.saveFileStructureData(htmlData[2]);
            FileManager.deleteDirectory(decoded_command[1]);
        }
        else if(decoded_command[0].equals("addFile")) {
            System.out.println("Trying to create file: " + decoded_command[1]);
            updateFileStructureAll(htmlData[0], htmlData[1]);
            manager.saveFileStructureData(htmlData[2]);
            FileManager.createFile(decoded_command[1]);
        }
        else if (decoded_command[0].equals("open2")) {
            path = "projects" + decoded_command[1];
            System.out.println("fileManagementHandler:Open2 " + path +" ");
            List<String> lines = new ArrayList<String>();
            try {
                lines = Files.readAllLines(Paths.get("projects"+decoded_command[1]));
            } catch (IOException e) {
                System.err.println("FileManager::else if(open2) IOException: I/O error; file cannot be read: " + e);
            }
                String file_as_str = "";
            for (String s : lines) {
                file_as_str += s + "\n";
            }
            Editor.file = file_as_str;
            // This is a hack but it updates users
            Editor.updateEditors("User OPEN", file_as_str);
            Editor.updateEditors("User OPEN", file_as_str);
            System.out.println("Handler:Open:file: "+file_as_str);
        }
    }
}


