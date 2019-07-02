package contactsapp.command.POSTCommands;

import contactsapp.command.Command;
import contactsapp.core.entity.Attachment;
import contactsapp.service.AttachmentService;
import contactsapp.utils.FileManager;
import contactsapp.utils.PropertiesManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;

public class AddAttachCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {

        try {
            Properties uploadFilesProp = PropertiesManager.getAttachProperties();
            String directoryPath = FileManager.getInstance().getAttachmentDirectory();
            String folderName = Long.toString(new Date().getTime());
            File uploadFolder = new File(directoryPath + File.separator + folderName);
            if(!uploadFolder.exists()) {
                uploadFolder.mkdir();
            }
            Attachment attachment = new Attachment();

            String comment = req.getParameter("comment");
            Integer ownerId = Integer.parseInt(req.getParameter("ownerId"));

            Part part = req.getPart("file");
            String fileName = part.getSubmittedFileName();
            String filePath = uploadFolder.getAbsolutePath() + File.separator + fileName;
            File file = new File(filePath);
            if(!file.exists()){
                file.createNewFile();
            }
            part.write(filePath);

            attachment.setOwnerId(ownerId);
            attachment.setComment(comment);
            attachment.setFileName(file.getName());
            attachment.setDownloadTime(Instant.ofEpochMilli(file.lastModified()).atZone(ZoneId.systemDefault()).toLocalDate());
            attachment.setFilePath(folderName + File.separator + fileName);

            AttachmentService service = new AttachmentService();
            service.insert(attachment);

            int id = service.getLastInsertedId();
            resp.getWriter().write("id=" + id);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}