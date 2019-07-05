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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GetContactPageCommand implements Command {
    private final static Logger LOGGER = LogManager.getLogger(GetContactPageCommand.class);

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
                contacts = service.selectAll();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                List<Contact> finalContacts = contacts;
                params.forEach((key, value) -> {
                    switch (key) {
                        case "firstName":
                            finalContacts.removeIf(contact -> (contact.getFullName().getFirstName() == null) ||
                                    !contact.getFullName().getFirstName().startsWith(value[0]));
                            break;
                        case "lastName":
                            finalContacts.removeIf(contact -> contact.getFullName().getLastName() == null ||
                                    !contact.getFullName().getLastName().startsWith(value[0]));
                            break;
                        case "parentName":
                            finalContacts.removeIf(contact -> contact.getFullName().getParentName() == null ||
                                    !contact.getFullName().getParentName().startsWith(value[0]));
                            break;
                        case "dateFrom":
                            finalContacts.removeIf(contact -> contact.getBirthDate() == null ||
                                    contact.getBirthDate().isBefore(LocalDate.parse(value[0].toString())));
                            break;
                        case "dateTo":
                            finalContacts.removeIf(contact -> contact.getBirthDate() == null ||
                                    contact.getBirthDate().isBefore(LocalDate.parse(value[0].toString())));
                            break;
                        case "sex":
                            finalContacts.removeIf(contact -> contact.getSex() == null ||
                                    !contact.getSex().toString().equals(value[0]));
                            break;
                        case "company":
                            finalContacts.removeIf(contact -> contact.getCompany() == null ||
                                    !contact.getCompany().startsWith(value[0]));
                            break;
                        case "website":
                            finalContacts.removeIf(contact -> contact.getWebsite() == null ||
                                    !contact.getWebsite().startsWith(value[0]));
                            break;
                        case "email":
                            finalContacts.removeIf(contact -> contact.getEmail() == null ||
                                    !contact.getEmail().startsWith(value[0]));
                            break;
                        case "country":
                            finalContacts.removeIf(contact -> contact.getAddress().getCountry() == null ||
                                    !contact.getAddress().getCountry().startsWith(value[0]));
                            break;
                        case "city":
                            finalContacts.removeIf(contact -> contact.getAddress().getCountry() == null ||
                                    !contact.getAddress().getCountry().startsWith(value[0]));
                            break;
                        case "street":
                            finalContacts.removeIf(contact -> contact.getAddress().getCity() == null ||
                                    !contact.getAddress().getCity().startsWith(value[0]));
                            break;
                        case "index":
                            finalContacts.removeIf(contact -> contact.getAddress().getIndex() == null ||
                                    !contact.getAddress().getIndex().equals(value[0]));
                        default:
                            break;
                    }
                });
                contacts = finalContacts;
            } else {
                contacts = service.getPage(pageN, pageSize);
            }
            int recordsNum = service.getRecordsNum();
            PageDto dto = new PageDto();
            dto.contacts = contacts;
            PageInfo pageInfo = new PageInfo(pageN, recordsNum, pageSize);
            dto.pageInfo = pageInfo;
            String resultJSON = JSONParser.toJson(dto);

            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(resultJSON);
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
