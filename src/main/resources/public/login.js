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
            var res = xhttp.responseText;
            console.log("response: " + res);
            if (res === "true") {
                document.getElementById("SignInButton").style.backgroundColor = "red";
            }
            else if ( res === "false" ) {
                document.getElementById("SignInButton").style.backgroundColor = "blue";
            }
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
            var res = xhttp.responseText;
            console.log("response: " + res);
            if (res === "true") {
                var para = document.createElement("p");
                var node = document.createTextNode("Your Username and Password were recognized!");
                para.appendChild(node);

                var element = document.getElementById("modalText");
                element.appendChild(para);

                var para = document.createElement("button");
                var node = document.createTextNode("Enter sQuire!");
                para.appendChild(node);

                var element = document.getElementById("link");
                element.appendChild(para);

            }
            else if ( res === "false" ) {
                var para = document.createElement("p");
                var node = document.createTextNode("User and Password not recognized. \nPlease try again, or Sign Up if you are new to sQuire!");
                para.appendChild(node);

                var element = document.getElementById("modalText");
                element.appendChild(para);
            }
        }
    };

    console.log("Logging In: ....");
    logInData.userName = document.getElementById("inputUser").value;
    logInData.password = document.getElementById("inputPassword").value;

    xhttp.open("POST", "/login", true);
    xhttp.send(JSON.stringify(logInData));
}

