"using strict";

import * as Tools from "./tools.js";
import * as NetworkReq from "./networkReq.js";
import { GlobalRef } from "./globalRef.js";
const globals = new GlobalRef();

function appendMovies(movies, carouselId) {
    const recentReleasedCarousel = document.getElementById(carouselId);
    const carouselInner = document.createElement('div');
    carouselInner.classList.add("carousel-inner");
    let movieCounter = 0;
    let carouselItem = document.createElement('div');
    carouselItem.classList.add("carousel-item");
    carouselItem.classList.add("active");
    let carouselRow = document.createElement('div');
    carouselRow.classList.add("carouselRow");
    for (let movie of movies) {
        if (movieCounter % 4 === 0 && movieCounter !== 0) {
            carouselItem.appendChild(carouselRow);
            carouselInner.appendChild(carouselItem);
            carouselItem = document.createElement('div');
            carouselItem.classList.add("carousel-item");
            carouselRow = document.createElement('div');
            carouselRow.classList.add("carouselRow");
        }

        const movieCard = document.createElement('div');
        movieCard.classList.add("movieCard", "bgNeutral", "card");
        const movieImage = document.createElement('img');
        movieImage.classList.add("card-img-top", "pt-1");
        movieImage.src = `${globals.movieImgBase}/${movie.id}`;
        movieImage.style.height = "215px";
        movieCard.appendChild(movieImage);

        const cardBody = document.createElement('div');

        const title = document.createElement('div');
        title.classList.add("col-10", "card-title", "mdFont");
        title.textContent = `${movie.title}`;
        title.style.textOverflow = "ellipsis";
        title.style.height = "34.50px";

        cardBody.appendChild(title);

        cardBody.classList.add("card-body");
        const categoryAndRating = document.createElement('div');
        categoryAndRating.classList.add("row", "g-0");
        const category = document.createElement('div');
        category.classList.add("col-10", "card-title", "mdFont");
        category.textContent = `${movie.mostPopularRatingCategory}`;
        category.style.textDecoration = "underline";
        categoryAndRating.appendChild(category);
        const rating = document.createElement('div');
        rating.classList.add("col-2", "textRight", "mdFont");
        rating.textContent = movie.mostPopRatingAvg;
        rating.style.textOverflow = "ellipsis";
        categoryAndRating.append(rating);
        cardBody.appendChild(categoryAndRating);

        const progressBar = document.createElement('progress-bar-create-modify');
        progressBar.setAttribute("scaleStart", "1");
        progressBar.setAttribute("scaleEnd", movie.mostPopRatingUpperBound);
        progressBar.setAttribute("ratingValue", movie.mostPopRatingAvg);
        progressBar.setAttribute("lowRatingColor", '#3d37bf');
        progressBar.setAttribute("highRatingColor", '#00ff00');
        progressBar.loadAttributes();
        cardBody.appendChild(progressBar);

        const tags = document.createElement('tags');
        tags.classList.add("row", "g-0", "mtXSM", "mbXSM");
        const tagsText = movie.attachedTags;
        for (let tag of tagsText) {
            const tagElement = document.createElement('div');
            tagElement.classList.add("brAll", "pXXSM", "col-4", "textCenter", "fontWhite", "bgSecondary", "xsFont");
            tagElement.textContent = `${tag}`;
            tags.appendChild(tagElement);
        }
        cardBody.appendChild(tags);

        const cardTextElement = document.createElement('div');
        cardTextElement.classList.add('card-text');
        cardTextElement.textContent = `${movie.summary}`;
        const fadeAway = document.createElement('div');
        fadeAway.classList.add('fadeAwayNeutral', 'fullWidth');
        cardTextElement.appendChild(fadeAway);
        cardBody.appendChild(cardTextElement);

        const showMoreButton = document.createElement('div');
        showMoreButton.classList.add("bgPrimary", "fontWhite", "fullWidth", "btPrimaryStyle", "textCenter", "ptXSM", "pbXSM", "brAll", "customShadow", "showMore");
        showMoreButton.setAttribute("data-bs-toggle", "modal");
        showMoreButton.setAttribute("data-bs-target", '#showMoreModal');
        showMoreButton.setAttribute("movieID",`${movie.id}`);
        showMoreButton.setAttribute("movieTitle",`${movie.title}`);
        showMoreButton.addEventListener("click", (event)=>{ getShowMoreData(event.target.getAttribute("movieID"), event.target.getAttribute("movieTitle"))});
        showMoreButton.textContent = "Show More";
        cardBody.appendChild(showMoreButton);
        movieCard.appendChild(cardBody);
        carouselRow.appendChild(movieCard);
        movieCounter++;
    }
    carouselItem.appendChild(carouselRow);
    carouselInner.appendChild(carouselItem);
    recentReleasedCarousel.appendChild(carouselInner);
}

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


