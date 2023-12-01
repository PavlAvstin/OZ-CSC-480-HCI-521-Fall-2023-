"using strict";

import * as Tools from "./tools.js";
import * as NetworkReq from "./networkReq.js";
import * as JSStyles from "./jsStyles.js";
import { GlobalRef } from "./globalRef.js";
const globals = new GlobalRef();



/**
 * Appends movies to the Recent Release Carousel.
 * @param {object} serverData 
 */
export const appendRowDataToRecentRelease = async(serverData)=>{
    try{
        let movies = await serverData.json();
        appendMovies(movies, "recentReleaseCarousel");
        appendControls("recentReleaseCarousel");
    } catch(error){
        console.log(error);
        JSStyles.alertAnimation("There was an error getting data from the server");
    }
}


/**
 * Appends movies to the Most Reviewed Carousel.
 * @param {object} serverData 
 */
export const appendRowDataToMostReviewed = async(serverData)=>{
    try{
        let movies = await serverData.json();
        appendMovies(movies, "mostReviewedCarousel");
        appendControls("mostReviewedCarousel");
    } catch(error){
        console.log(error);
        JSStyles.alertAnimation("There was an error getting data from the server");
    }
}


/**
 * Makes all of the network requests necessary to populate the rating page and delegates the responses to the populating functions.
 * @param {String} movieTitle 
 * @param {String} movieID 
 */
export const getRatingsPageData = (movieTitle, movieID)=>{
    document.getElementById("ratingTitle").innerText = `${movieTitle}`;

    let jSessionIdStringified = Tools.getJSessionId();

    //Get Existing Ratings
    NetworkReq.fetchPost(
        `${globals.ratingsBase}/rating/getUniqueRatingCategoriesAndUserRatingWithMovieId/${movieID}`,
        jSessionIdStringified,
        appendExistingCategories    
    );

    NetworkReq.fetchPost(
        `${globals.ratingsBase}/tag/getTagsWithMovieId/${movieID}`,
        jSessionIdStringified,
        appendUpDownVote    
    );

    progressBarForRatingUpdate();
}


/**
 * Appends filters for the user to select on the hamburger search menu.
 */
export const appendFilterMenu = ()=>{
    var filterMenuContainer = document.getElementById("filterContainer");
    var filterRow = Tools.createElm("div", null, "class", "row");
    var filterOptions = [
        "Comedy", "Drama", "Horror", "Sci-Fi", "Fantasy", "Action",
        "Romance", "Adventure", "War", "Biography", "Super Hero"
    ];
    for(let x=0; x < filterOptions.length; x++){
        var formCheck = Tools.createElm("div", null, "class", "form-check col-12 mtSM");
        var formInput = Tools.createElm("input", null, ["id", "type", "class"], [`filter${x}`, "checkbox", "col-2"]);
        var label = Tools.createElm("label", filterOptions[x], ["class", "for"], ["form-check-label col-10", `filter${x}`]);
        formCheck.appendChild(formInput);
        formCheck.appendChild(label);
        filterRow.appendChild(formCheck);
    }
    filterMenuContainer.appendChild(filterRow);
}


/**
 * Appends filter options to the frequency filter portion of the hamburger menu.
 */
export const appendFreqFilterMenu = ()=>{
    var filterMenuContainer = document.getElementById("freqFilterContainer");
    var filterRow = Tools.createElm("div", null, "class", "row");
    var filterOptions = ["Romance","Adventure","War","Biography","Super Hero"];
    for(let x=0; x < filterOptions.length; x++){
        var formCheck = Tools.createElm("div", null, "class", "form-check col-12 mtSM");
        var formInput = Tools.createElm("input", null, ["id","type", "class"], [`freqFilter${x}`,"checkbox", "col-2"]);
        var label = Tools.createElm("label", filterOptions[x], ["class", "for"], ["form-check-label col-10", `freqFilter${x}`]);
        formCheck.appendChild(formInput);
        formCheck.appendChild(label);
        filterRow.appendChild(formCheck);
    }
    filterMenuContainer.appendChild(filterRow);
}


/**
 * Update the progress bar when the user selects a new scale
 */
export function progressBarForRatingUpdate() {

    const ratingScaleEndNode = document.getElementById("ratingScaleEnd");

    var progressBar = Tools.createElm(
        "progress-create", null, 
        ["scaleStart","scaleEnd","ratingValue","lowRatingColor","highRatingColor","ratingName"], 
        ["1",`${ratingScaleEndNode.value}`,`${Math.round(ratingScaleEndNode.value / 2)}`,"#3d37bf","#00ff00", "Rating Value"]
    );

    document.getElementById("progressBarForRating").replaceChildren(progressBar);
}


