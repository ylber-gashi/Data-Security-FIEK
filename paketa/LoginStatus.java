package paketa;

public class LoginStatus {

    public void login(String username) throws Exception {
        System.out.println("Jepni fjalekalimin: ");
        Scanner input = new Scanner(System.in);
        String inputPass = input.nextLine();
        boolean isValid = validateUser(username, inputPass);

        if(isValid){
            String tokeni = generateToken(username);
            System.out.println("Token: "+tokeni);
        }
        else {
            System.out.println("Gabim: shfrytezuesi ose fjalekalimi i gabuar.");
        }
    }

    public void status(String token) throws Exception {
       
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

    }

}
