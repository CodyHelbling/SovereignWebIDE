/**
 * Created by Cody on 4/23/16.
 */



function SignUp() {

    var signUpData = {
    "email": "",
    "userName": "",
    "password": ""
};
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (xhttp.readyState == 4 && xhttp.status == 200) {

        }
    };
    console.log("Signing Up: ....");
    signUpData.email = document.getElementById("inputEmailSignUp").value;
    signUpData.userName = document.getElementById("inputUNameSignUp").value;
    signUpData.password = document.getElementById("inputPasswordSignUp").value;
    xhttp.open("POST", "/signup", true);
    console.log("signUpData: " + signUpData);
    xhttp.send(JSON.stringify(signUpData));
}




function LogIn() {

    var logInData = {
    "username":"",
    "password":""
};
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (xhttp.readyState == 4 && xhttp.status == 200) {

        }
    };

    console.log("Logging In: ....");
    logInData.userName = document.getElementById("inputUser").value;
    logInData.password = document.getElementById("inputPassword").value;

    xhttp.open("POST", "/login", true);
    xhttp.send(JSON.stringify(logInData));
}