/**
 * Alerts the user that their rating was submitted and refreshes the Rating Modal ratings.
 */
export function feedbackForRatingSubmission() {
    JSStyles.alertAnimation("Rating Created");
    let jSessionIdStringified = Tools.getJSessionId();

    const showMoreRateButton = document.getElementById("rateButton");
    let movieID = showMoreRateButton.getAttribute("movieID");
    //Update Ratings to include new rating
    NetworkReq.fetchPost(
        `${globals.ratingsBase}/rating/getUniqueRatingCategoriesAndUserRatingWithMovieId/${movieID}`,
        jSessionIdStringified,
        appendExistingCategories    
    );
}

/**
 * Alerts the user that their clicked rating was submitted
 */
export function feedbackForExistingRatingClick() {
    JSStyles.alertAnimation("Rating Created");
}


/**
 * Provides the user with confirmation that their tag was submitted and refreshes the tag portion of the Rating Modal.
 */
export function feedbackForTagSubmission() {
    JSStyles.alertAnimation("Tag Created");
    let jSessionIdStringified = Tools.getJSessionId();

    const showMoreRateButton = document.getElementById("rateButton");
    let movieID = showMoreRateButton.getAttribute("movieID");
    //Update Tags to include new rating
    NetworkReq.fetchPost(
        `${globals.ratingsBase}/tag/getTagsWithMovieId/${movieID}`,
        jSessionIdStringified,
        appendUpDownVote    
    );
}


/**
 * Alerts the user that their review was submitted.
 */
export function feedbackForReviewSubmission() {
    JSStyles.alertAnimation("Review Created");
}


/**
 * Toggles the associated tags up and down buttons accordingly.
 * @param {eventTarget} eventTarget 
 */
export function toggleUpDown(eventTarget){
    if(eventTarget.getAttribute("icon") === "true"){
        var upIcon = eventTarget.getAttribute("upIcon");
        var iconIDNumber = (eventTarget.getAttribute("idNumber"));
        if(upIcon === "true"){
            if(eventTarget.getAttribute("voted") === "false"){
                eventTarget.setAttribute("src", "../images/hand-thumbs-up-fill-white.png");
                eventTarget.setAttribute("voted", "true");
                document.getElementById(`voteID${Number(iconIDNumber) + 1}`).setAttribute("src", "../images/hand-thumbs-down-white.png");
            }
            else if(eventTarget.getAttribute("voted") === "true"){
                eventTarget.setAttribute("src", "../images/hand-thumbs-up-white.png");
                eventTarget.setAttribute("voted", "false");
            }
        } 
        else if(upIcon === "false"){
            if(eventTarget.getAttribute("voted") === "false"){
                eventTarget.setAttribute("src", "../images/hand-thumbs-down-fill-white.png");
                eventTarget.setAttribute("voted", "true");
                document.getElementById(`voteID${Number(iconIDNumber) - 1}`).setAttribute("src", "../images/hand-thumbs-up-white.png");
            }
            else if(eventTarget.getAttribute("voted") === "true"){
                eventTarget.setAttribute("src", "../images/hand-thumbs-down-white.png");
                eventTarget.setAttribute("voted", "false");
            }
        }
    }
}


/**
 * Send the new up down vote state
 * @param {eventTarget} eventTarget
 */
export function sendUpDownVoteUpdate(eventTarget, voteChange){
    var voteState = setVoteState(voteChange);
    var jsonString = Tools.formatJSONData(
        ["movieId", "tagName", "state"],
        [eventTarget.getAttribute("movieID"), eventTarget.getAttribute("tagName"), voteState]
    );
    if(voteState === "upVote"){ 
        NetworkReq.fetchPost(
            `${globals.ratingsBase}/tag/upvoteTag`,
            jsonString,
            displayVoteMessage
        )
    }
    else if(voteState === "downVote"){ 
        NetworkReq.fetchPost(
            `${globals.ratingsBase}/tag/downvoteTag`,
            jsonString,
            displayVoteMessage
        )
    }
    
    
}

/**
 * Check if the up / down vote changed
 * This is to protect the system from repeat voting
 * @param {eventTarget} eventTarget
 * @returns {Number}
 */
export function checkVoteChanged(eventTarget, upVoteIcon, downVoteIcon){
    var voteChange = 0;
    if(eventTarget.getAttribute("icon") === "true"){
        if(upVoteIcon.getAttribute("voted") === "false"){ voteChange++; }
        else if(upVoteIcon.getAttribute("voted") === "true"){ voteChange--; }
        if(downVoteIcon.getAttribute("voted") === "false"){ voteChange--; }
        else if(downVoteIcon.getAttribute("voted") === "true"){ voteChange++ }
    }
    return voteChange;
}



