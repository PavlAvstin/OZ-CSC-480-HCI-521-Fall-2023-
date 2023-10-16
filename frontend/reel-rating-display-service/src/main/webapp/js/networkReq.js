export const fetchPost = (url, jsonData, callbackFunc)=>{
    fetch(url,{
        mode : "cors",
        method : "post",
        headers:{
            "Content-Type" : "application/json",
        },
        body : jsonData
    })
    .then(async(serverData)=>{
        callbackFunc(serverData);
    });   
}