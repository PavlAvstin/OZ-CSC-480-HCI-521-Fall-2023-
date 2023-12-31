"using strict";


/**
 * Get new account data select data needed based on the state of the log in page
 * If new account return an object of username, email, password, and passwordMatch
 * If not return username and password
 * @param {object} newAccount 
 * @returns object
 */
export const getAccountData = (newAccount)=>{
    if(newAccount === "false"){
        var username = document.getElementById("username");
        var password = document.getElementById("password");
        return { username, password };
    } 
    else if(newAccount === "true"){
        var username = document.getElementById("username");
        var email = document.getElementById("email");
        var password = document.getElementById("password");
        var passwordMatch = document.getElementById("passwordMatch");
        return { username, email, password, passwordMatch };
    }
}


/**
 * Get elements that represent empty error messages
 * @param {object} newAccount 
 * @returns object
 */
export const getEmptyErrorMessages = (newAccount)=>{
    if(newAccount === "false"){
        var usernameError = document.getElementById("userNameError");
        var passwordEmptyError = document.getElementsByClassName("passwordErrorEmpty")[0];
        return { usernameError, passwordEmptyError };
    } 
    else if(newAccount === "true"){
        var usernameError = document.getElementById("userNameError");
        var emailError = document.getElementById("emailError");
        var passwordEmptyError = document.getElementsByClassName("passwordErrorEmpty")[0];
        var passwordMatchEmptyError = document.getElementsByClassName("passwordErrorEmpty")[1];
        return { usernameError, emailError, passwordEmptyError, passwordMatchEmptyError };
    }
}


/**
 * Checking for empty inputs. Return a bool of true if an empty input was found
 * @param {object} currentInputs 
 * @param {object} currentErrorMessages 
 * @param {object} newAccount 
 * @returns bool
 */
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


/**
 * Check to see if the passwords match for a new account. Return false if passwords dont match
 * @param {object} currentInputs 
 * @param {object[]} currentMatchErrors 
 * @returns bool
 */
export const checkForPasswordMatch = (currentInputs, currentMatchErrors)=>{
    var passwordMatch = true;
    if(currentInputs.password.value !== currentInputs.passwordMatch.value){
        passwordMatch = false; //Return false to make sure data is not sent to the server
        currentMatchErrors[0].classList.remove("hidden");
        currentMatchErrors[1].classList.remove("hidden");
    }
    return passwordMatch;
}


/**
 * 
 * @param {HTMLDivElement} submitButton 
 * @param {HTMLDivElement} newAccountButton 
 */
export const toggleCreateAccount = (submitButton, newAccountButton)=>{
    var passwordMatchContainer = document.getElementById("passwordMatchContainer");
    var emailContainer = document.getElementById("emailContainer");
    if(passwordMatchContainer.classList.contains("hidden")){
        passwordMatchContainer.classList.remove("hidden");
        emailContainer.classList.remove("hidden");
        submitButton.setAttribute("newAccount","true");
        submitButton.innerText = "Create!";
        newAccountButton.innerText = "Back";
    }
    else if(passwordMatchContainer.classList.contains("hidden") === false){
        passwordMatchContainer.classList.add("hidden");
        emailContainer.classList.add("hidden");
        submitButton.setAttribute("newAccount","false");
        submitButton.innerText = "Sign in";
        newAccountButton.innerText = "New Account";
    }
}


/**
 * Check for password complexity
 * @param {string} password 
 * @param {object} regExSpecChar 
 * @param {object} regExNum 
 * @returns bool
 */
export const checkVaildPassword = (password, regExSpecChar, regExNum)=>{
    var passwordNotVaildContainer = document.getElementById("passwordNotValid");
    var error = false;
    var errorHtml = "";

    if(password.length < 8){
        errorHtml += "<div>Password length must be 8 or more</div>";
        error = true;
    }

    
    if(regExNum.test(password) === false){
        errorHtml += "<div>Need at least one digit in your password</div>";
        error = true;
    }

    if(regExSpecChar.test(password) === false){
        errorHtml += "<div>Need at least one special char in your password</div>";
        error = true;
    }
    if (error === true){
        passwordNotVaildContainer.innerHTML = errorHtml;
        passwordNotVaildContainer.classList.remove("hidden");
    }

    return error;
}


/**
 * Check username to make sure that the string length is between 2 and 15 chars
 * @param {string} userName 
 * @returns bool
 */
export const checkValidUserName = (userName)=>{
    var userNameNotValidContainer = document.getElementById("userNameNotValid");
    var error = false;
    var errorHtml = "";

    //At the moment we are only checking for one condidition
    //However in the future there will be more
    //When this happens append a new div to the string
    if(userName.length < 2 || userName.length > 15){
        errorHtml += "<div>Username must be between 2 and 15 chars</div>";
        error = true;
    }

    if(error === true){
        userNameNotValidContainer.innerHTML = errorHtml;
        userNameNotValidContainer.classList.remove("hidden");
    }

    return error;
}

/**
 * Check for basic email. At the moment there are no numbers nor special chars in the email id
 * @param {string} email 
 * @param {object} regExEmail 
 * @returns bool
 */
export const checkValidEmail = (email, regExEmail)=>{
    var emailNotVaildContainer = document.getElementById("emailNotValid");
    var error = false;
    var errorHtml = "";

    if(regExEmail.test(email) === false){
        errorHtml += "<div>Please enter a vaild email (email@domain.com)</div>";
        error = true;
    }

    if (error === true){
        emailNotVaildContainer.innerHTML = errorHtml;
        emailNotVaildContainer.classList.remove("hidden");
    }

    return error;
}
