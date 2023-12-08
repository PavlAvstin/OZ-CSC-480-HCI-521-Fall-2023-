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
                var jsonData = Tools.formatJSONDataNoJSession(
                    ["username", "email", "password"],
                    [currentAccountData.username.value, currentAccountData.email.value, currentAccountData.password.value]
                );
                NetworkReq.fetchPost(globals.regPath, jsonData, Tools.navToHome);
                localStorage.setItem("userName",`${currentAccountData.username}`);
            } 
            else {
                var jsonData = Tools.formatJSONDataNoJSession(
                    ["username", "password"],
                    [currentAccountData.username.value, currentAccountData.password.value] 
                );
                NetworkReq.fetchPost(globals.logInPath, jsonData, Tools.navToHome);
                localStorage.setItem("userName",`${currentAccountData.username}`);
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
    var parentVertCenterElms = document.getElementsByClassName("vcToParent");
    setInterval(()=>{
        JSStyles.verticalCenterToParentHeight(parentVertCenterElms);
    }, 32); //32 miliseconds, 30 FPS
    
    var horizontalCenterElms = document.getElementsByClassName("hcToWindow");
    setInterval(()=>{
        JSStyles.horizontalCenterToWindowWidth(horizontalCenterElms);
    }, 32); //32 miliseconds, 30 FPS

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
    Home.setNotImplemented();
    
    //Setting listeners for the home page
    const showMoreRateButton = document.getElementById("rateButton");
    showMoreRateButton.addEventListener("click", Home.showMoreRateHandler);

    const upDownContainer = document.getElementById("upDownContainer");
    upDownContainer.addEventListener("click", (event)=>{
        var voteRow = event.target.parentNode;
        Home.toggleUpDown(event.target);
        var voteChange = Home.getCurrentVote(event.target, voteRow.childNodes[0], voteRow.childNodes[1]);
        if(voteChange != 0){
            Home.sendUpDownVoteUpdate(event.target, voteChange);
        }
    });
    
    const searchButton = document.getElementById("searchButton");
    const searchBar = document.getElementById("searchBar");
    const searchUI = document.getElementById("searchUI");
    searchButton.addEventListener("click", ()=>{
        const searchValue = searchBar.value.trim();
        document.getElementById("searchTitle").innerText = searchValue;
        // NetworkReq.fetchPostNoCors(
        //     `${globals.searchBase}/movie/searchByMovieNameIndex/${searchValue}`,
        //     Tools.getJSessionId(),
        //     Home.displaySearch
        // ); 
        NetworkReq.fetchPost(
            `${globals.searchBase}/movie/searchByMovieNameIndex/${searchValue}`,
            Tools.getJSessionId(),
            Home.displaySearch
        ); 
    });
    searchUI.addEventListener("keyup", (event)=>{
        if(event.key === "Enter"){
            const searchValue = searchBar.value.trim();
            document.getElementById("searchTitle").innerText = searchValue;
            // NetworkReq.fetchPostNoCors(
            //     `${globals.searchBase}/movie/searchByMovieNameIndex/${searchValue}`,
            //     Tools.getJSessionId(),
            //     Home.displaySearch
            // );
            NetworkReq.fetchPost(
                `${globals.searchBase}/movie/searchByMovieNameIndex/${searchValue}`,
                Tools.getJSessionId(),
                Home.displaySearch
            );
        }
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

    const allModals = document.getElementsByClassName("modal");
    const closeModalButtons = document.getElementsByClassName("close");
    for(let x =0; x < closeModalButtons.length; x++){
        closeModalButtons[x].addEventListener("click", ()=>{
            Home.closeAllModals(allModals);
        });
    } 

    let webSocket = NetworkReq.openWebSocket("ws://moxie.cs.oswego.edu:30505/reel-rating-search-service/autocomplete");
    searchBar.addEventListener("input", ()=>{
        NetworkReq.sendWebSocketMessage(webSocket, searchBar.value);
    });

    // webSocket.onclose = (exception) => {
    //     console.log(`Connection websocket closed because ${exception.reason}`);
    //     setTimeout(() => {
    //         webSocket = NetworkReq.openWebSocket("ws://moxie.cs.oswego.edu:30505/reel-rating-search-service/autocomplete");
    //     }, 1000);
    // }

    const searchAutoCompleteList = document.getElementById("searchAutoComplete");

    webSocket.onmessage = (response) => {
        let movieNames = JSON.parse(response.data).results;
        searchAutoCompleteList.replaceChildren();
        // Constrains the amount of auto complete results.
        let autoCompleteLimit = 5;
        let autoCompleteCurrentCount = 0;
        for (let movieName of movieNames) {
            if (autoCompleteCurrentCount >= autoCompleteLimit) break;
            let option = document.createElement("option");
            option.value = movieName;
            searchAutoCompleteList.appendChild(option);
            autoCompleteCurrentCount++;
        }
    }

    const ratingScaleEndNode = document.getElementById("ratingScaleEnd");
    ratingScaleEndNode.addEventListener("change", Home.progressBarForRatingUpdate);
   
}
