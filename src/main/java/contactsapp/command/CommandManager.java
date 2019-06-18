package contactsapp.command;

import contactsapp.command.GETCommands.GetContactCommand;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager {
    private  Map<String,String> patterns = new HashMap<>();

    private String GET = "GET";
    private String POST = "POST";
    private String DELETE = "DELETE";
    private String PUT = "PUT";

    private String ONE_CONTACT = "oneContact";

    private  Map<String, Command> commandMap = new HashMap<>();
    public CommandManager(){
        patterns.put("allContacts",".*\\/contacts");
        patterns.put(ONE_CONTACT,".*\\/contacts\\/\\d+");
        patterns.put("allContactAttach",".*\\/contacts\\/\\d+\\/attach");
        patterns.put("oneContactAttach",".*\\/contacts\\/\\d+\\/attach\\/\\d+");
        patterns.put("allContactPhone",".*\\/contacts\\/\\d+\\/phone");
        patterns.put("oneContactPhone",".*\\/contacts\\/\\d+\\/phone\\/\\d+");

        commandMap.put(null, new NoCommand());
        commandMap.put(GET + patterns.get(ONE_CONTACT), new GetContactCommand());
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
