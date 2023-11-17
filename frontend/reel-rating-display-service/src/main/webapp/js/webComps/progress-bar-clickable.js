"using strict;"


class ProgressBarClickable extends HTMLElement{
    constructor(){
        super();
        this.attachShadow({ mode : "open" });//Append the shadow root
    }

    connectedCallback(){
        //Needed Elms
        var progressBarContainer = document.createElement("div");
        progressBarContainer.setAttribute("id", "progressBarContainer");
        this.shadowRoot.appendChild(progressBarContainer);
        
        
        //Get the attributes that where passed in and create the component
        var labelContainer = document.createElement("div");
        labelContainer.setAttribute("id","labelContainer");
        var ratingName = document.createElement("div");
        ratingName.setAttribute("class","hideOverflow");
        ratingName.innerText = this.getAttribute("ratingName");
        labelContainer.appendChild(ratingName);
        
        var ratingValueDiv = document.createElement("div");
        ratingValueDiv.setAttribute("id","ratingValue");
        var ratingValue = this.getAttribute("ratingValue");
        ratingValueDiv.innerText = ratingValue;
        labelContainer.appendChild(ratingValueDiv);
        progressBarContainer.appendChild(labelContainer);

        var progressBar = document.createElement("div");
        progressBar.setAttribute("id", "progressBar");
        progressBarContainer.appendChild(progressBar);
        progressBar = this.shadowRoot.getElementById("progressBar");
        progressBar.addEventListener("click", (event)=>{ 
            var spanNumber = event.target.getAttribute("spanNumber");
            this.changeRating(spanNumber, scaleEnd, lowRatingColor, highRatingColor); 
            ratingValueDiv.innerText = `${spanNumber}`;
        });
        
        
        var scaleStart = Number(this.getAttribute("scaleStart"));
        var scaleEnd = Number(this.getAttribute("scaleEnd"));
        var lowRatingColor = this.getAttribute("lowRatingColor");
        var highRatingColor = this.getAttribute("highRatingColor");
        lowRatingColor = this.hexToRgb(lowRatingColor);
        highRatingColor = this.hexToRgb(highRatingColor);
        progressBar = this.createBlocks(progressBar, scaleStart, scaleEnd);
        var barColor = this.createBarColor(Number(ratingValue), scaleEnd, lowRatingColor, highRatingColor);
        this.fillProgressBar(progressBar, barColor, Number(ratingValue));
        var styleTag = this.setProgressBarStyle(progressBarContainer, scaleEnd);
        progressBarContainer.appendChild(styleTag);
        
        //These are needed because :last-child was not working for some reason
        progressBar.children[progressBar.children.length - 1].style.borderTopRightRadius = "15px";
        progressBar.children[progressBar.children.length - 1].style.borderBottomRightRadius = "15px";
    }


    fillProgressBar(progressBar, barColor, ratingValue){
        for(var x =0; x < progressBar.children.length; x++){ progressBar.children[x].style.backgroundColor = "white"; }
        for(var x =0; x < ratingValue; x++){ 
            progressBar.children[x].style.backgroundColor = `rgb(${barColor.red}, ${barColor.green}, ${barColor.blue})`; 
        }
    }


    setProgressBarStyle(progressBarContainer, scaleEnd){
        var styleTag = document.createElement("style");
        styleTag.innerHTML += `
            #labelContainer{ display:grid; grid-template-columns: 10fr 2fr; color:white; }
            #ratingValue{ text-align: right; }
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
            .hideOverflow{ overflow-x: hidden; white-space: nowrap; text-overflow: ellipsis;}
        `;
        return styleTag
    }


    createBlocks(progressBar, scaleStart, scaleEnd){
        for(var x = scaleStart; x <= scaleEnd; x++){
            progressBar.innerHTML += `<span class="ratingBlock" spanNumber="${x}"></span>`;
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


    changeRating(spanNumber, scaleEnd, lowRatingColor, highRatingColor){
        var barColor = this.createBarColor(spanNumber, scaleEnd, lowRatingColor, highRatingColor);
        this.setAttribute("ratingValue", spanNumber);
        this.fillProgressBar(
            this.shadowRoot.getElementById("progressBar"), 
            barColor, spanNumber
        );
    }

}
//Make this custom element avalible to the dom
window.customElements.define("progress-clickable", ProgressBarClickable);