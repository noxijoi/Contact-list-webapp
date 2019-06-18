package contactsapp.utils;

import java.util.Arrays;
import java.util.List;

public class URLParser {
    public static List<String> parseURLPath(String URLPath){
        List<String> list = Arrays.asList(URLPath.split("\\\\"));
        return list;
    }
}
