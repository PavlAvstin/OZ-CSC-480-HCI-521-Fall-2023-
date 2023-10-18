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


export const fetchGet = (url, callbackFunc)=>{
    fetch(url,{
        mode : "cors",
        method : "get"
    })
    .then(async(serverData)=>{
        callbackFunc(serverData);
    }); 
}