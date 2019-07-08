package contactsapp.command.GETCommands;

import com.fasterxml.jackson.core.JsonProcessingException;
import contactsapp.command.Command;
import contactsapp.service.MailService;
import contactsapp.utils.mail.Template;
import contactsapp.utils.serialization.JSONParser;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetMailTemplatesCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(GetContactPageCommand.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            MailService service = new MailService();
            List<Template> templates = service.getTemplates();
            JSONParser parser = new JSONParser();
            String json = parser.templateListToJSON(templates);
            resp.setCharacterEncoding("utf-8");
            resp.getWriter().write(json);
        } catch (JsonProcessingException e) {
            resp.setStatus(400);
            LOGGER.warn(e);
        } catch (IOException e) {
            LOGGER.error(e);
        }

    }
}
