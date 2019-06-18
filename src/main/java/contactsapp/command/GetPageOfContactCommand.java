package contactsapp.command;

import contactsapp.core.entity.Contact;
import contactsapp.dao.DaoException;
import contactsapp.service.ContactService;
import contactsapp.utils.JSONParser;
import contactsapp.utils.URLParser;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class GetPageOfContactCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        String url = req.getRequestURI();
        List<String> list = URLParser.parseURLPath(url);
        int lastRecordId = Integer.parseInt(req.getParameter("lastRecId"));
        int pageSize =  Integer.parseInt(req.getParameter("pageSize"));
        try {
            ContactService service = new ContactService();
            List<Contact> page = service.getPage(pageSize, lastRecordId);
            JSONParser parser = new JSONParser();
            String jsonContacts = parser.contactListToJson(page);
            resp.getWriter().write(jsonContacts);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            // какие татусы ставить,прям сложно
            resp.setStatus(400);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
