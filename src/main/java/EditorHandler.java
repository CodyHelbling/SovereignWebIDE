/**
 * Created by cody on 23/03/16.
 */

import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;


@WebSocket
public class EditorHandler {
    private int NumberOfUsers = 0;
    private String sender, msg;

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        System.out.print("Editor Connection Established!\n");
        NumberOfUsers += 1;
        String username = "User" + Editor.nextUserNumber++;
        Editor.userUsernameMap.put(user, username);
        Editor.updateEditors("Server", msg = (username + " joined the chat"));
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        System.out.print("Handler execution!: " + message + "\n");
        Editor.updateEditors(Editor.userUsernameMap.get(user), msg = message);
    }




}