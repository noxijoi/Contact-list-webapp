package contactsapp.validation;

import contactsapp.core.entity.Address;
import contactsapp.core.entity.Contact;
import contactsapp.core.entity.FullName;
import contactsapp.utils.FileManager;

public class ContactValidator implements Validator<Contact> {
    @Override
    public boolean validate(Contact contact) {
        if(contact.getId() == null){
            return false;
        }
        FullName fullName = contact.getFullName();
        if(fullName.getLastName() == null || fullName.getLastName().length() > 30){
            return false;
        }
        if(fullName.getFirstName() == null || fullName.getFirstName().length() > 30){
            return false;
        }
        if(fullName.getParentName() == null || fullName.getParentName().length() > 30){
            return false;
        }
        if(contact.getSex() == null){
            return false;
        }
        if(contact.getNationality() != null && contact.getNationality().length() > 30){
            return false;
        }

        if(contact.getWebsite() != null && contact.getWebsite().length() > 50){
            return false;
        }
        if(contact.getEmail() == null || contact.getEmail().length() > 50){
            return false;
        }
        Address address = contact.getAddress();
        if (address.getCountry() != null && address.getCountry().length() > 100){
            return false;
        }
        if (address.getCity() != null && address.getCity().length() > 50){
            return false;
        }
        if (address.getStreet() != null && address.getStreet().length() > 50){
            return false;
        }
        if (address.getHouse() != null && address.getHouse().length() > 10){
            return false;
        }
        if(contact.getBirthDate() == null){
            return false;
        }
        if(contact.getAvatar().getPath().isEmpty()){
            contact.getAvatar().setPath(FileManager.getInstance().getDefaultAvatarPath());
        }
        return true;
    }
}
