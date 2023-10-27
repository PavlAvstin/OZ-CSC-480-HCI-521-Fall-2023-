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


export const verticalCenterToParentHeight = (elmsToAlign)=>{
    for(let x=0; x < elmsToAlign.length; x++){
        var containerHeight = elmsToAlign[x].parentNode.clientHeight;
        var elmHeight = elmsToAlign[x].clientHeight;
        var elmMove = (containerHeight - elmHeight)/2;
        elmsToAlign[x].style.position = `relative`;
        elmsToAlign[x].style.top = `${elmMove}px`;
    }
}


export const horizontalCenterToWindowWidth = (elmsToAlign)=>{
    for(let x=0; x < elmsToAlign.length; x++){
        if(elmWidth !== 0){ 
            var containerWidth = window.innerWidth;
            var elmWidth = elmsToAlign[x].scrollWidth;
            var elmMove = (containerWidth - elmWidth) / 2;
            elmsToAlign[x].style.position = `relative`;
            elmsToAlign[x].style.left = `${elmMove}px`;
        }
    }
}