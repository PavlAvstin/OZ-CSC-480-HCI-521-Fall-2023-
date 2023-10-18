"using strict";



export const appendRowData = async(serverData)=>{
    try{
        console.log(serverData);
        debugger;
        var content = await serverData.json();
        console.log(content);
    } catch(error){
        console.log(error);
        alert("There was an error getting data from the server");
    }
}