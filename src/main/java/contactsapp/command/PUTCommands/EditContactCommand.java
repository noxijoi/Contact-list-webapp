package contactsapp.command.PUTCommands;

import contactsapp.command.Command;
import contactsapp.core.entity.Contact;
import contactsapp.service.ContactService;
import contactsapp.utils.serialization.JSONParser;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditContactCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            ContactService service = new ContactService();
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = req.getReader().readLine()) != null) {
                sb.append(s);
            }
            Contact contact = JSONParser.parseContact(sb.toString());
            service.update(contact);
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
