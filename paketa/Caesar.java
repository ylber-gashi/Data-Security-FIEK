package paketa;

public class Caesar {
    public static String encrypt(int key, String text)
    {
        StringBuilder cipherText = new StringBuilder();
        for(int i=0;i<text.length();i++)
        {
            char ch = text.charAt(i);
            if(ch>='a'&&ch<='z')
            {
                ch=(char)(ch+key);
                if(ch>'z')
                {
                    ch=(char)(ch-'z'+'a'-1);
                }
                cipherText.append(ch);
            }else if(ch>='A'&&ch<='Z')
            {
                ch=(char)(ch+key);
                if(ch>'Z')
                {
                    ch=(char)(ch-'Z'+'A'-1);
                }
                cipherText.append(ch);
            }
            else
                cipherText.append(ch);
        }
        return cipherText.toString();
    }

    public static String decrypt(int key, String text)
    {
        StringBuilder decryptedText = new StringBuilder();
        for(int i=0;i<text.length();i++)
        {
            char ch=text.charAt(i);
            if(ch>='a'&&ch<='z')
            {
                ch=(char)(ch-key);
                if(ch<'a'){
                    ch=(char)(ch+'z'-'a'+1);
                }
                decryptedText.append(ch);
            }
            else if(ch>='A'&&ch<='Z')
            {
                ch=(char)(ch-key);
                if(ch<'A')
                {
                    ch=(char)(ch+'Z'-'A'+1);
                }
                decryptedText.append(ch);
            }
            else
                decryptedText.append(text.charAt(i));
        }
        return decryptedText.toString();
    }
    public static void bruteForce(String text)
    {
        for (int i=0;i<26;i++)
        {
            System.out.println(decrypt(i, text));
        }
    }

}

