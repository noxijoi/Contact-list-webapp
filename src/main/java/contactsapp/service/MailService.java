package contactsapp.service;

import contactsapp.core.entity.Contact;
import contactsapp.utils.mail.MailParam;
import contactsapp.utils.PropertiesManager;
import contactsapp.utils.mail.Template;
import contactsapp.utils.mail.TemplateHandler;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class MailService {

    public void sendMessage(MailParam params) throws MessagingException, IOException {

        Properties mailProp = PropertiesManager.getMailProperties();
        String fromEmail = mailProp.getProperty("mail.sender");
        String password = mailProp.getProperty("mail.password");

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        Session session = Session.getInstance(props, auth);

        MimeMessage msg = new MimeMessage(session);
        msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
        msg.addHeader("format", "flowed");
        msg.addHeader("Content-Transfer-Encoding", "8bit");

        msg.setFrom(new InternetAddress(fromEmail, "Maskaliova Contact App"));

        msg.setReplyTo(InternetAddress.parse(fromEmail, false));

        msg.setSentDate(new Date());
        Template template = params.getTemplate();
        List<Contact> receivers = params.getReceivers();
        for (int i = 0; i < receivers.size(); i++) {
            Contact receiver = receivers.get(i);
            msg.setSubject(template.getSubject(), "UTF-8");
            if (template.getName() != null) {
                String text = new TemplateHandler().generateMessageForContact(template.getName(), receiver);
                msg.setText(text, "UTF-8");
            } else {
                msg.setText(template.getMessage(), "UTF-8");
            }
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receivers.get(i).getEmail()));
            Transport.send(msg);
        }

    }

    public List<Template> getTemplates() {
        return new TemplateHandler().getAllTemplates();
    }
}
