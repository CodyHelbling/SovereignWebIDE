/**
 * Created by cody on 23/03/16.
 */


// The link below explains WebSocket Client Applications.
// https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API/Writing_WebSocket_client_applications


//Establish the WebSocket connection and set up event handlers
var webSocketEdit = new WebSocket("ws://" + location.hostname + ":" + location.port + "/editor");
// An event listener to be called when a message is received from the server
webSocketEdit.onmessage = function (msg) { updateEditor(msg, myCodeMirror); };
webSocketEdit.onclose = function () { alert("WebSocket connection closed") };

//CodeMirror


var oldCode = "";
var count = 0;

var myCodeMirror = CodeMirror(document.anchors.namedItem("editor"), {
    value: "public class HelloWorld {\n\n\tpublic static void main(String[] args) {\n\t\t// Prints \"Hello, World\" to the terminal window.\n\t\tSystem.out.println(\"Hello, World\");\n\t}\n}",
    mode:  "text/x-java",
    lineNumbers: true,
    theme: "erlang-dark"
});


myCodeMirror.on("change", function(myCodeMirror, changeObj){
    oldCode = myCodeMirror.getValue();

    // ---------- Example Code -----------------------------
    // Access a specific property in the changeObj
    // console.log(changeObj.from.line);

    // A string version of the changeObj
    changeObj_as_string = JSON.stringify(changeObj);
    console.log(changeObj_as_string); // log to the console
    // -----------------------------------------------------

    // Send stuff to the server -> EditorHandler(...)
    webSocketEdit.send(oldCode);
});



//Update the chat-panel, and the list of connected users
function updateEditor(msg, myCodeMirror) {
    count = count+1;
    var data = msg;
    if(oldCode != msg.data && count%3 == 0){
        // console.log("new Code")
        myCodeMirror.setValue(msg.data)
    }
    else {
        console.log("No code to update!")
    }
    console.log("\n\n" + msg.data + " : updateEditor");
}

//Helper function for inserting HTML as the first child of an element
function insert(targetId, message) {
    id(targetId).insertAdjacentHTML("afterbegin", message);
}

//Helper function for selecting element by id
function id(id) {
    return document.getElementById(id);
}