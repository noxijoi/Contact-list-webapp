package contactsapp.validation;

import contactsapp.core.entity.Phone;

public class PhoneValidator implements Validator<Phone> {
    @Override
    public boolean validate(Phone phone) {
        if(phone.getId() == null){return false;}
        if(phone.getOwnerId() == null){ return  false;}
        if(phone.getCountryCode() == null || phone.getCountryCode().length() > 5){return false;}
        if(phone.getOperatorCode() == null || phone.getOperatorCode().length() > 5){return false;}
        if(phone.getNumber() == null || phone.getNumber().length() > 10){return false;}
        if(phone.getComment() != null && phone.getComment().length() > 255);
        return true;
    }
}
