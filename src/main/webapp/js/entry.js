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
    }
});







