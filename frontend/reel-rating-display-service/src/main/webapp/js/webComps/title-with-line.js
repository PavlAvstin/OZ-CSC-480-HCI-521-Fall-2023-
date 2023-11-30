"using strict;"

class TitleWithLine extends HTMLElement{
    constructor(){
        super();
        
        //Needed Elms
        var titleWithLineContainer = document.createElement("div");
        titleWithLineContainer.setAttribute("id", "titleWithLineContainer");
        
        this.attachShadow({ mode : "open" });//Append the shadow root
        this.shadowRoot.appendChild(titleWithLineContainer.cloneNode(true));
        var titleWithLineContainer = this.shadowRoot.getElementById("titleWithLineContainer");
        titleWithLineContainer.innerHTML = `<div id="title">${title}</div><div id="line" class="vcToParent"></div>`;
        var title = this.shadowRoot.getElementById("title");
        
        
        title.innerText = this.getAttribute("title");
        var lineColorOne = this.getAttribute("lineColorOne");
        var lineColorTwo = this.getAttribute("lineColorTwo");
        var fontSize = this.getAttribute("fontSize");

        titleWithLineContainer.innerHTML += `
            <style>
                :root{
                    --xlPX : 60px;
                    --lgPX : 37px;
                    --medPX : 23px;
                    --smPX : 14px;
                    --xsPX : 9px;
                    --xxsPX : 5px;
                }
                #titleWithLineContainer{ width : 100%; display : flex; }
                //#titleWithLineContainer > div{ display : inline-block; }
                #title{ color : white; font-size : ${fontSize};}
                #line{ 
                    background : linear-gradient(
                        180deg,
                        ${lineColorOne} 0%, 
                        ${lineColorTwo} 47.4%, 
                        ${lineColorOne} 100%
                    );
                    margin-left : 10px;
                    height : 5px;
                    border-radius : 15px;
                    box-shadow: 0px 2px 2px 0px rgba(0, 0, 0, 0.50);;
                }
            </style>
        `;
    }

    setLineSize(titleWithLineContainer, title, line){
        var overallWidth = Number(titleWithLineContainer.clientWidth);
        var titleWidth = Number(title.clientWidth) + 10;
        title.width = titleWidth; //This is to fix the "off by one px" problem forcing the 
        var newLineSize = overallWidth - titleWidth - 10;
        line.style.width = `${newLineSize}px`;
    }

    verticalCenterToParentHeight(elmToAlign){
        var containerHeight = elmToAlign.parentNode.clientHeight;
        var elmHeight = elmToAlign.clientHeight;
        var elmMove = (containerHeight - elmHeight)/2;
        elmToAlign.style.position = `relative`;
        elmToAlign.style.top = `${elmMove}px`;
    }

    connectedCallback(){
        var titleWithLineContainer = this.shadowRoot.getElementById("titleWithLineContainer");
        var title = this.shadowRoot.getElementById("title");
        var line = this.shadowRoot.getElementById("line");
        setInterval(()=>{
            this.setLineSize(titleWithLineContainer, title, line);
            this.verticalCenterToParentHeight(line);
        }, 200);
    }
    setTitle(newTitle){
        this.shadowRoot.getElementById("title").innerText = newTitle;
    }

    //Make this custom element avalible to the dom
}
window.customElements.define("title-with-line", TitleWithLine);