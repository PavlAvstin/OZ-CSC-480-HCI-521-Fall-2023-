

/**
 * Use fetch to send a Post request to the server
 * If the response is 200 run the callback
 * @param {string} url 
 * @param {object} jsonData 
 * @param {object} callbackFunc 
 */
export function fetchPost(url, jsonData, callbackFunc){
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


/**
 * Use fetch to send a Get request to the server
 * If the response is 200 run the callback
 * @param {string} url 
 * @param {object} callbackFunc 
 */
export function fetchGet(url, callbackFunc){
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


/**
 * Open the WebSocket at the url and append the listeners 
 * @param {string} url 
 * @return Object
 */
export function openWebSocket(url){
    var webSocket = new WebSocket(url);
    webSocket.onopen = ()=>{console.log(`WebSocket connection to : ${url}`)};

    webSocket.onmessage = (event)=>{
        console.log(`There was a message from the server of \n${event.data}`);
    };

    webSocket.onclose = (event)=>{
        if (event.wasClean) {
            console.log(`WebSocket closed cleanly, code=${event.code}, reason=${event.reason}`);
        } else {
            console.error('WebSocket connection died');
        }
    };

    // An error occurred.
    webSocket.onerror = (error)=>{
        console.error(`WebSocket error: ${error.message}`);
    };

    return webSocket;
}


/**
 * Use the current websocket to send a message to the server
 * @param {Object} currentWebSocket 
 * @param {string} message 
 */
export function sendWebSocketMessage(currentWebSocket, message){
    if (currentWebSocket && currentWebSocket.readyState === WebSocket.OPEN) {
        currentWebSocket.send(message);
    } else {
        console.error('WebSocket is not open');
    }
}