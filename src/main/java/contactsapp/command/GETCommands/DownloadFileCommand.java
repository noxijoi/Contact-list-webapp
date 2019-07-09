package contactsapp.command.GETCommands;

import contactsapp.command.Command;
import contactsapp.core.entity.Attachment;
import contactsapp.service.AttachmentService;
import contactsapp.utils.FileManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DownloadFileCommand implements Command {

    private final static Logger LOGGER = LogManager.getLogger(DownloadFileCommand.class);
    public static int BUFFER_SIZE = 1024 * 200;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        String uri = req.getRequestURI();
        String[] parts = uri.split("/");
        Integer attachN = null;
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals("download")) {
                attachN = Integer.parseInt(parts[i + 1]);
            }
        }
        try {
            AttachmentService service = new AttachmentService();
            Attachment attachment = service.getById(attachN);

            if (attachment == null){
                resp.setStatus(404);
                return;
            }
            String fullPath = FileManager.getInstance().getAttachmentDirectory() + File.separator + attachment.getFilePath();

            File file = new File(fullPath);
            OutputStream ostream = null;
            FileInputStream fis = null;

            if (file.exists()) {
                String mimeType = "application/octet-stream";
                resp.setContentType(mimeType);

                String headerKey = "Content-Disposition";
                String headerValue = String.format("attachment; filename=\"%s\"", file.getName());
                resp.setHeader(headerKey, headerValue);

                ostream = resp.getOutputStream();
                fis = new FileInputStream(file);
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead = -1;

                while ((bytesRead = fis.read(buffer)) != -1) {
                    ostream.write(buffer, 0, bytesRead);
                }
            }

        } catch (IOException e) {
            LOGGER.error(e);
        }
    }
}
