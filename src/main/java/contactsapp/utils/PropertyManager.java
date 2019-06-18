package contactsapp.utils;

import java.io.FileInputStream;

import java.io.IOException;
import java.util.Properties;

public class PropertyManager {
    public static Properties readProperties(String name) throws IOException {
        Properties properties = new Properties();
        String path = PropertyManager.class.getClassLoader().getResource(name).getPath();
        FileInputStream fis = new FileInputStream(path);
        properties.load(fis);
        return properties;
    }
}