/**
 * Get the data for show more rate modal
 * Set the ratingsScale
 * Get movieID and movieTitle
 */
export function showMoreRateHandler(){
    var movieID = document.getElementById("rateButton").getAttribute("movieID");
    var movieTitle = document.getElementById("showMoreTitle").innerText;
    document.getElementById("ratingScaleEnd").value = '10';
    getRatingsPageData(movieTitle ,movieID);
}


/**
 * Set this default alter to thing that are not implamented yet
 */
export function setNotImplemented(){
    var notImplemented = document.getElementsByClassName("notImplemented");
    for(let x =0; x < notImplemented.length; x++){
        notImplemented[x].addEventListener("click",()=>{
            JSStyles.alertAnimation("Feature is not implemented");
        });
    }
}


/**
 * Submit rating to the end point
 */
export function submitRatingHandler(){
    let jsonString = JSON.stringify({
        "ratingName" : document.getElementById("newRatingInput").value,
        "userRating" : document.querySelector("#progressBarForRating > progress-create").getAttribute("ratingValue"),
        "upperbound" : document.getElementById("ratingScaleEnd").value,
        "privacy" : "public",
        "subtype" : "scale",
        "movieId" : document.getElementById("rateButton").getAttribute("movieID"),
        "JSESSIONID" : sessionStorage.getItem("JSESSIONID")
    });
    NetworkReq.fetchPost(
        `${globals.ratingsBase}/rating/create`,
        jsonString,
        feedbackForRatingSubmission
    );
}


/**
 * Clear rating if the user wants to cancel
 */
export function clearRatingHandler(){
    document.getElementById("newRatingInput").value = "";
    document.getElementById("ratingScaleEnd").value = 10;
    progressBarForRatingUpdate();
}


/**
 * Submit a new tag
 */
export function submitTagHandler(){
    var movieID = document.getElementById("rateButton").getAttribute("movieID");
    NetworkReq.fetchPost(
        `${globals.ratingsBase}/tag/create/${movieID}`,
        JSON.stringify({
            "tagName" : document.getElementById("newTagInput").value,
            "privacy" : "public",
            "movieId" : movieID,
            "JSESSIONID" : sessionStorage.getItem("JSESSIONID")
        }),
        feedbackForTagSubmission
    )
}


/**
 * Submit review
 */
export function submitReviewHandler(){
    var movieID = document.getElementById("rateButton").getAttribute("movieID");
    NetworkReq.fetchPost(
        `${globals.reviewBase}/review/create/${movieID}`,
        JSON.stringify({
            "reviewDescription" : document.getElementById("newReviewInput").value,
            "privacy" : "public",
            "movieId" : movieID,
            "JSESSIONID" : sessionStorage.getItem("JSESSIONID")
        }),
        feedbackForReviewSubmission
    );
}


/**
 * Close all models on the x button
 * @param {HTMLCollection} allModals
 */
export function closeAllModals(allModals){
    for(let x =0; x < allModals.length; x++){
        if(allModals[x].classList.contains("show") === true){
            allModals[x].classList.remove("show");
            allModals[x].setAttribute("style",""); //Remove the style attribute that bootstrap has and default back to our custom css
        }
    }
    var modalBackdrops = document.getElementsByClassName("modal-backdrop show");

    let x = 0;
    while(x < modalBackdrops.length){ modalBackdrops[x].remove(); }
    
}


