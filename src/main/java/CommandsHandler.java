import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
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
     * param command_file_name - The name of the file to be saved.
     */
    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        Users.set(Users.recent, user);
        System.out.println("lfkajshdflkjashd");
    }


    @OnWebSocketMessage
    public void DecodeCommand(Session user, String command_file_name) throws IOException {
        System.out.print("Commands Handler execution!: " + command_file_name + "\n");

        String[] decoded_command = SplitString.get_command(command_file_name);

        //new for project differentiation
        decoded_command[1]=Users.prefix(user, decoded_command[1]);
        System.out.println("filename modified to:"+decoded_command[1]);


        if(decoded_command[0].equals("save")) {
            Commands.save_file(decoded_command[1]);
        }
        else if (decoded_command[0].equals("open")) {
            System.out.println("Handler:Open\n");


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
    }
}


