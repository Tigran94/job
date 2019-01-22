var today = new Date();
var dd = today.getDate();
var mm = today.getMonth()+1; //January is 0!
var yyyy = today.getFullYear();
if(dd<10){
    dd='0'+dd
}
if(mm<10){
    mm='0'+mm
}

today = yyyy+'-'+ mm +'-'+dd;
today2 = (yyyy + 2) + "-" + mm + "-" + dd;
document.getElementById("currentDate").setAttribute("value",today);
document.getElementById("currentDate").setAttribute("min", today);
document.getElementById("currentDate").setAttribute("max",today2);





