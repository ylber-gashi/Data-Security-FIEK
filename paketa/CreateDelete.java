package paketa;

import java.io.File;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

public class CreateDelete {
    static final String KEY_ALGORITHM = "RSA";
    static final int KEY_LENGTH = 1024;
    static final String NL = System.getProperty("line.separator");

    public void saveKeys(String user) throws Exception {
        KeyPair keyPair = createKeyPair(KEY_LENGTH);
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        String privateKeyAsXml = savePrivateKeyAsXml(privateKey);
        writeFile(privateKeyAsXml, "../keys/" +user + ".xml");
        String publicKeyAsXml = savePublicKeyAsXml(publicKey);
        writeFile(publicKeyAsXml, "../keys/" +user + ".pub.xml");
        System.out.println("Eshte krijuar celesi publik: " + user + ".pub.xml");
        System.out.println("Eshte krijuar celesi privat: " + user + ".xml");
    }

    public KeyPair createKeyPair(int keyLength) throws NoSuchAlgorithmException {
        KeyPairGenerator keygen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keygen.initialize(keyLength, new SecureRandom());
        KeyPair keyPair = keygen.generateKeyPair();
        return keyPair;
    }

    public String savePrivateKeyAsXml(PrivateKey privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        RSAPrivateCrtKeySpec spec = keyFactory.getKeySpec(privateKey, RSAPrivateCrtKeySpec.class);
        StringBuilder sb = new StringBuilder();

        sb.append("<RSAKeyValue>" + NL);
        sb.append(getElement("Modulus", spec.getModulus()));
        sb.append(getElement("Exponent", spec.getPublicExponent()));
        sb.append(getElement("P", spec.getPrimeP()));
        sb.append(getElement("Q", spec.getPrimeQ()));
        sb.append(getElement("DP", spec.getPrimeExponentP()));
        sb.append(getElement("DQ", spec.getPrimeExponentQ()));
        sb.append(getElement("InverseQ", spec.getCrtCoefficient()));
        sb.append(getElement("D", spec.getPrivateExponent()));
        sb.append("</RSAKeyValue>");

        return sb.toString();
    }

    public String savePublicKeyAsXml(PublicKey publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        RSAPublicKeySpec spec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
        StringBuilder sb = new StringBuilder();

        sb.append("<RSAKeyValue>" + NL);
        sb.append(getElement("Modulus", spec.getModulus()));
        sb.append(getElement("Exponent", spec.getPublicExponent()));
        sb.append("</RSAKeyValue>");

        return sb.toString();
    }

    public String getElement(String parameter, BigInteger bigInt) throws Exception {

        byte[] bytesFromBigInt = bigInt.toByteArray();
        String elementContent = Base64.getEncoder().encodeToString(bytesFromBigInt);
        return String.format("  <%s>%s</%s>%s", parameter, elementContent, parameter, NL);
    }

    public void deleteUser(String user) {
        File publik = new File("../keys/"+user+".pub.xml");
        File privat = new File("../keys/"+user+".xml");
        if (user.equals(user) && (publik.exists() || privat.exists())) {
            if (publik.delete()) {
                System.out.println("Eshte larguar celesi publik: '" + "keys/" + user + ".pub.xml" + "' ");
            }
            if (privat.delete()) {
                System.out.println("Eshte larguar celesi privat: '"+"keys/"+ user +".xml"+"' \n");
            }
        }
        else
            System.out.println("Celesi '"+user+"' nuk ekziston");
    }

    public void writeFile(String text, String filename) throws Exception{
        try(PrintWriter writer = new PrintWriter(filename)){
            writer.write(text);
        }
    }
}
