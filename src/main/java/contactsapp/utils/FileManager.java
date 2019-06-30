package contactsapp.utils;

import contactsapp.dao.connectionmanager.ConnectionManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Properties;

public class FileManager {
    private static volatile FileManager instance;
    private static Properties fileProperties;

    private FileManager() throws IOException {
        fileProperties = PropertyManager.getAttachProperties();
    }

    public static FileManager getInstance() throws IOException {
        if(instance == null){
            synchronized (ConnectionManager.class) {
                if(instance == null) {
                    instance = new FileManager();
                }
            }
        }
        return instance;
    }

    public String getDefaultAvatarPath() {
        return fileProperties.getProperty("attachment.pathForAvatarDirectory")
                +File.separator + fileProperties.getProperty("attachment.DefaultAvatarFileName");
    }

    public String getAttachmentDirectory(){
        return fileProperties.getProperty("attachment.pathToAttachDirectory");
    }

    public String decodedImage(String pathToImg) throws IOException {
        if(pathToImg == null){
            pathToImg = getDefaultAvatarPath();
        }
        File img = new File(pathToImg);
        String contentType = Files.probeContentType(img.toPath());
        byte[] data = Files.readAllBytes(img.toPath());
        String base64str = Base64.getEncoder().encodeToString(data);
        return "data:" + contentType + ";base64," + base64str;
    }



}
