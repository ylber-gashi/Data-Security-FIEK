package paketa;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class WriteRead {

    public PublicKey getPublicElements(String name) throws Exception {
        try {
            File file = new File("./keys/" + name + ".pub.xml");
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
            System.out.println("Gabim: Celesi publik '" + name + "' nuk ekziston.");
            System.exit(1);
        }
        return null;
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

    public  String encrypt(String name,String plainText) throws Exception {
        PublicKey publicKey = getPublicElements(name);
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] cipherbytes = encryptCipher.doFinal(plainText.getBytes());
        String cipherText = Base64.getEncoder().encodeToString(cipherbytes);
        return cipherText;
    }

    public String decrypt(String data,String user) throws Exception {
        PrivateKey privateKey = getPrivateElements(user);
        Cipher decriptCipher = Cipher.getInstance("RSA");
        decriptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytes = Base64.getDecoder().decode(data.getBytes());
        return new String(decriptCipher.doFinal(bytes));
    }

    public IvParameterSpec generateIV(){
        byte[] iv = new byte[8];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        return ivParameterSpec;
    }

    public SecretKeySpec generateDESKey(){
        Random random = new Random();
        byte[] keyBytes =  new byte[8];
        random.nextBytes(keyBytes);
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "DES");
        return secretKey;
    }

    public byte[] encryptTextDES(String plaintext,String IV, String deskey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        byte[] keyBytes = Base64.getDecoder().decode(deskey.getBytes());
        byte[] ivBytes = Base64.getDecoder().decode(IV.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyBytes, "DES"),new IvParameterSpec(ivBytes));
        return cipher.doFinal(plaintext.getBytes());
    }

    public String decryptTextDES(String ciphertext,String IV, String deskey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        byte[] keyBytes = Base64.getDecoder().decode(deskey.getBytes());
        byte[] ivBytes = Base64.getDecoder().decode(IV.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyBytes, "DES"),new IvParameterSpec(ivBytes));
        byte[] bytes = Base64.getMimeDecoder().decode(ciphertext.getBytes());
        return new String(cipher.doFinal(bytes));
    }

    public String encryptWrite(String name,String plaintext,String token) throws Exception {
        String IV = Base64.getEncoder().encodeToString(generateIV().getIV());
        String deskey = Base64.getEncoder().encodeToString(generateDESKey().getEncoded());
        String uname = Base64.getEncoder().encodeToString(name.getBytes());

        byte[] byteEncryptedMessage = encryptTextDES(plaintext, IV, deskey);
        String encryptedMessage = Base64.getEncoder().encodeToString(byteEncryptedMessage);

        if(token == null){
            return uname + "." + IV + "." + encrypt(name,deskey) + "." + encryptedMessage;
        }else if(isValidToken(token)){
            String[] textArray = token.split("\\.");
            String sender = new String(Base64.getDecoder().decode(textArray[0].getBytes()));

            return uname + "." + IV + "." + encrypt(name,deskey) + "." + encryptedMessage + "." + textArray[0] + "." + Signature(sender,byteEncryptedMessage);
        }else
            return "Tokeni nuk eshte valid";
    }

    public String decryptRead(String ciphertext) throws Exception {
        String[] textArray = ciphertext.split("\\.");

        String user = new String(Base64.getDecoder().decode(textArray[0].getBytes()));

        String deskey = decrypt(textArray[2],user);
        StringBuilder sb = new StringBuilder();
        sb.append("Marresi: ");
        sb.append(user);
        sb.append("\n");
        sb.append("Mesazhi: ");
        sb.append(decryptTextDES(textArray[3],textArray[1], deskey));
        sb.append("\n");

        if(textArray.length==6){
            String derguesi = new String(Base64.getDecoder().decode(textArray[4].getBytes()));
            sb.append("Derguesi: ");
            sb.append(derguesi);
            sb.append("\n");
            sb.append("Nenshkrimi: ");
            byte[] byteMessage = Base64.getDecoder().decode(textArray[3].getBytes());
            if(isValidSignature(derguesi,textArray[5],byteMessage)){
                sb.append("valid");
            }else{
                sb.append("mungon celesi publik " + derguesi);
            }
        }
        return sb.toString();
    }

    public void Write(String name,String plaintext,String filePath,String token) throws Exception {
        if(filePath==null){
            System.out.println(encryptWrite(name,plaintext,token));
        }else{
            String text = encryptWrite(name,plaintext,token);
            writeFile(text,filePath);
            System.out.println("Mesazhi i enkriptuar u ruajt ne fajllin "+filePath);
        }
    }

    public void Read(String ciphertext) throws Exception {
        File file = new File(ciphertext);
        if (file.exists()){
            String text = readFile(ciphertext);
            System.out.println(decryptRead(text));
        } else {
            System.out.println(decryptRead(ciphertext));
        }
    }

    public void writeFile(String text, String filename) throws Exception{
        try(PrintWriter writer = new PrintWriter(filename)){
            writer.write(text);
        }
    }
    public String readFile(String fileName) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        try {
            File file = new File(fileName);
            if ((file.exists())){
                Scanner reader = new Scanner(file);
                while(reader.hasNextLine()){
                    sb.append(reader.nextLine());
                    sb.append( "\n");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }

    public boolean isValidToken(String token) throws Exception {
        String[] textArray = token.split("\\.");

        String user = new String(Base64.getDecoder().decode(textArray[0].getBytes()));
        String dateTime = new String(Base64.getDecoder().decode(textArray[1].getBytes()));

        byte[] signatureText = Base64.getDecoder().decode(textArray[2]);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH);
        LocalDateTime datee = LocalDateTime.parse(dateTime, formatter);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Signature sign = Signature.getInstance("SHA256withRSA");
        PublicKey publicKey = getPublicElements(user);

        byte[] user1 = Base64.getDecoder().decode(textArray[0].getBytes());
        byte[] date = Base64.getDecoder().decode(textArray[1].getBytes());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        outputStream.write( user1 );
        outputStream.write( date );
        byte[] signatureData = outputStream.toByteArray( );

        sign.initVerify(publicKey);
        sign.update(signatureData);

        boolean bool = sign.verify(signatureText);
        if(bool && now.isBefore(datee)) {
            return true;
        }else
            return false;
    }

    public String Signature(String user,byte[] bytes) throws Exception {
        PrivateKey key = getPrivateElements(user);
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initSign(key);
        sign.update(bytes);
        return Base64.getEncoder().encodeToString(sign.sign());
    }

    public boolean isValidSignature(String derguesi,String nenshkrimi,byte[] byteMsg) throws Exception {
        byte[] signatureText = Base64.getMimeDecoder().decode(nenshkrimi);

        Signature sign = Signature.getInstance("SHA256withRSA");
        PublicKey publicKey = getPublicElements(derguesi);

        sign.initVerify(publicKey);
        sign.update(byteMsg);
        boolean bool = sign.verify(signatureText);
        if(bool){
            return true;
        }else
            return false;
    }
}
