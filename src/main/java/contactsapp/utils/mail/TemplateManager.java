package contactsapp.utils.mail;

import java.util.HashMap;
import java.util.Map;

public class TemplateManager {
    private final String AUTOWIN ="autowin";
    private final String AUTOWIN_SUBJECT = "победа в конкурсе";

    private final String INVITATION = "invitation";
    private final String INVITATION_SUBJECT = "приглашение на курсы";


    private Map<String, String> templateSubject;

    public TemplateManager() {
        templateSubject = new HashMap<>();

        templateSubject.put(AUTOWIN, AUTOWIN_SUBJECT);
        templateSubject.put(INVITATION, INVITATION_SUBJECT);
    }

    public String getSubject(String templateName) {
        return templateSubject.get(templateName);
    }
}
