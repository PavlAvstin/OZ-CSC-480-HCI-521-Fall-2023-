"use strict";

export const verticalCenterToWindowHeight = (elmsToAlign)=>{
    for(let x=0; x < elmsToAlign.length; x++){
        var containerHeight = window.innerHeight;
        var elmHeight = elmsToAlign[x].clientHeight;
        var elmMove = (containerHeight - elmHeight)/2;
        elmsToAlign[x].style.position = `relative`;
        elmsToAlign[x].style.top = `${elmMove}px`;
    }
}