package paketa;
import java.io.PrintWriter;

public class ExportImport {

    public void exportKey() throws Exception {

    }

    public void importKey() throws FileNotFoundException {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getElement() throws Exception {
    }

    public String readFile() throws FileNotFoundException {

    }
    public void writeFile(String text, String filename) throws Exception{
        PrintWriter writer = new PrintWriter(filename);
        try{
            writer.write(text);
        }catch (Exception e){
            e.getMessage();
        }
    }

    public void deleteUser() {

    }
}