export async function displaySearch(serverData){
    var searchContainer = document.getElementById("searchResults");
    Tools.clearChildren(searchContainer);
    var searchResults = await serverData.json();
    if(searchResults.length !== 0){
        try{
            var searchRow = Tools.createElm("div", null, "class", "row");
            for(let x = 0; x < searchResults.length; x++){
                var movieCard = Tools.createElm("div", null, "class", "movieCard bgNeutral card col-4");
                var movieImage = Tools.createElm(
                    "img", null, 
                    ["src", "class", "alt"], 
                    [`${globals.movieImgBase}/${searchResults[x].id}`, "card-img-top pt-1", `${searchResults[x].title} Movie Image`]
                );
                movieCard.appendChild(movieImage);
                
                const cardBody = Tools.createElm("div", null, "class", "card-body pb-2 pt-0 px-2");
                const title = Tools.createElm(
                    "div", `${searchResults[x].title}`, "class", 
                    "col-12 card-title mdFont mb-0 hideOverflow"
                );
                cardBody.appendChild(title);
                
                // const categoryAndRating = Tools.createElm("div", null, "class", "row g-0");
                // const category = Tools.createElm(
                //     "div", `${searchResults[x].mostPopularRatingCategory}`, 
                //     "class", "col-10 smFont hideOverflow"
                // );
                // categoryAndRating.appendChild(category);

                // const rating = Tools.createElm("div",`${parseFloat(searchResults[x].mostPopRatingAvg).toFixed(1)}`,"class", "col-2 textRight smFont")
                // categoryAndRating.append(rating);
                // cardBody.appendChild(categoryAndRating);
                
                // const progressBar = Tools.createElm(
                //     "progress-bar", null,
                //     ["scaleStart", "scaleEnd", "ratingValue", "lowRatingColor", "highRatingColor"],
                //     [
                //         "1",
                //         searchResults[x].mostPopRatingUpperBound,
                //         searchResults[x].mostPopRatingAvg,
                //         "#3d37bf","#00ff00"
                //     ]
                // )
                // cardBody.appendChild(progressBar);

                // const tagsRow = Tools.createElm("div", null, "class", "row g-0 mtXSM mbXSM");
                // const tagsText = searchResults[x].attachedTags;
                // for (let x = 0; x < tagsText.length; x++) {
                //     const tag = Tools.createElm(
                //         "div", `${tagsText[x]}`, "class", 
                //         "brAll pXXSM ptXSM pbXSM col-4 textCenter fontWhite bgSecondary xsFont"
                //     );
                //     tagsRow.appendChild(tag);
                // }
                // cardBody.appendChild(tagsRow);
            
                const cardTextDiv = Tools.createElm("div", null, "class", "card-text");
                const summary = Tools.createElm("div", `${searchResults[x].summary}`);
                const fadeAway = Tools.createElm("div",null,"class","fadeAwayNeutral fullWidth");
                cardTextDiv.appendChild(fadeAway);
                cardTextDiv.appendChild(summary);
                cardBody.appendChild(cardTextDiv);
            
                const showMoreButton = Tools.createElm(
                    "div", "Show More", 
                    ["class","data-bs-toggle","data-bs-target","movieID","movieTitle",], 
                    [
                        "bgPrimary fontWhite fullWidth btPrimaryStyle textCenter ptXSM pbXSM brAll customShadow showMore",
                        "modal","#showMoreModal",`${searchResults[x].id}`,`${searchResults[x].title}`
                    ]
                );
                showMoreButton.addEventListener("click", (event)=>{ 
                    getShowMoreData(
                        event.target.getAttribute("movieID"), 
                        event.target.getAttribute("movieTitle")
                    )
                });
                cardBody.appendChild(showMoreButton);
                movieCard.appendChild(cardBody);
                searchRow.appendChild(movieCard);
                searchContainer.appendChild(searchRow);
                openSearchModal();
            }
        } catch(error){
            console.log(`There was an error appending search results cards\n${error}`);
            JSStyles.alertAnimation("Error showing results. Please try again");
        }
    }
    else{
        JSStyles.alertAnimation("No results found");
    }
}

/**
 * Open the search modal after the network request has been made
 */
function openSearchModal(){
    var searchModal = document.getElementById("searchModal");
    var modalBackDrop = Tools.createElm("div", null, "class", "modal-backdrop show");
    document.getElementById("mainContent").appendChild(modalBackDrop);
    searchModal.classList.add("show");
    searchModal.style.display = "block";
}

/**
 * Appends movies to the specified carouselId.
 * @param {object[]} movies - A list of movie objects that each contain a movie title, id, summary, aggregated rating value, rating upperbound, and rating name.
 * @param {string} carouselId - The html id of the target carousel.
 */
