package contactsapp.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import contactsapp.core.entity.Attachment;
import contactsapp.core.entity.Contact;

import java.io.IOException;
import java.util.List;

public class JSONParser {
    public Contact parseContact(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Contact contact = mapper.readValue(json, Contact.class);
        return contact;
    }

    public String contactToJSON(Contact contact) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(contact);
        return json;
    }
    public String contactListToJson(List<Contact> contactList) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(contactList);
        return json;
    }

    public List<Contact> parseListContact(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Contact> contactList = mapper.readValue(json, new TypeReference<List<Contact>>() {});
        return contactList;
    }
    public Attachment parseAttachment(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Attachment attachment = mapper.readValue(json, Attachment.class);
        return attachment;
    }


}
