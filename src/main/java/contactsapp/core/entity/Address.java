package contactsapp.core.entity;

public class Address{
    String country;
    String city;
    String street;
    String homeNumber;
    int index;

    public Address(String country, String city, String street, String homeNumber, int index) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.homeNumber = homeNumber;
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

    public String getHomeNumber() {
        return homeNumber;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
