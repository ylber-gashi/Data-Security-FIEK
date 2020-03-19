package paketa;

import java.io.*;
import java.util.*;
import javax.sound.sampled.*;
import java.util.List;
public class MorseCode {
    private static final int DOT = 200, DASH = DOT * 3, FREQ = 800;
    public static HashMap<String, String> encodeMap(){
        HashMap<String,String> morseDict = new HashMap<String, String>();
        List<Character> letters = Arrays.asList(' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
                'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0');
        List<String> morseLetters = Arrays.asList("/", ".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---",
                "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-",
                "..-", "...-", ".--", "-..-", "-.--", "--..", ".----", "..---", "...--", "....-",
                ".....", "-....", "--...", "---..", "----.", "-----");

            for (int i = 0; i < letters.size(); i++) {
                morseDict.put(Character.toString(letters.get(i)), morseLetters.get(i));
            }

        return morseDict;
    }

    public static HashMap<String,String> decodeMap() {
        HashMap<String,String> morseDict = new HashMap<String, String>();
        List<Character> letters = Arrays.asList(' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
                'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0');



        List<String> morseLetters = Arrays.asList("/", ".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---",
                "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-",
                "..-", "...-", ".--", "-..-", "-.--", "--..", ".----", "..---", "...--", "....-",
                ".....", "-....", "--...", "---..", "----.", "-----");

            for (int i = 0; i < letters.size(); i++) {
                morseDict.put(morseLetters.get(i), Character.toString(letters.get(i)));
            }

        return morseDict;
    }

    public static String encode(String x) {
        x = x.toLowerCase();
        String encodedText="";
        for (int i = 0; i < x.length(); i++) {
            encodedText+=encodeMap().get(Character.toString(x.charAt(i))) + " ";
        }
        return encodedText;
    }

    public static String decode(String x) {
        String[] textArray = x.split(" ");
        String decodedText="";
        for (int i = 0; i < textArray.length; i++) {
            decodedText += decodeMap().get(textArray[i]);
        }
        return decodedText;
    }


    
}

