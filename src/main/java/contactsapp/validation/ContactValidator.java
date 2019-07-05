package contactsapp.validation;

import contactsapp.core.entity.Address;
import contactsapp.core.entity.Contact;
import contactsapp.core.entity.FullName;
import contactsapp.utils.FileManager;


public class ContactValidator implements Validator<Contact> {
    @Override
    public boolean validate(Contact contact) throws DataValidationException {
        if(contact.getId() == null){
            throw new DataValidationException("contact don't have id");

        }
        FullName fullName = contact.getFullName();
        if(fullName.getLastName() == null || fullName.getLastName().length() > 30){
            throw new DataValidationException("contact have incorrect last name (to long or missing)");
        }
        if(fullName.getFirstName() == null || fullName.getFirstName().length() > 30){
            throw new DataValidationException("contact have incorrect first name (to long or missing)");
        }
        if(fullName.getParentName() == null || fullName.getParentName().length() > 30){
            throw new DataValidationException("contact have incorrect parent name (to long or missing)");
        }
        if(contact.getSex() == null){
            throw new DataValidationException("contact don't have correct sex");
        }
        if(contact.getNationality() != null && contact.getNationality().length() > 30){
            throw new DataValidationException("contact have incorrect nationality value (to long)");
        }

        if(contact.getWebsite() != null && contact.getWebsite().length() > 50){
            throw new DataValidationException("contact have incorrect website (to long value)");
        }

        if(contact.getEmail() == null || contact.getEmail().length() > 50){
            throw new DataValidationException("contact have incorrect email value (to long or missing)");
        }
        Address address = contact.getAddress();
        if (address.getCountry() != null && address.getCountry().length() > 100){
            throw new DataValidationException("contact have incorrect country value (to long)");
        }
        if (address.getCity() != null && address.getCity().length() > 50){
            throw new DataValidationException("contact have incorrect city value (to long)");
        }
        if (address.getStreet() != null && address.getStreet().length() > 50){
            throw new DataValidationException("contact have incorrect street value (to long)");
        }
        if (address.getHouse() != null && address.getHouse().length() > 10){
            throw new DataValidationException("contact have incorrect house value (to long)");
        }
        if(contact.getBirthDate() == null){
            throw new DataValidationException("contact have incorrect birth date value (missing)");
        }
        if(contact.getAvatar().getPath().isEmpty()){
            contact.getAvatar().setPath(FileManager.getInstance().getDefaultAvatarPath());
        }
        return true;
    }
}
