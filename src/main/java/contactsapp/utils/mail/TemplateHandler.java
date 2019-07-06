package contactsapp.utils.mail;

import contactsapp.core.entity.Contact;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.util.*;

public class TemplateHandler {
    private String templateFile = "mailtemplates.stg";

    private Map<String, String> temptlateSubjectMap= new HashMap<>();

    public TemplateHandler(){
        temptlateSubjectMap.put("/autowin", "выигрыш призов");
        temptlateSubjectMap.put("/invitation", "приглашение на курсы");
    }



    public String generateMessageForContact(String templateName, Contact contact){

        STGroup group = new STGroupFile(TemplateHandler.class.getClassLoader().getResource(templateFile).getPath());
        ST st = group.getInstanceOf(templateName);
        st.add("name", contact.getFullName());
        return st.render();

    }

    public List<Template> getAllTemplates(){
        STGroup group = new STGroupFile(TemplateHandler.class.getClassLoader().getResource(templateFile).getPath());
        List<Template> templates = new ArrayList<>();
        Set<String> names = group.getTemplateNames();
        Contact emptyContact = new Contact();
        for (String name : names) {
            String subj =  temptlateSubjectMap.get(name);
            String text = generateMessageForContact(name, emptyContact);
            Template templ = new Template(name, subj, text);
            templates.add(templ);
        }
        return templates;
    }


}
