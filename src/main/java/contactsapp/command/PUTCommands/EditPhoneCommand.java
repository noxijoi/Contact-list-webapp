package contactsapp.command.PUTCommands;

import contactsapp.command.Command;
import contactsapp.core.entity.Phone;
import contactsapp.service.PhoneService;
import contactsapp.utils.serialization.JSONParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditPhoneCommand implements Command {
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
