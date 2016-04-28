import java.util.Hashtable;

/**
 * Created by Hue on 4/27/2016.
 */
public class Users {
    static Hashtable<String, String> projects=new Hashtable<String, String>();
    public static String getProject(String name){
        return projects.get(name);
    }
    public static String enterProject(String name, String pName){
        return projects.put(name, pName);
    }
    public static String prefix(String uName, String path){
        return "projects/"+projects.get(uName)+"/"+path;
    }
}