function appendMovies(movies, carouselId) {
    try{
        const carouselContainer = document.getElementById(carouselId);
        const carouselInner = Tools.createElm("div", null, "class", "carousel-inner");
        let carouselItem = Tools.createElm("div", null, "class", "carousel-item active");
        let carouselRow = Tools.createElm("div", null, "class", "carouselRow");
        let movieCounter = 0;

        for (let x=0; x < movies.length; x++) {
            if (movieCounter % 4 === 0 && movieCounter !== 0) {
                carouselItem.appendChild(carouselRow);
                carouselInner.appendChild(carouselItem);
                carouselItem = Tools.createElm("div", null, ["class","data-bs-interval"], ["carousel-item","14000"]);
                carouselRow = Tools.createElm("div", null, "class", "carouselRow");
            }
    
            const movieCard = Tools.createElm("div", null, "class", "movieCard bgNeutral card");
            const movieImage = Tools.createElm(
                "img", null, 
                ["src", "class", "alt"], 
                [`${globals.movieImgBase}/${movies[x].id}`, "card-img-top pt-1", `${movies[x].title} Movie Image`]
            );
            movieCard.appendChild(movieImage);
    
            const cardBody = Tools.createElm("div", null, "class", "card-body pb-2 pt-0 px-2");
            const title = Tools.createElm(
                "div", `${movies[x].title}`, "class", 
                "col-12 card-title mdFont mb-0 hideOverflow"
            );
            cardBody.appendChild(title);
    
            const categoryAndRating = Tools.createElm("div", null, "class", "row g-0");
            const category = Tools.createElm(
                "div", `${movies[x].mostPopularRatingCategory}`, 
                "class", "col-10 smFont hideOverflow"
            );
            categoryAndRating.appendChild(category);

            const rating = Tools.createElm("div",`${parseFloat(movies[x].mostPopRatingAvg).toFixed(1)}`,"class", "col-2 textRight smFont")
            categoryAndRating.append(rating);
            cardBody.appendChild(categoryAndRating);
    
            const progressBar = Tools.createElm(
                "progress-bar", null,
                ["scaleStart", "scaleEnd", "ratingValue", "lowRatingColor", "highRatingColor"],
                [
                    "1",
                    movies[x].mostPopRatingUpperBound,
                    movies[x].mostPopRatingAvg,
                    "#3d37bf","#00ff00"
                ]
            )
            cardBody.appendChild(progressBar);

            const tagsRow = Tools.createElm("div", null, "class", "row g-0 mtXSM mbXSM");
            const tagsText = movies[x].attachedTags;
            for (let x = 0; x < tagsText.length; x++) {
                const tag = Tools.createElm(
                    "div", `${tagsText[x]}`, "class", 
                    "brAll pXXSM ptXSM pbXSM col-4 textCenter fontWhite bgSecondary xsFont"
                );
                tagsRow.appendChild(tag);
            }
            cardBody.appendChild(tagsRow);
    
            const cardTextDiv = Tools.createElm("div", null, "class", "card-text");
            const summary = Tools.createElm("div", `${movies[x].summary}`);
            const fadeAway = Tools.createElm("div",null,"class","fadeAwayNeutral fullWidth");
            cardTextDiv.appendChild(fadeAway);
            cardTextDiv.appendChild(summary);
            cardBody.appendChild(cardTextDiv);
    
            const showMoreButton = Tools.createElm(
                "div", "Show More", 
                ["class","data-bs-toggle","data-bs-target","movieID","movieTitle",], 
                [
                    "bgPrimary fontWhite fullWidth btPrimaryStyle textCenter ptXSM pbXSM brAll customShadow showMore",
                    "modal","#showMoreModal",`${movies[x].id}`,`${movies[x].title}`
                ]
            );
            showMoreButton.addEventListener("click", (event)=>{ 
                getShowMoreData(
                    event.target.getAttribute("movieID"), 
                    event.target.getAttribute("movieTitle")
                )
            });
            cardBody.appendChild(showMoreButton);
            movieCard.appendChild(cardBody);
            carouselRow.appendChild(movieCard);
            movieCounter++;
        }
        carouselItem.appendChild(carouselRow);
        carouselInner.appendChild(carouselItem);
        carouselContainer.appendChild(carouselInner);

    } catch(error){
        console.log(`There was an error appending home page cards\n${error}`);
    }
}


/**
 * Setups the show more modal, appends a movietitle and loads image before making network requests to populate the modal.
 * @param {string} movieID - A movies database id.
 * @param {string} movieTitle - The movie title associated with the movieID.
 */
function getShowMoreData(movieID, movieTitle){
    //Set static elms
    document.getElementById("showMoreTitle").innerText = movieTitle;
    document.getElementById("showMoreImg").src = `${globals.movieImgBase}/${movieID}`;
    document.getElementById("showMoreImg").setAttribute("alt", `${movieTitle} Movie Image`);
    var showMoreRateButton = document.getElementById("rateButton");
    showMoreRateButton.setAttribute("movieID", movieID);
    showMoreRateButton.innerText = `Rate ${movieTitle}`;
    
    let jSessionIdStringified = Tools.getJSessionId();

    //Get General Info
    NetworkReq.fetchPost(
        `${globals.movieDataBase}/movie/getMovieWithMovieId/${movieID}`,
        jSessionIdStringified,
        appendGeneralSection
    );

    //Get Existing Ratings
    NetworkReq.fetchPost(
        `${globals.ratingsBase}/rating/getRatingsWithMovieId/${movieID}`,
        jSessionIdStringified,
        appendExistingRatings
    );

    //Get Actors
    NetworkReq.fetchPost(
        `${globals.actorBase}/actor/getActorsWithMovieId/${movieID}`,
        jSessionIdStringified,
        appendActors
    );

    //

    //Get Friends Reviews
    appendFriends();//Likely will not get this feature up and running
}


