package contactsapp.validation;

import contactsapp.core.entity.Attachment;

public class AttachmentValidator implements Validator<Attachment> {
    @Override
    public boolean validate(Attachment attachment) throws DataValidationException {
        if(attachment.getId() == null){
            throw new DataValidationException("Attachment don't have id");
        }
        if(attachment.getOwnerId() == null){
            throw new DataValidationException("Attachment don't have owner id");
        }
        if(attachment.getFileName() == null || attachment.getFileName().length() > 100){
            throw new DataValidationException("Attachment have incorrect file name");
        }
        if (attachment.getFilePath() == null || attachment.getFilePath().length() > 200){
            throw new DataValidationException("Attachment have incorrect file path");
        }
        if (attachment.getDownloadTime() == null){
            throw new DataValidationException("Attachment don't have download time");
        }
        if (attachment.getComment() != null && attachment.getComment().length() > 200){
            throw new DataValidationException("Attachment have to long comment");
        }
        return true;
    }
}
