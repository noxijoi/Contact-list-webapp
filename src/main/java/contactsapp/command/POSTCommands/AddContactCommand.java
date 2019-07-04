package contactsapp.command.POSTCommands;

import contactsapp.command.Command;
import contactsapp.core.entity.Contact;
import contactsapp.service.ContactService;
import contactsapp.utils.FileManager;
import contactsapp.utils.serialization.JSONParser;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddContactCommand  implements Command {

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
            if(contact.getAvatar().getPath() == null){
                contact.getAvatar().setPath(FileManager.getInstance().getDefaultAvatarPath());
            }
            FileManager.getInstance().writeImg(contact.getAvatar());
            service.insert(contact);
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
