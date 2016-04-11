import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

/**
 * Created by cody on 04/04/16.
 *
 * Commands contains methods for commands that will be ran in the IDE.
 *  - Save File
 *  - Open File
 *  - Find File
 *  - Compile
 *  - Run
 */
public class Commands {

    /**
     * Establishes the webSocket used for IDE commands.
     */
    public static void main() {
        webSocket("/commands", CommandsHandler.class);
    }


    /**
     * Saves the file to the project directory.
     * @param file_name - The name of the file being saved.
     */
    public static void save_file(String file_name) {
        try {
            // Grab the file from Editor. It is currently one string containing '\n'.
            String[] file_as_string = SplitString.main(Editor.file);
            // Have to convert to list for 'Files.write'.
            List<String> file_as_list = Arrays.asList(file_as_string);

            // This may be useful for optimization in the future...
            // List<String> lines = Files.readAllLines(Paths.get("Test.txt"));
            // lines.add("print('foobar');");

            // Write the List to a file.
            if( file_name != "") {
                Files.write(Paths.get(file_name), file_as_list);
                System.out.print("Writing string to " + file_name + "...");
            }
            else {
                Files.write(Paths.get("Test.txt"), file_as_list);
                System.out.print("No file name specified. Writing string to Test.txt....\n");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
