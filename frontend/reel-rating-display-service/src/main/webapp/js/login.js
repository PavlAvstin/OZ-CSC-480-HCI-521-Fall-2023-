"using strict";


export const getAccountData = (newAccount)=>{
    if(newAccount === "false"){
        var username = document.getElementById("username");
        var password = document.getElementById("password");
        return { username, password };
    } 
    else if(newAccount === "true"){
        var username = document.getElementById("username");
        var password = document.getElementById("password");
        var passwordMatch = document.getElementById("passwordMatch");
        return { username, password, passwordMatch };
    }
}


export const getEmptyErrorMessages = (newAccount)=>{
    if(newAccount === "false"){
        var usernameError = document.getElementById("userNameError");
        var passwordEmptyError = document.getElementsByClassName("passwordErrorEmpty")[0];
        return { usernameError, passwordEmptyError };
    } 
    else if(newAccount === "true"){
        var usernameError = document.getElementById("userNameError");
        var passwordEmptyError = document.getElementsByClassName("passwordErrorEmpty")[0];
        var passwordMatchEmptyError = document.getElementsByClassName("passwordErrorEmpty")[1];
        return { usernameError, passwordEmptyError, passwordMatchEmptyError };
    }
}


export const checkForEmptyInputs = (currentInputs, currentErrorMessages, newAccount)=>{
    var emptyInput = false;
    if(currentInputs.username.value === ""){
        emptyInput = true; //Set empty input to make sure no data is sent to the server
        currentErrorMessages.usernameError.classList.remove("hidden");
    }

    if(currentInputs.password.value === ""){
        emptyInput = true;
        currentErrorMessages.passwordEmptyError.classList.remove("hidden");
    }

    if(newAccount === true){
        if(currentInputs.passwordMatch.value === ""){
            emptyInput = true;
            currentErrorMessages.passwordMatchEmptyError.classList.remove("hidden");
        }
    }
    
    return emptyInput;
}


export const checkForPasswordMatch = (currentInputs, currentMatchErrors)=>{
    var passwordMatch = true;
    if(currentInputs.password.value !== currentInputs.passwordMatch.value){
        passwordMatch = false; //Return false to make sure data is not sent to the server
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


export const checkVaildPassword = (password)=>{
    var passwordNotVaildContainer = document.getElementById("passwordNotValid");
    var error = false;
    var errorHtml = "";

    //At the moment we are only checking for one condidition
    //However in the future there will be more
    //When this happens append a new div to the string
    if(password.length < 8){
        errorHtml += "<div>Password length must be 8 or more</div>";
        error = true;
    }

    if (error === true){
        passwordNotVaildContainer.innerHTML = errorHtml;
        passwordNotVaildContainer.classList.remove("hidden");
    }

    return error;
}