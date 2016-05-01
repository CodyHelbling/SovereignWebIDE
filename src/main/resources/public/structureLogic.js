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
var webSocketFileManagement = new WebSocket("ws://" + location.hostname + ":" + location.port + "/structure");
//webSocket.createStructure = function () { };
var cMenu1 = document.getElementById("fileStructureMenu");
var cMenu1Target;
var dict = new fileDict();

function fileDict() {
    var targetDict = {};
    var cMenu1Target;
    var currentOpenFilePath;
    targetDict["src"] = "srcFiles";
    targetDict["src-main"] = "mainFiles";
//
    /**
     * Creates a new folder in the file management UI. The location where all the necessary elements will be created is
     * predetermined by "menuShowHide(event)", which preserves the id of the container that triggered the contextmenu
     * event.
     * @param {string} folderName - Specifies name of folder used to create appropriate elements and containers.
     * @returns {boolean}  - Prevent unexpected behavior.
     */
    this.addFolder = function(folderName) { //indentType="sub-folder" indentType="root-folder"
        if (!(targetDict[cMenu1Target+"-"+folderName])) { //folder doesn't exist
            var dirLocate = cMenu1Target;
            webSocketCommands.send("addFolder:" /*+ "/home/austin/sQuire/"*/ + dirLocate.replace(/-/g, "/") + "/"+ folderName);
            var divID = cMenu1Target;   //parent of target div
            var folderDiv = createDiv(cMenu1Target+"-"+folderName, "sub-folder"); //new div for folder
            var subDiv = createDiv(cMenu1Target+"-"+folderName+"-sub","sub-container"); //file container inside the folder div
            subDiv.style.display = "none";
            var filesDiv = createDiv(cMenu1Target+"-"+folderName+"-files","file-container"); //file container inside the folder div
            var foldersDiv = createDiv(cMenu1Target+"-"+folderName+"-folders","sub-folder-container"); //folder container inside the folder div
            var img = createImg(folderName + "img_" + divID, "14", "20", "index.png"); //folder image
            var name = document.createTextNode(folderName); //start appending to document...
            var button = document.createElement("BUTTON");
            button.style.margin = "1px";
            var dropB = createDropButton(cMenu1Target+"-"+folderName+"_drop", cMenu1Target+"-"+folderName+"-sub");
            button.appendChild(name);
            document.body.appendChild(button);
            button.setAttribute("id", folderName + divID);
            button.setAttribute("class", "button-text-only");
            button.addEventListener("contextmenu", menuShowHide);
            filesDiv.setAttribute("style", "display:none");
            folderDiv.appendChild(dropB);
            folderDiv.appendChild(img);
            folderDiv.appendChild(button);
            subDiv.appendChild(foldersDiv);
            subDiv.appendChild(filesDiv);
            folderDiv.appendChild(subDiv);
            document.getElementById(divID+"-folders").appendChild(folderDiv);
            targetDict[folderDiv.getAttribute("id")] = filesDiv.getAttribute("id");
        }
        else { //folder does exist
            alert('Folder already exist!');
        }
        return false;
    };
    /**
     * Creates a new file div inside a parent folder div and sends a command via a websocket to create a new file.
     *
     * @param {string} fileName - Name of the new file that will appended to the path of the parent directory. All "-" in
     * the file name are replaced with "?" temporarily to aid setting the file div's id without ambiguity in the html.
     */

    this.addFile = function(fileName) {
        if (!(targetDict[cMenu1Target+"-"+fileName])) {
            var dirLocate = cMenu1Target;
            var fileNameFix = fileName.replace(/-/g, "?"); //replace - with illegal "?" to prevent path corruption
            webSocketCommands.send("addFile:" /*+ "/home/austin/sQuire/"*/ + dirLocate.replace(/-/g, "/") + "/" + fileName);
            var divID = cMenu1Target;
            var div = createDiv(cMenu1Target+"-"+fileNameFix, "file");
            var name = document.createTextNode(fileName);
            var button = document.createElement("BUTTON");
            button.appendChild(name);
            document.body.appendChild(button);
            button.setAttribute("id", fileName + divID);
            button.setAttribute("class", "button-text-only file");
            button.addEventListener("dblclick", fileOpenDoubleClick);
            div.appendChild(button);
            document.getElementById(divID+"-files").appendChild(div);
            targetDict[div.getAttribute("id")] = cMenu1Target+"-"+fileNameFix;
        }
        else {
            alert('File already exist!');
        }
    };
    this.deleteFolder = function(id) {
        targetDict[id] = null;
        document.getElementById(id).remove();
        webSocketCommands.send("deleteFolder:" /*+ "/home/austin/sQuire/"*/ + id.replace(/-/g, "/").replace(/\?/g, "-"));
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
    this.testTarget = function(key){
        if (targetDict[key])
            return true;
        else
            return false;
    };
    this.setCurrentOpenFile = function(filePath){
        if (currentOpenFilePath != null) {
            currentOpenFilePath = filePath;
        }
        else {
            console.log("structureLogic.js::setCurrentOpenFile(): currentOpenFilePath already set");
        }
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

function deleteFolder() {
    if(document.getElementById(dict.getCurrTarget()).getAttribute("class") != "file") {
        var parentContainer = document.getElementById(dict.getCurrTarget()).parentNode.parentNode.id;
        dict.deleteFolder(parentContainer);
    }
    else {
        dict.deleteFolder(dict.getCurrTarget());
    }
}

//http://stackoverflow.com/questions/15702867/html-tooltip-position-relative-to-mouse-pointer
function menuShowHide(event) {
    cMenu1.addEventListener("click", menuShowHide);
    if(cMenu1.getAttribute("class") == "contextMenuHide"){
        dict.setMenuTarget(event.target.parentNode.getAttribute("id"));
        //alert(event.target.parentNode.getAttribute("id"));
        var x = event.clientX, y = event.clientY;
        cMenu1.setAttribute("class", "contextMenuShow");
        cMenu1.style.top = (y + 0) + 'px';
        cMenu1.style.left = (x + 0) +'px';
        cMenu1.addEventListener("mouseleave", menuHide);
    } else {
        cMenu1.setAttribute("class", "contextMenuHide");
    }
}

function fileOpenDoubleClick(event) {
    webSocketCommands.send("open2:" /*+ "/home/austin/sQuire/"*/ + event.target.parentNode.getAttribute("id").replace(/-/g, "/").replace(/\?/g, "-"));
}

function fileOpenFromMenu(event) {

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
 * @param {String} id - id of parent div.
 */

function createFile (id) {
    dict.addFile(document.getElementById(id).value);
    showHide("newFileForm");
    document.getElementById("fileInput").value = "";
    return false;
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
    dropB.setAttribute("class", "button-text-only");
    dropB.setAttribute("style", "margin:1px");
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
function createDiv(id, aClass) {
    var div = document.createElement("div");
    div.setAttribute("id", id);
    div.setAttribute("class", aClass);
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
/*
function openFile() {
    alert('You opened a file');
}
*/
