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
            console.log("response: " + res);
        }
    };
    console.log("Creating Project: ....");
    CreateProjectData.projName = document.getElementById("new-proj").value;
    CreateProjectData.userName = document.getElementById("inputUNameCreateProj").value;
    CreateProjectData.password = document.getElementById("inputPasswordCreateProj").value;
    xhttp.open("POST", "/createproject", true);
    console.log("CreateProjectData: " + CreateProjectData);
    xhttp.send(JSON.stringify(CreateProjectData));
    console.log("initFileStructure: "+CreateProjectData.projName);
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
            console.log("response: " + res);
        }
    };
    console.log("Opening Project: ....");
    OpenProjectData.projName = document.getElementById("exist-proj").value;
    OpenProjectData.userName = document.getElementById("inputUNameOpenProj").value;
    OpenProjectData.password = document.getElementById("inputPasswordOpenProj").value;
    xhttp1.open("POST", "/openproject", true);
    console.log("OpenProjectData: " + OpenProjectData);
    xhttp1.send(JSON.stringify(OpenProjectData));
   // initFileStructure(OpenProjectData.projName);
}
