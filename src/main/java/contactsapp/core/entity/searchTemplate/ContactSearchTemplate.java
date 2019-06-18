package contactsapp.core.entity.searchTemplate;

import contactsapp.core.entity.Address;
import contactsapp.core.entity.MaritalStatus;
import contactsapp.core.entity.Sex;

import java.util.Calendar;

public class ContactSearchTemplate implements  SearchTemplate{
    String firstName;
    String lastName;
    String parentName;
    Calendar youngerThan;
    Calendar olderThan;
    Sex sex;
    String nationality;
    MaritalStatus maritalStatus;
    String website;
    String email;
    String companyName;
    Address address;
}
