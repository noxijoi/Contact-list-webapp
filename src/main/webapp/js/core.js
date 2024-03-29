const PAGE_SIZE = 2;
const TEMPLATE_NAMES = {
    editContact: "editContactTemplate",
    contactsTable: "contactsTableTempate",
    searchBar: "searchBarTemplate",
    phonesAttachBar: "phonesAttachBarTemplate",
    mailToMain: "mailToMainTemplate",
    mailToSettings: "mailToSettingsTemplate",
    phoneModal: "phoneModalTemplate",
    attachModal: "attachModalTemplate",
    attachEditModal: "editAttachModalTemplate",
    empty: "emptyTemplate"

}

const databaseId = /\/d+$/;
const tempId = /\/d+a$/;

//исходное состояние контакта
var startCotactData = {};
//состояние после всех изменений
var contactData = {};
//список контактов страницы
var contactPageData = {};

var params = {};

var selectedContacts = [];
var deletedPhones = [];
var deletedAttachs = [];
var templates = [];

var communicator = new Communicator();
var controller = new Controller();
var router = new Router(controller);
router.init();


//Communicator общается с сервером
function Communicator() {
    this.sendGET = function (url) {
        url = url || location.pathname + "contacts/" + location.hash.slice(1);
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
            case "mailPage": return this.mailPage;
            case "editContactPage": return this.editContactPage;
        }
    }


    this.handlePage = function (hash) {
        for (const key in this.hashPatterns) {
            if (this.hashPatterns[key].test(hash)) {
                return this.getFunction(key)();
            } 
        }
        view.showErr("incorrect link");
    }

    this.contactsPage = function () {
        params.size = PAGE_SIZE;
        var paramStr = router.getParamString(params);
        communicator.sendGET(location.pathname + "contacts/" + location.hash.slice(1) + paramStr)
            .then(response => {
                return response.json();
            })
            .then(data => {
                contactPageData = {};
                contactPageData = data;
                data.contacts.forEach(contact =>
                    contact.birthDate = moment(contact.birthDate).format("YYYY-MM-DD")
                );
                view.renderWorkArea(TEMPLATE_NAMES.contactsTable, data);
                view.renderSidenav(TEMPLATE_NAMES.searchBar, params);

                view.listenerManager.addListenersForTable();
                view.listenerManager.addListenersForSearchForm();
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
                data.contact.birthDate = moment(data.contact.birthDate).format("YYYY-MM-DD");
                data.attachs.forEach(attach => {
                    attach.downloadTime = moment(attach.downloadTime).format("YYYY-MM-DD");
                })
                view.renderWorkArea(TEMPLATE_NAMES.editContact, data);
                view.renderSidenav(TEMPLATE_NAMES.phonesAttachBar, data);
                view.listenerManager.addListenersForEditContactForm();
                var sex = data.contact.sex;
                var radios = document.querySelectorAll('input[name="sex"]');
                if (sex == "MALE") {
                    if (radios[0].value == "MALE") {
                        radios[0].checked = true;
                    } else {
                        radios[1].checked = true;
                    }
                } else {
                    if (radios[0].value == "FEMALE") {
                        radios[0].checked = true;
                    } else {
                        radios[1].checked = true;
                    }
                }
            })
    }

    this.addContactPage = function () {
        contactData = {};
        contactData.contact = new Contact();
        startCotactData = {};
        view.renderWorkArea(TEMPLATE_NAMES.editContact);
        view.listenerManager.addListenersForContactForm();
        view.renderSidenav(TEMPLATE_NAMES.empty);
    }

    this.mailPage = function () {
        selectedContacts = view.dataCollector.collectSelectedContacts();

        var emails = new Array();
        var data = {};


        communicator.sendGET()
            .then(response => {
                return response.json();
            })
            .then(temp => {
                templates = temp;
                data.templates = temp
                selectedContacts.forEach(contct => emails.push(contct.email));
                data.emails = emails;

                view.renderWorkArea(TEMPLATE_NAMES.mailToMain);
                view.renderSidenav(TEMPLATE_NAMES.mailToSettings, data);
                view.listenerManager.addListenersForMailForm()
            })
            .catch(e => {
                view.showErr(e);
                view.renderWorkArea(TEMPLATE_NAMES.mailToMain);
                view.renderSidenav(TEMPLATE_NAMES.mailToSettings, data);
                view.listenerManager.addListenersForMailForm();
            })



    }



    this.searchByParams = function (parameters) {
        controller.toMainPage();
        var searchParams = router.getParamString(parameters);
        communicator.sendGET(location.pathname + "contacts/" + location.hash.slice(1) + location.search + searchParams)
            .then(response => {
                return response.json();
            })
            .then(data => {
                view.renderWorkArea(TEMPLATE_NAMES.contactsTable, data);
                //view.renderSidenav(TEMPLATE_NAMES.searchBar, params);
                view.listenerManager.addListenersForTable();
                view.listenerManager.addListenersForSearchForm();
            })
            .catch(e => {
                view.showErr(e);
                controller.toMainPage();
            })
    }

    this.sendMail = function () {
        var mailParams = view.dataCollector.collectMail();
        communicator.sendPOST(mailParams, location.pathname + "contacts/mail")
            .then(response => {
                response.text()
                    .then(text => {
                        if (text) {
                            view.showErr(text)
                        } else {
                            view.showOk("send");
                            controller.toMainPage();
                        }
                    });
            })
            .catch(error => {
                view.showErr(e);
                console.log(error)
            });
    }


    this.toMainPage = function () {
        location.hash = router.startHash;
    }


    this.addContactToDB = function () {
        view.dataCollector.collectContactData();
        if (contactData.contact) {
            communicator.sendPOST(contactData.contact)
                .then(response => {
                    view.showOk("added");
                    controller.toMainPage();
                })
                .catch(e => view.showErr(e));
        }
    }

    this.submitContact = function () {
        view.dataCollector.collectContactData();

        var phones = contactData.phones;
        phones.forEach(phone => {
            if (phone.id.toString().endsWith("a")) {
                phone.id = 0;
                communicator.sendPOST(phone, location.pathname + "contacts/" + contactData.contact.id + "/phone");
            } else {
                communicator.sendPUT(phone, location.pathname + "contacts/" + contactData.contact.id + "/phone");
            }
        });
        var attaches = contactData.attachs;
        attaches.forEach(attach => {
            if (attach.id.toString().endsWith("a")) {
                var formData = formDataFromAttach(attach);
                communicator.sendFormDataPOST(formData, location.pathname + "contacts/" + contactData.contact.id + "/attach");
            } else {
                communicator.sendPUT(attach, location.pathname + "contacts/" + contactData.contact.id + "/attach");
            }
        });
        if (deletedPhones.length > 0) {
            communicator.sendDELETE(deletedPhones, location.pathname + "contacts/" + contactData.contact.id + "/phone");
        }
        if (deletedAttachs.length > 0) {
            communicator.sendDELETE(deletedAttachs, location.pathname + "contacts/" + contactData.contact.id + "/attach");
        }
        communicator.sendPUT(contactData.contact)
            .then(function () {
                view.showOk("done");
                controller.toMainPage();
            })
            .catch(e => view.showErr(e));

    }

    this.deleteSelectedContact = function () {
        var contacts = view.dataCollector.collectSelectedContacts();
        if (contacts.length === 0) {
            return;
        }
        communicator.sendDELETE(contacts)
            .then(response => {
                view.showOk("deleted");
                controller.toMainPage();
            })
            .catch(e => view.showErr(e));
    }

    //+-
    this.deleteSelectedAttach = function () {
        var attachId = view.dataCollector.collectSelectedAttach();
        if (attachId.length === 0) {
            return;
        }
        attachId.forEach(id => {
            for (var index = contactData.attachs.length - 1; index >= 0; index--) {
                if (id === contactData.attachs[index].id.toString()) {
                    if (!id.toString().endsWith("a")) {
                        deletedAttachs.push(contactData.attachs[index]);
                    }
                    contactData.attachs.splice(index, 1);
                }
            }
        });
        view.rerenderSideTables();
    }

    //+-
    this.deleteSelectedPhones = function () {
        var phonesId = view.dataCollector.collectSelectedPhones();
        if (phonesId.length === 0) {
            return;
        }
        phonesId.forEach(id => {
            for (var index = contactData.phones.length - 1; index >= 0; index--) {
                if (id === contactData.phones[index].id.toString()) {
                    if (!id.toString().endsWith("a")) {
                        deletedPhones.push(contactData.phones[index]);
                    }
                    contactData.phones.splice(index, 1);
                }
            }
        });
        view.rerenderSideTables();
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

    this.getParamString = function (params) {
        var paramsString = "";
        for (const key in params) {
            if (params[key]) {
                paramsString += key + "=" + params[key] + '&';
            }
        }
        return '?' + paramsString;
    }

}

function formDataFromAttach(attach) {
    var formData = new FormData();
    formData.append("id", 0);
    formData.append("file", attach.file);
    formData.append("comment", attach.comment);
    formData.append("ownerId", attach.ownerId);

    return formData;
}
