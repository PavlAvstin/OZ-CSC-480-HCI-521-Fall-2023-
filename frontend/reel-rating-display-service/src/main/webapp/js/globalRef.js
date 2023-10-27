"using strict;"

const guiPort = 30400;
const authPort = 30500;
const dataPort = 30501;
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
        this.baseDataPath = `http://localhost:${dataPort}/reel-rating-movie-data-service`;
        this.regExSpecChar = /\W/; //Is not word char nor digit
        this.regExNum = /\d/;
    }
}