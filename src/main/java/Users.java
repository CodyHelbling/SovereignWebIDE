import org.eclipse.jetty.server.session.JDBCSessionManager;
import java.util.Hashtable;

/**
 * Created by Hue on 4/27/2016.
 */
public class Users {
    static Hashtable<String, String> projects=new Hashtable<String, String>();
    public static JDBCSessionManager.Session current;
    public static String getProject(String name){
        return projects.get(name);
    }
    public static String enterProject(String name, String pName){
        return projects.put(name, pName);
    }
    public static String prefix(String uName, String path){
        if(bounds(path)) {
            return "projects/" + projects.get(uName) + "/" + path;
        }
        return null;
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
