package contactsapp.core.entity;

import contactsapp.dao.Identified;

import java.io.File;
import java.util.Date;

public class Contact implements Identified {

    private Integer id;

    private FullName fullName;

    private Date birthDate;
    private Sex sex;
    private String nationality;
    private MaritalStatus maritalStatus;
    private String website;
    private String email;
    private String company;
    private Address address;

    private File avatar = null;

    public Contact() {
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

    public Contact(Integer id, FullName fullName, Date birthDate, Sex sex, String email) {

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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
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

    public File getAvatar() {
        return avatar;
    }

    public void setAvatar(File avatar) {
        this.avatar = avatar;
    }

}
