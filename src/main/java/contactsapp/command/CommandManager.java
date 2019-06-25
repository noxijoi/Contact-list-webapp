package contactsapp.command;

import contactsapp.command.DELETECommands.DeleteContactCommand;
import contactsapp.command.GETCommands.GetContactCommand;
import contactsapp.command.GETCommands.GetContactPageCommand;
import contactsapp.command.POSTCommands.AddContactCommand;
import contactsapp.command.PUTCommands.EditContactCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private  Map<String,String> patterns = new HashMap<>();

    private String GET = "GET";
    private String POST = "POST";
    private String DELETE = "DELETE";
    private String PUT = "PUT";

    private String ONE_CONTACT = "oneContact";
    private String PAGE = "page";
    private String ADD_CONTACT = "addContact";

    private  Map<String, Command> commandMap = new HashMap<>();
    public CommandManager(){
        //patterns.put("allContacts",".*\\/contacts");
        patterns.put(ONE_CONTACT,".*\\/contacts\\/\\d+");
        patterns.put(ADD_CONTACT, ".*\\/contacts\\/add");
        patterns.put("allContactAttach",".*\\/contacts\\/\\d+\\/attach");
        patterns.put("oneAttach",".*\\/contacts\\/attach\\/\\d+");
        patterns.put("allContactPhone",".*\\/contacts\\/\\d+\\/phone");
        patterns.put("onePhone",".*\\/contacts\\/phone\\/\\d+");
        patterns.put(PAGE,".*\\/contacts\\/page\\/\\d+");

        commandMap.put(null, new NoCommand());
        commandMap.put(GET + patterns.get(ONE_CONTACT), new GetContactCommand());
        commandMap.put(GET + patterns.get(PAGE), new GetContactPageCommand());
        commandMap.put(POST + patterns.get(ADD_CONTACT), new AddContactCommand());
        commandMap.put(PUT + patterns.get(ONE_CONTACT), new EditContactCommand());

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
