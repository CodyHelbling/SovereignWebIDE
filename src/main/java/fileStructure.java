/**
 * Created by austin on 3/24/16.
 * links:
 * https://docs.oracle.com/javase/tutorial/essential/io/file.html
 */
import java.nio.file.*;
import java.nio.file.*;
import java.lang.*;
import java.util.*;
import java.io.*;

public class fileStructure {
    public static void main(String[] args){
        new fileStructure().createStructure();
        new fileStructure().deleteStructure();
    }

    public void createStructure (){
        try {
            Files.createDirectories(Paths.get("/home/austin/sQuire/src/main"));
        }
        catch (IOException e) {
            System.out.println("IOException");
        }
    }

    public void deleteStructure () {
        try {
            Files.delete(Paths.get("/home/austin/sQuire/src/main"));
            Files.delete(Paths.get("/home/austin/sQuire/src"));
            Files.delete(Paths.get("/home/austin/sQuire"));
        } catch (NoSuchFileException e) {
            System.out.println(e);
        } catch (DirectoryNotEmptyException e1) {
            System.out.println(e1);
        } catch (IOException e2) {
            System.out.println(e2);
        }
    }

}
