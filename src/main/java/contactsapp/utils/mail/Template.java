package contactsapp.utils.mail;

public class Template {
    private String name;
    private String subject;
    private String messageTemplate;

    public Template(String name, String subject, String messageTemplate) {
        this.name = name;
        this.subject = subject;
        this.messageTemplate = messageTemplate;
    }

    public Template(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }

    public void setMessageTemplate(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }
}
