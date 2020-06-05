package paketa;

public class LoginStatus {

    public void login(String username) throws Exception {
        System.out.println("Jepni fjalekalimin: ");
        Scanner input = new Scanner(System.in);
        String inputPass = input.nextLine();
        boolean isValid = validateUser(inputPass);

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

    public String generateToken(String user) throws Exception {

    }

    public String Signature(String user,byte[] userdate) throws Exception {

    }


    public void writeFile(String text, String filename) throws Exception{

    }

}
