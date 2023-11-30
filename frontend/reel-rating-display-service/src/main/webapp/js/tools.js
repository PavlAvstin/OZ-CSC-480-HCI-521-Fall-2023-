"using strict";

import { GlobalRef } from "./globalRef.js";
const globals = new GlobalRef();

/**
 * 
 * @param {object[]} arrayData 
 * @param {object[]} arrayKeys 
 * @returns {JSON}
 */
export const formatJSONData = (arrayKeys, arrayData)=>{
    var jsonString = "";
    for(var x=0; x < arrayData.length; x++){
        if((x+1) !== arrayData.length){
            jsonString += `"${arrayKeys[x]}":"${arrayData[x]}",`;
        }
        else{
            jsonString += `"${arrayKeys[x]}":"${arrayData[x]}"`;
        }
    }
    var jSessionID = sessionStorage.getItem("JSESSIONID");
    if(jSessionID !== undefined && jSessionID !== null){ jsonString += `"JSESSIONID":"${jSessionID}"`; }
    
    return `{${jsonString}}`; //Add the wrapping {} to complete the json object and return it
}


export const clearErrors = ()=>{
    var allErrors = document.getElementsByClassName("error");
    for(var x=0; x < allErrors.length; x++){
        allErrors[x].classList.add("hidden");
    }
}


export const getEndOfURL = ()=>{
    var currentURL = window.location.pathname;
    var pathSegs = currentURL.split("/");
    return pathSegs[pathSegs.length - 1];
}


export const navToHome = async(serverRes)=>{
    if(serverRes.status === 200){
        const jSessionId = await serverRes.text();
        sessionStorage.setItem("JSESSIONID", jSessionId.split(",")[1]);
        window.location.href = globals.homeLocation;
    } else {
        alert(`There was an error going to home page\nserver status ${serverRes.status}`);
    }
}


export const createElm = (elmType, textData, attributeType, attributeString)=>{
    try{
        var newElm = document.createElement(elmType);
        if(typeof textData === "string"){ newElm.innerText = textData; }
        if(typeof attributeType === "string" && typeof attributeString === "string"){
            newElm.setAttribute(`${attributeType}`, `${attributeString}`);
        }
        else if(typeof attributeType === "object" && typeof attributeString === "object"){
            for(var x=0; x < attributeType.length; x++){
                newElm.setAttribute(`${attributeType[x]}`, `${attributeString[x]}`);
            }
        }
        return newElm;
    }catch(error){
        console.log(`There was an error in createElm()\n${error}`);
    }
    
}


export const randomString = () =>{
    const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    let randomString = '';
  
    for (let i = 0; i < 10; i++) {
      const randomIndex = Math.floor(Math.random() * characters.length);
      randomString += characters.charAt(randomIndex);
    }
  
    return randomString;
}


export const randomNum = ()=>{
    return Math.random() * (10 - 0) + 0;
}

/**
 * 
 * @returns {JSON}
 */
export const getJSessionId = () => {
    let JSESSIONID = sessionStorage.getItem("JSESSIONID");
    if (JSESSIONID === null) {
        window.location.href = globals.indexLocation;
    }

    let jsonObject = {JSESSIONID};
    return JSON.stringify(jsonObject);
}


export const clearChildren = (container)=>{
    while (container.firstChild) {
        container.removeChild(container.firstChild);
    }
}