/**
 * Appends the movie summary, directors, writers, release date, and runtime to the Show More Modal. 
 * Also calls a fetch post that after recieving data appends tags to the Show More Modal.
 * @param {object} serverRes - An object that contains a movie summary, directors, writers, release date, runtime, and id.
 */
async function appendGeneralSection(serverRes){
    try{
        var genData = await serverRes.json();
        
        //General
        document.getElementById("showMoreSummary").innerText = genData.summary;
        document.getElementById("showMoreDirector").innerText = genData.director;
        document.getElementById("showMoreWriter").innerText = genData.writers;
        document.getElementById("showMoreReleaseDate").innerText = genData.releaseDate;
        document.getElementById("showMoreRuntime").innerText = genData.runtime;

        let jSessionIdStringified = Tools.getJSessionId();

        //Get Tags
        NetworkReq.fetchPost(
            `${globals.ratingsBase}/tag/getTagsWithMovieId/${genData.id}`,
            jSessionIdStringified,
            appendTagsToShowMore
        )
    }catch(error){
        console.log(`There was an error appending general content\n${error}`);
    }
}


/**
 * Appends tags to the Show More Modal.
 * @param {object} serverRes - The server response object is a list consiting of tag objects that have tag names.
 */
async function appendTagsToShowMore(serverRes) {
    //Tags
    var tagsRow = Tools.createElm("div", null, "class", "row");
    var tags = await serverRes.json();
    var currentTag;
    for(var x =0; x < tags.length; x++){
        currentTag = Tools.createElm("div", tags[x].tagName, "class", "col-4 tag");
        tagsRow.appendChild(currentTag);
    }
        
    document.getElementById("showMoreTagsContainer").replaceChildren(tagsRow);
}


/**
 * Appends actors to the Show More Modal.
 * @param {object} serverRes - A list of actor objects that each contain an actors name
 */
async function appendActors(serverRes){
    try{
        var actors = await serverRes.json();
        //Actors
        var actorsRow = Tools.createElm("div", null, "class", "row");
        if(actors.length === 0){ 
            var currentActor = Tools.createElm("div", "No actors recorded", "class", "col-4 actor");
            actorsRow.appendChild(currentActor);
        }
        else{
            for(var x =0; x < actors.length; x++){
                var currentActor = Tools.createElm("div", actors[x].name, "class", "col-4 actor");
                actorsRow.appendChild(currentActor);
            }
        }
        document.getElementById("showMoreActorsContainer").replaceChildren(actorsRow);
    }
    catch(error){
        console.log(`There was an error appending actors\n${error}`);
    }
}


/**
 * Appends aggregated rating progress bars with associated names and values to the Show More Modal.
 * @param {object} serverRes - The server response is a list consiting of rating objects that have rating category names, upperbounds, and rating.
 */
async function appendExistingRatings(serverRes){
    try{
        var ratings = await serverRes.json();
        var ratingsRow = Tools.createElm("div", null, "class", "row");
        for(var x =0; x < ratings.length; x++){
            var currentRatingContainer = Tools.createElm("div", null, "class", "col-6 mtXSM");
            var currentRatingRow = Tools.createElm("div", null, "class", "row");
            var ratingName = Tools.createElm("div", ratings[x].ratingName, "class", "col-10 hideOverflow");
            var ratingValue = Tools.createElm("div", ratings[x].userRating, "class", "col-2");

            var progressBar = Tools.createElm(
                "progress-bar", null, 
                ["scaleStart","scaleEnd","ratingValue","lowRatingColor","highRatingColor"], 
                ["1",`${ratings[x].upperbound}`,`${ratings[x].userRating}`,"#3d37bf","#00ff00"]
            );
            currentRatingRow.appendChild(ratingName);
            currentRatingRow.appendChild(ratingValue);
            currentRatingRow.appendChild(progressBar);
            currentRatingContainer.appendChild(currentRatingRow);
            ratingsRow.appendChild(currentRatingContainer);
        }
        document.getElementById("ratingsContainer").replaceChildren(ratingsRow);
    } catch(error){
        console.log(`There was an error in appendingExistingRatings\n${error}`);
    }
}


