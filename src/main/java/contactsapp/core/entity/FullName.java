package contactsapp.core.entity;

public class FullName {

    private String firstName = "";
    private String lastName = "";
    private String parentName = "";

    public FullName(){

    }

    public FullName(String firstName, String lastName, String parentName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.parentName = parentName;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " " + parentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }

        FullName fullName = (FullName) o;

        if (!firstName.equals(fullName.firstName)){
            return false;
        }
        if (!lastName.equals(fullName.lastName)){
            return false;
        }
        return parentName != null ? parentName.equals(fullName.parentName) : fullName.parentName == null;
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + (parentName != null ? parentName.hashCode() : 0);
        return result;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

}
