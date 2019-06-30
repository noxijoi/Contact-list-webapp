package contactsapp.service;

import contactsapp.core.entity.MailParam;
import contactsapp.utils.PropertyManager;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class MailService {
    public void sendMessage(MailParam params){
        try {
            Properties mailProp = PropertyManager.getMailProperties();
            Properties sysProp = System.getProperties();
            sysProp.setProperty("mail.smtp.host", mailProp.getProperty("mail.host"));

            Session session = Session.getDefaultInstance(sysProp);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailProp.getProperty("mail.sender")));

            List<String> emails = params.getReceivers();
            for (String email : emails) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            }

            message.setSubject(params.getSubject());
            message.setText(params.getMessage());

            Transport.send(message);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
