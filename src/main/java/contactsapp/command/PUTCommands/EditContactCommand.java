package contactsapp.command.PUTCommands;

import contactsapp.command.Command;
import contactsapp.core.entity.Contact;
import contactsapp.dao.DaoException;
import contactsapp.service.ContactService;
import contactsapp.utils.FileManager;
import contactsapp.utils.serialization.JSONParser;
import contactsapp.validation.ContactValidator;
import contactsapp.validation.DataValidationException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.LinkOption;
import java.time.LocalDate;

public class EditContactCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(EditContactCommand.class);
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
            FileManager.getInstance().writeImg(contact.getAvatar());
            ContactValidator validator = new ContactValidator();
            validator.validate(contact);
            service.update(contact);
        } catch (NamingException e) {
            LOGGER.error(e);
        } catch (IOException e) {
            LOGGER.error(e);
        } catch (DataValidationException e) {
            resp.setStatus(400);
            LOGGER.warn(e);
        } catch (DaoException e) {
            LOGGER.error(e);
        }
    }
}
