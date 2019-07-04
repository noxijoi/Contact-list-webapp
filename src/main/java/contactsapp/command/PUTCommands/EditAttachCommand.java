package contactsapp.command.PUTCommands;

import contactsapp.command.Command;
import contactsapp.core.entity.Attachment;
import contactsapp.core.entity.Contact;
import contactsapp.service.AttachmentService;
import contactsapp.utils.serialization.JSONParser;
import contactsapp.validation.AttachmentValidator;
import contactsapp.validation.DataValidationException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class EditAttachCommand implements Command {
    private final static Logger LOGGER = LogManager.getLogger(EditAttachCommand.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        try{
            AttachmentService service = new AttachmentService();
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = req.getReader().readLine()) != null) {
                sb.append(s);
            }
            Attachment attachment = JSONParser.parseAttachment(sb.toString());
            AttachmentValidator validator = new AttachmentValidator();
            validator.validate(attachment);
            service.update(attachment);
        } catch (IOException e) {
            LOGGER.error(e);
        } catch (DataValidationException e) {
            resp.setStatus(400);
            LOGGER.error(e);
        }
    }
}
