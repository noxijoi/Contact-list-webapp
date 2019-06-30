const PAGE_SIZE = 2;
const TEMPLATE_NAMES = {
    editContact: "editContactTemplate",
    contactsTable: "contactsTableTempate",
    searchBar: "searchBarTemplate",
    phonesAttachBar: "phonesAttachBarTemplate",
    mailToMain: "mailToMainTemplate",
    mailToSettings: "mailToSettingsTemplate",
    phoneModal: "phoneModalTemplate",
    phoneRow: "phoneRowTemplate",
    attachModal: "attachModalTemplate",
    attachRow: "attachRowTemplate",
    empty: "emptyTemplate"

}

const databaseId = /\/d+$/;
const tempId     = /\/d+a$/; 

//исходное состояние контакта
var startCotactData = {};
//состояние после всех изменений
var contactData = {};
//загруженные на страницу файлы
var files = {};
//список контактов страницы
var contactPageData = {};
var deletedPhones =[];
var deletedAttachs =[];

var communicator = new Communicator();
var controller = new Controller();
var router = new Router(controller);
router.init();


//Communicator общается с сервером
function Communicator() {
    this.sendGET = function (url) {
        url = url || location.pathname + "contacts/" + location.hash.slice(1) + location.search;
        return fetch(url, { method: "GET" });
    };
    this.sendPOST = function (body, url) {
        url = url || location.pathname + "contacts/" + location.hash.slice(1);
        return fetch(url, {
            method: "POST",
            body: JSON.stringify(body)
        });
    };
    this.sendFormDataPOST = function (formData, url) {
        url = url || location.pathname + "contacts/" + location.hash.slice(1);
        return fetch(url, {
            method: "POST",
            body: formData
        });
    };
    this.sendPUT = function (body, url) {
        url = url || location.pathname + "contacts/" + location.hash.slice(1);
        return fetch(url, {
            method: "PUT",
            body: JSON.stringify(body)
        });
    };
    this.sendDELETE = function (body, url) {
        url = url || location.pathname + "contacts/" + location.hash.slice(1);
        return fetch(url, {
            method: "DELETE",
            body: JSON.stringify(body)
        });
    }


}


