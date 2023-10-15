"using strict;"


class ProgressBar extends HTMLElement{
    constructor(){
        super();
        
        //Needed Elms
        var progressBarContainer = document.createElement("div");
        progressBarContainer.setAttribute("id", "progressBarContainer");
        
        var progressBar = document.createElement("div");
        progressBar.setAttribute("id", "progressBar");
        progressBarContainer.appendChild(progressBar);

        this.attachShadow({ mode : "open" });//Append the shadow root
        this.shadowRoot.appendChild(progressBarContainer.cloneNode(true));
        

        //Get the attributes that where passed in and create the component
        progressBarContainer = this.shadowRoot.getElementById("progressBarContainer");
        progressBar = this.shadowRoot.getElementById("progressBar");

        var scaleStart = Number(this.getAttribute("scaleStart"));
        var scaleEnd = Number(this.getAttribute("scaleEnd"));
        var ratingValue = Number(this.getAttribute("ratingValue"));
        var lowRatingColor = this.getAttribute("lowRatingColor");
        var highRatingColor = this.getAttribute("highRatingColor");
        lowRatingColor = this.hexToRgb(lowRatingColor);
        highRatingColor = this.hexToRgb(highRatingColor);
        progressBar = this.createBlocks(progressBar, scaleStart, scaleEnd);
        var barColor = this.createBarColor(ratingValue, scaleEnd, lowRatingColor, highRatingColor);
        this.fillProgressBar(progressBar, barColor, ratingValue);
        this.setProgressBarStyle(progressBarContainer, scaleEnd);

        //These are needed because :last-child was not working for some reason
        progressBar.children[progressBar.children.length - 1].style.borderTopRightRadius = "15px";
        progressBar.children[progressBar.children.length - 1].style.borderBottomRightRadius = "15px";
    }

    fillProgressBar(progressBar, barColor, ratingValue){
        for(var x =0; x < ratingValue; x++){ 
            progressBar.children[x].style.backgroundColor = `rgb(${barColor.red}, ${barColor.green}, ${barColor.blue})`; 
        }
    }

    setProgressBarStyle(progressBarContainer, scaleEnd){
        var styleTag = document.createElement("style");
        styleTag.innerHTML += `
            #progressBar{ 
                display : grid;
                grid-template-columns: repeat(${scaleEnd}, 1fr);
                width : 100%;
                box-shadow : 0px 2px 2px 0px rgba(0, 0, 0, .5);
                border-radius: 15px;
            }
            .ratingBlock:first-child{
                border-top-left-radius: 15px; 
                border-bottom-left-radius: 15px;
            }
            .ratingBlock{
                border-left: solid;
                border-color : black;
                height : 33px;
                border-width : 1px;
                background-color : white;
            }
        `;
        progressBarContainer.appendChild(styleTag);
    }


    createBlocks(progressBar, scaleStart, scaleEnd){
        for(var x = scaleStart; x <= scaleEnd; x++){
            progressBar.innerHTML += `<span class="ratingBlock"></span>`;
        }
        return progressBar;
    }

    createBarColor(ratingValue, scaleEnd, lowRatingColor, highRatingColor){
        var redStepAmount = this.getStepAmount(lowRatingColor.red, highRatingColor.red, scaleEnd);
        var greenStepAmount = this.getStepAmount(lowRatingColor.green, highRatingColor.green, scaleEnd);
        var blueStepAmount = this.getStepAmount(lowRatingColor.blue, highRatingColor.blue, scaleEnd);
        var red = lowRatingColor.red - (redStepAmount * ratingValue);
        var green = lowRatingColor.green + (greenStepAmount * ratingValue);
        var blue = lowRatingColor.blue - (blueStepAmount * ratingValue);
        return { red, green, blue };
    }

    getStepAmount(startColor, endColor, maxSteps){
        var colorStep = 0;
        if(startColor > endColor){ colorStep = (startColor - endColor) / maxSteps; }
        else if(startColor < endColor){ colorStep = (endColor - startColor) / maxSteps; }
        return colorStep;
    }

    hexToRgb(hex) {
        hex = hex.replace(/^#/, '');
        const bigint = parseInt(hex, 16);
        var red = (bigint >> 16) & 255;
        var green = (bigint >> 8) & 255;
        var blue = bigint & 255;
        return { red, green, blue };
    }

    //Make this custom element avalible to the dom
}
window.customElements.define("progress-bar", ProgressBar);