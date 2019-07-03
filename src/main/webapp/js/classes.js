//Full name class
function FullName(firstName, lastName, parentName) {
    this.firstName = firstName || "";
    this.lastName = lastName || "";
    this.parentName = parentName || "";
}
//Address class
function Address(country, city, street, house, index){
    this.country = country || "";
    this.city = city || "";
    this.street = street || "";
    this.house = house || "";
    this.index = index || "";
}
//Phone number class
function Phone(id, ownerId, code, operatorCode, number, type, comment){
    this.id = id;
    this.ownerId = ownerId;
    this.countryCode = code;
    this.operatorCode = operatorCode;
    this.number = number;
    this.type = type;
    this.comment = comment;

    this.isHome = function(){
        return type === "HOME";
    }
}
//Attachment class
function Attachment(id, ownerId, fileName, filePath, downloadTime, comment){
    this.id = id;
    this.ownerId = ownerId;
    this.fileName = fileName ||"";
    this.filePath = filePath ||"";
    this.downloadTime = downloadTime || null;
    this.comment = comment;
}

//Contact class
function Contact(id, fullName, birthDate, sex, nationality, maritalStatus, website, email, company, address ){
    this.id = id || 0; 
    this.fullName = fullName || null;
    this.birthDate = birthDate || null;
    this.sex = sex || null;
    this.nationality = nationality || "";
    this.maritalStatus = maritalStatus || null;
    this.website = website || "";
    this.email = email || "";
    this.company = company || "";
    this.address = address || null;

    this.isMale = function(){return sex === "male";}
    this.isSingle = function(){ return maritalStatus === "SINGLE";}
}

function MailParams(receivers, message, subject){
    this.receivers = receivers || null;
    this.message = message || null;
    this.subject = subject || null;
}


