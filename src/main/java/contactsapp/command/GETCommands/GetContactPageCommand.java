package contactsapp.command.GETCommands;

import contactsapp.command.Command;
import contactsapp.command.GETCommands.dto.PageDto;
import contactsapp.command.GETCommands.dto.PageInfo;
import contactsapp.command.GETCommands.dto.ShortContact;
import contactsapp.core.entity.Contact;
import contactsapp.dao.DaoException;
import contactsapp.service.ContactService;
import contactsapp.utils.JSONParser;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetContactPageCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        String uri = req.getRequestURI();
        int pageSize = Integer.parseInt(req.getParameter("size"));
        String[] parts = uri.split("/");
        Integer pageN = null;
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals("page")){
                pageN = Integer.parseInt(parts[i+1]);
            }        }

        try{
            ContactService service = new ContactService();
            List<Contact> contacts = service.getPage(pageN, pageSize);

            int recordsNum = service.getRecordsNum();
            PageDto dto = new PageDto();
            dto.contacts = contacts;
            PageInfo pageInfo = new PageInfo(pageN, recordsNum, pageSize);
            dto.pageInfo = pageInfo;
            String resultJSON = JSONParser.toJson(dto);
            resp.getWriter().write(resultJSON);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
