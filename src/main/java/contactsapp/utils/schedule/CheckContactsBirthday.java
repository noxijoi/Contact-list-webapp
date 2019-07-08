package contactsapp.utils.schedule;

import contactsapp.core.entity.Contact;
import contactsapp.dao.DaoException;
import contactsapp.utils.mail.MailParam;
import contactsapp.service.ContactService;
import contactsapp.service.MailService;
import contactsapp.utils.PropertiesManager;
import contactsapp.utils.mail.Template;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.mail.MessagingException;
import javax.naming.NamingException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class CheckContactsBirthday implements Job {
    private final static Logger LOGGER = LogManager.getLogger(CheckContactsBirthday.class);

    private final String positiveMessage = "Dear Admin, today next contacts have birth day:";
    private final String negativeMessage = "Dear Admin, no birthday boys in our contact list today.";

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        int month = LocalDate.now().getMonthValue();
        int day = LocalDate.now().getDayOfMonth();
        List<Contact> birthdayBoys = new ArrayList<>();
        try {
            ContactService service = new ContactService();
            birthdayBoys = service.getContactsBorn(month, day);

            String message = "";
            if (birthdayBoys.isEmpty()) {
                message = negativeMessage;
            } else {
                message = positiveMessage;
                for (Contact birthdayBoy : birthdayBoys) {
                    message += ", " + birthdayBoy.getEmail();
                }
            }
            MailService mailService = new MailService();
            Properties properties = PropertiesManager.readProperties("mail.properties");
            String adminMail = properties.getProperty("mail.adminmail");
            String subject = "birthDays";
            Contact admin = new Contact();
            admin.setEmail(adminMail);
            Template template = new Template(null, "birth days", message);
            MailParam mailParam = new MailParam(Arrays.asList(admin), template);
            mailService.sendMessage(mailParam);
        } catch (IOException e) {
            LOGGER.error(e);
        } catch (NamingException e) {
            LOGGER.error(e);
        } catch (MessagingException e) {
            LOGGER.error(e);
        } catch (DaoException e) {
            LOGGER.error(e);
        }


    }
}
