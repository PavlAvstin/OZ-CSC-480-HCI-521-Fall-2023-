export const fetchPost = (url, jsonData)=>{
    fetch(url,{
        mode : "cors",
        method : "post",
        headers:{
            "Content-Type" : "application/json",
        },
        body : jsonData
    })
    .then(async(serverData)=>{
        alert(`Response from server at \n${serverData.url}`)
    });   
}