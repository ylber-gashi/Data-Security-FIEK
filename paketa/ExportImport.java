package paketa;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

public class ExportImport {
    static final String NL = System.getProperty("line.separator");

    public void exportKey(String type, String user, String filepath) throws Exception {
        String name = null;
        if (type.equals("public")) {
            name = "../keys/" + user + ".pub.xml";
        } else if (type.equals("private")) {
            name = "../keys/" + user + ".xml";
        }
        if (filepath == null) {
            System.out.println(readFile(name, user, type));
        } else {
            String text = readFile(name, user, type);
            writeFile(text, filepath);
            deleteUser(name);
        }
    }

    public void importKey(String user,String filePath) {
        boolean isPath = isValidURL(filePath);
        try {
            if (!isPath) {
                try {
                    File file = new File(filePath);
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    org.w3c.dom.Document doc = db.parse(file);
                    doc.getDocumentElement().normalize();

                    NodeList nodeList = doc.getElementsByTagName("RSAKeyValue");
                    Node node = nodeList.item(0);

                    Element eElement = (Element) node;
                    String modulus = eElement.getElementsByTagName("Modulus").item(0).getTextContent();
                    String exponent = eElement.getElementsByTagName("Exponent").item(0).getTextContent();

                    if (eElement.getElementsByTagName("P") > 0) {
                        String type = "privat";

                        StringBuilder sb = new StringBuilder();
                        sb.append("<RSAKeyValue>" + NL);
                        sb.append(getElement("Modulus", modulus));
                        sb.append(getElement("Exponent", exponent));
                        sb.append("</RSAKeyValue>");

                        String x = "../keys/" + user + ".pub.xml";
                        writeFile(sb.toString(),x);
                        System.out.println("Celesi publik u ruajt ne fajllin '" + x + "'.");
                        String text = readFile(filePath, user, type);
                        String y = "../keys/" + user + ".xml";
                        writeFile(text,y);
                        System.out.println("Celesi privat u ruajt ne fajllin '" + y + "'.");
                    } else {
                        String type = "public";
                        String text = readFile(filePath, user, type);
                        String x = "../keys/" + user + ".pub.xml";
                        writeFile(text,x);
                        System.out.println("Celesi publik u ruajt ne fajllin '" + x + "'.");
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        System.out.println("Gabim: Fajlli i dhene nuk eshte celes valid.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                String keyFromUrl = sendGET(filePath);
                Document doc = convertStringToXMLDocument(keyFromUrl);
                doc.getDocumentElement().normalize();

                if() {

                }else{

                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getElement(String name, String type) throws Exception {
        return String.format("  <%s>%s</%s>%s", name, type, name, NL);
    }

    public String readFile(String fileName, String user, String type) throws FileNotFoundException {
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
            else
                sb.append("Gabim: Celesi " + type +" '" + user + "' nuk ekziston.");
        }catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }
    public void writeFile(String text, String filename) throws Exception{
        PrintWriter writer = new PrintWriter(filename);
        try{
            writer.write(text);
            System.out.println("Celesi publik u ruajt ne fajllin '" + filename + "'.");
        }catch (Exception e){
            e.getMessage();
        }finally {
            writer.close();
        }

    }

    public void deleteUser(String user) {
        File file = new File(user);
        if ((file.exists())) {
            file.delete();
        } else
            System.out.println("Gabim tek fshirja: Celesi '"+ user +"' nuk ekziston.");
    }

    //Metoda per ta marre celesin nga url
    public String sendGET(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent","Mozilla/5.0");
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                response.append("\n");
            }
            in.close();
            return response.toString();
        } else {
            return "GET request not worked";
        }
    }


    //Metoda per konvertimin e nje permbajtje String ne XML document, qe perdoret kur kemi import-key me URL
    public  Document convertStringToXMLDocument(String xmlString)
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try
        {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        }
        catch (SAXParseException | ParserConfigurationException e) {
            System.out.println("Gabim: URL i dhene nuk eshte valide ose nuk permban ndonje celes valid.");
        } catch (IOException | SAXException e) {
            System.out.println("Gabim: URL i dhene nuk eshte valide ose nuk permban ndonje celes valid.");
        }
        return null;
    }


    //Metoda per ta kontrolluar se a eshte valide nje url e dhene
    public  boolean isValidURL(String urlString){
        try
        {
            URL url = new URL(urlString);
            url.toURI();
            return true;
        } catch (Exception exception)
        {
            return false;
        }
    }
}