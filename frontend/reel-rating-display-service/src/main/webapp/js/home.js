"using strict";

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
        movieImage.src = `http://localhost:30501/reel-rating-movie-data-service/movie/getMovieImage/${movie.id}`;
        movieCard.appendChild(movieImage);

        const cardBody = document.createElement('div');
        cardBody.classList.add("card-body");
        const titleAndRating = document.createElement('div');
        titleAndRating.classList.add("row", "g-0");
        const title = document.createElement('div');
        title.classList.add("col-10", "card-title", "mdFont");
        title.textContent = `${movie.title}`;
        titleAndRating.appendChild(title);
        const rating = document.createElement('div');
        rating.classList.add("col-2", "textRight", "mdFont");
        rating.textContent = "9.3";
        titleAndRating.append(rating);
        cardBody.appendChild(titleAndRating);

        let progressAtributes = {
            scaleStart: "1",
            scaleEnd: '10',
            ratingValue: '9.3',
            lowRatingColor:'#3d37bf',
            highRatingColor:'#00ff00'
        };

        //const progressBar = document.createElement('progress-bar', progressAtributes);

        //cardBody.appendChild(progressBar);

        const tags = document.createElement('tags');
        tags.classList.add("row", "g-0", "mtXSM", "mbXSM");
        const tagsText = ["tag1", "tag2", "tag3"]
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
        showMoreButton.classList.add("bgPrimary", "fontWhite", "fullWidth", "btPrimaryStyle", "textCenter", "ptXSM", "pbXSM", "brAll", "customShadow");
        showMoreButton.setAttribute("data-bs-toggle", "modal");
        showMoreButton.setAttribute("data-bs-target", '#showMoreModal');
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