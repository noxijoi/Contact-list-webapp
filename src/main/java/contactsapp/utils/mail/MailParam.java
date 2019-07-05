package contactsapp.utils.mail;

import contactsapp.core.entity.Contact;

import java.util.ArrayList;
import java.util.List;

public class MailParam {
    private List<Contact> receivers = new ArrayList<>();
    private String templateName;
    private List<String> messages = new ArrayList<>();
    private String subject;

    public MailParam(){

    }

    public MailParam(List<Contact> receivers, String templateName){
        this.receivers = receivers;
        TemplateHandler handler = new TemplateHandler();
        receivers.forEach(receiver ->
                this.messages.add(handler.generateMessageForContact(templateName, receiver)));
        TemplateManager manager = new TemplateManager();
        this.subject = manager.getSubject(templateName);
    }
    public MailParam(List<Contact> receivers, List<String> messages, String subject) {
        this.receivers = receivers;
        this.messages = messages;
        this.subject = subject;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public List<Contact> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<Contact> receivers) {
        this.receivers = receivers;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
