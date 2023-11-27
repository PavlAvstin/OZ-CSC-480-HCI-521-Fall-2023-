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
                    [currentAccountData.username, currentAccountData.email, currentAccountData.password], 
                    ["username", "email", "password"]
                );
                NetworkReq.fetchPost(globals.regPath, jsonData, Tools.navToHome);
            } else {
                var jsonData = Tools.formatJSONData(
                    [currentAccountData.username, currentAccountData.password], 
                    ["username", "password"]
                );
                NetworkReq.fetchPost(globals.logInPath, jsonData, Tools.navToHome);
            }
        }
    });


    var newAccountButton = document.getElementById("newAccount");
    newAccountButton.addEventListener("click", ()=>{
        Login.toggleCreateAccount(submitButton, newAccountButton);
    });


    //Vertical Center Elms that need it
    var windowVertCenterElms = document.getElementsByClassName("vcToWindow");
    setInterval(()=>{
        JSStyles.verticalCenterToWindowHeight(windowVertCenterElms);
    }, 350); //350 miliseconds, slightly higher than average reaction time
}

function homeInit(){
    //Vertical Center Elms that need it
    var parentVertCenterElms = document.getElementsByClassName("vcToParent");
    setInterval(()=>{
        JSStyles.verticalCenterToParentHeight(parentVertCenterElms);
    }, 32); //350 miliseconds, slightly higher than average reaction time
    
    //Vertical Center Elms that need it
    var horizontalCenterElms = document.getElementsByClassName("hcToWindow");
    setInterval(()=>{
        JSStyles.horizontalCenterToWindowWidth(horizontalCenterElms);
    }, 32); //350 miliseconds, slightly higher than average reaction time

    let JSessionId = Tools.getJSessionId();
    //Get data for the recent movies row
    NetworkReq.fetchPost(
        `${globals.movieDataBase}/movie/getRecentReleaseMovies`,
        JSessionId,
        Home.appendRowDataToRecentRelease
    );

    //Get data for the movies with most reviews
    NetworkReq.fetchPost(
        `${globals.movieDataBase}/movie/getMoviesWithMostReviews`,
        JSessionId,
        Home.appendRowDataToMostReviewed
    );
    
    //Append the filter menu with dynamic id's to avoid all the id errors
    Home.appendFilterMenu();
    Home.appendFreqFilterMenu();
    Home.setNotImplemented();
    
    //Setting listeners for the home page
    const showMoreRateButton = document.getElementById("rateButton");
    showMoreRateButton.addEventListener("click", Home.showMoreRateHandler);

    const upDownContainer = document.getElementById("upDownContainer");
    upDownContainer.addEventListener("click", (event)=>{
        Home.toggleUpDown(event.target);
    });

    const submitRatingButton = document.getElementById("submitRating");
    submitRatingButton.addEventListener("click", Home.submitRatingHandler);

    const clearRatingButton = document.getElementById("cancelRating");
    clearRatingButton.addEventListener("click", Home.clearRatingHandler);

    const submitTagButton = document.getElementById("submitTag");
    submitTagButton.addEventListener("click", Home.submitTagHandler);

    const clearTagButton = document.getElementById("cancelTag");
    clearTagButton.addEventListener("click", () => {document.getElementById("newTagInput").value = "";});

    const submitReviewButton = document.getElementById("submitReview");
    submitReviewButton.addEventListener("click", Home.submitReviewHandler);

    const clearReviewButton = document.getElementById("cancelReview");
    clearReviewButton.addEventListener("click", () => {
        document.getElementById("newReviewInput").value = "";
    });

    // const webSocket = NetworkReq.openWebSocket("urlForWS");
    // const searchBar = document.getElementById("searchBar");
    // searchBar.addEventListener("input", ()=>{
    //     NetworkReq.sendWebSocketMessage(webSocket, searchBar.value);
    // });

    const ratingScaleEndNode = document.getElementById("ratingScaleEnd");
    ratingScaleEndNode.addEventListener("change", Home.progressBarForRatingUpdate);
   
}
