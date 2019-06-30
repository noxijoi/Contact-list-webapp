package contactsapp.core.entity;

import java.util.List;

public class MailParam {
    private List<String> receivers;
    private String message;
    private String subject;

    public MailParam(){

    }

    public MailParam(List<String> receivers, String message, String subject) {
        this.receivers = receivers;
        this.message = message;
        this.subject = subject;
    }

    public List<String> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<String> receivers) {
        this.receivers = receivers;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
