package contactsapp.utils;

import java.io.FileInputStream;

import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {
    private static final String dbPropFile ="db.properties";
    private static final String attachPropFile ="attachments.properties";
    private static final String mailPropFile = "mail.properties";
    private static final String mailTemplatePropFile = "template.properties";

    public static Properties readProperties(String name) throws IOException {
        Properties properties = new Properties();
        String path = PropertiesManager.class.getClassLoader().getResource(name).getPath();
        FileInputStream fis = new FileInputStream(path);
        properties.load(fis);
        return properties;
    }
    public static Properties getDBProperties() throws IOException {
        return readProperties(dbPropFile);
    }

    public static Properties getTemplatesProperties() throws IOException {
        return readProperties(mailTemplatePropFile);
    }
    public static Properties getAttachProperties() throws IOException{
        return  readProperties(attachPropFile);
    }

    public static Properties getMailProperties() throws IOException {
        return readProperties(mailPropFile);
    }
}
