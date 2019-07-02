
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
    var templateElement = document.getElementById(templateName);
    var templateSource = templateElement.innerHTML;
    return Mustache.to_html(templateSource, data);
  },

  rerenderSideTables: function () {
    view.renderSidenav(TEMPLATE_NAMES.phonesAttachBar, contactData);
    view.addListenersForEditContactForm
  },


  dataCollector: {
    //id выбранных контактов
    collectSelectedContacts: function () {
      var contactsArray = new Array();
      var mainTable = document.getElementById("main-table");
      var inputElements = mainTable.querySelectorAll(".checkContact");
      inputElements.forEach(item => {
        if (item.checked) {
          var contact = new Contact(item.value);
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

      var birthDate = document.getElementById("birth-date-input").value;
      var nationality = document.getElementById("nationality-input").value;

      var sex = document
        .querySelector('input[name="sex"]:checked')
        .value.toUpperCase();
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

      var contact = new Contact(null, fullName, birthDate, sex, nationality, maritalStatus,
        website, email, company, address);

      return contact;
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
      //все телефоны, которые ещё не добавлены в базу будут иметь индекс с меткой "а"
      var id = contactData.phones.length + 1;
      id += "a";
      phone.id = id;
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
          contactsIdInputs.push(input);
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
    },

    //+
    collectAttachData: function () {
      var attach = new Attachment();
      var file = document.getElementById("addFile").files[0];
      attach.ownerId = contactData.contact.id;
      var id = contactData.attachs.length + 1;
      id += "a";

      files[id] = file;

      attach.id = id;
      attach.fileName = file.name;
      attach.comment = document.getElementById("attachComment").value;

      return attach;
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

    addListenersForEditPhoneNodal: function () {
      var confirmPhoneButton = document.getElementById("confirmPhone");
      confirmPhoneButton.addEventListener("click", view.editPhone);
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
      //--
      var cancelButton = document.querySelector(".contact-cancel");
      cancelButton.addEventListener("click", controller.toMainPage);
      //+
      var avatarInput = document.getElementById("avatarUpload");
      avatarInput.addEventListener("change", function (evt) {
        var file = evt.target.files[0];
        contactData.contact.avatar.path = file.fileName;
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
          };
        })(file);
        reader.readAsDataURL(file);

        contactData.avatar.decodedImg = document.getElementById("avatar-img").src;
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
      var editPhoneButtons = phonesTable.querySelectorAll("editBtn");
      editPhoneButtons.forEach(cell => {
        var row = cell.parentElement;
        var phoneId = row.querySelector("input").value;
        var phoneN = contactData.phones.find(phone => {
          return phone.id === phoneId;
        });
        view.openPhoneModal(phoneN);
      });

      var attachsTable = document.getElementById("phones-table");
      var editAttachButtons = attachsTable.querySelectorAll("editBtn");
      editAttachButtons.forEach(cell => {
        var row = cell.parentElement;
        var attachId = row.querySelector("input").value;
        var attachN = contactData.phones.find(attach => {
          return attach.id === attachId;
        });
        view.openEditAttachModal(attachN);
      });

    },

    addListenersForContactForm: function () {
      var okButton = document.querySelector(".contact-ok");
      okButton.addEventListener("click", controller.addContactToDB);
      var cancelButton = document.querySelector(".contact-cancel");
      cancelButton.addEventListener("click", controller.toMainPage);
    },

    addListenersForEditContactForm: function () {
      this.addListersForEditMain();
      this.addListenersForEditSide();
    },

    addListenersForContactForm: function () {
      var okButton = document.querySelector(".contact-ok");
      okButton.addEventListener("click", controller.addContactToDB);
      var cancelButton = document.querySelector(".contact-cancel");
      cancelButton.addEventListener("click", controller.toMainPage);
    },

    addListenersForSearchForm: function () {
      var searchButton = document.getElementById("paramSearchButton");
      searchButton.addEventListener("click", function () {
        var params = view.dataCollector.collectSearchParams();
        controller.searchByParams(params);
      });
    }
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
    view.rerenderSideTables();
  },
  //++
  addAttachToTable: function () {
    var attach = view.dataCollector.collectAttachData();
    contactData.attachs.push(attach);
    view.rerenderSideTables();
  },

  editPhone: function(){
    var attach = view.dataCollector.collectAttachData();
  }
};
