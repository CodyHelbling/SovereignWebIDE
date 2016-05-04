/**
 * Created by cody on 23/03/16.
 */

import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;


/**
 * Handles the Editor's webSocket. These methods are accessible from the client and the server.
 */

@WebSocket
public class EditorHandler {
    private int NumberOfUsers = 0;
    private String sender, msg;


    /**
     * This method is called whenever a new client connects.
     * @param user The id of the user.
     * @throws Exception
     */
    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        System.out.print("Editor Connection Established!\n");
        NumberOfUsers += 1;
        String username = "User" + Editor.nextUserNumber++;
        Editor.userUsernameMap.put(user, username);

        //probably not the best way of doing this
        Users.current=user;
    }


    /**
     * This method is called whenever the client sends a message, or the server updates the clients.
     * @param user - The id of the user.
     * @param message - For now, this contains all the code in the editor.
     */
    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        // System.out.print("Handler execution!: " + message + "\n");
        Editor.updateEditors(Editor.userUsernameMap.get(user), msg = message);
    }




}