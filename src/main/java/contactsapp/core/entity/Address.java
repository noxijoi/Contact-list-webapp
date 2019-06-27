package contactsapp.core.entity;

public class Address{
    String country;
    String city;
    String street;
    String house;
    Integer index;

    public Address(String country, String city, String street, String house, int index) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.house = house;
        this.index = index;
    }

    public Address() {

    }

    public String getCountry() {

        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
