import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

/**
 * Created by cody on 04/04/16.
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.lang.*;

@WebSocket
public class CommandsHandler {

    private int NumberOfUsers = 0;
    private String msg;
    private FileManager projectSpace;


    /**
     * Calls the save_file function in Commands.java.
     * @param command_file_name - The name of the file to be saved.
     */
    @OnWebSocketMessage
    public void DecodeCommand(String command_file_name) throws IOException {
        System.out.print("Commands Handler execution!: " + command_file_name + "\n");

        String[] decoded_command = SplitString.get_command(command_file_name);
        if(decoded_command[0].equals("save")) {
            Commands.save_file(decoded_command[1]);
        }
        else if (decoded_command[0].equals("open")) {
            System.out.println("Handler:Open\n");

            //new for project differentiation
            //decoded_command[1]="projects/"+Users.getProject()+"/"+decoded_command[1];

            List<String> lines = Files.readAllLines(Paths.get(decoded_command[1]));


            String file_as_str = "";
            for (String s : lines) {
                file_as_str += s + "\n";
            }
            Editor.file = file_as_str;
            // This is a hack but it updates users
            Editor.updateEditors("User OPEN", file_as_str);
            System.out.println("Handler:Open:file: "+file_as_str);
        }
        else if (decoded_command[0].equals("addFolder")) {
            System.out.println("Trying to create folder: " + decoded_command[1]);
            try {
                FileManager.createDirectory(decoded_command[1]);
            } catch (NullPointerException e) {
                System.err.println(e);
            }
        }
        else if (decoded_command[0].equals("deleteFolder")) {
            System.out.println("Trying to delete: " + decoded_command[1]);
            FileManager.deleteDirectory(decoded_command[1]);
        }
        else if(decoded_command[0].equals("addFile")) {
            System.out.println("Trying to create file: " + decoded_command[1]);
            FileManager.createFile(decoded_command[1]);
        }
        else if (decoded_command[0].equals("open2")) {
            System.out.println("Handler:Open2\n");
            List<String> lines = Files.readAllLines(Paths.get(System.getProperty("user.home") + "/sQuire/" + decoded_command[1]));
            String file_as_str = "";
            for (String s : lines) {
                file_as_str += s + "\n";
            }
            Editor.file = file_as_str;
            // This is a hack but it updates users
            Editor.updateEditors("User OPEN", file_as_str);
            System.out.println("Handler:Open:file: "+file_as_str);
        };
    }
}


