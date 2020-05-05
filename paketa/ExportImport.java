package paketa;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.*;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.ConnectException;
import java.util.Scanner;

public class ExportImport {
    static final String NL = System.getProperty("line.separator");

    public void exportKey(String type, String user, String filepath) throws Exception {
        String name = null;
        if (type.equals("public")) {
            type = "publik";
            name = "./keys/" + user + ".pub.xml";
        } else if (type.equals("private")) {
            type = "privat";
            name = "./keys/" + user + ".xml";
        }
        if (filepath == null) {
            System.out.println(readFile(name, user, type));
        } else {
            String text = readFile(name, user, type);
            if(text != null) {
                writeFileEXPORT(type, text, filepath);
            }
            else{
                System.out.println("Gabim: Celesi " + type + " '" + user + "' nuk ekziston.");
            }
        }
    }

    public void importKey(String user, String filePath) {
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

                    if (eElement.getElementsByTagName("P").getLength() > 0) {
                        String type = "privat";

                        StringBuilder sb = new StringBuilder();
                        sb.append("<RSAKeyValue>" + NL);
                        sb.append(getElement("Modulus", modulus));
                        sb.append(getElement("Exponent", exponent));
                        sb.append("</RSAKeyValue>");

                        String x = "./keys/" + user + ".pub.xml";
                        writeFile("publik", sb.toString(), x);

                        String text = readFile(filePath, user, type);
                        String y = "./keys/" + user + ".xml";
                        writeFile(type, text, y);
                    } else {
                        String type = "publik";
                        String text = readFile(filePath, user, type);
                        String x = "./keys/" + user + ".pub.xml";
                        writeFile(type, text, x);
                    }
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

            } else {
                String keyFromUrl = sendGET(filePath);
                Document doc = convertStringToXMLDocument(keyFromUrl);
                doc.getDocumentElement().normalize();

                NodeList nodeList = doc.getElementsByTagName("RSAKeyValue");
                Node node = nodeList.item(0);

                Element eElement = (Element) node;
                String modulus = eElement.getElementsByTagName("Modulus").item(0).getTextContent();
                String exponent = eElement.getElementsByTagName("Exponent").item(0).getTextContent();

                if (eElement.getElementsByTagName("P").getLength() > 0) {
                    String type = "privat";
                    
                    StringBuilder sb = new StringBuilder();
                    sb.append("<RSAKeyValue>" + NL);
                    sb.append(getElement("Modulus", modulus));
                    sb.append(getElement("Exponent", exponent));
                    sb.append("</RSAKeyValue>");

                    String x = "./keys/" + user + ".pub.xml";
                    writeFile("publik", sb.toString(), x);

                    String text = sendGET(filePath);
                    String y = "./keys/" + user + ".xml";
                    writeFile(type, text, y);
                } else {
                    String type = "publik";
                    String text = sendGET(filePath);
                    String x = "./keys/" + user + ".pub.xml";
                    writeFile(type, text, x);
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Krijimi i XML doc me permbajtje te URL deshtoi.");
        } catch (ConnectException e) {
            System.out.println("Krijimi i XML doc me permbajtje te URL deshtoi.");
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
            if ((file.exists())) {
                Scanner reader = new Scanner(file);
                while (reader.hasNextLine()) {
                    sb.append(reader.nextLine() + "\n");
                }
                reader.close();
            } else
                sb.append("Gabim: Celesi " + type + " '" + user + "' nuk ekziston.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public void writeFileEXPORT(String type,String text, String filename) throws Exception {
        File file = new File(filename);
        if(!file.exists()){
            System.out.println("Gabim: Celesi '"+filename+"' nuk ekziston.\n");
        }else{
            PrintWriter writer = new PrintWriter(filename);
            try {
                writer.write(text);
                System.out.println("Celesi "+type+" u ruajt ne fajllin '" + filename + "'.");
            } catch (Exception e) {
                e.getMessage();
            } finally {
                writer.close();
            }
        }
    }
    
    public void writeFile(String type, String text, String filename) throws Exception {
        File fajlli = new File(filename);
        try {
            if(fajlli.exists()) {
                System.out.println("Gabim: Celesi " + filename + " ekziston paraprakisht.");
            }
            else{
                PrintWriter writer = new PrintWriter(filename);
                writer.write(text);
                System.out.println("Celesi " + type + " u ruajt ne fajllin '" + filename + "'.");
                writer.close();
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    //Metoda per ta marre celesin nga url
    public String sendGET(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
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
    public Document convertStringToXMLDocument(String xmlString) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        } catch (SAXParseException e ) {
            System.out.println("Gabim: URL i dhene nuk eshte valide ose nuk permban ndonje celes valid.");
        } catch (IOException e) {
            System.out.println("Gabim: URL i dhene nuk eshte valide ose nuk permban ndonje celes valid.");
        }catch (ParserConfigurationException e){
            System.out.println("Gabim: URL i dhene nuk eshte valide ose nuk permban ndonje celes valid.");
        }catch (SAXException e){
            System.out.println("Gabim: URL i dhene nuk eshte valide ose nuk permban ndonje celes valid.");
        }
        return null;
    }


    //Metoda per ta kontrolluar se a eshte valide nje url e dhene
    public boolean isValidURL(String urlString) {
        try {
            URL url = new URL(urlString);
            url.toURI();
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
}
