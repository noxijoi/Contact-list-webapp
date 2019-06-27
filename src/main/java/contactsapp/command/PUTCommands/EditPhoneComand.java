package contactsapp.command.PUTCommands;

import contactsapp.command.Command;
import contactsapp.core.entity.Contact;
import contactsapp.core.entity.Phone;
import contactsapp.service.ContactService;
import contactsapp.service.PhoneService;
import contactsapp.service.Service;
import contactsapp.utils.JSONParser;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditPhoneComand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            PhoneService service = new PhoneService();
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = req.getReader().readLine()) != null) {
                sb.append(s);
            }
            Phone phone = JSONParser.parsePhone(sb.toString());
            service.update(phone);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
