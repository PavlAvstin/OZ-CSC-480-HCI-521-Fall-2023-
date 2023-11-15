"use strict";
import * as JSStyles from "./jsStyles.js";
import * as NetworkReq from "./networkReq.js";
import * as Login from "./login.js";
import * as Tools from "./tools.js";
import * as Home from "./home.js";
import { GlobalRef } from "./globalRef.js";
const globals = new GlobalRef();


window.onload = ()=>{
    //Run the JS nessary for the page
    var currentPage = Tools.getEndOfURL();
    switch (currentPage){
        case "": loginInit(); break;
        case "index.html": loginInit(); break;
        case "home.html": homeInit(); break;
    }
}

function loginInit(){
    var submitButton = document.getElementById("submit");
    submitButton.addEventListener("click", ()=>{
        var passwordMatch = true;
        var emptyError = false;
        var validError = false;
        var emailError = false;
        var usernameError = false;

        var newAccount = submitButton.getAttribute("newAccount");
        var currentAccountData = Login.getAccountData(newAccount);
        var currentEmptyErrorMessages = Login.getEmptyErrorMessages(newAccount);
        var currentMatchErrors = document.getElementsByClassName("passwordErrorNoMatch");
        Tools.clearErrors();

        if(newAccount === "false"){
            emptyError = Login.checkForEmptyInputs(currentAccountData, currentEmptyErrorMessages, false);
        }
        else if(newAccount === "true"){
            emptyError = Login.checkForEmptyInputs(currentAccountData, currentEmptyErrorMessages, true);
            emailError = Login.checkValidEmail(
                currentAccountData.email.value, 
                globals.regExEmail
            )
            validError = Login.checkVaildPassword(
                currentAccountData.password.value, 
                globals.regExSpecChar,
                globals.regExNum
            );
            usernameError = Login.checkValidUserName(currentAccountData.username.value);
            passwordMatch = Login.checkForPasswordMatch(currentAccountData, currentMatchErrors);

        }

        if(
            emptyError === false && validError === false && 
            passwordMatch === true && usernameError === false &&
            emailError === false
        ){
            if(newAccount === "true"){
                var jsonData = Tools.formatJSONData(
                    [
                        currentAccountData.username,
                        currentAccountData.email,
                        currentAccountData.password
                    ], 
                    [
                        "username",
                        "email", 
                        "password"
                    ]
                );
                NetworkReq.fetchPost(
                    globals.regPath, 
                    jsonData,
                    Tools.navToHome
                );
            } else {
                var jsonData = Tools.formatJSONData(
                    [
                        currentAccountData.username,
                        currentAccountData.password
                    ], 
                    [
                        "username", 
                        "password"
                    ]
                );
                NetworkReq.fetchPost(
                    globals.logInPath, 
                    jsonData,
                    Tools.navToHome
                );
            }
        }
    });


    var newAccountButton = document.getElementById("newAccount");
    newAccountButton.addEventListener("click", ()=>{
        Login.toggleCreateAccount(submitButton, newAccountButton);
    });


    var notImplemented = document.getElementsByClassName("notImplemented");


    //Vertical Center Elms that need it
    var windowVertCenterElms = document.getElementsByClassName("vcToWindow");
    setInterval(()=>{
        JSStyles.verticalCenterToWindowHeight(windowVertCenterElms);
    }, 350); //350 miliseconds, slightly higher than average reaction time
}

function homeInit(){
    //Setting listener for the rate button on the showMoreModal
    var showMoreRateButton = document.getElementById("rateButton");
    showMoreRateButton.addEventListener("click", ()=>{
        var movieID = showMoreRateButton.getAttribute("movieID");
        var movieTitle = document.getElementById("showMoreTitle").innerText;
        Home.getRatingsPageData(movieTitle ,movieID);
        const ratingScaleEndNode = document.getElementById("ratingScaleEnd");
        ratingScaleEndNode.value = '10';
    });

    let JSessionId = Tools.getJSessionId();

    NetworkReq.fetchPost(
        `${globals.movieDataBase}/movie/getRecentReleaseMovies`,
        JSessionId,
        Home.appendRowDataToRecentRelease
    );

    NetworkReq.fetchPost(
        `${globals.movieDataBase}/movie/getMoviesWithMostReviews`,
        JSessionId,
        Home.appendRowDataToMostReviewed
    );

    //Append the filter menu with dynamic id's to avoid all the id errors
    Home.appendFilterMenu();
    Home.appendFreqFilterMenu();

    
    var notImplemented = document.getElementsByClassName("notImplemented");
    for(let x =0; x < notImplemented.length; x++){
        notImplemented[x].addEventListener("click",()=>{
            alert("Feature is not implemented");
        });
    }

    var upDownContainer = document.getElementById("upDownContainer");
    upDownContainer.addEventListener("click", (event)=>{
        Home.toggleUpDown(event.target);
    })


    //Vertical Center Elms that need it
    var parentVertCenterElms = document.getElementsByClassName("vcToParent");
    setInterval(()=>{
        JSStyles.verticalCenterToParentHeight(parentVertCenterElms);
    }, 350); //350 miliseconds, slightly higher than average reaction time
    
    //Vertical Center Elms that need it
    var horizontalCenterElms = document.getElementsByClassName("hcToWindow");
    setInterval(()=>{
        JSStyles.horizontalCenterToWindowWidth(horizontalCenterElms);
    }, 350); //350 miliseconds, slightly higher than average reaction time

    const ratingScaleEndNode = document.getElementById("ratingScaleEnd");
    ratingScaleEndNode.addEventListener("change", () => {Home.progressBarForRatingUpdate();});
   
}
