
import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;

/**
 * Contains WebSocket functions for connecting, messaging, and closing.
 */
@WebSocket
public class ChatWebSocketHandler {

    private String sender, msg, username;

    /**
     * On connect creates and assigns username and outputs that user joined
     * @param user - individual entering the room
     * @throws Exception
     */
    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        username = Chat.currentUserName;
       /* if(Chat.currentUserName.equals("Placeholder")){
        username = "User" + Chat.nextUserNumber++;
        }*/

        Chat.userUsernameMap.put(user, username);
        Chat.broadcastMessage(sender = "Server", msg = (username + " joined the chat"));
    }

    /**
     * On close, tells room that user has left room then removes them from userlist
     * @param user - individual leaving the room
     * @param statusCode
     * @param reason
     */
    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        String username = Chat.userUsernameMap.get(user);
        Chat.userUsernameMap.remove(user);
        Chat.broadcastMessage(sender = "Server", msg = (username + " left the chat"));
    }

    /**
     * On message, use chat functions so send message to all users in room
     * @param user -  individual sending the message
     * @param message - string to be sent to other users
     */
    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        Chat.broadcastMessage(sender = Chat.userUsernameMap.get(user), msg = message);
    }

}


