import edu.duke.*;

public class tester {
    public static void main(String[] args) {
        //FileResource fileResource = new FileResource();
        //String test = fileResource.asString();
        //int[] key = {17, 14, 12, 4};
        //VigenereCipher vigenereCipher = new VigenereCipher( key );
        VigenereBreaker vigenereBreaker =new VigenereBreaker();
        //System.out.println(vigenereBreaker.mostCommonCharIn( vigenereBreaker.readDictionary(fileResource) ));
        //System.out.println(vigenereBreaker.sliceString( "abcdefghijklm", 1, 4 ));
        //System.out.println(vigenereBreaker.tryKeyLength( test, 5, 'e' ));
        //vigenereBreaker.tryKeyLength( test, 4, 'e' );
        vigenereBreaker.breakVigenere();
    }
}
