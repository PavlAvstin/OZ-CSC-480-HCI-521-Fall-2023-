function getAccountData(newAccount){
  accountData = [];
  if(newAccount === "false"){
      accountData.push(document.getElementById("username").value);
      accountData.push(document.getElementById("password").value);
  } 
  else if(newAccount === "true"){
      accountData.push(document.getElementById("username").value);
      accountData.push(document.getElementById("password").value);
      accountData.push(document.getElementById("passwordMatch").value);
  }
  return accountData;
}


function checkForErrors(currentValues){
  emptyInput = false;
  for(x=0; x < currentValues.length; x++){
      if(currentValues[x] === ""){
          emptyInput = true
      }
  }
  return emptyInput;
}


function toggleCreateAccount(){

}

function fetchPost(url, fetchData){
  fetch(url,{
      mode : "cors",
      method : "post",
      headers:{
          "Content-Type" : "application/json"
      },
      body : JSON.stringify(fetchData)
  })
  .then((serverData)=>{
      "do stuff"
  });
  
}


function formatFetchData(arrayData, arrayKeys){
  jsonData = {}
  for(x=0; x < arrayData.length; x++){
      jsonData += `${arrayKeys[x]}:${arraData[x]}`
  }
}