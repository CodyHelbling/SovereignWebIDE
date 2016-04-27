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
    public void onConnect(Session user) throws Exception {
        System.out.print("Console Connection Established!\n");
        NumberOfUsers += 1;
        String username = "User" + Console.nextUserNumber++;
        Console.userUsernameMap.put(user, username);
        System.out.print("User: " + user + "\nUsername: " + username + "\n");
        Console.updateConsole("Server", msg = (username + " joined the chat"));
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        System.out.print("Console Update!: " + message + "\n");
        Console.updateConsole(Console.userUsernameMap.get(user), msg = message);
    }
}