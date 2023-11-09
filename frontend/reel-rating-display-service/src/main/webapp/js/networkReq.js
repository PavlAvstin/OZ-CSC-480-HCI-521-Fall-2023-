export const fetchPost = (url, jsonData, callbackFunc)=>{
    fetch(url,{
        mode : "cors",
        method : "post",
        headers:{
            "Content-Type" : "application/json",
        },
        body : jsonData,
        credentials: "include"
    })
    .then((serverData)=>{
        callbackFunc(serverData);
    });   
}


export const fetchGet = (url, callbackFunc)=>{
    fetch(url,{
        mode : "cors",
        method : "get",
        credentials: "include"
    })
    .then((serverData)=>{
        callbackFunc(serverData);
    }); 
}