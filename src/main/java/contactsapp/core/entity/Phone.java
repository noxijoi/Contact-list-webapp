package contactsapp.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class Phone implements Identified<Integer> {
    private Integer id;
    private Integer ownerId;
    private String countryCode;
    private String operatorCode;
    private String number;
    private PhoneType type;
    private String comment;

    public Phone(String countryCode, String operatorCode, String number,
                 PhoneType type) {
        this.countryCode = countryCode;
        this.operatorCode = operatorCode;
        this.number = number;
        this.type = type;
    }

    public Phone() {

    }

    public String getComment() {
        return comment;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PhoneType getType() {
        return type;
    }

    public void setType(PhoneType type) {
        this.type = type;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    @JsonIgnore
    public String getFullNumber(){
        return countryCode + operatorCode + number;
    }

}
