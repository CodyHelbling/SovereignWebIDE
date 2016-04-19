/**
 * Created by austin on 3/24/16.
 */


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

import static spark.Spark.*;
import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;

@WebSocket
public class fileManagementHandler {
    private fileStructure newStructure;

    @OnWebSocketConnect
    public void creator(Session session){
        newStructure = new fileStructure();
        newStructure.createStructure();
    }
    @OnWebSocketClose
    public void destructor(int closeCode, String closeReason){
        newStructure.deleteStructure();
        System.out.println("Destructor: websocket closed with:" + closeReason + " " + closeCode);
    }
}

