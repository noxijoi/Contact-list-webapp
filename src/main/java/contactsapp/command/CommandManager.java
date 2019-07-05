package contactsapp.command;

import contactsapp.command.DELETECommands.DeleteAttachCommand;
import contactsapp.command.DELETECommands.DeleteContactCommand;
import contactsapp.command.DELETECommands.DeletePhoneCommand;
import contactsapp.command.GETCommands.DownloadFileCommand;
import contactsapp.command.GETCommands.GetAttachmentCommand;
import contactsapp.command.GETCommands.GetContactCommand;
import contactsapp.command.GETCommands.GetContactPageCommand;
import contactsapp.command.POSTCommands.AddAttachCommand;
import contactsapp.command.POSTCommands.AddContactCommand;
import contactsapp.command.POSTCommands.AddPhoneCommand;
import contactsapp.command.POSTCommands.SendEmailCommand;
import contactsapp.command.PUTCommands.EditAttachCommand;
import contactsapp.command.PUTCommands.EditContactCommand;
import contactsapp.command.PUTCommands.EditPhoneCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private  Map<String,String> patterns = new HashMap<>();

    private String GET = "GET";
    private String POST = "POST";
    private String DELETE = "DELETE";
    private String PUT = "PUT";

    private String ONE_CONTACT = "oneContact";
    private String PHONE = "Phone";
    private String PAGE = "page";
    private String ADD_CONTACT = "addContact";
    private String ATTACH = "Attach";
    private String DOWNLOAD_ATTACH ="downLoadAttach";
    private String MAIL = "mail";

    private  Map<String, Command> commandMap = new HashMap<>();
    public CommandManager(){
        patterns.put(ONE_CONTACT,".*\\/contacts\\/\\d+");
        //выбивается
        patterns.put(ADD_CONTACT, ".*\\/contacts\\/add");
        patterns.put(PHONE, ".*\\/contacts\\/\\d+\\/phone");
        patterns.put(ATTACH, ".*\\/contacts\\/\\d+\\/attach");
        patterns.put(DOWNLOAD_ATTACH, ".*\\/contacts\\/attach\\/download\\/.*");
        patterns.put(PAGE,".*\\/contacts\\/page\\/\\d+");
        patterns.put(MAIL, ".*\\/contacts\\/mail");


        commandMap.put(null, new NoCommand());

        commandMap.put(GET + patterns.get(ONE_CONTACT), new GetContactCommand());
        commandMap.put(GET + patterns.get(PAGE), new GetContactPageCommand());
        commandMap.put(GET + patterns.get(DOWNLOAD_ATTACH), new DownloadFileCommand());
        commandMap.put(GET + patterns.get(ATTACH), new GetAttachmentCommand());

        commandMap.put(POST + patterns.get(ADD_CONTACT), new AddContactCommand());
        commandMap.put(POST + patterns.get(PHONE), new AddPhoneCommand());
        commandMap.put(POST + patterns.get(ATTACH), new AddAttachCommand());
        commandMap.put(POST + patterns.get(MAIL), new SendEmailCommand());

        commandMap.put(PUT + patterns.get(ONE_CONTACT), new EditContactCommand());
        commandMap.put(PUT + patterns.get(ATTACH), new EditAttachCommand());
        commandMap.put(PUT + patterns.get(PHONE), new EditPhoneCommand());

        commandMap.put(DELETE + patterns.get(PAGE), new DeleteContactCommand());
        commandMap.put(DELETE + patterns.get(PHONE), new DeletePhoneCommand());
        commandMap.put(DELETE + patterns.get(ATTACH), new DeleteAttachCommand());
    }


    public Command getGETCommand(String uri){
        return getCommand(GET, uri);
    }
    public Command getPOSTCommand(String uri){
        return getCommand(POST,uri);
    }
    public Command getDELETECommand(String uri){
        return getCommand(DELETE, uri);
    }
    public Command getPUTCommand(String uri){
        return getCommand(PUT, uri);
    }

    public Command getCommand(String method, String uri){
        String pattern = findSuitablePattern(uri);
        if(pattern == null){
            return  commandMap.get(pattern);
        }
        return commandMap.get(method + pattern);
    }

    private String findSuitablePattern(String uri) {
        String result = null;
        for (String value : patterns.values()) {
            if(uri.matches(value)){
                return value;
            }
        }
        return null;
    }
}
