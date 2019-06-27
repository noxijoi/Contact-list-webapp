function View() {
    this.renderWorkArea = function (templateName, data) {
        data = data || {};
        var elementTo = document.getElementById("main");
        elementTo.innerHTML =  this.generateTemplateHTML(templateName, data);
    }
    this.renderSidenav = function (templateName, data) {
        data = data || {};
        var elementTo = document.getElementById("sidenav");
        elementTo.innerHTML = this.generateTemplateHTML(templateName, data);
    }
    this.generateTemplateHTML = function(templateName, data){
        var templateElement = document.getElementById(templateName);
        var templateSource = templateElement.innerHTML;
        return  Mustache.to_html(templateSource, data);
    }

    //TODO
    this.addListenersForAttachAndPhones = function () {

    }
    this.addListenersForMailForm = function () {
        var sendMailButton = document.getElementById("send-mail");
        sendMailButton.addEventListener('click', controller.sendMail);
        var cancelMailButton = document.getElementById("cancel-mail");
        cancelMailButton.addEventListener('click', controller.toMainPage);
    }
    //TODO
    this.addListenersForSearchForm = function () {

    }
    this.addListenersForContactForm = function () {
        var okButton = document.querySelector(".contact-ok");
        okButton.addEventListener('click', controller.addContactToDB);
        var cancelButton = document.querySelector(".contact-cancel");
        cancelButton.addEventListener('click', controller.toMainPage);

        //avatar
        var avatarEl = document.querySelector(".avatar");
        avatarEl.addEventListener('click', editAvatar);
    }
    this.addListenersForEditContactForm = function () {
        var okButton = document.querySelector(".contact-ok");
        okButton.addEventListener('click', controller.editContact);
        var cancelButton = document.querySelector(".contact-cancel");
        cancelButton.addEventListener('click', controller.toMainPage);

        var avatarEl = document.querySelector(".avatar");
        avatarEl.addEventListener('click', editAvatar);
        //sideBar
        var deletePhonesButton = document.querySelector(".phonebuttons .delete-button");
        deletePhonesButton.addEventListener('click', controller.deletePhones);
        var deleteAttachsButton = document.querySelector(".attachbuttons .delete-button");
        deleteAttachsButton.addEventListener('click', controller.deleteAttach);
        //modal
        var confirmPhoneButton = document.getElementById("confirmPhone");
        confirmPhoneButton.addEventListener('click', controller.addPhone);

        var confirmAttachButton = document.getElementById("confirmAttach");
        confirmAttachButton.addEventListener('click', controller.addAttach);

        var closeModals = document.getElementsByClassName("close-modal");
        Array.prototype.forEach.call(closeModals, closeModal =>{
            closeModal.addEventListener('click', function(){
                var modals = document.getElementsByClassName('modal');
                Array.prototype.forEach.call(modals, modal=> modal.style.display="none");
            })
        })
    }

    this.collectContactData = function () {
        var fullName = new FullName();
        fullName.firstName = document.getElementById("first-name-input").value;
        fullName.lastName = document.getElementById("last-name-input").value;
        fullName.parentName = document.getElementById("parent-name-input").value;

        var birthDate = document.getElementById("birth-date-input").value;
        var nationality = document.getElementById("nationality-input").value;

        var sex = document.querySelector('input[name="sex"]:checked').value.toUpperCase();
        var company = document.getElementById("company-input").value;

        var e = document.getElementById("marital-status-input");
        var maritalStatus = e.options[e.selectedIndex].value.toUpperCase();

        var website = document.getElementById("website-input").value;
        var email = document.getElementById("email-input").value;

        var address = new Address();
        address.country = document.getElementById("country-input").value;
        address.city = document.getElementById("city-input").value;
        address.street = document.getElementById("street-input").value;
        address.house = document.getElementById("house-input").value;
        address.index = document.getElementById("index-input").value;

        var contact = new Contact(null, fullName, birthDate, sex, nationality,
            maritalStatus, website, email, company, address);

        return contact;
    }
    this.collectPhoneData = function(){
        var countryCode = document.getElementById("country-code-input").value;
        var operatorCode = document.getElementById("operator-code-input").value;
        var number = document.getElementById("number-input").value;
        var type = document.querySelector('input[name="phoneType"]:checked').value.toUpperCase();
        var comment = document.getElementById('phone-comment-input').value;

        var phone = new Phone(0,countryCode, operatorCode, number, type, comment);
        return phone;
    }

    this.openPhoneModal = function(){
        var phoneModal = document.getElementById("phoneModal");
        phoneModal.style.display = "block";
    }
    this.openAttachModal = function(){
        var attachModal = document.getElementById("attachModal");
        attachModal.style.display = "block";
    }
}

//TODO
function editAvatar() { }
