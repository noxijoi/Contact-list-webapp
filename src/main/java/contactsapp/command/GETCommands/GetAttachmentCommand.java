package contactsapp.command.GETCommands;

import contactsapp.command.Command;
import contactsapp.core.entity.Attachment;
import contactsapp.service.AttachmentService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class GetAttachmentCommand implements Command {
    private final static Logger LOGGER = LogManager.getLogger(GetAttachmentCommand.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {

        try {
            String uri = req.getRequestURI();
            String[] parts = uri.split("/");

            Integer contactN = null;
            Integer attachN = null;
            for (int i = 0; i < parts.length; i++) {
                if (parts[i].equals("contacts")) {
                    contactN = Integer.parseInt(parts[i + 1]);
                }
                if (parts[i].equals("attach")) {
                    attachN = Integer.parseInt(parts[i + 1]);
                }
            }
            AttachmentService service = new AttachmentService();
            List<Attachment> attachments = service.getByOwnerId(contactN);
            Attachment attachment = attachments.get(attachN);
            resp.setCharacterEncoding("utf-8");
        } catch (IOException e) {
            LOGGER.error(e);
        }


    }
}
