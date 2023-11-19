"use strict";

import * as Tools from "./tools.js";

/**
 * Vertically center elm on the y axis
 * @param {HTMLElement} elmsToAlign 
 */
export const verticalCenterToWindowHeight = (elmsToAlign)=>{
    for(let x=0; x < elmsToAlign.length; x++){
        var containerHeight = window.innerHeight;
        var elmHeight = elmsToAlign[x].clientHeight;
        var elmMove = (containerHeight - elmHeight)/2;
        elmsToAlign[x].style.position = `relative`;
        elmsToAlign[x].style.top = `${elmMove}px`;
    }
}


/**
 * Vertically center elm to its parent container
 * @param {HTMLElement} elmsToAlign 
 */
export const verticalCenterToParentHeight = (elmsToAlign)=>{
    for(let x=0; x < elmsToAlign.length; x++){
        var containerHeight = elmsToAlign[x].parentNode.clientHeight;
        var elmHeight = elmsToAlign[x].clientHeight;
        var elmMove = (containerHeight - elmHeight)/2;
        elmsToAlign[x].style.position = `relative`;
        elmsToAlign[x].style.top = `${elmMove}px`;
    }
}


/**
 * Center elm to window on the x axis
 * @param {HTMLElement} elmsToAlign 
 */
export const horizontalCenterToWindowWidth = (elmsToAlign)=>{
    for(let x=0; x < elmsToAlign.length; x++){
        if(elmWidth !== 0){ 
            var containerWidth = window.innerWidth;
            var elmWidth = elmsToAlign[x].scrollWidth;
            var elmMove = (containerWidth - elmWidth) / 2;
            elmsToAlign[x].style.position = `relative`;
            elmsToAlign[x].style.marginLeft = `${elmMove}px`;
        }
    }
}


/**
 * Show a message in the top marginLeft hand corner with an animation
 * @param {string} message 
 */
export const alertAnimation = (message)=>{
    var alertContainer = document.getElementById("alertContainer");
    let newAlert = Tools.createElm("div", null, "class", "alertMessage customShadow brAll plXSM ptSM pbSM");
    alertContainer.appendChild(newAlert);
    newAlert.animate(
        [
            { opacity : 0, width : "0%", marginLeft : "100%" },
            { opacity : .1 },
            { opacity : .2, width : "4%", marginLeft : "96%"},
            { opacity : 1, width : "100%", marginLeft : "0%" },
        ], 
        { 
            duration : 400,
            fill : "forwards"
        }
    );
    setTimeout(()=>{ newAlert.innerText = message; }, 350)
    fadeAndDelete(newAlert);
}


/**
 * Allow the alert message to display for a time and remove it
 * @param {HTMLElement} newAlert 
 */
function fadeAndDelete(newAlert){
    setTimeout(() => {
        newAlert.animate(
            [
                { opacity : 1, width : "100%", marginLeft : "0%" },
                { opacity : .2, width : "4%", marginLeft : "96%"},
                { opacity : .1 },
                { opacity : 0, width : "0%", marginLeft : "100%" },
            ], 
            { 
                duration : 400,
                fill : "forwards"
            }
        );
        setTimeout(()=>{ newAlert.innerText = ""; newAlert.remove(); }, 50);
    }, 3000);
}