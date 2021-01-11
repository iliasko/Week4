import java.io.File;
import java.sql.SQLOutput;
import java.util.*;
import edu.duke.*;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        //REPLACE WITH YOUR CODE
        StringBuilder answerString = new StringBuilder();
        for (int k = whichSlice; k < message.length(); k += totalSlices){
            answerString.append( message.charAt( k ) );
        }
        return answerString.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        //WRITE YOUR CODE HERE
        CaesarCracker caesarCracker = new CaesarCracker(mostCommon);
        for (int k = 0; k < klength; k++){
            String testString = sliceString( encrypted, k, klength );
            key[k] = caesarCracker.getKey( testString );
        }
        return key;
    }

    public HashSet<String> readDictionary(FileResource fr){
        HashSet<String> dict = new HashSet<>();
        for (String words : fr.words()){
            words = words.toLowerCase();
            dict.add( words );
        }
        return dict;
    }

    public int countWords(String message, HashSet dict){
        int countTrueWords = 0;
        for (String words : message.split( "\\W+" )){
            words = words.toLowerCase();
            if (dict.contains( words )){
                countTrueWords++;
            }
        }
        return countTrueWords;
    }

    public String breakForLanguage(String messege, HashSet dict){
        int trueWords = 0;
        String answerMessage = "";
        int kl = 0;
        for (int k = 1; k <= 100; k++){
            int[] key = tryKeyLength( messege, k, mostCommonCharIn( dict ) );
            VigenereCipher vc = new VigenereCipher( key );
            String trayMessage = vc.decrypt( messege );
            int trayTrueWords = countWords( trayMessage, dict );
            if (trayTrueWords > trueWords){
                trueWords = trayTrueWords;
                answerMessage = trayMessage;
                kl = key.length;
            }
        }
        System.out.println("TrueWords = "+ trueWords);
        System.out.println("KeyLength = "+ kl);
        return answerMessage;
    }

    public char mostCommonCharIn(HashSet<String> dict){
        String dictString = dict.toString();
        HashMap<Character, Integer> charCount = new HashMap<>();
        for (int k = 0; k < dictString.length(); k++){
            char ch = dictString.charAt( k );
            if (! charCount.keySet().contains( ch )){
                charCount.put( ch, 1 );
            } else {
                charCount.put( ch, charCount.get( ch ) + 1 );
            }
        }
        int maxCount = 0;
        char mustChar = ' ';
        for (Character ch : charCount.keySet()){
            if (!(ch == ' ' || ch == ',') && maxCount < charCount.get( ch )){
                maxCount = charCount.get( ch );
                mustChar = ch;
            }
        }
        return mustChar;
    }

    public void  breakForAllLangs(String encrypted, HashMap<String, HashSet<String>> languages){
        int maxTrueWords = 0;
        String bestLang = "";
        String decryptedString = "";
        for (String curLang : languages.keySet()){
            HashSet<String> curDict = languages.get( curLang );
            System.out.println("curLang = "+ curLang);
            String curDecryptedString = breakForLanguage( encrypted, curDict );
            int curTrueWords = countWords( curDecryptedString, curDict );
            if (maxTrueWords < curTrueWords){
                maxTrueWords = curTrueWords;
                bestLang = curLang;
                decryptedString = curDecryptedString;
            }
        }
        System.out.println("------------------");
        System.out.println(bestLang);
        System.out.println(maxTrueWords);
        System.out.println(decryptedString);
    }



    public void breakVigenere () {
        //WRITE YOUR CODE HERE
        FileResource fileResource = new FileResource();
        String fileString = fileResource.asString();
        DirectoryResource dictionarys = new DirectoryResource();
        HashMap<String, HashSet<String>> dictMap = new HashMap<>();
        for (File file : dictionarys.selectedFiles()){
            FileResource fr = new FileResource(file);
            dictMap.put( file.getName(), readDictionary(fr) );
        }
        breakForAllLangs( fileString, dictMap );
    }
    
}