function getShowMoreData(movieID, movieTitle){
    //Set static elms
    document.getElementById("showMoreTitle").innerText = movieTitle;
    document.getElementById("showMoreImg").src = `${globals.movieImgBase}/${movieID}`;
    var showMoreRateButton = document.getElementById("rateButton");
    showMoreRateButton.setAttribute("movieID", `Rate ${movieID}`);
    showMoreRateButton.innerText = movieTitle;

    //Get General Info
    NetworkReq.fetchGet(
        `${globals.baseDataPath}/movie/getByTitle/${movieTitle}`,
        appendGeneralSection
    );

    //Get Existing Ratings
    appendExistingRatings();//Still need the network request

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
        //document.getElementById("showMoreWriter").innerText = genData.writer; Not in the data yet
        document.getElementById("showMoreReleaseDate").innerText = genData.releaseDate;
        document.getElementById("showMoreRuntime").innerText = genData.runtime;

        //Actors
        var actorsRow = Tools.createElm("div", null, "class", "row");
        var actors = [ //Need the end point random names for now
            'Alice Smith', 'Bob Johnson','Charlie Brown','David Davis','Emma Lee',
            'Fiona Wilson','George Evans','Hannah Clark','Isaac Thomas','Julia Walker',
            'Kevin Parker','Linda Moore','Michael Gonzalez','Nancy Hall','Oliver Martinez',
            'Paula Young','Quincy White','Rachel Martin','Steven Santent'
        ];
        var currentActor;
        for(var x =0; x < actors.length; x++){
            currentActor = Tools.createElm("div", actors[x], "class", "col-4 actor");
            actorsRow.appendChild(currentActor);
        }


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
            currentTag = Tools.createElm("div", actors[x], "class", "col-4 tag");
            tagsRow.appendChild(currentTag);
        }
        document.getElementById("showMoreActorsContainer").appendChild(actorsRow);
        document.getElementById("showMoreTagsContainer").appendChild(tagsRow);
    }catch(error){
        console.log(`There was an error appending general content\n${error}`);
    }
}

function appendExistingRatings(){
    var ratingsRow = Tools.createElm("div", null, "class", "row");
    for(var x =0; x < 50; x++){
        var currentRatingContainer = Tools.createElm("div", null, "class", "col-6 mtXSM");
        var currentRatingRow = Tools.createElm("div", null, "class", "row");
        var ratingName = Tools.createElm("div", Tools.randomString(), "class", "col-10");
        var ratingValue = Tools.createElm("div", Tools.randomNum(), "class", "col-2");
        
        var progressBar = Tools.createElm(
            "progress-bar", null, 
            ["scaleStart","scaleEnd","ratingValue","lowRatingColor","highRatingColor"], 
            ["1","10",`${Tools.randomNum()}`,"#3d37bf","#00ff00"]
        );
        currentRatingRow.appendChild(ratingName);
        currentRatingRow.appendChild(ratingValue);
        currentRatingRow.appendChild(progressBar);
        currentRatingContainer.appendChild(currentRatingRow);
        ratingsRow.appendChild(currentRatingContainer);
    }
    document.getElementById("ratingsContainer").appendChild(ratingsRow);
}

function appendFriends(){
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
}