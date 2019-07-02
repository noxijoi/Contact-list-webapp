package contactsapp.utils;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Properties;



public class PropertiesManagerTest {
    String name = "db.properties";

    @Test
    public void testGetProperties() throws IOException {
        Properties properties = PropertiesManager.readProperties(name);
        Assert.assertFalse(properties.isEmpty());
    }


}