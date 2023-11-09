"using strict";

import * as Tools from "./tools.js";
import * as NetworkReq from "./networkReq.js";
import { GlobalRef } from "./globalRef.js";
const globals = new GlobalRef();


export const appendRowDataToRecentRelease = async(serverData)=>{
    try{
        let movies = await serverData.json();
        appendMovies(movies, "recentReleaseCarousel");
    } catch(error){
        console.log(error);
        alert("There was an error getting data from the server");
    }
}


export const appendRowDataToMostReviewed = async(serverData)=>{
    try{
        let movies = await serverData.json();
        appendMovies(movies, "mostReviewedCarousel");
    } catch(error){
        console.log(error);
        alert("There was an error getting data from the server");
    }
}


export const getRatingsPageData = (movieTitle, movieID)=>{
    document.getElementById("ratingTitle").innerText = `${movieTitle}`;

    //Get Existing Ratings
    NetworkReq.fetchGet(
        `${globals.ratingsBase}/rating/getRatingsWithMovieId/${movieID}`,
        appendExistingCategories    
    );

    //Get Actors
    NetworkReq.fetchGet(
        `${globals.actorBase}/actor/getActorsWithMovieId/${movieID}`,
        appendActors
    )

    appendUpDownVote();
}


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
                ["src", "class"], 
                [`${globals.movieImgBase}/${movies[x].id}`, "card-img-top pt-1"]
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

            const rating = Tools.createElm("div",`${movies[x].mostPopRatingAvg}`,"class", "col-2 textRight smFont")
            categoryAndRating.append(rating);
            cardBody.appendChild(categoryAndRating);
    
            const progressBar = document.createElement('progress-bar-create-modify');
            progressBar.setAttribute("scaleStart", "1");
            progressBar.setAttribute("scaleEnd", movies[x].mostPopRatingUpperBound);
            progressBar.setAttribute("ratingValue", movies[x].mostPopRatingAvg);
            progressBar.setAttribute("lowRatingColor", '#3d37bf');
            progressBar.setAttribute("highRatingColor", '#00ff00');
            progressBar.loadAttributes();
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


function getShowMoreData(movieID, movieTitle){
    //Set static elms
    document.getElementById("showMoreTitle").innerText = movieTitle;
    document.getElementById("showMoreImg").src = `${globals.movieImgBase}/${movieID}`;
    var showMoreRateButton = document.getElementById("rateButton");
    showMoreRateButton.setAttribute("movieID", movieID);
    showMoreRateButton.innerText = `Rate ${movieTitle}`;
    

    //Get General Info
    NetworkReq.fetchGet(
        `${globals.movieDataBase}/movie/getByTitle/${movieTitle}`,
        appendGeneralSection
    );

    //Get Existing Ratings
    NetworkReq.fetchGet(
        `${globals.ratingsBase}/rating/getRatingsWithMovieId/${movieID}`,
        appendExistingRatings
    );

    //Get Friends Reviews
    appendFriends();//Likely will not get this feature up and running
}


async function appendGeneralSection(serverRes){
    try{
        var genData = await serverRes.json();
        genData = genData[0];
        
        //General
        document.getElementById("showMoreSummary").innerText = genData.summary;
        document.getElementById("showMoreDirector").innerText = genData.director;
        //document.getElementById("showMoreWriter").innerText = genData.writer;
        document.getElementById("showMoreReleaseDate").innerText = genData.releaseDate;
        document.getElementById("showMoreRuntime").innerText = genData.runtime;


        //Tags
        var tagsRow = Tools.createElm("div", null, "class", "row");
        var tags = [ //Need the end point random names for now
            'apple', 'banana', 'cherry', 'dog', 'elephant',
            'fish', 'grape', 'horse', 'iguana', 'jacket',
            'kiwi', 'lemon', 'mango', 'noodle', 'orange',
            'pear', 'quilt', 'rabbit', 'strawberry', 'tiger',
            'umbrella', 'violet', 'watermelon', 'xylophone', 'zebra',
            'carrot', 'broccoli', 'potato', 'computer', 'guitar',
            'keyboard', 'camera', 'sunglasses', 'book', 'pencil',
            'lamp', 'table', 'chair', 'shoe', 'hat',
            'globe', 'clock', 'window', 'door', 'balloon'
        ];
        var currentTag;
        for(var x =0; x < tags.length; x++){
            currentTag = Tools.createElm("div", tags[x], "class", "col-4 tag");
            tagsRow.appendChild(currentTag);
        }
        
        document.getElementById("showMoreTagsContainer").appendChild(tagsRow);
    }catch(error){
        console.log(`There was an error appending general content\n${error}`);
    }
}


