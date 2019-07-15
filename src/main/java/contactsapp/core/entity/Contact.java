package contactsapp.core.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;

public class Contact implements Identified {

    private Integer id;

    private FullName fullName;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthDate;
    private Sex sex;
    private String nationality;
    private MaritalStatus maritalStatus;
    private String website;
    private String email;
    private String company;
    private Address address;

    private Avatar avatar;

    public Contact() {
        this.fullName = new FullName();
        this.birthDate = null;
        this.sex = Sex.MALE;
        this.nationality = "";
        this.maritalStatus = MaritalStatus.SINGLE;
        this.website = "";
        this.email = "";
        this.company = "";
        this.address = new Address();
        this.avatar = new Avatar();
    }

    public Contact(Integer id, FullName fullName, LocalDate birthDate, Sex sex, String nationality,
                   MaritalStatus maritalStatus, String website, String email, String company, Address address) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.sex = sex;
        this.nationality = nationality;
        this.maritalStatus = maritalStatus;
        this.website = website;
        this.email = email;
        this.company = company;
        this.address = address;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FullName getFullName() {
        return fullName;
    }

    public void setFullName(FullName fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Avatar getAvatar(){
        return avatar;
    }

    public void setAvatar(Avatar avatar){
        this.avatar = avatar;
    }


}
