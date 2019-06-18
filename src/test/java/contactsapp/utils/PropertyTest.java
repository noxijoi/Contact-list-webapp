package contactsapp.utils;

import org.junit.Test;

import java.util.Properties;

public class PropertyTest {
   @Test
   public void shouldReadProperty(){
       Properties dbProp = new Properties();
       String path = PropertyTest.class.getClassLoader().getResource("db.properties").getPath();
   }
}
