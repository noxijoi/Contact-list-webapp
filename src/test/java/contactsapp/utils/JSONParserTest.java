package contactsapp.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import contactsapp.core.entity.*;
import org.junit.Test;

import java.util.Date;
import java.util.GregorianCalendar;


public class JSONParserTest {
    Contact testContact = new Contact();
    JSONParser parser = new JSONParser();


    @Test
    public void shouldParseContactToString() throws JsonProcessingException {
        String json = parser.contactToJSON(testContact);
        System.out.println(json);
    }

}