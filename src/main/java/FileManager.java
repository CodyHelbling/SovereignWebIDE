/**
 * Created by austin on 4/20/16.
 */
import java.io.*;
import java.nio.file.*;
import java.lang.*;

public class FileManager {
    public String projectName;
    public static void main(String [] args) {
        //FileManager baseDir = new FileManager();
        //Path pathBase = Paths.get("/home/austin/sQuire/src/main");
        //baseDir.createProjectSpace(pathBase);
        //Path newDir = Paths.get("/home/austin/sQuire/src/main/resources");
        //baseDir.createDirectory(newDir);
    }
    public static void createProjectSpace() {
        try {
            Files.createDirectories(Paths.get(/*System.getProperty("user.home") + */"projects/"));
        } catch (IOException e) {
            System.err.println("FileManager::createProjectSpace() IOException: I/O error; parent FileManager may not exist.");
        }/* catch (FileAlreadyExistsException e) {
            System.err.println("FileManager::createDirectory() SecurityException: ...");
        } */
    }

    public static void deleteProjectSpace(Path pathBase) {
        try {
            Files.deleteIfExists(pathBase);
        } catch (IOException e) {
            System.err.println("FileManager::deleteProjectSpace() IOException +++ "+e+" +++: I/O error.");
        }
    }

    public static void createDirectory(String pathName) {
       System.out.println("FileManager::createDirectory(): " + pathName);
       try {
           Files.createDirectory(Paths.get(/*System.getProperty("user.home") + "/sQuire/" + */"projects/" + pathName));
       } catch (IOException e) {
           System.err.println("FileManager::createDirectory() IOException +++ "+e+" +++: I/O error; parent directory may not exist.");
       }/* catch (FileAlreadyExistsException e) {
           System.err.println("FileManager::createDirectory() SecurityException: ...");
       } */
    }

    public static void deleteDirectory(String pathName) {
        System.out.println("FileManager::deleteDirectory(): " + pathName);
        try {
            Files.deleteIfExists(Paths.get(/*System.getProperty("user.home") + "/sQuire/" + */"projects/" + pathName));
        } catch (IOException e) {
            System.err.println("FileManager::deleteDirectory() IOException +++ "+e+" +++: I/O error.");
        }
    }
    public static void createFile(String pathName) {
        try {
            System.out.println(/*System.getProperty("user.home") + "/sQuire/" + */pathName);
            Files.createFile(Paths.get(/*System.getProperty("user.home") + "/sQuire/" + */"projects/" + pathName));
        } catch (IOException e) {
            System.err.println("FileManager::createFile() IOException +++ " + e + " +++: I/O error.");
        }
        /*catch (FileAlreadyExistsException e) {
            System.err.println("FileManager::createFile() FileAlreadyExistException +++ "+e+" +++: I/O error.");
        }*/
    }
    public void saveFileStructureData(String data){
        System.out.println("FileManager::saveFileStructureData(): Writing... " + data);
        BufferedWriter out = null;
        try {
            File file = new File("projects/" + projectName +"/fileStructureHTML.txt");
            out = new BufferedWriter(new FileWriter(file));
            out.write(data);

        } catch (IOException e) {
            System.err.println("FileManager::saveFileStructureData() IOException +++ " + e + " +++: I/O error.");
        }
        finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                System.err.println("FileManager::saveFileStructureData() IOException +++ " + e + " +++: I/O error.");
            }
        }
    }
    public String readFileStructureData(){
        String data = "";
        String line;
        BufferedReader in = null;
        try {
            File filein = new File("projects/" + projectName +"/fileStructureHTML.txt");
            in = new BufferedReader(new FileReader(filein));
            while ((line = in.readLine()) != null)
                    data += line;
        } catch (IOException e){
            System.err.println("FileManager::readFileStructureData() IOException +++ " + e + " +++: I/O error.");
        }
        finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                System.err.println("FileManager::readFileStructureData() IOException +++ " + e + " +++: I/O error.");
            }
        }
        System.out.println("FileManager::readFileStructureData(): Read... " + data);
        return data;
    }
    public boolean checkForData(String pName) {
        return Files.exists(Paths.get("projects/" + projectName +"/fileStructureHTML.txt"));
    }
}
