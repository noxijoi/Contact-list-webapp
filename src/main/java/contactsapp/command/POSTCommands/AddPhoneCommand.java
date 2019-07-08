package contactsapp.command.POSTCommands;

import contactsapp.command.Command;
import contactsapp.core.entity.Phone;
import contactsapp.dao.DaoException;
import contactsapp.service.PhoneService;
import contactsapp.utils.serialization.JSONParser;
import contactsapp.validation.DataValidationException;
import contactsapp.validation.PhoneValidator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddPhoneCommand implements Command {
    private final static Logger LOGGER = LogManager.getLogger(AddPhoneCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        try{
            PhoneService service = new PhoneService();
            StringBuilder sb = new StringBuilder();
            String s;
            String[] parts = req.getRequestURI().split("/");
            Integer ownerId = null;
            for (int i = 0; i < parts.length; i++) {
                if (parts[i].equals("contacts")){
                    ownerId = Integer.parseInt(parts[i+1]);
                }
            }
            while ((s = req.getReader().readLine()) != null) {
                sb.append(s);
            }
            Phone phone = JSONParser.parsePhone(sb.toString());
            phone.setOwnerId(ownerId);
            PhoneValidator validator= new PhoneValidator();
            validator.validate(phone);
            service.insert(phone);
            LOGGER.info("add 1 new phone");
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
