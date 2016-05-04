import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;

/**
 * Created by Tyler Songstad on 4/25/2016.
 */
@WebSocket
public class CompileHandler {

    private int NumberOfUsers = 0;
    private String msg = "";

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        System.out.print("Compile WebSocket Connection Established!\n");
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        System.out.print("Compile Update!: " + "\n");
        Console.compile();
    }
}
