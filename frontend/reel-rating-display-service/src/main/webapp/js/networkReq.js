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
        if(serverData.status === 200){
            callbackFunc(serverData);
        } else {
            throw `There was an error in getting data at ${url}`
        }
    });   
}


export const fetchGet = (url, callbackFunc)=>{
    fetch(url,{
        mode : "cors",
        method : "get",
        credentials: "include"
    })
    .then((serverData)=>{
        if(serverData.status === 200){
            callbackFunc(serverData);
        } else {
            throw `There was an error in getting data at ${url}`
        }
    }); 
}