async function appendActors(serverRes){
    try{
        var actors = await serverRes.json();
        //Actors
        var actorsRow = Tools.createElm("div", null, "class", "row");
        var currentActor;
        for(var x =0; x < actors.length; x++){
            currentActor = Tools.createElm("div", actors[x].name, "class", "col-4 actor");
            actorsRow.appendChild(currentActor);
        }
        document.getElementById("showMoreActorsContainer").appendChild(actorsRow);
    }
    catch(error){
        console.log(`There was an error appending actors\n${error}`);
    }
}


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
        document.getElementById("ratingsContainer").appendChild(ratingsRow);
    } catch(error){
        console.log(`There was an error in appendingExistingRatings\n${error}`);
    }
}


async function appendExistingCategories(serverRes){
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
        document.getElementById("ratingsExsistingCat").appendChild(ratingsRow);
    } catch(error){
        console.log(`There was an error in appendExistingCategories\n${error}`);
    }
}


function appendUpDownVote(){
    try{
        //Tags
        var votingRow = Tools.createElm("div", null, "class", "row");
        var tags = [ //Need the end point random names for now
            'apple', 'banana', 'cherry', 'dog', 'elephant',
            'fish', 'grape', 'horse', 'iguana', 'jacket',
            'kiwi', 'lemon', 'mango', 'noodle', 'orange',
            'pear', 'quilt', 'rabbit', 'strawberry', 'tiger',
            'umbrella', 'violet', 'watermelon', 'xylophone', 'zebra',
            'carrot', 'broccoli', 'potato', 'computer', 'guitar',
            'keyboard', 'camera', 'sunglasses', 'book', 'pencil',
            'lamp', 'table', 'chair', 'shoe', 'hat',
            'globe', 'clock', 'window', 'door', 'balloon'
        ];
        for(var x =0; x < tags.length; x++){
            var voteRowContainer = Tools.createElm("div", null, "class", "col-6");
            var voteRow = Tools.createElm("div",null,"class","upVoteRow row mtSM");
            var upVote = Tools.createElm("img", null, ["class","src"],["upVote col-2",`../images/hand-thumbs-up-white.png`]);
            var downVote = Tools.createElm("img", null, ["class","src"],["downVote col-2",`../images/hand-thumbs-down-white.png`]);
            var tagName = Tools.createElm("div", tags[x], "class", "col-8 tagName");
            voteRow.appendChild(upVote);
            voteRow.appendChild(downVote);
            voteRow.appendChild(tagName);
            voteRowContainer.appendChild(voteRow);
            votingRow.appendChild(voteRowContainer);
        }
        document.getElementById("upDownContainer").appendChild(votingRow);
    } catch(error){
        console.log(`There was an error appendUpDownVote\n${error}`);
    }
    
}


function appendFriends(){
    try{
        var names = [ //Need the end point random names for now
            'Alice Smith', 'Bob Johnson','Charlie Brown','David Davis','Emma Lee',
            'Fiona Wilson','George Evans','Hannah Clark','Isaac Thomas','Julia Walker',
            'Kevin Parker','Linda Moore','Michael Gonzalez','Nancy Hall','Oliver Martinez',
            'Paula Young','Quincy White','Rachel Martin','Steven Santent'
        ];
        var tempContainer = Tools.createElm("div", null, "class", "col-12");
        for(var x=0; x < 20; x++){
            var friendRow = Tools.createElm("div", null, "class", "row followedReview brAll ptXXSM pbXXSM mtXSM");
            var imgContainer = Tools.createElm("div", null, "class", "col-2");
            var friendImg = Tools.createElm("img", null, ["src", "class"], ["../images/person-1.webp", "img-fluid"]);
            var friendName = Tools.createElm("div", `${names[x]}`, "class", "col-10 mdFont vcToParent personName fontBlack");
            imgContainer.appendChild(friendImg);
            friendRow.appendChild(imgContainer);
            friendRow.appendChild(friendName);
            tempContainer.appendChild(friendRow);
        }
        document.getElementById("friendsContainer").appendChild(tempContainer);
    } catch(error){
        console.log(`There was an error in appendFriends\n${error}`);
    }
}