/**
 * Appends aggregated rating progress bars with associated names and values to the Rating Modal.
 * @param {object} serverRes - The server response is a list consiting ratings with rating category names, upperbounds, and rating.
 */
async function appendExistingCategories(serverRes){
    try{
        var ratings = await serverRes.json();
        var ratingsRow = Tools.createElm("div", null, "class", "row");
        for (var x = 0; x < ratings.length; x++) {
            var currentRatingContainer = Tools.createElm("div", null, "class", "col-6 mtSM");
            let progressBar = Tools.createElm(
                "progress-clickable", null, 
                ["ratingName","scaleStart","scaleEnd","userRating","lowRatingColor","highRatingColor", "ratingValue"], 
                [`${ratings[x].ratingName}`,"1",`${ratings[x].upperbound}`,`${ratings[x].userRating}`,"#3d37bf","#00ff00", `${ratings[x].avgRating}`]
            );
            progressBar.addEventListener("click", () => {
                let jsonString = JSON.stringify({
                    "ratingName" : progressBar.getAttribute("ratingName"),
                    "userRating" : progressBar.getAttribute("ratingValue"),
                    "upperbound" : progressBar.getAttribute("scaleEnd"),
                    "privacy" : "public",
                    "subtype" : "scale",
                    "movieId" : document.getElementById("rateButton").getAttribute("movieID"),
                    "JSESSIONID" : sessionStorage.getItem("JSESSIONID")
                });
                NetworkReq.fetchPost(
                    `${globals.ratingsBase}/rating/create`,
                    jsonString,
                    feedbackForExistingRatingClick
                );
            });
            currentRatingContainer.appendChild(progressBar);
            ratingsRow.appendChild(currentRatingContainer);
        }
        document.getElementById("ratingsExsistingCat").replaceChildren(ratingsRow);
    } catch(error){
        console.log(`There was an error in appendExistingCategories\n${error}`);
    }
}


/**
 * Appends tags with the ability to be upvoted or downvoted to the Rating Modal. The userurs' previous upvotes/downvotes
 * are taken into account based on a voteID.
 * @param serverData - The server response object is a list consiting of tag objects that have tag names and an associated voteID.
 * @type {(serverData : object[])}
 */
async function appendUpDownVote(serverData){
    try{
        var upDownData = await serverData.json();
        var upDownContainer = document.getElementById("upDownContainer");
        Tools.clearChildren(upDownContainer);
        
        var votingRow = Tools.createElm("div", null, "class", "row");
        var voteID = 0
        for(var x =0; x < upDownData.length; x++){
            var voteRowContainer = Tools.createElm("div", null, "class", "col-6");
            var voteRow = Tools.createElm("div",null,"class","upVoteRow row mtSM");
            if(upDownData[x].state === "upvote"){
                var upVote = Tools.createElm(
                    "img", null, 
                    ["id", "idNumber", "movieID", "tagName", "class", "src", "icon", "voted", "upIcon"],
                    [
                        `voteID${voteID}`,
                        `${voteID}`, 
                        `${upDownData[x].movieID}`, 
                        `${upDownData[x].tagName}`, 
                        "upVote col-2",
                        `../images/hand-thumbs-up-fill-white.png`, 
                        "true", //icon 
                        "true", //voted 
                        "true" //upIcon
                    ]
                );
                var downVote = Tools.createElm(
                    "img", null, 
                    ["id", "idNumber",  "movieID", "tagName", "class", "src", "icon", "voted", "upIcon"],
                    [
                        `voteID${voteID + 1}`, 
                        `${voteID + 1}`, 
                        `${upDownData[x].movieId}`, 
                        `${upDownData[x].tagName}`, 
                        "downVote col-2",
                        `../images/hand-thumbs-down-white.png`, 
                        "true",  //icon
                        "false", //voted 
                        "false" //upIcon
                    ]
                );
            }
            else if(upDownData[x].state === "downvote"){
                var upVote = Tools.createElm(
                    "img", null, 
                    ["id", "idNumber", "movieID", "tagName", "class", "src", "icon", "voted", "upIcon"],
                    [
                        `voteID${voteID}`, 
                        `${voteID}`, 
                        `${upDownData[x].movieId}`, 
                        `${upDownData[x].tagName}`, 
                        "upVote col-2",
                        `../images/hand-thumbs-up-white.png`, 
                        "true",  //icon
                        "false", //voted
                        "true" //upIcon
                    ]
                );
                var downVote = Tools.createElm(
                    "img", null, 
                    ["id", "idNumber", "movieID", "tagName", "class", "src", "icon", "voted", "upIcon"],
                    [
                        `voteID${voteID + 1}`, 
                        `${voteID + 1}`, 
                        `${upDownData[x].movieId}`, 
                        `${upDownData[x].tagName}`, 
                        "downVote col-2",
                        `../images/hand-thumbs-down-fill-white.png`, 
                        "true", //icon
                        "true", //voted
                        "false" //upIcon
                    ]
                );
            }
            else{ //If no vote
                var upVote = Tools.createElm(
                    "img", null, 
                    ["id", "idNumber", "movieID", "tagName", "class", "src", "icon", "voted", "upIcon"],
                    [
                        `voteID${voteID}`, 
                        `${voteID}`, 
                        `${upDownData[x].movieId}`, 
                        `${upDownData[x].tagName}`, 
                        "upVote col-2",
                        `../images/hand-thumbs-up-white.png`, 
                        "true", //icon
                        "false", //voted
                        "true" //upIcon
                    ]
                );
                var downVote = Tools.createElm(
                    "img", null, 
                    ["id", "idNumber", "movieID", "tagName", "class", "src", "icon", "voted", "upIcon"],
                    [
                        `voteID${voteID + 1}`, 
                        `${voteID + 1}`, 
                        `${upDownData[x].movieId}`, 
                        `${upDownData[x].tagName}`, 
                        "downVote col-2",
                        `../images/hand-thumbs-down-white.png`, 
                        "true", //icon
                        "false", //voted
                        "false" //upIcon
                    ]
                );
            }
            var tagName = Tools.createElm("div", upDownData[x].tagName, ["class","icon"], ["col-8 tagName hideOverflow","false"]);
            voteRow.appendChild(upVote);
            voteRow.appendChild(downVote);
            voteRow.appendChild(tagName);
            voteRowContainer.appendChild(voteRow);
            votingRow.appendChild(voteRowContainer);
            voteID += 2;
        }
        upDownContainer.appendChild(votingRow);
    } catch(error){
        console.log(`There was an error appendUpDownVote\n${error}`);
    }
    
}


