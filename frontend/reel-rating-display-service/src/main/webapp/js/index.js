window.onload = ()=>{
    submitButton = document.getElementById("submit");
    submitButton.addEventListener("click", ()=>{
        errors = false;
        passwordMatch = true;
        newAccount = submitButton.getAttribute("newAccount");
        currentAccountData = getAccountData(newAccount);
        currentEmptyErrorMessages = getEmptyErrorMessages(newAccount);
        currentMatchErrors = document.getElementsByClassName("passwordErrorNoMatch");
        clearErrors(currentEmptyErrorMessages);
        clearErrors(currentMatchErrors);


        if(newAccount === "false"){
            errors = checkForEmptyInputs(currentAccountData, currentEmptyErrorMessages);
        }
        else if(newAccount === "true"){
            errors = checkForEmptyInputs(currentAccountData, currentEmptyErrorMessages);
            passwordMatch = checkForPasswordMatch(currentAccountData, currentMatchErrors);
        }

        if(errors === false && passwordMatch === true){
            jsonData = formatFetchData(currentAccountData, ["username","password"]);
            fetchPost(
                "http://localhost:30400/reel-rating-auth-service/auth/login", 
                jsonData
            );
        }
    });

    newAccountButton = document.getElementById("newAccount");
    newAccountButton.addEventListener("click", toggleCreateAccount)
}

function getAccountData(newAccount){
    accountData = [];
    if(newAccount === "false"){
        accountData.push(document.getElementById("username"));
        accountData.push(document.getElementById("password"));
    } 
    else if(newAccount === "true"){
        accountData.push(document.getElementById("username"));
        accountData.push(document.getElementById("password"));
        accountData.push(document.getElementById("passwordMatch"));
    }
    return accountData;
}


function getEmptyErrorMessages(newAccount){
    errorMessages = [];
    if(newAccount === "false"){
        errorMessages.push(document.getElementById("userNameError"));
        errorMessages.push(document.getElementsByClassName("passwordErrorEmpty")[0]);
    } 
    else if(newAccount === "true"){
        errorMessages.push(document.getElementById("userNameError"));
        errorMessages.push(document.getElementsByClassName("passwordErrorEmpty")[0]);
        errorMessages.push(document.getElementsByClassName("passwordErrorEmpty")[1]);
    }
    return errorMessages;
}

function checkForEmptyInputs(currentInputs, currentErrorMessages){
    emptyInput = false;
    for(x=0; x < currentInputs.length; x++){
        if(currentInputs[x].value === ""){
            emptyInput = true
            currentErrorMessages[x].classList.remove("hidden");
        }
    }
    return emptyInput;
}


function checkForPasswordMatch(currentInputs, currentMatchErrors){
    passwordMatch = true;
    if(currentInputs[1].value !== currentInputs[2].value){
        passwordMatch = false;
        currentMatchErrors[0].classList.remove("hidden");
        currentMatchErrors[1].classList.remove("hidden");
    }
    return passwordMatch;
}


function toggleCreateAccount(){
    passwordMatchContainer = document.getElementById("passwordMatchContainer");
    submitButton = document.getElementById("submit");
    if(passwordMatchContainer.classList.contains("hidden")){
        passwordMatchContainer.classList.remove("hidden");
        submitButton.setAttribute("newAccount","true");
    }
    else if(passwordMatchContainer.classList.contains("hidden") === false){
        passwordMatchContainer.classList.add("hidden");
        submitButton.setAttribute("newAccount","false");
    }
}

function fetchPost(url, jsonData){
    fetch(url,{
        mode : "no-cors",
        method : "post",
        headers:{
            "Content-Type" : "application/json",
            // "Access-Control-Allow-Origin" : "http://localhost:30400",
        },
        body : jsonData
    })
    .then((serverData)=>{
        console.log(serverData);
    });   
}


function formatFetchData(arrayData, arrayKeys){
    jsonString = "";
    for(x=0; x < arrayData.length; x++){
        if((x+1) !== arrayData.length){
            jsonString += `"${arrayKeys[x]}":"${arrayData[x]}",`;
        }
        else{
            jsonString += `"${arrayKeys[x]}":"${arrayData[x]}"`;
        }
    }
    jsonData = JSON.stringify(`{${jsonString}}`);
    return jsonData;
}

function clearErrors(errorsArray){
    for(x=0; x < errorsArray.length; x++){
        errorsArray[x].classList.add("hidden");
    }
}