

// The link below explains WebSocket Client Applications.
// https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API/Writing_WebSocket_client_applications


//Establish the WebSocket connection and set up event handlers
var webSocketCompile = new WebSocket("ws://" + location.hostname + ":" + location.port + "/compile");

// Add a listener. Runs when a message is received from the server
webSocketCompile.onmessage  = function () {compile ()};

function compile(){
    
};