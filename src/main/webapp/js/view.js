function fooo(){
  var prostovarChtobiObnovilos;
  var ttuttutututtututut="тыгыдыкский конь";
}
var view = {
  renderSidenav: function (templateName, data) {
    data = data || {};
    var elementTo = document.getElementById("sidenav");
    elementTo.innerHTML = view.generateTemplateHTML(templateName, data);
  },
  renderWorkArea: function (templateName, data) {
    data = data || {};
    var elementTo = document.getElementById("main");
    elementTo.innerHTML = view.generateTemplateHTML(templateName, data);
    fooo();
  },
  rerenderSideTables: function () {
    view.renderSidenav(TEMPLATE_NAMES.phonesAttachBar, contactData);
    view.listenerManager.addListenersForEditSide();
  },

  generateTemplateHTML: function (templateName, data) {
    data = data || {};
    var templateElement = document.getElementById(templateName);
    var templateSource = templateElement.innerHTML;
    return Mustache.to_html(templateSource, data);
  },
  


  dataCollector: {
    //id выбранных контактов
    collectSelectedContacts: function () {
      var contactsArray = new Array();
      var mainTable = document.getElementById("main-table");
      if (!mainTable) {
        return;
      }
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

      if (!moment(birthDate).isValid()) {
        view.showErr('incorrect date, use format: "YYYY-MM-DD"');
        return;
      }

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
      params.size = PAGE_SIZE;
      params.firstName = document.getElementById("fName").value;
      params.lastName = document.getElementById("lName").value;
      params.parentName = document.getElementById("pName").value;
      params.dateFrom = document.getElementById("dateFrom").value;
      if (params.dateFrom && !moment(params.dateFrom).isValid()) {
        view.showErr('incorrect "from" date, use format: "YYYY-MM-DD"')
      }
      params.dateTo = document.getElementById("dateTo").value;
      if (params.dateTo && !moment(params.dateTo).isValid()) {
        view.showErr('incorrect "to" date, use format: "YYYY-MM-DD"')
      }
      var sexs = document.querySelector('input[name="sexRadio"]:checked');
      if (sexs) {
        params.sex = sexs.value.toUpperCase();
      } else {
        params.sex = null;
      }
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


    collectMail: function () {

      var tempName = document.getElementById("mail-template-select").value;
      var temp = templates.find(template => template.name === tempName);
      if (!selectedContacts || selectedContacts.length == 0) {
        selectedContacts = [];
        var emailStr = document.getElementById("receiver").value.split(',');
        emailStr.forEach(em => {
          selectedContacts.push(new Contact(null, null, null, null, null, null, null, em));
        })
      }
      if (!temp) {
        temp = {};
        temp.message = document.getElementById("mail-text").value;
        temp.subject = document.getElementById("subj").value;
      }
      var mailParams = new MailParams(selectedContacts, temp);
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
      var workField = document.getElementById("mail-text");
      var select = document.getElementById("mail-template-select");
      var subjField = document.getElementById("subj");
      var receivers = document.getElementById("receiver");

      if (receivers.value) {
        receivers.disabled = true;
      }
      select.addEventListener('change', function () {
        var value = select.value;
        if (value != "NoTemplate") {
          var templ = templates.find(temp => temp.name === value);

          workField.value = templ.message;
          workField.disabled = true;

          subjField.value = templ.subject;
          subjField.disabled = true;

        } else {
          workField.value = "";
          workField.disabled = false;

          subjField.value = "";
          subjField.disabled = false;
        }

      })

      var sendMailButton = document.getElementById("send-mail");
      sendMailButton.addEventListener("click", function () {
        var form = document.getElementById("mail-form");
        if (form.checkValidity()) {
          controller.sendMail()
        } else {
          view.showErr("Заполните все поля со *");
        }
      });

      var cancelMailButton = document.getElementById("cancel-mail");
      cancelMailButton.addEventListener("click", controller.toMainPage);
    },

    addListenersForTable: function () {
      var deleteButton = document.querySelector(".delete-button");
      deleteButton.addEventListener("click", controller.deleteSelectedContact);
    },

    addListersForEditMain: function () {

      var okButton = document.querySelector(".contact-ok");
      okButton.addEventListener("click", function () {
        var form = document.querySelector("form");
        if (form.checkValidity()) {
          controller.submitContact();
        } else {
          view.showErr("Заполните все поля со звездочкой");
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
            var form = document.querySelector("form");
            if (form.checkValidity()) {
              var id = phoneToEdit.id;
              phoneToEdit = view.dataCollector.collectPhoneData();
              phoneToEdit.id = id;
              var index = contactData.phones.findIndex(ph => ph.id.toString() === phoneToEdit.id.toString());
              contactData.phones[index] = phoneToEdit;
              view.rerenderSideTables();
              phoneModal.style.display = "none";
            } else {
              view.showErr("Заполните все поля со звездочкой");
            }
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

        var downloadLinks = attachsTable.querySelectorAll("td a");
        downloadLinks.forEach(link => {
          var row = link.parentElement.parentElement;
          var attachId = row.querySelector("input").value;

          if (attachId.toString().endsWith("a")) {
            link.parentElement.innerHTML = "не тык";
          }
        });
      });

    },

    addListenersForContactForm: function () {
      var okButton = document.querySelector(".contact-ok");
      okButton.addEventListener("click", function () {
        var form = document.querySelector("form");
        if (form.checkValidity()) {
          controller.addContactToDB();
        } else {
          var date = document.getElementById("birth-date-input");
          if (!moment(date.value).isValid()) {
            view.showErr("Неправильно заполена дата")
          } else {
            view.showErr("Заполните все поля со звездочкой");
          }
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
        view.dataCollector.collectSearchParams();
        location.hash = router.startHash;
        controller.contactsPage();

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
      var form = document.querySelector(".phone form");
      if (form.checkValidity()) {
        view.addPhoneToTable();
        phoneModal.style.display = "none";
      } else {
        view.showErr("Заполните все поля со звездочкой");
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
      var form = document.querySelector(".attach form");
      var fileName = document.getElementById("addFile").files[0].name;
      if (form.checkValidity()) {
        if (!isKyr(fileName)) {
          view.addAttachToTable();
          attachModal.style.display = "none";
        } else {
          view.showErr("Выберите файл без кирилицы в названии");
        }
      } else {
        view.showErr("Выберите файл");
      }
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
    var id = contactData.attachs.length + 1;
    id += "a";
    attach.id = id;
    contactData.attachs.push(attach);
    view.rerenderSideTables();
  },


  //уведомляшки
  showErr: function (msg) {
    var el = document.getElementById("msg-err");
    el.querySelector("p").innerHTML = msg;
    el.style.display = "block";
    setTimeout(function () { el.style.display = "none" }, 2000);
  },
  showOk: function (msg) {
    var el = document.getElementById("msg-success");
    el.querySelector("p").innerHTML = msg;
    el.style.display = "block";
    setTimeout(function () { el.style.display = "none" }, 2000);
  }

};


var isKyr = function (str) {
  return /[а-я]/i.test(str);
}