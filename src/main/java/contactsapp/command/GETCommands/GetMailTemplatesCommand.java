package contactsapp.command.GETCommands;

import com.fasterxml.jackson.core.JsonProcessingException;
import contactsapp.command.Command;
import contactsapp.service.MailService;
import contactsapp.utils.mail.Template;
import contactsapp.utils.serialization.JSONParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetMailTemplatesCommand implements Command {
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
