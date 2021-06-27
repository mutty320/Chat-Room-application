// /*
//
//
//     function getLoggedInUsers() {
//
//     let name = document.getElementById("name").value;
//     console.log(name);
//
//     fetch("/getConnectedUsers?name=" + name, {//the name is necessary for updating timestamp
//
//     headers: {
//     'Content-Type': 'application/json'
// }
// })
//     .then(res => res.json())
//     .then(resp => {
//     if (resp.length === 0) {
// } else {
//     document.getElementById("connected-users").innerHTML = "";
//     res = "";
//     for (el of resp) {
//     res = res + "<br/>" + el;//.name;
//     document.getElementById("connected-users").innerHTML = res;
// }
//     ;
// }//close else
//     //document.getElementById("connected-users").innerHTML = res;
// })
//
//     .catch(e => {
//     document.getElementById("connected-users").innerHTML = "Some error occured!";
// });
//
//     setTimeout(getLoggedInUsers,10000);
// };
//
//     function getMessages() {
//
//
//     var i =    document.getElementById("id").value;//index for the profile pic
//     var user = document.getElementById("name").value;
//
//     fetch("/getMessages", {
//
//     headers: {
//     'Content-Type': 'application/json'
// }
// })
//     .then(res => res.json())
//     .then(resp => {
//     if (resp.length === 0) {
// } else {
//     res = "";
//     document.getElementById("users").innerHTML = "";
//
//     for (el of resp.reverse()) {
//
//
//     // i=(index%5);
//     //i=(el.pngIndex%5)+1;
//     if(el.name==user)
//     i=i%5;
//     else{
//     i = (el.id % 5);
//     user=el.name;}
//     var link = "http://nicesnippets.com/demo/man0" + i + ".png";
//
//     document.getElementById("users").innerHTML += newMessage(el.name, el.chat, link, i);
//
// }
//     ;
// }//close else
//
// })
//     .catch(e => {
//     document.getElementById("message").innerHTML = "Some error occured!";
// });
//     setTimeout(getMessages,10000);
//
// };
//
//     document.addEventListener('DOMContentLoaded', function () {
//     getLoggedInUsers();
//     getMessages();
//     validateInput()
//
// });
//
//
//     function newMessage(name, chat, link, index) {
//
//     console.log("index= " + index % 2 + " ");
//     let oddMessage =
//     "<ul class='p-0'>" +
//     "<li>" +
//     "<div class='row comments mb-2'>" +
//     "<div class = 'col-md-2 col-sm-2 col-3 text-center user-img'>\n" +
//     " <img id='profile-photo' src =" + link + " className='rounded-circle'/>\n" +
//     "</div>" +
//     "<div class='col-md-9 col-sm-9 col-9 comment rounded mb-2'>\n" +
//     "<h4 class='m-0'>" +
//     "<a href='#' >" + name + "</a>" +
//     "</h4>" +
//     "<p class='mb-0 text-white'  >" + chat + " </p>" +
//     "</div>" +
//     "</div>" +
//     "</li>" +
//     "</ul>";
//
//     let evenMessage =
//     "<ul class='p-0'>" +
//     "<li>" +
//     "<div class='row comments mb-2'>" +
//     "<div class = 'col-md-2 offset-md-2 col-sm-2 offset-sm-2 col-3 offset-1 text-center user-img'>\n" +
//     " <img id='profile-photo' src =" + link + " className='rounded-circle'/>\n" +
//     "</div>" +
//     "<div class='col-md-7 col-sm-7 col-8 comment rounded mb-2'>\n" +
//     "<h4 class='m-0'>" +
//     "<a href='#'> " + name + "</a>" +
//     "</h4>" +
//     "<p class='mb-0 text-white'>" + chat + " </p>" +
//     "</div>" +
//     "</div>" +
//     "</li>" +
//     "</ul>";
//
//
//     if (index % 2 == 1)
//     return oddMessage;
//
//     return evenMessage;
//
//
// };
//
//     function validateInput() {
//     document.getElementById("button").addEventListener("click", function (event) {
//         let chat = document.getElementById("chat").value.trim();
//
//         if (chat === "")
//             event.preventDefault()
//         return;
//     });
// }
//
// */
