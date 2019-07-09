package contactsapp.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        resp.setStatus(404);
    }
}
