"use strict";
import * as JSStyles from "./jsStyles.js"
import * as NetworkReq from "./networkReq.js"
import * as Login from "./login.js"
import * as Tools from "./tools.js"

window.onload = ()=>{
    var submitButton = document.getElementById("submit");
    submitButton.addEventListener("click", ()=>{
        var errors = false;
        var passwordMatch = true;
        var newAccount = submitButton.getAttribute("newAccount");
        var currentAccountData = Login.getAccountData(newAccount);
        var currentEmptyErrorMessages = Login.getEmptyErrorMessages(newAccount);
        var currentMatchErrors = document.getElementsByClassName("passwordErrorNoMatch");
        Tools.clearErrors();

        if(newAccount === "false"){
            errors = Login.checkForEmptyInputs(currentAccountData, currentEmptyErrorMessages, false);
        }
        else if(newAccount === "true"){
            errors = Login.checkForEmptyInputs(currentAccountData, currentEmptyErrorMessages, true);
            errors = Login.checkVaildPassword(currentAccountData.password.value);
            passwordMatch = Login.checkForPasswordMatch(currentAccountData, currentMatchErrors);
        }

        if(errors === false && passwordMatch === true){
            var jsonData = Tools.formatJSONData(currentAccountData, ["username", "password"]);
            NetworkReq.fetchPost(
                "http://localhost:30500/reel-rating-auth-service/auth/login", 
                jsonData
            );
        }
    });


    var newAccountButton = document.getElementById("newAccount");
    newAccountButton.addEventListener("click", ()=>{
        Login.toggleCreateAccount(submitButton);
    });


    //Vertical Center Elms that need it
    var vertCenterElms = document.getElementsByClassName("vcToWindow");
    setInterval(()=>{
        JSStyles.verticalCenterToWindowHeight(vertCenterElms);
    }, 350); //350 miliseconds, slightly higher than average reaction time
}