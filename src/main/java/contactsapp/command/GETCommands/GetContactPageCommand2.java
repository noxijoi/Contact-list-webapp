package contactsapp.command.GETCommands;

import com.fasterxml.jackson.core.JsonProcessingException;
import contactsapp.command.Command;
import contactsapp.command.GETCommands.dto.PageDto;
import contactsapp.command.GETCommands.dto.PageInfo;
import contactsapp.core.entity.Contact;
import contactsapp.dao.DaoException;
import contactsapp.service.ContactService;
import contactsapp.utils.serialization.JSONParser;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetContactPageCommand2 implements Command {
    private static final Logger LOGGER = LogManager.getLogger(GetContactPageCommand2.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        String uri = req.getRequestURI();
        int pageSize = Integer.parseInt(req.getParameter("size"));
        String[] parts = uri.split("/");
        Integer pageN = null;
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals("page")) {
                pageN = Integer.parseInt(parts[i + 1]);
            }
        }
        try {
            Map<String, String[]> params = req.getParameterMap();
            List<Contact> contacts = new ArrayList<>();
            ContactService service = new ContactService();
            if (params.size() > 1) {
                String conditionStr;
                List<String> conditions = new ArrayList<>();
                params.forEach((key, value) -> {
                    switch (key) {
                        case "firstName":
                            conditions.add(" f_name LIKE " + "'" + value[0] + "%' ");
                            break;
                        case "lastName":
                            conditions.add(" l_name LIKE " + "'" + value[0] + "%' ");
                            break;
                        case "parentName":
                            conditions.add(" p_name LIKE " + "'" + value[0] + "%' ");
                            break;
                        case "dateFrom":
                            conditions.add(" b_date >= " + "'" + value[0] + "' ");
                            break;
                        case "dateTo":
                            conditions.add(" b_date <= " + "'" + value[0] + "' ");
                            break;
                        case "sex":
                            conditions.add(" sex = " + "'" + value[0] + "' ");
                            break;
                        case "company":
                            conditions.add(" company LIKE " + "'" + value[0] + "%' ");
                            break;
                        case "website":
                            conditions.add(" nationality LIKE " + "'" + value[0] + "%' ");
                            break;
                        case "email":
                            conditions.add(" email LIKE " + "'" + value[0] + "%' ");
                            break;
                        case "country":
                            conditions.add(" country LIKE " + "'" + value[0] + "%' ");
                            break;
                        case "city":
                            conditions.add(" city LIKE " + "'" + value[0] + "%' ");
                            break;
                        case "street":
                            conditions.add(" street LIKE " + "'" + value[0] + "%' ");
                            break;
                        case "index":
                            conditions.add(" index = " + "'" + value[0] + "%' ");
                            break;
                        default:
                            break;
                    }
                });

                String[] arr = conditions.toArray(new String[0]);

                String limit = " LIMIT " + ((pageN - 1) * pageSize) + " , " + pageSize;
                String order = " ORDER BY id";
                conditionStr = String.join(" AND ", arr);
                String query = "SELECT * FROM contact WHERE " + conditionStr + order + limit;
                contacts = service.executeSelectQuery(query);

                PageDto dto = new PageDto();
                dto.contacts = contacts;

                int count = service.getRecordsNum("SELECT COUNT(*) FROM contact WHERE " + conditionStr);
                PageInfo pageInfo = new PageInfo(pageN, count, pageSize);
                dto.pageInfo = pageInfo;
                String resultJSON = JSONParser.toJson(dto);
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(resultJSON);
            } else {
                contacts = service.getPage(pageN, pageSize);

                PageDto dto = new PageDto();
                int recordsNum = service.getRecordsNum();
                dto.contacts = contacts;
                PageInfo pageInfo = new PageInfo(pageN, recordsNum, pageSize);
                dto.pageInfo = pageInfo;
                String resultJSON = JSONParser.toJson(dto);
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(resultJSON);
            }
        } catch (JsonProcessingException e) {
            LOGGER.error(e);
        } catch (IOException e) {
            LOGGER.error(e);
        } catch (DaoException e) {
            LOGGER.error(e);
        } catch (NamingException e) {
            LOGGER.error(e);
        }
    }
}
