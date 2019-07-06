package contactsapp.utils.mail;

public class Template {
    private String name;
    private String subject;
    private String message;

    public Template(String name, String subject, String message) {
        this.name = name;
        this.subject = subject;
        this.message = message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
