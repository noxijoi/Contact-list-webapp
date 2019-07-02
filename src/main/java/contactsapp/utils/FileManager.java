package contactsapp.utils;

import contactsapp.core.entity.Avatar;
import contactsapp.dao.connectionmanager.ConnectionManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Properties;


public class FileManager {
    private static final Logger LOGGER = LogManager.getLogger(FileManager.class);

    private static volatile FileManager instance;
    private static Properties fileProperties;

    private FileManager(){

    }

    public static FileManager getInstance(){
        if(instance == null){
            synchronized (ConnectionManager.class) {
                if(instance == null) {
                    instance = new FileManager();
                }
            }
        }
        return instance;
    }

    public void init() throws IOException {
        fileProperties = PropertiesManager.getAttachProperties();
    }

    public String getPathToAvatarDirectory(){
        return fileProperties.getProperty("attachment.pathForAvatarDirectory");
    }
    public String getDefaultAvatarPath() {
        return fileProperties.getProperty("attachment.DefaultAvatarFileName");
    }

    public String getAttachmentDirectory(){
        return fileProperties.getProperty("attachment.pathToAttachDirectory");
    }

    public String decodeImage(String pathToImg) throws IOException {
        if(pathToImg == null){
            pathToImg = getDefaultAvatarPath();
        }
        File img = new File(getPathToAvatarDirectory() + File.separator + pathToImg);
        String contentType = Files.probeContentType(img.toPath());
        byte[] data = Files.readAllBytes(img.toPath());
        String base64str = Base64.getEncoder().encodeToString(data);
        return "data:" + contentType + ";base64," + base64str;
    }


    public void writeImg(Avatar avatar) {

        String encodingPrefix = "base64,";
        if(avatar.getDecodedImg() == null || avatar.getDecodedImg().isEmpty()){
            return;
        }
        int contentStartIndex = avatar.getDecodedImg().indexOf(encodingPrefix) + encodingPrefix.length();
        byte[] imageData = Base64.getDecoder().decode(avatar.getDecodedImg().substring(contentStartIndex));
        try {
            String fullPath;
            String dirPath = getPathToAvatarDirectory();
            fullPath = dirPath + File.separator + avatar.getPath();

            File file = new File(fullPath);
            if (!file.exists()) {
                file.createNewFile();
            }
            try (FileOutputStream stream = new FileOutputStream(file)) {
                stream.write(imageData);
            }
        } catch (IOException e) {
            LOGGER.warn("Can't write avatar img to file " + e);
        }
    }
}
