package contactsapp.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import contactsapp.core.entity.*;
import contactsapp.utils.serialization.JSONParser;
import org.junit.Test;


public class JSONParserTest {
    Contact testContact = new Contact();
    JSONParser parser = new JSONParser();


    @Test
    public void shouldParseContactToString() throws JsonProcessingException {
        String json = parser.contactToJSON(testContact);
        System.out.println(json);
    }

}