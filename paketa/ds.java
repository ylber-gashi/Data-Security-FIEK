package paketa;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

public class ds {
    public static void main(String[] args)throws ArrayIndexOutOfBoundsException, IOException, LineUnavailableException, InterruptedException {
        Caesar caesar = new Caesar();
        MorseCode morseCode=new MorseCode();
        Count count=new Count();
        if(args.length < 3 || args.length >4){
            throw new ArrayIndexOutOfBoundsException("\n\tNot enough arguments!" +
                                                    "\n\tFunctions of this program accept 3 or 4 arguments!");
        }
        else if ("morse-code".equals(args[0])){
            if("encode".equals(args[1]))
                System.out.println(morseCode.encode(args[2]));
            else if("decode".equals(args[1]))
                System.out.println(morseCode.decode(args[2]));
            else if("beep".equals(args[1]))
                morseCode.morseAudio(args[2]);
            else{
                System.out.println("\nInvalid arguments. \nPlease make sure to write the arguments as follows: \n\n" +
                        "morse-code encode/decode <text>/<morse code> \n" + "To use the beep function:\n" +
                        "morse-code beep <morse code>\n");
                System.exit(1);
            }
        }
        else if("caesar".equals(args[0]))
        {   if ("encrypt".equals(args[1])) {
                String cipherText = caesar.encrypt(Integer.parseInt(args[2]), args[3]);
                System.out.println("Teksti origjinal:    " + args[3]);
                System.out.println("Teksti i enkriptuar: " + cipherText);
            } else if("decrypt".equals(args[1]))
            {
                String plainText = caesar.decrypt(Integer.parseInt(args[2]), args[3]);
                System.out.println("Teksti i enkriptuar: "+args[3]);
                System.out.println("Teksti origjinal:    "+plainText);
            }
            else if("brute-force".equals(args[1])){
                caesar.bruteForce(args[2]);
            }
            else{
                System.out.println("\nInvalid arguments. \nPlease make sure to write the arguments as follows: \n\n" +
                        "caesar encrypt/decrypt <key> <plainText>/<cipherText> \n" +
                        "To use the brute-force function:"+
                        "caesar brute-force <cipherText>\n\n");
                System.exit(1);
            }
        }
        else if("count".equals(args[0])){
            if ("lines".equals(args[1])) {
                System.out.println("Numri i rreshtave eshte: " + count.countLines(args[2]));
            } else if ("words".equals(args[1])) {
                System.out.println("Numri i fjaleve eshte: " + count.countWords(args[2]));
            } else if ("letters".equals(args[1])) {
                System.out.println("Numri i shkronjave eshte: " + count.countLetters(args[2]));
            } else if ("characters".equals(args[1])) {
                System.out.println("Numri i karaktereve eshte: " + count.countCharacters(args[2]));
            } else if ("symbols".equals(args[1])) {
                System.out.println("Numri i simboleve eshte: " + count.countSymbols(args[2]));
            } else if ("vowels".equals(args[1])) {
                System.out.println("Numri i zanoreve eshte: " + count.countVowels(args[2]));
            } else if ("consonants".equals(args[1])) {
                System.out.println("Numri i bashtingelloreve eshte: " + count.countConsonants(args[2]));
            } else if ("spaces".equals(args[1])) {
                System.out.println("Numri i hapesirave eshte: " + count.countSpaces(args[2]));
            }else {
                System.out.println("\nInvalid arguments. \nPlease make sure to write the arguments as follows:  \n"+
                        "count lines <text>      : to count the number of lines\n" +
                        "count words <text>      : to count the number of words\n" +
                        "count letters <text>      : to count the number of letters\n" +
                        "count characters <text> : to count the number of characters\n"+
                        "count symbols <text>    : to count the number of symbols\n"+
                        "count consonants <text> : to count the number of consonants\n" +
                        "count spaces <text>     : to count the number of consonants\n\n");
                System.exit(1);
            }
        }
        else {
            System.out.println("\nInvalid arguments. Please use one of the program's functions as the first argument: \n" +
                    " morse-code\n" +
                    " caesar\n" +
                    " count\n\n");
            System.exit(1);
        }
    }
}
