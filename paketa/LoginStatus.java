package paketa;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.io.Console;
import javax.naming.NamingException;

public class LoginStatus {

    public void login(String username) throws Exception {
        System.out.print("Jepni fjalekalimin: ");

        Console console1 = System.console();
        char[] passChar = console1.readPassword();
        String pass = String.valueOf(passChar);

        boolean isValid = validateUser(username, pass);

        if(isValid){
            String tokeni = generateToken(username);
            System.out.println("Token: "+tokeni);
        }
        else {
            System.out.println("Gabim: shfrytezuesi ose fjalekalimi i gabuar.");
        }
    }

    public void status(String token) throws Exception {
        try{
            String[] textArray = token.split("\\.");

            StringBuilder sb = new StringBuilder();
            String isValid = "";

            String user = new String(Base64.getDecoder().decode(textArray[0].getBytes())); //useri lexohet nga tokeni
            String dateTime = new String(Base64.getDecoder().decode(textArray[1].getBytes())); //data skadimit lexohet nga tokeni
            byte[] signatureText = Base64.getDecoder().decode(textArray[2]); //nenshkrimi lexohet nga tokeni
        
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH);
            LocalDateTime datee = LocalDateTime.parse(dateTime, formatter); //parsimi i dates qe e marrim nga tokeni si string

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now(); //leximi i dates aktuale qe e krahasojme me daten e skadimit
        
            Signature sign = Signature.getInstance("SHA256withRSA");
            PublicKey publicKey = getPublicElements(user); //e marrim celesin publik per verifikim te nenshkrimit

            byte[] user1 = Base64.getDecoder().decode(textArray[0].getBytes()); //emri i userit i dekoduar nga base64
            byte[] date = Base64.getDecoder().decode(textArray[1].getBytes()); //data e dekoduar nga base64

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
            outputStream.write( user1 );
            outputStream.write( date );
            byte[] signatureData = outputStream.toByteArray( ); //permbajtja e nenshkrimit qe e perdorim edhe per verifikim

            sign.initVerify(publicKey);
            sign.update(signatureData);
            boolean bool = sign.verify(signatureText);
            if(bool && now.isBefore(datee)) {
                isValid = "Po";
            }else
                isValid = "Jo";

            sb.append("User: "+user);
            sb.append("\n");
            sb.append("Valid: "+isValid);
            sb.append("\n");
            sb.append("Skadimi: " + dateTime);
            System.out.println(sb.toString());
        }
        catch(SignatureException e){
            System.out.println("\nGabim ne verifikimin e nenshkrimit.");
        }
        catch(IllegalArgumentException e){
            System.out.println("\nToken i korruptuar.");
        }
        catch(DateTimeParseException e){
            System.out.println("\nGabim ne parsimin e dates nga tokeni.");
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println("\nNuk eshte dhene ndonje token valid.");
        }
    }
    
    public boolean validateUser(String user,String inputPW) throws Exception {
        String query = "SELECT salt,passwordd FROM users WHERE username = '"+user+"';";
        String password="",salt="";
        Connection connection = dbConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);

        while(rs.next()){
            salt = rs.getString("salt");
            password = rs.getString("passwordd");
        }
        
        byte[] byteSalt = Base64.getDecoder().decode(salt);
        String checkPassword = SHA1SecurePassword(inputPW,byteSalt);

        if(password.equals(checkPassword)){
            return true;
        }else
            return false;
    }

    public String generateToken(String user) throws Exception {
        String uname = Base64.getEncoder().encodeToString(user.getBytes());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now().plusMinutes(20);
        String dataSkadimit = Base64.getEncoder().encodeToString(dtf.format(now).getBytes());

        byte[] user1 = Base64.getDecoder().decode(uname.getBytes());
        byte[] date = Base64.getDecoder().decode(dataSkadimit.getBytes());

        //Concat two byte arrays
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        outputStream.write( user1 );
        outputStream.write( date );
        byte[] signatureData = outputStream.toByteArray( ); //te dhenat qe nenshkruhen

        return uname + "." + dataSkadimit + "." + Signature(user,signatureData); //tokeni
    }

    public String Signature(String user,byte[] userANDexpireDate) throws Exception {
        PrivateKey privateKey = getPrivateElements(user);
        Signature sign = Signature.getInstance("SHA256withRSA"); //nenshkrimi sipas algoritmit SHA256 me RSA
        sign.initSign(privateKey);
        sign.update(userANDexpireDate); //te dhenat qe nenshkruhen
        return Base64.getEncoder().encodeToString(sign.sign());
    }


    public void writeFile(String text, String filename) throws Exception{
        try(PrintWriter writer = new PrintWriter(filename)){
            writer.write(text);
        }
    }
    
    public String SHA1SecurePassword(String passwordToHash, byte[] salt)
    {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }
    
    public PrivateKey getPrivateElements(String user) throws Exception {
        try {
            File file = new File("./keys/" + user + ".xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("RSAKeyValue");
            Node node = nodeList.item(0);

            Element eElement = (Element) node;
            String modulus = eElement.getElementsByTagName("Modulus").item(0).getTextContent();
            String d = eElement.getElementsByTagName("D").item(0).getTextContent();
            KeyFactory rsaFactory = KeyFactory.getInstance("RSA");

            byte[] modBytes = modulus.getBytes();
            byte[] dBytes = d.getBytes();
            BigInteger modBigInt = new BigInteger(1, Base64.getDecoder().decode(modBytes));
            BigInteger dBigInt = new BigInteger(1, Base64.getDecoder().decode(dBytes));

            RSAPrivateKeySpec rsaKeyspec = new RSAPrivateKeySpec(modBigInt, dBigInt);
            PrivateKey key = rsaFactory.generatePrivate(rsaKeyspec);

            return key;
        }catch (FileNotFoundException e) {
            System.out.println("Gabim: Celesi privat '" + user + "' nuk ekziston.");
            System.exit(1);
        }
        return null;
    }
    
    public PublicKey getPublicElements(String user) throws Exception {
        try {
            File file = new File("./keys/" + user + ".pub.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("RSAKeyValue");
            Node node = nodeList.item(0);
            Element eElement = (Element) node;
            String modulus = eElement.getElementsByTagName("Modulus").item(0).getTextContent();
            String exponent = eElement.getElementsByTagName("Exponent").item(0).getTextContent();
            KeyFactory rsaFactory = KeyFactory.getInstance("RSA");

            byte[] modBytes = modulus.getBytes();
            byte[] expBytes = exponent.getBytes();
            BigInteger modBigInt = new BigInteger(1, Base64.getDecoder().decode(modBytes));
            BigInteger expBigInt = new BigInteger(1,Base64.getDecoder().decode(expBytes));

            RSAPublicKeySpec rsaKeyspec;
            rsaKeyspec = new RSAPublicKeySpec(modBigInt,expBigInt);
            PublicKey key = rsaFactory.generatePublic(rsaKeyspec);
            return key;
        }
        catch (FileNotFoundException e) {
            System.out.println("Gabim: Celesi publik '" + user + "' nuk ekziston.");
            System.exit(1);
        }
        return null;
    }

}
