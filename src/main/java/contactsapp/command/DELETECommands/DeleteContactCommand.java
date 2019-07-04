package contactsapp.command.DELETECommands;

import contactsapp.command.Command;
import contactsapp.core.entity.Contact;
import contactsapp.service.ContactService;
import contactsapp.utils.serialization.JSONParser;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DeleteContactCommand implements Command {

    private final static Logger LOGGER = LogManager.getLogger(DeleteContactCommand.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            ContactService service = new ContactService();
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = req.getReader().readLine()) != null) {
                sb.append(s);
            }
            List<Contact> list = JSONParser.parseListContact(sb.toString());
            service.delete(list);
            LOGGER.info("delete "+ list.size() + " contacts");
        } catch (NamingException e) {
            LOGGER.warn(e);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }
}
