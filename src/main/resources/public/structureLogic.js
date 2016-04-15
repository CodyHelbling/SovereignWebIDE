/**
 * @author Austin Sass
 * @date 3/24/16
 * @overview This source file contains much of the logic that goes into the file structure UI.
 * See related files: index.html, fileManagement.css, fileManagementHandler.java
 */

/**
 * File structure websocket.
 * @type {WebSocket}
 */
var webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/structure");
webSocket.createStructure = function () { };
var creator = document.getElementById('creator');
var newfile = document.getElementById('newfile');
var bCount = 1;
var cMenu1 = document.getElementById("fileStructureMenu");
var cMenu1Target;
var dict = new fileDict();

function fileDict() {
    var fileCount = 0;
    var targetDict = {};
    var cMenu1Target;
    targetDict["Srcs"] = "srcFiles";
    targetDict["Mains"] = "mainFiles";

    this.addFolder = function(folderName) {
        var divID = targetDict[cMenu1Target];
        var div = createDiv(folderName + "div")
        var filesDiv = createDiv(folderName + "div" + "files");
        var img = createImg(folderName + "img", "14", "20", "index.png");
        var name = document.createTextNode(folderName);
        var button = document.createElement("BUTTON");
        var dropB = createDropButton(folderName+"drop", filesDiv.getAttribute("id"));
        button.appendChild(name);
        document.body.appendChild(button);
        button.setAttribute("id", folderName);
        button.setAttribute("class", "folderStyle");
        button.addEventListener("contextmenu", menuShowHide);
        div.appendChild(dropB);
        div.appendChild(img);
        div.appendChild(button);
        div.appendChild(filesDiv);
        document.getElementById(divID).appendChild(div);
        targetDict[div.getAttribute("id")] = filesDiv.getAttribute("id");
        //fileCount += 1;
        return false;
    };
    this.getCurrTarget = function() {
        return targetDict[cMenu1Target];
    };
    this.getMenuTarget = function(){
        return cMenu1Target;
    };
    this.setMenuTarget = function(aTarget){
        cMenu1Target = aTarget;
    };
    return this;
}

/**
 * Creates a new div (folder) to be used as a container for more divs and buttons and places it inside another div (folder).
 * @param {string} divID - This is the id of the parent div in which the child div for the new folder will be created.
 */


function createFolder (input) {
    dict.addFolder(document.getElementById(input).value);
    showHide("newFolderForm");
    document.getElementById("folderInput").value = "";
    return false;
}


//http://stackoverflow.com/questions/15702867/html-tooltip-position-relative-to-mouse-pointer
function menuShowHide(event) {
    cMenu1.addEventListener("click", menuShowHide);
    if(cMenu1.getAttribute("class") == "contextMenuHide"){
        dict.setMenuTarget(event.target.parentNode.getAttribute("id"));
        var x = event.clientX, y = event.clientY;
        cMenu1.setAttribute("class", "contextMenuShow");
        cMenu1.style.top = (y + 0) + 'px';
        cMenu1.style.left = (x + 0) +'px';
        cMenu1.addEventListener("mouseleave", menuHide);
    } else {
        cMenu1.setAttribute("class", "contextMenuHide");
    }
}
function menuHide() {
    if(cMenu1.getAttribute("class") == "contextMenuShow") {
        cMenu1.setAttribute("class", "contextMenuHide");
    }
}

function showHide(id){
    var aObject = document.getElementById(id);
    if(aObject.style.display == "none") {
        aObject.style.display = "block";
    }
    else {
        aObject.style.display = "none";
    }
}

/**
 * Creates a new div and button to act as a file place-mark.
 * A double-click attribute is added to the newly created button,
 * allowing the end user to command the opening of a respective
 * file.
 * @param {String} divID - id of parent div.
 */

function createFile () {
    var divID = dict.getMenuTarget();
    var buttonName = 'button' + bCount;
    bCount += 1;
    var div = createDiv(buttonName + "div");
    //var img = createImg(buttonName + "img", "14", "20", "index.png");
    //document.body.appendChild(img);
    var name = document.createTextNode(buttonName);
    var button = document.createElement("BUTTON");
    button.appendChild(name);
    document.body.appendChild(button);
    button.setAttribute("id", buttonName);
    button.setAttribute("class", "fileStyle");
    button.addEventListener("dblclick", openFile);
    //div.appendChild(img);
    div.appendChild(button);
    document.getElementById(divID).appendChild(div);
}

/**
 * This function returns a new button that is used to the toggle the display of submenus.
 * Usage of this function can been seen in the createFolder function.
 * @param id
 * @param divId
 * @returns {Element}
 */
function createDropButton(id, divId) {
    var dropB = document.createElement("BUTTON");
    dropB.innerHTML = ">";
    dropB.setAttribute("id", id);
    dropB.setAttribute("onclick", "toggleDisplay('"+ divId + "','" + id + "');");
    dropB.setAttribute("class", "dropdown");
    document.body.appendChild(dropB);
    return dropB;
}

/**
 * Creates a new button that is either able to create a new folder button or file button.
 * @param {String} id - Will be used to set the new button's ID.
 * @param {String} typeCall - Specifies whether this button is to be used as "createFile" or "createFolder".
 * @returns {Element} - returns the a new button
 *
 * @example
 * var newButton = createAddButton("foldButton001", "newFolder");
 */
function createAddButton(id, typeCall) {
    var addB = document.createElement("BUTTON");
    addB.innerHTML = (createImg(id+"img", 10, 10, "add_file.png")).outerHTML;
    addB.setAttribute("id", id);
    addB.setAttribute("class", "addButton");
    addB.setAttribute("onclick", "createFile('" + typeCall + "')");
    document.body.appendChild(addB);
    return addB;
}

/**
 *
 * @param {String} id - Used to set id attribute of image.
 * @param {String} hi - Used to set image height.
 * @param {String} wi - Used to set image width.
 * @param {String} imgfile - Name of image to be linked (Must be located in resource directory).
 * @returns {Element}
 */
function createImg (id, hi, wi, imgfile) {
    var img = document.createElement("img");
    img.setAttribute("id", id);
    img.setAttribute("height", hi);
    img.setAttribute("width", wi);
    img.setAttribute("src", imgfile);
    document.body.appendChild(img);
    return img;
}

/**
 * Appends a new div to a document.
 * @param {String} id - id of the new div.
 * @returns {Element}
 */
function createDiv(id) {
    var div = document.createElement("div");
    div.setAttribute("id", id);
    div.setAttribute("class", "folder");
    document.body.appendChild(div);
    return div;
}

/**
 * Hides/Displays div content
 *
 * @param {String} id - id of div (folder) that contains submenu items. This is NOT the same div id2 is contained in.
 * @param {String} id2 - id of button that call this function.
 */
function toggleDisplay(id, id2) {
    if (document.getElementById(id).style.display == "block") {
        document.getElementById(id).style.display = "none";
        document.getElementById(id2).innerHTML = ">";
    } else {
        document.getElementById(id).style.display = "block";
        document.getElementById(id2).innerHTML = "^";
    }
}

/**
 * Unimportant.
 */
function displayMenu(){
    alert('Menu Displayed')
}

/**
 * Unimportant.
 */
function openFile() {
    alert('You opened a file');
}

