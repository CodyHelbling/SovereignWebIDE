/**
 * Created by austin on 4/20/16.
 */
import java.io.*;
import java.nio.file.*;
import java.lang.*;

public class FileManager {
    public static void main(String [] args) {
        //FileManager baseDir = new FileManager();
        Path pathBase = Paths.get("/home/austin/sQuire/src/main");
        //baseDir.createProjectSpace(pathBase);
        Path newDir = Paths.get("/home/austin/sQuire/src/main/resources");
        //baseDir.createDirectory(newDir);
    }
    public static void createProjectSpace() {
        try {
            Files.createDirectories(Paths.get(System.getProperty("user.home") + "/sQuire"));
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
           Files.createDirectory(Paths.get(System.getProperty("user.home") + "/sQuire/" + pathName));
       } catch (IOException e) {
           System.err.println("FileManager::createDirectory() IOException +++ "+e+" +++: I/O error; parent directory may not exist.");
       }/* catch (FileAlreadyExistsException e) {
           System.err.println("FileManager::createDirectory() SecurityException: ...");
       } */
    }

    public static void deleteDirectory(String pathName) {
        System.out.println("FileManager::deleteDirectory(): " + pathName);
        try {
            Files.deleteIfExists(Paths.get(System.getProperty("user.home") + "/sQuire/" + pathName));
        } catch (IOException e) {
            System.err.println("FileManager::deleteDirectory() IOException +++ "+e+" +++: I/O error.");
        }
    }
    public static void createFile(String pathName) {
        try {
            System.out.println(System.getProperty("user.home") + "/sQuire/" + pathName);
            Files.createFile(Paths.get(System.getProperty("user.home") + "/sQuire/" + pathName));
        } catch (IOException e) {
            System.err.println("FileManager::createFile() IOException +++ " + e + " +++: I/O error.");
        } /*catch (FileAlreadyExistsException e) {
            System.err.println("FileManager::createFile() FileAlreadyExistException +++ "+e+" +++: I/O error.");
        }*/
    }
}
