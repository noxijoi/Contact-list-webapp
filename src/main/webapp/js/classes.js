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
function Phone(id, code, operatorCode, number, type, comment){
    this.id = id;
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
function Attachment(){

}

//Contact class
function Contact(id, fullName, birthDate, sex, nationality, maritalStatus, website, email, company, address ){
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

    this.isMale = function(){return sex === "male";}
    
}

