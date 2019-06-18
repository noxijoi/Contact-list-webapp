package contactsapp.utils;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Properties;



public class PropertyManagerTest {
    String name = "db.properties";

    @Test
    public void testGetProperties() throws IOException {
        Properties properties = PropertyManager.readProperties(name);
        Assert.assertFalse(properties.isEmpty());
    }


}