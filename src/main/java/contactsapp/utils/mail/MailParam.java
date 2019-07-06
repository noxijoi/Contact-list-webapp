package contactsapp.utils.mail;

import contactsapp.core.entity.Contact;


import java.util.ArrayList;
import java.util.List;

public class MailParam {
    private List<Contact> receivers = new ArrayList<>();
    private Template template;

    public MailParam(){

    }

    public List<Contact> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<Contact> receivers) {
        this.receivers = receivers;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public MailParam(List<Contact> receivers, Template template) {
        this.receivers = receivers;
        this.template = template;
    }
}
