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
function Phone(phoneId, code, operatorCode, number, type, comment){
    this.phoneId = phoneId;
    this.code = code;
    this.operatorCode = operatorCode;
    this.number = number;
    this.type = type;
    this.comment = comment;
}

//Attachment class
function Attachment(){

}

//Contact class
function Contact(id, fullName, sex, nationality, maritalStatus, website, email, company, address ){
    this.id = id || 0; 
    this.fullName = fullName || "";
    this.birthDate = birthDate || null;
    this.sex = sex || "";
    this.nationality = nationality || "";
    this.maritalStatus = maritalStatus || "";
    this.website = website || "";
    this.email = email || "";
    this.company = company || "";
    this.address = address || null;
}

//POST dto
function PostDTO(dataType, data){
    this.dataType = dataType;
    this.data = data;

}