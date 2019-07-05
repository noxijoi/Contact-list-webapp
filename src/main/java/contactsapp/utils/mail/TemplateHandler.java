package contactsapp.utils.mail;

import contactsapp.core.entity.Contact;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

public class TemplateHandler {
    private String templateFile = "mailtemplates.stg";



    public String generateMessageForContact(String templateName, Contact contact){

        STGroup group = new STGroupFile(TemplateHandler.class.getClassLoader().getResource(templateFile).getPath());
        ST st = group.getInstanceOf(templateName);
        st.add("name", contact.getFullName().toString());
        return st.render();

    }


}
