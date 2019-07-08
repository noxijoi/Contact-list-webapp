package contactsapp.command.POSTCommands;

import contactsapp.command.Command;
import contactsapp.core.entity.Attachment;
import contactsapp.service.AttachmentService;
import contactsapp.utils.FileManager;
import contactsapp.utils.PropertiesManager;
import contactsapp.validation.AttachmentValidator;
import contactsapp.validation.DataValidationException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


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
    private static final Logger LOGGER = LogManager.getLogger(AddAttachCommand.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {

        try {
            String directoryPath = FileManager.getInstance().getAttachmentDirectory();
            String folderName = Long.toString(new Date().getTime());
            File uploadFolder = new File(directoryPath + File.separator + folderName);
            if(!uploadFolder.exists()) {
                uploadFolder.mkdir();
            }
            Attachment attachment = new Attachment();

            String comment = req.getParameter("comment");
            Integer ownerId = Integer.parseInt(req.getParameter("ownerId"));
            Integer id = Integer.parseInt(req.getParameter("id"));
            Part part = req.getPart("file");
            String fileName = part.getSubmittedFileName();
            String filePath = uploadFolder.getAbsolutePath() + File.separator + fileName;
            LOGGER.info(filePath);
            File file = new File(uploadFolder.getAbsolutePath(), fileName);
            if(!file.exists()){
                file.createNewFile();
            }
            part.write(filePath);

            attachment.setId(id);
            attachment.setOwnerId(ownerId);
            attachment.setComment(comment);
            attachment.setFileName(file.getName());
            attachment.setDownloadTime(Instant.ofEpochMilli(file.lastModified())
                    .atZone(ZoneId.systemDefault()).toLocalDate());
            attachment.setFilePath(folderName + File.separator + fileName);

            AttachmentService service = new AttachmentService();
            AttachmentValidator validator = new AttachmentValidator();
            validator.validate(attachment);
            service.insert(attachment);
            LOGGER.info("add 1 new Attachment to database");
        } catch (IOException e) {
            LOGGER.error(e);
        } catch (ServletException e) {
            LOGGER.error(e);
        } catch (DataValidationException e) {
            resp.setStatus(400);
            LOGGER.warn(e);
        }
    }
}
