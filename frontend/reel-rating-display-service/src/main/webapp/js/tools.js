"using strict";

export const formatJSONData = (arrayData, arrayKeys)=>{
    var jsonString = "";
    for(var x=0; x < arrayData.length; x++){
        if((x+1) !== arrayData.length){
            jsonString += `"${arrayKeys[x]}":"${arrayData[x].value}",`;
        }
        else{
            jsonString += `"${arrayKeys[x]}":"${arrayData[x].value}"`;
        }
    }
     
    return `{${jsonString}}`; //Add the wrapping {} to complete the json object and return it
}

export const clearErrors = ()=>{
    var allErrors = document.getElementsByClassName("error");
    for(var x=0; x < allErrors.length; x++){
        allErrors[x].classList.add("hidden");
    }
}