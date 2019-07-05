
var view = {
  renderWorkArea: function (templateName, data) {
    data = data || {};
    var elementTo = document.getElementById("main");
    elementTo.innerHTML = view.generateTemplateHTML(templateName, data);
  },
  renderSidenav: function (templateName, data) {
    data = data || {};
    var elementTo = document.getElementById("sidenav");
    elementTo.innerHTML = view.generateTemplateHTML(templateName, data);
  },

  generateTemplateHTML: function (templateName, data) {
    data = data || {};
    var templateElement = document.getElementById(templateName);
    var templateSource = templateElement.innerHTML;
    return Mustache.to_html(templateSource, data);
  },

  rerenderSideTables: function () {
    view.renderSidenav(TEMPLATE_NAMES.phonesAttachBar, contactData);
    view.listenerManager.addListenersForEditSide();
  },


  dataCollector: {
    //id выбранных контактов
    collectSelectedContacts: function () {
      var contactsArray = new Array();
      var mainTable = document.getElementById("main-table");
      var inputElements = mainTable.querySelectorAll(".checkContact");
      inputElements.forEach(item => {
        if (item.checked) {
          var id = item.value;
          var contact = contactPageData.contacts.find(cont => cont.id.toString() === id.toString());
          contactsArray.push(contact);
        }
      });
      return contactsArray;
    },

    collectContactData: function () {
      var fullName = new FullName();
      fullName.firstName = document.getElementById("first-name-input").value;
      fullName.lastName = document.getElementById("last-name-input").value;
      fullName.parentName = document.getElementById("parent-name-input").value;
      contactData.contact.fullName = fullName;

      var birthDate = document.getElementById("birth-date-input").value;
      contactData.contact.birthDate = birthDate;

      var nationality = document.getElementById("nationality-input").value;
      contactData.contact.nationality = nationality;

      var sex = document
        .querySelector('input[name="sex"]:checked')
        .value.toUpperCase();
      contactData.contact.sex = sex;

      var company = document.getElementById("company-input").value;
      contactData.contact.company = company;

      var e = document.getElementById("marital-status-input");
      var maritalStatus = e.options[e.selectedIndex].value.toUpperCase();
      contactData.contact.maritalStatus = maritalStatus;

      var website = document.getElementById("website-input").value;
      contactData.contact.website = website;

      var email = document.getElementById("email-input").value;
      contactData.contact.email = email;

      var address = new Address();
      address.country = document.getElementById("country-input").value;
      address.city = document.getElementById("city-input").value;
      address.street = document.getElementById("street-input").value;
      address.house = document.getElementById("house-input").value;
      address.index = document.getElementById("index-input").value;
      contactData.contact.address = address;
    },
    //+
    collectPhoneData: function () {
      var phone = new Phone();
      phone.countryCode = document.getElementById("country-code-input").value;
      phone.operatorCode = document.getElementById("operator-code-input").value;
      phone.number = document.getElementById("number-input").value;
      phone.type = document
        .querySelector('input[name="phoneType"]:checked')
        .value.toUpperCase();
      phone.comment = document.getElementById("phone-comment-input").value;
      phone.ownerId = contactData.contact.id;

      return phone;
    },

    collectSearchParams: function () {
      var params = {};

      params.firstName = document.getElementById("fName").value;
      params.lastName = document.getElementById("lName").value;
      params.parentName = document.getElementById("pName").value;
      params.dateFrom = document.getElementById("dateFrom").value;
      params.dateTo = document.getElementById("dateTo").value;
      params.sex = document
        .querySelector('input[name="sexRadio"]:checked')
        .value.toUpperCase();
      params.company = document.getElementById("company-input").value;
      params.email = document.getElementById("email-input").value;
      params.country = document.getElementById("country-input").value;
      params.city = document.getElementById("city-input").value;
      params.street = document.getElementById("street-input").value;
      params.index = document.getElementById("index-input").value;

      return params;
    },

    collectSelectedPhones: function () {
      var contactsIdInputs = new Array();
      document.querySelectorAll("#phones-table input").forEach(input => {
        if (input.checked) {
          contactsIdInputs.push(input.value);
        }
      });
      return contactsIdInputs;
    },

    collectSelectedAttach: function () {
      var attachsId = new Array();
      document.querySelectorAll("#attachs-table input").forEach(input => {
        if (input.checked) {
          attachsId.push(input.value);
        }
      });
      return attachsId;
    },

    //+
    collectAttachData: function () {
      var attach = new Attachment();
      var file = document.getElementById("addFile").files[0];
      attach.file = file;
      attach.ownerId = contactData.contact.id;

      attach.fileName = file.name;
      attach.comment = document.getElementById("attachComment").value;

      return attach;
    },

    collectMail: function(){
      var receivers = document.getElementById("reciever").value.split(',');
      var msg = document.getElementById("mail-text").value;
      var subject = document.getElementById("topic").value;
      var mailParams = new MailParams(receivers, msg, subject);
      return mailParams;
    }
  },

  listenerManager: {

    addListenersForPhoneModal: function () {
      var confirmPhoneButton = document.getElementById("confirmPhone");
      confirmPhoneButton.addEventListener("click", view.addPhoneToTable);
      confirmPhoneButton.addEventListener("click", function () {
        phoneModal.style.display = "none";
      });
      var cancelPhoneButton = document.getElementById("cancelPhone");
      cancelPhoneButton.addEventListener("click", function () {
        phoneModal.style.display = "none";
      });
    },

    addListenersForMailForm: function () {
      var sendMailButton = document.getElementById("send-mail");
      sendMailButton.addEventListener("click", controller.sendMail);
      var cancelMailButton = document.getElementById("cancel-mail");
      cancelMailButton.addEventListener("click", controller.toMainPage);
    },

    addListenersForTable: function () {
      var deleteButton = document.querySelector(".delete-button");
      deleteButton.addEventListener("click", controller.deleteSelectedContact);
    },

    addListersForEditMain: function () {

      var okButton = document.querySelector(".contact-ok");
      okButton.addEventListener("click", controller.submitContact);

      var submitButton = document.querySelector(".submit");
      submitButton.addEventListener('click', function () {
        var form = document.querySelector("form");
        if (form.checkValidity()) {
          okButton.click();
        } else {
          alert("Заполните все поля со звездочкой");
        }
      });

      //--
      var cancelButton = document.querySelector(".contact-cancel");
      cancelButton.addEventListener("click", controller.toMainPage);
      //+
      var avatarInput = document.getElementById("avatarUpload");
      avatarInput.addEventListener("change", function (evt) {
        var file = evt.target.files[0];
        contactData.contact.avatar.path = file.name;
        var reader = new FileReader();
        reader.onload = (function (theFile) {
          return function (e) {
            var span = document.getElementById("avatarWrap");
            span.innerHTML = [
              '<img class="thumb" id="avatar-img" src="',
              e.target.result,
              '" title="',
              escape(theFile.name),
              '"/>'
            ].join("");
            contactData.contact.avatar.decodedImg = e.target.result;
          };
        })(file);
        reader.readAsDataURL(file);
      });
    },


    addListenersForEditSide: function () {
      var openPhoneModalButton = document.querySelector(".phonebuttons .add-button");
      openPhoneModalButton.addEventListener("click", view.openPhoneModal);

      var openAttachModalButton = document.querySelector(".attachbuttons .add-button");
      openAttachModalButton.addEventListener("click", view.openAttachModal);

      var deletePhonesButton = document.querySelector(".phonebuttons .delete-button");
      deletePhonesButton.addEventListener("click", controller.deleteSelectedPhones);

      var deleteAttachsButton = document.querySelector(".attachbuttons .delete-button");
      deleteAttachsButton.addEventListener("click", controller.deleteSelectedAttach);

      var phonesTable = document.getElementById("phones-table");
      var editPhoneButtons = phonesTable.querySelectorAll(".editBtn");
      editPhoneButtons.forEach(cell => {
        var row = cell.parentElement.parentElement;
        var phoneId = row.querySelector("input").value;
        var phoneToEdit = contactData.phones.find(phone => {
          return phone.id.toString() === phoneId.toString();
        });
        cell.addEventListener('click', function () {
          var phoneModal = document.getElementById("phoneModal");
          phoneModal.innerHTML = view.generateTemplateHTML(TEMPLATE_NAMES.phoneModal, phoneToEdit);
          phoneModal.style.display = "block";
          var confirmPhoneButton = document.getElementById("confirmPhone");
          confirmPhoneButton.addEventListener("click", function () {
            var id = phoneToEdit.id;
            phoneToEdit = view.dataCollector.collectPhoneData();
            phoneToEdit.id = id;
            var index = contactData.phones.findIndex(ph => ph.id.toString() === phoneToEdit.id.toString());
            contactData.phones[index] = phoneToEdit;
            view.rerenderSideTables();
            phoneModal.style.display = "none";
          });
          var cancelPhoneButton = document.getElementById("cancelPhone");
          cancelPhoneButton.addEventListener("click", function () {
            phoneModal.style.display = "none";
          });
        });
      });

      var attachsTable = document.getElementById("attachs-table");
      var editAttachButtons = attachsTable.querySelectorAll(".editBtn");
      editAttachButtons.forEach(cell => {
        var row = cell.parentElement.parentElement;
        var attachId = row.querySelector("input").value;

        var attach = contactData.attachs.find(att => {
          return att.id.toString() === attachId.toString();
        });

        cell.addEventListener('click', function () {
          var attachModal = document.getElementById("attachModal");
          attachModal.innerHTML = view.generateTemplateHTML(TEMPLATE_NAMES.attachEditModal, attach);

          var confirmAttachButton = document.getElementById("confirmAttach");
          confirmAttachButton.addEventListener("click", function () {

            attach.comment = document.getElementById("attachComment").value;
            var index = contactData.attachs.findIndex(att => att.id.toString() === attach.id.toString());
            contactData.attachs[index] = attach;

            view.rerenderSideTables();
            attachModal.style.display = "none";
          });
          var cancelAttachButton = document.getElementById("cancelAttach");
          cancelAttachButton.addEventListener("click", function () {
            attachModal.style.display = "none";
          });
          attachModal.style.display = "block";
        });
      });

    },

    addListenersForContactForm: function () {
      var okButton = document.querySelector(".contact-ok");
      okButton.addEventListener("click", controller.addContactToDB);

      var submitButton = document.querySelector(".submit");
      submitButton.addEventListener('click', function () {
        var form = document.querySelector("form");
        if (form.checkValidity()) {
          okButton.click();
        } else {
          alert("Заполните все поля со звездочкой");
        }
      });

      var cancelButton = document.querySelector(".contact-cancel");
      cancelButton.addEventListener("click", controller.toMainPage);
      document.querySelector(".avatar").style.display = "none";
    },

    addListenersForEditContactForm: function () {
      this.addListersForEditMain();
      this.addListenersForEditSide();
    },

    addListenersForSearchForm: function () {
      var searchButton = document.getElementById("paramSearchButton");
      searchButton.addEventListener("click", function () {
        var params = view.dataCollector.collectSearchParams();
        controller.searchByParams(params);
      });
    },
  },

  //+
  openPhoneModal: function (phone) {
    var phoneModal = document.getElementById("phoneModal");
    phoneModal.innerHTML = view.generateTemplateHTML(TEMPLATE_NAMES.phoneModal, phone);
    phoneModal.style.display = "block";

    var confirmPhoneButton = document.getElementById("confirmPhone");
    confirmPhoneButton.addEventListener("click", function () {
      view.addPhoneToTable();
      phoneModal.style.display = "none";
    });

    var submitButton = document.querySelector(".phone .submit");
    submitButton.addEventListener('click', function () {
      var form = document.querySelector("form");
      if (form.checkValidity()) {
        confirmPhoneButton.click();
      } else {
        alert("Заполните все поля со звездочкой");
      }
    });

    var cancelPhoneButton = document.getElementById("cancelPhone");
    cancelPhoneButton.addEventListener("click", function () {
      phoneModal.style.display = "none";
    });

  },

  openAttachModal: function (attach) {
    var attachModal = document.getElementById("attachModal");
    attachModal.innerHTML = view.generateTemplateHTML(TEMPLATE_NAMES.attachModal, attach);

    var confirmAttachButton = document.getElementById("confirmAttach");
    confirmAttachButton.addEventListener("click", function () {
      view.addAttachToTable();
      attachModal.style.display = "none";
    });

    var submitButton = document.querySelector(".attach .submit");
    submitButton.addEventListener('submit', confirmAttachButton.click);

    var cancelAttachButton = document.getElementById("cancelAttach");
    cancelAttachButton.addEventListener("click", function () {
      attachModal.style.display = "none";
    });
    attachModal.style.display = "block";
  },

  //++
  addPhoneToTable: function () {
    var phone = view.dataCollector.collectPhoneData();
    contactData.phones.push(phone);
    //все телефоны, которые ещё не добавлены в базу будут иметь индекс с меткой "а"
    var id = contactData.phones.length + 1;
    id += "a";
    phone.id = id;
    view.rerenderSideTables();
  },
  //++
  addAttachToTable: function () {
    var attach = view.dataCollector.collectAttachData();
    //все атачи, которые ещё не добавлены в базу будут иметь индекс с меткой "а"
    var id = contactData.phones.length + 1;
    id += "a";
    attach.id = id;
    contactData.attachs.push(attach);
    view.rerenderSideTables();
  },
};
