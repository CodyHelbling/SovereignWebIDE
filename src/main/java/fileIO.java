import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

/**
 * Created by austin on 4/19/16.
 */
public class fileIO {
    public static void main(String[] args){
        new fileIO().createStructure();
        new fileIO().deleteStructure();
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
