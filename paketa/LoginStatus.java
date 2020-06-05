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

    }

    public String Signature(String user,byte[] userdate) throws Exception {

    }


    public void writeFile(String text, String filename) throws Exception{

    }

}
