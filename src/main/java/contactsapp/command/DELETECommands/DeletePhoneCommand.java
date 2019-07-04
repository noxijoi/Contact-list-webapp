package contactsapp.command.DELETECommands;

import contactsapp.command.Command;
import contactsapp.core.entity.Contact;
import contactsapp.core.entity.Phone;
import contactsapp.service.PhoneService;
import contactsapp.utils.serialization.JSONParser;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class DeletePhoneCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(DeletePhoneCommand.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            PhoneService service = new PhoneService();
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = req.getReader().readLine()) != null) {
                sb.append(s);
            }
            List<Phone> list = JSONParser.parseListPhones(sb.toString());
            service.delete(list);
            LOGGER.info("delete "+ list.size() + " contacts");
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }
}
