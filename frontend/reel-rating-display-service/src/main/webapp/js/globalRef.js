"using strict;"

const guiPort = 30400;
const authPort = 30500;
const movieDataPort = 30501;
const reviewsPort = 30502;
const ratingsPort = 30503;
const actorPort = 30504;
export class GlobalRef{
    constructor(){
        if(window.location.port === `${guiPort}`){
            this.homeLocation = `http://localhost:${guiPort}/views/home.html`;
            this.indexLocation = `http://localhost:${guiPort}/index.html`;
        }
        else{
            this.homeLocation = `http://localhost:5500/frontend/reel-rating-display-service/src/main/webapp/views/home.html`;
            this.indexLocation = `http://localhost:5500/frontend/reel-rating-display-service/src/main/webapp/index.html`;
        }
        
        this.logInPath = `http://localhost:${authPort}/reel-rating-auth-service/auth/login`;
        this.regPath = `http://localhost:${authPort}/reel-rating-auth-service/auth/register`;
        this.movieDataBase = `http://localhost:${movieDataPort}/reel-rating-movie-data-service`;
        this.movieImgBase = `http://localhost:${movieDataPort}/reel-rating-movie-data-service/movie/getMovieImage`;
        this.reviewBase = `http://localhost:${reviewsPort}/reel-rating-review-data-service`;
        this.ratingsBase = `http://localhost:${ratingsPort}/reel-rating-rating-data-service`;
        this.actorBase = `http://localhost:${actorPort}/reel-rating-actor-data-service`;
        this.regExSpecChar = /\W/; //Is not word char nor digit
        this.regExNum = /\d/;
        this.regExEmail = /^\w+?@\w+\.\w+/;
    }
}