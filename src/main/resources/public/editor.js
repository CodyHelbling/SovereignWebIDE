


// The link below explains WebSocket Client Applications.
// https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API/Writing_WebSocket_client_applications


//Establish the WebSocket connection and set up event handlers
var webSocketEdit = new WebSocket("ws://" + location.hostname + ":" + location.port + "/editor");
// An event listener to be called when a message is received from the server
webSocketEdit.onmessage = function (msg) { updateEditor(msg, myCodeMirror); };
webSocketEdit.onclose = function (event) { alert("WebSocket connection closed"); /*alert(event.code)*/ };


var webSocketChat = new WebSocket("ws://" + location.hostname + ":" + location.port + "/chat");
webSocketChat.onmessage = function(message) {updateChat(message);};
//webSocketChat.onclose = function(event){alert ("Chat WebSocket Closed");};



//CodeMirror


var oldCode = "";
var count = 0;

var myCodeMirror = CodeMirror(document.anchors.namedItem("editor"), {
    value: "public class HelloWorld {\n\n\tpublic static void main(String[] args) {\n\t\t// Prints \"Hello, World\" to the terminal window.\n\t\tSystem.out.println(\"Hello, World\");\n\t}\n}\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n",
    mode:  "text/x-java",
    lineNumbers: true,
    theme: "erlang-dark"
});

myCodeMirror.setSize("100%", 700);


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
    if(oldCode != msg.data && count%2 == 0){
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

//Chat jQuery functions
$('.js-trigger').on('click', function() {
    $('html').toggleClass('show-me')
});

$('.conversation__header').on('click', function() {
    $('.conversation').slideToggle(300);  //.conversation
});

$('.chat__name').on('click', function() {
    $('.conversation').slideToggle(300);
});

$('.chat__avatar').on('click', function() {
    $('.conversation').slideToggle(300);
});

/*--------Other Chat functions -----------*/
//Send message if enter is pressed in the input field
id("message").addEventListener("keypress", function (e) {
    if (e.keyCode === 13) { sendMessage(e.target.value); }
});

id("send").addEventListener("click", function () {
    sendMessage(id("message").value); 
});


//Send a message if it's not empty, then clear the input field
function sendMessage(message) {
    if (message !== "") {
        webSocketChat.send(message);
        id("message").value = "";
    }
}

//Update the chat-panel, and the list of connected users
function updateChat(message) {
    var data = JSON.parse(message.data);
    insert("chat2", data.userMessage);
    id("userlist").innerHTML = "";
    data.userlist.forEach(function (user) {
        insert("userlist", "<li>" + user + "</li>");
    });
}