//Controller получает с вьюхи комманды отправляет запросы куда надо
function Controller() {
    this.hashPatterns = {
        contactsPage: /page\/\d+$/,
        addContactPage: /add$/,
        mailPage: /mail$/,
        editContactPage: /\d$/,
    }

    this.getFunction = function (key) {
        switch (key) {
            case "contactsPage": return this.contactsPage;
            case "addContactPage": return this.addContactPage;
            case "mailToPage": return this.mailToPage;
            case "editContactPage": return this.editContactPage;
        }
    }


    this.handlePage = function (hash) {
        for (const key in this.hashPatterns) {
            if (this.hashPatterns[key].test(hash)) {
                return this.getFunction(key)();
            }
        }
    }

    this.contactsPage = function () {
        var params = {
            size: PAGE_SIZE
        };
        router.addParams(params);
        communicator.sendGET()
            .then(response => {
                return response.json();
            })
            .then(data => {
                contactPageData = {};
                communicator.list = data.contacts;

                view.renderWorkArea(TEMPLATE_NAMES.contactsTable, data);
                view.renderSidenav(TEMPLATE_NAMES.searchBar, data);

                view.listenerManager.addListenersForTable();
                view.listenerManager.addListenersForSearchForm();
            })
            .catch(e =>{
                alert(e);
            });
    }

    this.editContactPage = function () {
        contactData = {};
        startCotactData = {};
        deletedAttachs = [];
        deletedPhones = [];
        this.communicator.sendGET()
            .then(response => {
                return response.json();
            })
            .then(data => {
                startCotactData = data;
                contactData = data;
                view.renderWorkArea(TEMPLATE_NAMES.editContact, data);
                view.renderSidenav(TEMPLATE_NAMES.phonesAttachBar, data);
                view.listenerManager.addListenersForEditContactForm();
            })
            .catch(e => alert(e));
    }

    this.addContactPage = function () {
        view.renderWorkArea(TEMPLATE_NAMES.editContact);
        view.listenerManager.addListenersForContactForm();
        view.renderSidenav(TEMPLATE_NAMES.empty);
    }

    this.mailToPage = function () {
        var contactsIds = view.dataCollector.collectSelectedContacts();
        var emails = new Array();
        var data = {};
        
        contactPageData.list.forEach(contact =>{
           if(contactsIds.includes(contact.id)){
               emails.push(contact.email);
           }
        });
        data.emails = emails;

        view.renderWorkArea(TEMPLATE_NAMES.mailToMain);
        view.renderSidenav(TEMPLATE_NAMES.mailToSettings, data);
        view.listenerManager.addListenersForMailForm()
    }

    

    this.searchByParams = function(params){
        params.size = PAGE_SIZE;
        router.addParams(params);
        communicator.sendGET()
            .then(response => {
                return response.json();
            })
            .then(data => {
                view.renderWorkArea(TEMPLATE_NAMES.contactsTable, data);
                view.renderSidenav(TEMPLATE_NAMES.searchBar, params);
                view.listenerManager.addListenersForTable();
                view.listenerManager.addListenersForSearchForm();
            });
    }


    this.toMainPage = function () {
        location.hash = router.startHash;
    }


    this.addContactToDB = function () {
        var contact = view.dataCollector.collectContactData();
        if (contact) {
            communicator.sendPOST(contact)
                .then(response => {
                    return response.json();
                })
                .then(data => {
                    console.log(data);
                });
        }
    }

    this.addPhone = function () {
        var phone = view.dataCollector.collectPhoneData();
        if (phone) {
            communicator.sendPOST(phone, location.pathname + "contacts/" +phone.ownerId +"/phone" )
                .then(response => {
                    console.log(response);
                })
        }
    }

    this.addAttach = function () {
        var attachData = new FormData(document.getElementById("attach-form"));
        if (attachData) {
            communicator.sendFormDataPOST(attachData, location.pathname +"contacts/"+ contactData.contactId +"/attach" )
            .then(response => {
                console.log(response);
            })
        }
    }

    this.submitContact = function () {
        var contact = view.dataCollector.collectContactData();
        contact.id = startCotactData.contact.id;
        var phones = contactData.phones;
        phones.forEach(phone => {
            if(phone.id.toString().endsWith("a")){
                phone.id = 0;
                communicator.sendPOST(phone, location.pathname + "contacts/" + contact.id + "/phone");
            } else {
                communicator.sendPUT(phone, location.pathname + "contacts/" + contact.id + "/phone");
            }
        });
        var attaches = contactData.attachs;
        attaches.forEach(attach => {
            if(attach.id.toString().endsWith("a")){
                var formData = formDataFromAttach(attach);
                communicator.sendPOST(formData, location.pathname + "contacts/" + contact.id + "/attach");
            } else{
                communicator.sendPUT(attach, location.pathname + "contacts/" + contact.id + "/attach");
            }
        });
        if(deletedPhones.length > 0){
            communicator.sendDELETE(deletedPhones, location.pathname + "contacts/" + contact.id + "/phone");
        }
        if(deletedAttachs.length > 0){
            communicator.sendDELETE(deletedAttachs, location.pathname + "contacts/" + contact.id + "/attach");
        }
        communicator.sendPUT(contact)
        .then(function(){
            controller.toMainPage();
        })
        
    }

    this.deleteSelectedContact = function(){
        var contacts = view.dataCollector.collectSelectedContacts();
        communicator.sendDELETE(contacts);
        router.handlehash();
    }

    //+-
    this.deleteSelectedAttach = function(){
        var attachId = view.dataCollector.collectSelectedAttach();
        attachId.forEach(id => {
            for (var index = contactData.attachs.length - 1; index >= 0; index--) {
                if(id === contactData.attachs[index]){
                    deletedAttachs.put(contactData.attachs[index]);
                    contactData.attachs.splice(index, 1);
                }   
            }
        });
    }

    //+-
    this.deleteSelectedPhones = function(){
        var phonesId = view.dataCollector.collectSelectedPhones();
        phonesId.forEach(id=>{
            for (var index = contactData.phones.length -1; index >= 0; index--) {
                if(id === contactData.phones[index]){
                    deletedPhones.put(contactData.phones[index]);
                    contactData.phones.splice(index, 1);
                }   
            }
        });
    }
}


//router
function Router(controller) {
    this.startHash = "page/1";
    this.controller = controller;

    this.init = function () {
        addEventListener('hashchange', this.handlehash);
        this.handlehash();
    }


    
    this.handlehash = function () {
        var hash = location.hash.slice(1);
        this.controller.handlePage(hash);
    }

    this.addParams = function (params) {
        var paramsString = "";
        for (const key in params) {
            if(params[key]){
                paramsString += key + "=" + params[key] + '&';
            }
        }
        location.search = '?' + paramsString;
    }

    this.getParams = function(){

    }
}

//Validator
var validator = {

}

function formDataFromAttach(attach){
    var formData = new FormData();
    var file = files[attach.id];
    formData.append("file", file);
    formData.append("comment", attach.comment);
    formData.append("ownerId". attacg.ownerId);

    return formData;
}
