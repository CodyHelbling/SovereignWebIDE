/**
 * Created by Cody on 4/23/16.
 */

var signUpData = {
    "email": "",
    "userName": "",
    "password": ""
};

function SignUp() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            console.log("Signing Up: ....");
            var email = document.getElementById("inputEmailSignUp").value;
            var userName = document.getElementById("inputUNameSignUp").value;
            var password = document.getElementById("inputPasswordSignUp").value;
            signUpData.email = email;
            signUpData.userName = userName;
            signUpData.password = password;
        }
    };
    xhttp.open("POST", "/signup", true);
    xhttp.send(JSON.stringify(signUpData));
}


var logInData = {
    "username":"",
    "password":""
};

function LogIn() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            console.log("Logging In: ....");
            var userName = document.getElementById("inputUser").value;
            var password = document.getElementById("inputPassword").value;
            logInData.userName = userName;
            logInData.password = password;
        }
    };
    xhttp.open("POST", "/login", true);
    xhttp.send(JSON.stringify(signUpData));
}

