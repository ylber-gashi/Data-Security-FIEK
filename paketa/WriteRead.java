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
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Random;

public class WriteRead {
    public String name;

    public WriteRead(String name) {
        this.name = name;
    }
    public PublicKey getPublicElements() throws Exception {

        File file = new File("./keys/"+ name +".pub.xml");
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
}