import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;

/**
 * Created by Tyler Songstad on 4/25/2016.
 */
@WebSocket
public class ConsoleHandler{
    private int NumberOfUsers = 0;
    private String msg = "";

    @OnWebSocketConnect
    public void onConnect(Session user){
        NumberOfUsers += 1;
        String username = "User" + Console.nextUserNumber++;
        Console.userUsernameMap.put(user, username);
        System.out.print("Console Connection Established!\n");
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        Console.updateConsole(Console.userUsernameMap.get(user), msg = message);
    }

}