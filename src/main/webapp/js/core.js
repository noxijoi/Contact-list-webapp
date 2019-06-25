const PAGE_SIZE = 20;
const TEMPLATE_NAMES = {
    editContact: "editContactTemplate",
    contactsTable: "contactsTableTempate",
    searchBar: "searchBarTemplate",
    phonesAttachBar: "phonesAttachBarTemplate",
    mailToMain: "mailToMainTemplate",
    mailToSettings: "mailToSettingsTemplate",
    empty: "emptyTemplate"
}
var communicator = new Communicator();
var controller = new Controller();
var view = new View(controller);
var router = new Router(controller);
router.init();


//Communicator общается с сервером
function Communicator() {
    this.sendGET = function () {
        var url = location.pathname + "contacts/" + location.hash.slice(1) + location.search;
        return fetch(url, { method: "GET" });
    }
    this.sendPOST = function (body) {
        var url = location.pathname + "contacts/" + location.hash.slice(1);
        return fetch(url, {
            method:"POST",
            body: JSON.stringify(body)
        });
    }
    this.sendPUT = function () {

    }
    this.sendDELETE = function () {

    }

}


//Controller получает с вьюхи комманды отправляет запросы куда надо
function Controller() {
    this.hashPatterns = {
        contactsPage: /page\/\d/,
        addContactPage: /add/,
        mailToPage: /mailTo/,
        editContactPage: /\d/,
        phonePage: /\d\/phone[\/\d]{0,1}/,
        attachPage: /\d\/attach[\/\d]{0,1}/,
    }

    this.getFunction = function (key) {
        switch (key) {
            case "contactsPage": return this.contactsPage;
            case "addContactPage": return this.addContactPage;
            case "mailToPage": return this.mailToPage;
            case "editContactPage": return this.editContactPage;
            case "phonePage": return this.phonePage;
            case "attachPage": return this.attachPage;
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
                view.renderWorkArea(TEMPLATE_NAMES.contactsTable, data);
                view.renderSidenav(TEMPLATE_NAMES.searchBar, data);
            });
    }

    this.addContactPage = function () {
        view.renderWorkArea(TEMPLATE_NAMES.editContact);
        view.addListenersForContactForm();
        view.renderSidenav(TEMPLATE_NAMES.empty);
    }
    this.mailToPage = function () {
        view.renderWorkArea(TEMPLATE_NAMES.mailToMain);
        view.renderSidenav(TEMPLATE_NAMES.mailToSettings);
    }
    this.editContactPage = function () {
        this.communicator.sendGET()
            .then(response =>{
                return response.json();
            })
            .then(data =>{
                view.renderWorkArea(TEMPLATE_NAMES.editContact, data);
                view.renderSidenav(TEMPLATE_NAMES.phonesAttachBar, data);
                view.addListenersForEditContactForm();
            })
        ;
        
    }
    this.phonePage = function () {
        view.openPhoneModal();
    }
    this.attachPage = function () {
        view.openAttachModal();
    }
    this.toMainPage = function(){
        location.hash = router.startHash;
    }

    this.addContactToDB = function(){
        var contact = view.collectContactData();
        if(contact){
            communicator.sendPOST(contact)
            .then(response =>{
                return response.json();
            })
            .then(data =>{
                console.log(data);

            });
        }
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
            paramsString += key + "=" + params[key] + '&';
        }
        location.search = '?' + paramsString;
    }
}

//Validator
var validator = {

}
