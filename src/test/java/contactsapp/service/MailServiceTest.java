package contactsapp.service;

import contactsapp.core.entity.Contact;
import contactsapp.core.entity.FullName;
import contactsapp.core.entity.MaritalStatus;
import contactsapp.core.entity.Sex;
import contactsapp.utils.mail.MailParam;
import contactsapp.utils.mail.Template;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;

class MailServiceTest {
    private static final Contact contact = new Contact(1, new FullName("Mary","Mas","MMM"),
            LocalDate.now(), Sex.FEMALE, "bel", MaritalStatus.SINGLE,"","noxijoi@gmial.com","",null);
    private static final MailParam mailParams = new MailParam(Arrays.asList(contact),new Template(null, "simpleMsg", "mmmsssgg"));


    @Test
    void testSendMessage() throws IOException, MessagingException {
        MailService mailService = new MailService();
        mailService.sendMessage(mailParams);
    }
}