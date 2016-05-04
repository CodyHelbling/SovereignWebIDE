/**
 * Created by Cody on 5/1/16.
 */
/**
 * Created by Cody on 4/23/16.
 */



function CreateProj() {

    var CreateProjectData = {
        "projName": "",
        "userName": "",
        "password": "",
    };
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            var res = xhttp.responseText;
            createStatus(res);
            console.log("response: " + res);
        }
    };
    console.log("Creating Project: ....");
    CreateProjectData.projName = document.getElementById("new-proj").value;
    xhttp.open("POST", "/createproject", true);
    console.log("CreateProjectData: " + CreateProjectData);
    xhttp.send(JSON.stringify(CreateProjectData));
}


function createStatus(fb){
    if(fb === 'true'){
        window.location = "/index.html";
    } else {
        document.getElementById("projError").innerHTML = "Project Name Taken"
    }
}


function OpenProj() {

    var OpenProjectData = {
        "projName": "",
        "userName": "",
        "password": "",
    };
    var xhttp1 = new XMLHttpRequest();
    xhttp1.onreadystatechange = function() {
        if (xhttp1.readyState == 4 && xhttp1.status == 200) {
            var res = xhttp1.responseText;
            openStatus(res);
            console.log("response: " + res);
        }
    };
    console.log("Opening Project: ....");
    OpenProjectData.projName = document.getElementById("exist-proj").value;
    xhttp1.open("POST", "/openproject", true);
    console.log("OpenProjectData: " + OpenProjectData);
    xhttp1.send(JSON.stringify(OpenProjectData));
}

function openStatus(fb){
    if(fb === 'true'){
        window.location = "/index.html";
    } else {
        document.getElementById("OprojError").innerHTML = "Project Doesn't Exist"
    }
}