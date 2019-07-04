package contactsapp.command.DELETECommands;

import contactsapp.command.Command;
import contactsapp.core.entity.Attachment;
import contactsapp.core.entity.Contact;
import contactsapp.service.AttachmentService;
import contactsapp.utils.serialization.JSONParser;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DeleteAttachCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(DeleteAttachCommand.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            AttachmentService service = new AttachmentService();
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = req.getReader().readLine()) != null) {
                sb.append(s);
            }
            List<Attachment> list = JSONParser.parseListAttach(sb.toString());
            service.delete(list);
            LOGGER.info("Delete " + list.size() + " attachments");
        } catch (IOException e) {
            LOGGER.error(e);
        }

    }
}
