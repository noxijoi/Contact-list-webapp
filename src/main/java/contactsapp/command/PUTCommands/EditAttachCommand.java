package contactsapp.command.PUTCommands;

import contactsapp.command.Command;
import contactsapp.core.entity.Attachment;
import contactsapp.core.entity.Contact;
import contactsapp.service.AttachmentService;
import contactsapp.utils.serialization.JSONParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditAttachCommand implements Command {
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
            service.update(attachment);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
