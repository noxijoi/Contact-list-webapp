package contactsapp.validation;

import contactsapp.core.entity.Attachment;

public class AttachmentValidator implements Validator<Attachment> {
    @Override
    public boolean validate(Attachment attachment) {
        if(attachment.getId() == null){
            return false;
        }
        if(attachment.getOwnerId() == null){
            return false;
        }
        if(attachment.getFileName() == null || attachment.getFileName().length() > 100){
            return false;
        }
        if (attachment.getFilePath() == null || attachment.getFilePath().length() > 200){
            return false;
        }
        if (attachment.getDownloadTime() == null){
            return false;
        }
        if (attachment.getComment() != null && attachment.getComment().length() >200){
            return false;
        }
        return true;
    }
}
