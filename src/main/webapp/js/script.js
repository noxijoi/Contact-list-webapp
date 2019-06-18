var PAGE_SIZE = 15;
var START_URL;
//
document.DOMContentLoaded = function(){
    downloadPage(PAGE_SIZE,0);
    START_URL = location.href;
}

//URL manipulation
function changeURL(URLQuery){
    var newURL = START_URL + URLQuery;
    location.href = newURL;
}

//pagination
function downloadPage(pageSize, lastId){
    var xhr = new XMLHttpRequest();
    var queryObj = {};
    fillTable();
}

function firstPage() {

}

function prevPage(){

}

function nextPage(){

}

function lastPage(){

}

//addContact

function addContact(){
    var contact = new Contact();
    collectContactData(contact);
    var arr = new Array();
    arr.put(contact);
    var dto = new PostDTO("contact", arr);
    var json = JSON.parse(dto);


    var xhr = new XMLHttpRequest();
    xhr.open("POST","", true);
    xhr.send(json);

    xhr.onreadystatechange = function () {
        if(xhr.readyState != 4){
            return;
        }
         if(xhr.status !=200){
             alert(xhr.statusText);
             return;
         }
    }
}

function collectContactData(contactObj){
    var fullName = new FullName();
    fullName.firstName = document.getElementById("first-name-input").value;
    fullName.lastName = document.getElementById("last-name-input").value;
    fullName.parentName = document.getElementById("parent-name-input").value;

    contactObj.birthDate = document.getElementById("birth-date-input").value;
    contactObj.sex = document.querySelector('input[name="sex"]:checked').value;
    contactObj.company = document.getElementById("company-input").value;
    contactObj.maritalStatus = document.getElementById("marital-status-input").options[sel.selectedIndex].value;
    contactObj.website = document.getElementById("web-site-input").value;
    contactObj.email = document.getElementById("email-input");

    var address = new Address();
    address.city = document.getElementById("city-input").value;
    address.country = document.getElementById("country-input").value;
    address.street = document.getElementById("street-input").value;
    address.house = document.getElementById("house_input").value;
    address.index = document.getElementById("index_input").value;

    contactObj.address = address;
}