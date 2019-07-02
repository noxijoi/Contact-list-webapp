package contactsapp.command.GETCommands;

import contactsapp.command.Command;
import contactsapp.command.GETCommands.dto.FullContactInfo;
import contactsapp.core.entity.Attachment;
import contactsapp.core.entity.Contact;
import contactsapp.core.entity.Phone;
import contactsapp.service.AttachmentService;
import contactsapp.service.ContactService;
import contactsapp.service.PhoneService;
import contactsapp.utils.serialization.JSONParser;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetContactCommand implements Command {
    private final static Logger LOGGER = LogManager.getLogger(GetContactCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        String uri = req.getRequestURI();
        String[] parts = uri.split("/");
        Integer contactN = null;
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals("contacts")){
                contactN = Integer.parseInt(parts[i+1]);
            }
        }
        try {
            ContactService service = new ContactService();
            Contact contact = service.getById(contactN);


            AttachmentService attachmentService = new AttachmentService();
            List<Attachment> attachs = attachmentService.getByOwnerId(contactN);

            PhoneService phoneService = new PhoneService();
            List<Phone> phones = phoneService.getByOwnerId(contactN);

            FullContactInfo fullContactInfo = new FullContactInfo(contact, phones, attachs);

            JSONParser parser = new JSONParser();
            String contactJson = parser.toJson(fullContactInfo);
            resp.setCharacterEncoding("utf-8");
            resp.getWriter().write(contactJson);
        } catch (IOException e) {
            LOGGER.error(e);
        } catch (NamingException e) {
            LOGGER.warn(e);
        }
    }


}
