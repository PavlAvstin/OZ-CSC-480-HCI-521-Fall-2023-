"using strict";

export const getAccountData = (newAccount)=>{
    var accountData = [];
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


export const getEmptyErrorMessages = (newAccount)=>{
    var errorMessages = [];
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

export const checkForEmptyInputs = (currentInputs, currentErrorMessages)=>{
    var emptyInput = false;
    for(var x=0; x < currentInputs.length; x++){
        if(currentInputs[x].value === ""){
            emptyInput = true
            currentErrorMessages[x].classList.remove("hidden");
        }
    }
    return emptyInput;
}


export const checkForPasswordMatch = (currentInputs, currentMatchErrors)=>{
    var passwordMatch = true;
    if(currentInputs[1].value !== currentInputs[2].value){
        passwordMatch = false;
        currentMatchErrors[0].classList.remove("hidden");
        currentMatchErrors[1].classList.remove("hidden");
    }
    return passwordMatch;
}


export const toggleCreateAccount = (submitButton)=>{
    var passwordMatchContainer = document.getElementById("passwordMatchContainer");
    if(passwordMatchContainer.classList.contains("hidden")){
        passwordMatchContainer.classList.remove("hidden");
        submitButton.setAttribute("newAccount","true");
        submitButton.innerText = "Create!";
    }
    else if(passwordMatchContainer.classList.contains("hidden") === false){
        passwordMatchContainer.classList.add("hidden");
        submitButton.setAttribute("newAccount","false");
        submitButton.innerText = "Sign in";
    }
}