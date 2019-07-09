package contactsapp.command.POSTCommands;

import contactsapp.command.Command;
import contactsapp.utils.mail.MailParam;
import contactsapp.service.MailService;
import contactsapp.utils.serialization.JSONParser;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SendEmailCommand implements Command {
    private static Logger LOGGER = LogManager.getLogger(SendEmailCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = req.getReader().readLine()) != null) {
                sb.append(s);
            }

            MailParam params = JSONParser.parseObject(sb.toString());
            MailService service = new MailService();
            service.sendMessage(params);
            LOGGER.info("send "+ params.getReceivers().size() + " messages");
        } catch (IOException e) {
            LOGGER.error(e);
        } catch (MessagingException e) {
            try {
                resp.getWriter().write("invalid email address");
            } catch (IOException e1) {
                LOGGER.error(e1);
                LOGGER.warn(e);
            }
        }
    }
}
