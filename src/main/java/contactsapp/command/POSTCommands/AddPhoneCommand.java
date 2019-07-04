package contactsapp.command.POSTCommands;

import contactsapp.command.Command;
import contactsapp.core.entity.Phone;
import contactsapp.service.PhoneService;
import contactsapp.utils.serialization.JSONParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddPhoneCommand implements Command {

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
                }        }
            while ((s = req.getReader().readLine()) != null) {
                sb.append(s);
            }
            Phone phone = JSONParser.parsePhone(sb.toString());
            phone.setOwnerId(ownerId);
            service.insert(phone);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
