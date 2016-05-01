/**
 * Created by Hue on 4/24/2016.
 */

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class Authentication {
    static String fileName="users.txt";//name of the file of user data.
    
    public static String[] chop(String info, int type){
    //cuts the given string into pieces. May have trouble if the format is off or a field is empty.
        int i=info.indexOf(":")+2;
        int j=info.indexOf("\"", i);
        if(j==-1){System.out.println("bad string\n"); return null;}
        String[] s=new String[4];
        for(int k=0; k<4; k++){
            s[k] = info.substring(i, j);
            i = info.indexOf(":", j) + 2;
            j = info.indexOf("\"", i);
        }
        return s;
    }

    public static boolean createUser(String uName, String password, String email){
        try{
            if(Authentication.isUser(uName, password, false)){//only works if the username is not taken
                return false;
            }else {
                //Section dealing with creating a new user
                //to be modified to work with the database
                String temp=uName+"\n"+password+"\n"+email+"\n\n";
                Files.write(Paths.get(Authentication.fileName), temp.getBytes(), StandardOpenOption.APPEND);
                //
                return true;
            }
        }catch(Exception e){
            System.out.println("exception-create");
            return false;
        }
    }

    public static boolean isUser(String uName, String password, Boolean checkPass){
        //This entire function will be much simplified with the addition of the database
        //output: if(checkpass){if(user exists AND password matches){true}else{false}}
        //        else{if(user exists){true}else{false}}
        try{
            File f=new File(Authentication.fileName);
            if(f.createNewFile()){System.out.println("file created"); return false;}
            BufferedReader in=new BufferedReader(new FileReader(f));
            String line;
            while((line=in.readLine())!=null){//loops through the file, reading a person at a time
                if(line.startsWith(uName)){
                    if(checkPass){
                        return (in.readLine()).startsWith(password);
                    }else {
                        return true;
                    }
                }else{
                    in.readLine();
                    in.readLine();
                    in.readLine();
                }
            }
            return false;
        }catch(Exception e){
            System.out.println("exception-test");
            return false;
        }
    }

    public static boolean logIn(String uName, String password, String project) {
        //logs a user in. Would have more to it if the username actually got used anywhere
        try {
            if(Authentication.isUser(uName, password, true)){
                Users.enterProject(uName, project);
                return true;
            }else{return false;}
        } catch (Exception e) {
            return false;
        }
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




