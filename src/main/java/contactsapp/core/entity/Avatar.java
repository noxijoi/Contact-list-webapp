package contactsapp.core.entity;

import contactsapp.utils.FileManager;

import java.io.File;
import java.io.IOException;

public class Avatar {
    private String path;
    private String decodedImg;

    public Avatar(String path){
        this.path = path;
        try {
            this.decodedImg = FileManager.getInstance().decodeImage(path);
        } catch (IOException e) {
            this.decodedImg = null;
        }
    }

    public Avatar(String path, String decodedImg) {
        this.path = path;
        this.decodedImg = decodedImg;
    }

    public Avatar() {
        this.path = FileManager.getInstance().getDefaultAvatarPath();
        this.decodedImg = null;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDecodedImg() {
        return decodedImg;
    }

    public void setDecodedImg(String decodedImg) {
        this.decodedImg = decodedImg;
    }

    public String getPath() {
        return path;
    }
}
