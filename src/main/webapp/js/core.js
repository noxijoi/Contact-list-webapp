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
    };
    this.sendPOST = function (body) {
        var url = location.pathname + "contacts/" + location.hash.slice(1);
        return fetch(url, {
            method: "POST",
            body: JSON.stringify(body)
        });
    };
    this.sendFormDataPOST = function (formData) {
        var url = location.pathname + "contacts/" + location.hash.slice(1);
        return fetch(url, {
            method: "POST",
            body: formData
        });
    };
    this.sendPUT = function (body) {
        var url = location.pathname + "contacts/" + location.hash.slice(1);
        return fetch(url, {
            method: "PUT",
            body: JSON.stringify(body)
        });
    };
    this.sendDELETE = function (body) {
        var url = location.pathname + "contacts/" + location.hash.slice(1);
        return fetch(url, {
            method: "DELETE",
            body: JSON.stringify(body)
        });
    }


}


//Controller получает с вьюхи комманды отправляет запросы куда надо
function Controller() {
    this.hashPatterns = {
        contactsPage: /page\/\d$/,
        addContactPage: /add$/,
        addPhoneModal: /\d\/addphone$/,
        addAttachModal: /\d\/addattach$/,
        mailToPage: /mailto$/,
        editContactPage: /\d$/,
        phonePage: /\d\/phone[\/\d]{0,1}$/,
        attachPage: /\d\/attach[\/\d]{0,1}$/,
    }

    this.getFunction = function (key) {
        switch (key) {
            case "addPhoneModal": return this.addPhoneModal;
            case "addAttachModal": return this.addAttachModal;
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

    this.addPhoneModal = function () {
        view.openPhoneModal();
    }

    this.addAttachModal = function () {
        view.openAttachModal();
    }

    this.mailToPage = function () {
        view.renderWorkArea(TEMPLATE_NAMES.mailToMain);
        view.renderSidenav(TEMPLATE_NAMES.mailToSettings);
        view.addListenersForMailForm()
    }

    this.editContactPage = function () {
        this.communicator.sendGET()
            .then(response => {
                return response.json();
            })
            .then(data => {
                view.renderWorkArea(TEMPLATE_NAMES.editContact, data);
                var sideData = {};
                sideData.ownerId = data.id;
                view.renderSidenav(TEMPLATE_NAMES.phonesAttachBar, sideData);
                view.addListenersForEditContactForm();
            })
            ;

    }
    //TODO
    this.phonePage = function () {
    }
    //TODO
    this.attachPage = function () {
    }
    this.toMainPage = function () {
        location.hash = router.startHash;
    }

    this.addContactToDB = function () {
        var contact = view.collectContactData();
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
        var phone = view.collectPhoneData();
        if (phone) {
            communicator.sendPOST(phone)
                .then(response => {
                    console.log(response);
                })
        }
    }
    
    this.addAttach = function () {
        var attachData = new FormData(document.getElementById("attach-form"));
        if (attachData) {
            communicator.sendFormDataPOST(attachData)
            .then(response => {
                console.log(response);
            })
        }


    }
    this.editContact = function () {
        var contact = view.collectContactData();
        if (contact) {
            communicator.sendPUT(contact)
                .then(response => {
                    console.log(response);
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
