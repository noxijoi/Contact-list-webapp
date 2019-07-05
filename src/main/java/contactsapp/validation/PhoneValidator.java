package contactsapp.validation;

import contactsapp.core.entity.Phone;

public class PhoneValidator implements Validator<Phone> {
    @Override
    public boolean validate(Phone phone) throws DataValidationException {
        if(phone.getId() == null){throw new DataValidationException("Phone don't have id");}
        if(phone.getOwnerId() == null){ throw new DataValidationException("Phone don't owner id");}
        if(phone.getCountryCode() == null || phone.getCountryCode().length() > 5){
            throw new DataValidationException("Phone have incorrect country code(to long or missing)");
        }
        if(phone.getOperatorCode() == null || phone.getOperatorCode().length() > 5){
            throw new DataValidationException("Phone have incorrect operator code(to long or missing)");
        }
        if(phone.getNumber() == null || phone.getNumber().length() > 10){
            throw new DataValidationException("Phone have incorrect number (to long or missing)");
        }
        if(phone.getComment() != null && phone.getComment().length() > 255){
            throw new DataValidationException("Phone have incorrect comment(to long)");

        }
        return true;
    }
}
