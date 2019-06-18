"use strict"
var START_LOCATION;
var dataForMainTable = {
    isTableView: "true",
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
    isContactForm:"true",
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
    START_LOCATION = document.location;
    render("main", dataForMainTable);
    render("sidenav",{isTableView:true});
    initRouter();
});

function openAddForm(){
    render("main", {isContactForm:true});
    render("sidenav", dataForSidenavAdd);
}

function initRouter(){
    window.addEventListener('beforeunload', function(){

    })
}
