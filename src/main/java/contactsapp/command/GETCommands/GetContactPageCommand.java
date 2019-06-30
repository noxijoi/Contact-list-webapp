package contactsapp.command.GETCommands;

import com.fasterxml.jackson.core.JsonProcessingException;
import contactsapp.command.Command;
import contactsapp.command.GETCommands.dto.PageDto;
import contactsapp.command.GETCommands.dto.PageInfo;
import contactsapp.core.entity.Contact;
import contactsapp.dao.DaoException;
import contactsapp.service.ContactService;
import contactsapp.utils.serialization.JSONParser;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class GetContactPageCommand implements Command {
    private final String FIRST_NAME ="firstName";
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
        Map params = req.getParameterMap();
        try {
            if (params.size() > 1) {
                ContactService service = new ContactService();
                List<Contact> contacts = service.selectAll();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                params.forEach((key, value)->{
                    switch (key.toString()){
                        case "firstName":
                            contacts.removeIf(contact -> contact.getFullName().getFirstName()== null &&
                                    !contact.getFullName().getFirstName().equals(value));
                            break;
                        case "lastName":
                            contacts.removeIf(contact -> contact.getFullName().getLastName()== null &&
                                    !contact.getFullName().getLastName().equals(value));
                            break;
                        case "parentName":
                            contacts.removeIf(contact -> contact.getFullName().getParentName() == null &&
                                    !contact.getFullName().getParentName().equals(value));
                            break;
                        case "dateFrom":
                            contacts.removeIf(contact -> contact.getBirthDate() == null &&
                                    contact.getBirthDate().isBefore(LocalDate.parse(value.toString())));
                            break;
                        case "dateTo":
                            contacts.removeIf(contact -> contact.getBirthDate() == null &&
                                    contact.getBirthDate().isBefore(LocalDate.parse(value.toString())));
                            break;
                        case "sex":
                            contacts.removeIf(contact -> contact.getSex() == null &&
                                    !contact.getSex().toString().equals(value));
                            break;
                        case "company":
                            contacts.removeIf(contact -> contact.getCompany()== null &&
                                    !contact.getCompany().equals(value));
                            break;
                        case "website":
                            contacts.removeIf(contact -> contact.getWebsite() == null &&
                                    !contact.getWebsite().equals(value));
                            break;
                        case "email":
                            contacts.removeIf(contact -> !contact.getEmail().equals(value));
                            break;
                        case "country":
                            contacts.removeIf(contact -> !contact.getAddress().getCountry().equals(value));
                            break;
                        case "city":
                            contacts.removeIf(contact -> !contact.getAddress().getCountry().equals(value));
                            break;
                        case "street":
                            contacts.removeIf(contact -> !contact.getAddress().getCity().equals(value));
                            break;
                        case "index":
                            contacts.removeIf(contact -> !contact.getAddress().getIndex().equals(value));
                            default:
                                break;

                    }
                });

            } else {
                ContactService service = new ContactService();
                List<Contact> contacts = service.getPage(pageN, pageSize);

                int recordsNum = service.getRecordsNum();
                PageDto dto = new PageDto();
                dto.contacts = contacts;
                PageInfo pageInfo = new PageInfo(pageN, recordsNum, pageSize);
                dto.pageInfo = pageInfo;
                String resultJSON = JSONParser.toJson(dto);

                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(resultJSON);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
