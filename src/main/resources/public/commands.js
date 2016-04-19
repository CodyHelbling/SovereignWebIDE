/**
 * Created by cody on 04/04/16.
 */

// The link below explains WebSocket Client Applications.
// https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API/Writing_WebSocket_client_applications

//Establish the WebSocket connection and set up event handlers
var webSocketCommands = new WebSocket("ws://" + location.hostname + ":" + location.port + "/commands");
// webSocketCommands.onmessage = function (msg) { saveFile(msg); };
webSocketCommands.onclose = function () { alert("WebSocket connection closed") };



// TODO: If you try to save a file before modifying the Editor, it will save it as an empty file.

//Update the chat-panel, and the list of connected users
function saveFile() {
    fileName = document.getElementById("fileName").value;
    console.log(fileName);
    webSocketCommands.send(fileName);
    console.log("Save file function called!");
}
