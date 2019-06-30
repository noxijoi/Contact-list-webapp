package contactsapp.command.POSTCommands;

import contactsapp.command.Command;
import contactsapp.core.entity.MailParam;
import contactsapp.utils.serialization.JSONParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SendEmailCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = req.getReader().readLine()) != null) {
                sb.append(s);
            }

            MailParam params = JSONParser.parseObject(sb.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
