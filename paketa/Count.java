package paketa;

public class Count {
    public static int countLines(String teksti) {
        String[] lines = teksti.split("\r\n|\r|\n");
        return  lines.length;
    }

    public static int countWords(String teksti) {
        if (teksti.length() == 0) {
            return 0;
        } else {
            teksti = teksti.trim();
            String[] tekstiArray = teksti.split("\\s+");
            return tekstiArray.length;
        }
    }
    public static int countLetters(String teksti) {
        int nrishkronjave = 0;

        teksti = teksti.toLowerCase();
        for (int i = 0; i < teksti.length(); i++) {
            if ((teksti.charAt(i) >= 'a' && teksti.charAt(i) <= 'z')){
                nrishkronjave++;
            }
        }
        return nrishkronjave;
    }

    public static int countCharacters(String teksti) {
        int nrikaraktereve = 0;
        for (int i = 0; i < teksti.length(); i++) {
            if (teksti.charAt(i) != ' ')
                nrikaraktereve++;
        }

        return nrikaraktereve;
    }

    public static int countSymbols(String teksti) {
        int nrisimboleve = 0;

        teksti = teksti.toLowerCase();
        for (int i = 0; i < teksti.length(); i++) {
            if ((teksti.charAt(i) >= 'a' && teksti.charAt(i) <= 'z') || teksti.charAt(i) == ' '
                    || (teksti.charAt(i) >= '0' && teksti.charAt(i) <= '9')) {
                continue;
            } else {
                nrisimboleve++;
            }
        }
        return nrisimboleve;
    }

    public static int countVowels(String teksti) {
        int nrizanoreve = 0;
        teksti = teksti.toLowerCase();
        for (int i = 0; i < teksti.length(); i++) {
            switch (teksti.charAt(i)) {
                case 'a':
                    nrizanoreve++;
                    break;
                case 'e':
                    nrizanoreve++;
                    break;
                case 'o':
                    nrizanoreve++;
                    break;
                case 'u':
                    nrizanoreve++;
                    break;
                case 'i':
                    nrizanoreve++;
                    break;
            }
        }
        return nrizanoreve;
    }

    public static int countSpaces(String teksti) {
        int numriispaces = 0;
        for (int i = 0; i < teksti.length(); i++) {
            if (teksti.charAt(i) == ' ')
                numriispaces++;
        }
        return numriispaces;
    }

    public static int countConsonants(String teksti) {
        return teksti.length() - countSpaces(teksti) - countVowels(teksti) - countSymbols(teksti);
    }
}

