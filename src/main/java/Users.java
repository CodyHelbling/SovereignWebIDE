import org.eclipse.jetty.websocket.api.Session;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hue on 4/27/2016.
 */
public class Users {
    public static Map<String, String> projects=new HashMap<>();
    public static Map<Session, String> userUsernameMap=new HashMap<>();
    public static Session current=null;
    static String fileName="owners.txt";


    public static String getProject(String name){
        try {
            String s = projects.get(name);
            if (s!=null) {
                return s;
            } else {
                return "default";
            }
        }catch(Exception e){e.printStackTrace(); return "default";}
    }


    public static boolean pExists(String pName){
        File f=new File("projects");
        return Arrays.asList(f.list()).contains(pName);
    }


    public static boolean createProject(String name, String pName){
        try{
            if (!Files.exists(Paths.get("projects"))) {
                File f = new File("projects");
                f.mkdir();
            }
            if (!Files.exists(Paths.get("projects/" + pName))) {
                File f = new File("projects/" + pName);
                f.mkdir();
                String temp="//This file contains the names of users allowed to do various things with this project.\n"+
                        "//The owner's name is provided as a guide. Removing it will not change their permissions.\n"+
                        "//All lines should be commented out and there should be a blank line between each user.\n"+
                        "//\n//"+name+"\n//+read\n//+write\n//+execute\n//\n";
                Files.write(Paths.get("projects/"+pName+"/users.txt"), temp.getBytes(), StandardOpenOption.APPEND);
                return true;
            }
            return false;
        }catch(Exception e){e.printStackTrace(); return false;}
    }


    public static boolean enterProject(String name, String pName){
        try {//enters a user into a project
            if(pName.equals("")){pName="default";}
            if (!Files.exists(Paths.get("projects"))) {
                File f = new File("projects");
                f.mkdir();
                return false;
            }
            if (!Files.exists(Paths.get("projects/" + pName))) {return false;}
            if(canEnter(name, pName)) {
                projects.put(name, pName);
                System.out.println("logging into " + pName);
                return true;
            }
            return false;
        }catch(Exception e){e.printStackTrace(); return false;}
    }


    public static void own(String uName, String pName){
        try {
            File f=new File(fileName);
            if(f.createNewFile()){System.out.println("file created");}
            if(!pExists(pName)) {
                String temp = pName + "\n" + uName + "\n\n";
                Files.write(Paths.get(fileName), temp.getBytes(), StandardOpenOption.APPEND);
            }
        }catch(Exception e){System.out.println("trouble with owning");}
    }


    public static boolean owner(String uName, String pName){
        try{
            File f=new File(fileName);
            if(f.createNewFile()){System.out.println("file created"); return false;}
            BufferedReader in=new BufferedReader(new FileReader(f));
            String line;
            while((line=in.readLine())!=null){//loops through the file, reading a person at a time
                if(line.startsWith(pName)){
                    return in.readLine().equals(uName);
                }else{
                    in.readLine();
                    in.readLine();
                }
            }
            return false;
        }catch(Exception e){
            System.out.println("trouble with ownership");
            return false;
        }
    }



//prefix operations----------------------------------------------------------------------------
    public static String prefix(String uName, String path){
        if(bounds(path)) {
            return getPrefix(uName) + path;
        }
        return getPrefix(uName) + "Try_to_keep_your_files_inside_your_own_project";
    }


    public static String prefix(Session user, String path){
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
//end prefix operations------------------------------------------------------------------------





//permissions checkers-------------------------------------------------------------------------
    public static boolean canEnter(String uName, String pName){
        if(owner(uName, pName)||canRead(uName, pName)){return true;}
        return false;
    }


    public static boolean canRead(String uName, String pName){
        try{
            File f=new File("projects/"+pName+"/users.txt");
            if(f.createNewFile()){System.out.println("file created"); return false;}
            BufferedReader in=new BufferedReader(new FileReader(f));
            String line;
            in.readLine();
            in.readLine();
            in.readLine();
            in.readLine();
            while((line=in.readLine())!=null){//loops through the file, reading a person at a time
                if(line.startsWith("//"+uName)){
                    while((line=in.readLine())!=null&&line.contains("+")){
                        if(line.startsWith("//+r")){return true;}
                    }
                    return false;
                }
            }
            return false;
        }catch(Exception e){
            System.out.println("trouble with ownership");
            return false;
        }
    }
}
