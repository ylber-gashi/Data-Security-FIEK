package paketa;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

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

    public String readFile(String fileName) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        try {
            File file = new File(fileName);
            if ((file.exists())){
                Scanner reader = new Scanner(file);
                while(reader.hasNextLine()){
                    sb.append(reader.nextLine() + "\n");
                }
                reader.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }
    public void writeFile(String text, String filename) throws Exception{
        PrintWriter writer = new PrintWriter(filename);
        try{
            writer.write(text);
        }catch (Exception e){
            e.getMessage();
        }finally {
            writer.close();
        }

    }

    public void deleteUser() {

    }
}