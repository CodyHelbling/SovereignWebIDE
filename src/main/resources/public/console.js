


// The link below explains WebSocket Client Applications.
// https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API/Writing_WebSocket_client_applications


//Establish the WebSocket connection and set up event handlers
var webSocketConsole = new WebSocket("ws://" + location.hostname + ":" + location.port + "/console");

var consoleCodeMirror = CodeMirror(document.anchors.namedItem("console"), {
    value: "Console...",
    mode:  "text",
    lineNumbers: false,
    theme: "erlang-dark"
});



