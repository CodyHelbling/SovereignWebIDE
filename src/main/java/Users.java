import org.eclipse.jetty.websocket.api.Session;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hue on 4/27/2016.
 */
public class Users {
    public static Map<String, String> projects=new HashMap<>();
    public static Map<Session, String> userUsernameMap=new HashMap<>();
    public static Session current=null;
    public static String getProject(String name){
        return projects.get(name);
    }
    public static void enterProject(String name, String pName){
        try {//enters a user into a project and creates the relevant files
            if(pName.equals("")){pName="default";}
            if (!Files.exists(Paths.get("projects"))) {
                File f = new File("projects");
                f.mkdir();
            }
            if (!Files.exists(Paths.get("projects/" + pName))) {
                File f = new File("projects/" + pName);
                f.mkdir();
            }
            projects.put(name, pName);
            System.out.println("logging into "+pName);
        }catch(Exception e){e.printStackTrace();}
    }
    public static String prefix(String uName, String path){
        if(bounds(path)) {
            return getPrefix(uName) + path;
        }
        return getPrefix(uName) + "Try_to_keep_your_files_inside_your_own_project";
    }public static String prefix(Session user, String path){
        if(bounds(path)) {
            return getPrefix(userUsernameMap.get(user)) + path;
        }
        return getPrefix(userUsernameMap.get(user)) + "Try_to_keep_your_files_inside_your_own_project";
    }
    public static String prefix(String path){
        if(bounds(path)&&current!=null) {
            return getPrefix(userUsernameMap.get(current)) + path;
        }
        return getPrefix(userUsernameMap.get(current))+"Try_to_keep_your_files_inside_your_own_project";
    }
    public static String getPrefix(String uName){
        return "projects/" + getProject(uName) + "/";
    }
    public static boolean bounds(String path){//sneakiness checker
        int i=0, j=0, k=0;
        while(k!=-1){
            k=path.indexOf("/", k);
            if((path.substring(k+1, k+3)).equals("../")){j++;}else{i++;}
            if(j>i){return false;}
        }
        return true;
    }
}
