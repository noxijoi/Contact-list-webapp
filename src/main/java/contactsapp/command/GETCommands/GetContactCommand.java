package contactsapp.command.GETCommands;

import contactsapp.command.Command;
import contactsapp.core.entity.Contact;
import contactsapp.service.ContactService;
import contactsapp.utils.JSONParser;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetContactCommand implements Command {
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
            JSONParser parser = new JSONParser();
            String contactJson = parser.contactToJSON(contact);
            resp.getWriter().write(contactJson);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