/**
 * Appends friends to the Show More Modal.
 */
function appendFriends(){
    try{
        var names = [ //Need the end point random names for now
            'Alice Smith', 'Bob Johnson','Charlie Brown','David Davis','Emma Lee',
            'Fiona Wilson','George Evans','Hannah Clark','Isaac Thomas','Julia Walker',
            'Kevin Parker','Linda Moore','Michael Gonzalez','Nancy Hall','Oliver Martinez',
            'Paula Young','Quincy White','Rachel Martin','Steven Santent'
        ];
        var tempContainer = Tools.createElm("div", null, "class", "col-12");
        for(var x=0; x < 19; x++){
            var friendRow = Tools.createElm("div", null, "class", "row followedReview brAll ptXXSM pbXXSM mtXSM");
            var imgContainer = Tools.createElm("div", null, "class", "col-2");
            var friendImg = Tools.createElm("img", null, ["src", "class"], ["../images/person-1.webp", "img-fluid"]);
            var friendName = Tools.createElm("div", `${names[x]}`, "class", "col-10 mdFont vcToParent personName fontBlack");
            imgContainer.appendChild(friendImg);
            friendRow.appendChild(imgContainer);
            friendRow.appendChild(friendName);
            tempContainer.appendChild(friendRow);
        }
        document.getElementById("friendsContainer").replaceChildren(tempContainer);
    } catch(error){
        console.log(`There was an error in appendFriends\n${error}`);
    }
}


/**
 * Adds flag controls to the specified carousel.
 * @param carouselContainerID - The html id of the carousel container.
 * @type {(carouselContainerID : String)}
 */
function appendControls(carouselContainerID){
    var carouselContainer = document.getElementById(carouselContainerID);
    var prevButton = Tools.createElm(
        "button", "<", 
        ["class","type","data-bs-target","data-bs-slide"], 
        ["carousel-control-prev controls lgFont","button",`#${carouselContainerID}`,"prev"]
    );
    
    var nextButton = Tools.createElm(
        "button", ">", 
        ["class","type","data-bs-target","data-bs-slide"], 
        ["carousel-control-next controls lgFont", "button",`#${carouselContainerID}`,"next"]
    );

    carouselContainer.appendChild(prevButton);
    carouselContainer.appendChild(nextButton);
}


/**
 * set vote state to upVote or downVote
 * @returns {string}
 */
function setVoteState(voteChange){
    var voteState = "";
    if(voteChange > 0){ voteState = "upVote"; }
    else if(voteChange < 0){ voteState = "downVote"; }
    return voteState;
}

function displayVoteMessage(){
    JSStyles.alertAnimation("Vote Changed");
}

