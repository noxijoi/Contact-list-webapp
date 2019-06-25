"use strict"

var dataForMainTable = {
    contacts: [
        {
            id:"1",
            fullName: {
                fName: "Иван",
                lName: "Иванов",
                pName: "Иванович"
            },
            bDate:"10-10-1990",
            address:{
                country: "Belarus",
                city: "Minsk",
                street: "Gikalo",
                house: "12a"
            }
        },
        {
            id:"2",
            fullName: {
                fName: "Петр",
                lName: "Петров",
                pName: "Петрович"
            },
            bDate:"11-10-1980",
            address:{
                country: "Belarus",
                city: "Minsk",
                street: "Malinina",
                house: "10"
            }
        }
    ]
};
var dataForSidenavAdd = {
    phones:[
        {
            phoneId: 1,
            code: "+375",
            operatorCode: "44",
            number: "1111111",
            type:"mobile"
        }, 
        {
            phoneId: 2,
            code: "+375",
            operatorCode: "17",
            number: "2222222",
            type: "home"
        }
    ],
    attachs:[
        {
            attachId:1,
            name: "ЯФайл",
            date: "16-04-2020",
            commemt: "Файл"
        },
        {
            attachId:2,
            name: "ОнФайл",
            date: "16-04-2020"
        }
    ]
}


document.addEventListener('DOMContentLoaded', function(){ 
    if(!location.hash){
        location.hash += "page/1";
        location.search +="size=20";
    }
});


function initEventListenerForTableView(){

    //to open contact
    var tableEl = document.getElementById("main-table");
    var contactCells = tableEl.querySelectorAll(".nameCell");
    contactCells.forEach(function(element){
        element.addEventListener('click', openContact);
    });

    //pagination
    var paginationEl = document.querySelector(".pagination");
    var firstPageEl = paginationEl.querySelector(".first-page");
    firstPageEl.addEventListener('click', toFirstPage);
    var prevPageEl = paginationEl.querySelector(".prev-page");
    prevPageEl.addEventListener('click', toPrevPage);
    var nextPageEl = paginationEl.querySelector(".next-page");
    nextPageEl.addEventListener('click', toNextPage);
    var lastPageEl = paginationEl.querySelector(".last-page");
    lastPageEl.addEventListener('click', toLastPage);

    //tablebuttons
    var addButton = document.querySelector(".add-button");
    addButton.addEventListener('click', addContact);
    var deleteButton = document.querySelector(".delete-button");
    deleteButton.addEventListener('click',deleteChoosen)
    var mailtoButton = document.querySelector(".mailto-button");
    mailtoButton.addEventListener('click',mailtoChoosen);

    var searchButton = document.getElementById("paramSearchButton");
    searchButton.addEventListener('click', searchWithParams);


    function openContact(event){
        var parentRow = event.target.parentNode;
        var checkBoxEl = parentRow.querySelector(".checkContact");
        var id = checkBoxEl.value;
    }
    
    
    function toFirstPage(){
    
    }
    function toPrevPage(){
    
    }
    function toNextPage(){
    
    }
    function toLastPage(){
    
    }
    
    
    
    function addContact(){
    
    }
    function deleteChoosen(){
    
    }
    function mailtoChoosen(){
    
    }
    
    function searchWithParams(){
    
    }
}

function initEventListenersForContactView(){
    var okButton = document.querySelector(".contact-ok");
    okButton.addEventListener('click', confirmContact);
    var cancelButton = document.querySelector(".contact-cancel");
    cancelButton.addEventListener('click', cancelContact);

    //avatar
    var avatarEl = document.querySelector(".avatar");
    avatarEl.addEventListener('click', editAvatar);

    //phones
    var tablePhoneEl = document.getElementById("phones-table");
    var phoneCells = tablePhoneEl.querySelectorAll(".nameCell");
    phoneCells.forEach(function(element){
        element.addEventListener('click',editPhone);
    });
    var addPhoneButton = document.querySelector(".phonebuttons .add-button");
    addPhoneButton.addEventListener('click', addPhone);
    var deletePhonesButton = document.querySelector(".phonebuttons .delete-button");
    deletePhonesButton.addEventListener('click', deletePhones);


    //attachments
    var tableAttachEl = document.getElementById("attach-table");
    var phoneCells = tableAttachEl.querySelectorAll(".nameCell");
    phoneCells.forEach(function(element){
        element.addEventListener('click',editAttach);
    });
    var addPhoneButton = document.querySelector(".attachbuttons .add-button");
    addPhoneButton.addEventListener('click', addAttach);
    var deletePhonesButton = document.querySelector(".phonebuttons .delete-button");
    deletePhonesButton.addEventListener('click', deleteAttach);

    //modal window initialisation
    //phonemodal
    var phoneModal = document.getElementById("phoneModal");
    var openPhoneModalButton = document.querySelector(".phonebuttons .add-button");
    openPhoneModalButton.addEventListener('click',function(){
        phoneModal.style.display ="block";
    })
    var closePhoneModalButtons = phoneModal.querySelectorAll(".close-modal");
    closePhoneModalButtons.forEach(function(btn){
        btn.addEventListener('click', function(){
            phoneModal.style.display="none"
        });
    });
    //attachmentmodal
    var attachModal = document.getElementById("attachModal");
    var openAttachModalButton = document.querySelector(".attachbuttons .add-button");
    openAttachModalButton.addEventListener('click',function(){
        attachModal.style.display ="block";
    })
    var closeAttachModalButtons = attachModal.querySelectorAll(".close-modal");
    closeAttachModalButtons.forEach(function(btn){
        btn.addEventListener('click',function(){
             attachModal.style.display="none"
        });
    });



    function editAvatar(){}

    function editPhone(){}
    function deletePhones(){}
    function addPhone(){}
     
    function editAttach(){}
    function deleteAttach(){}
    function addAttach(){}

    function confirmContact(){

    }

    function cancelContact(){

    }
}






