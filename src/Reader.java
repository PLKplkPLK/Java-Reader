import java.io.IOException;
import java.util.Scanner;
/**
 * @author MSI
 */
public class Reader
{
    /**
     * Reads one character from the input stream System.in
     * @return  returns read character
     */

    private static char    LastChar='\0';
    private static boolean ungetted=false;

    public static char getChar()  // only one char could be ungetted
    {
        try {
            if (!ungetted) {
                LastChar = (char) System.in.read();
            } else ungetted = false;
            return LastChar;
        }
        catch( java.io.IOException e ) {return '\0';}
    }

    /**
     * Returns one character to the input for reloading. Only one char could be returned!
     * @param c - zwracany znak do ponownego odczytu
     */
    public static void ungetChar( char c )
    {
        LastChar = c;
        ungetted=true;
    }

    /**
     * Reads the integer value written in char case
     * @return return the value of integer  number
     */
    public static int readInt()
    {
        //czy znak - fun
        // czytanie liczby bez znaku - fun
        // zwrocic wart (return)
        return getSign()? -readNum() : readNum();
    }

    /**
     * Reads the double value written in char case
     * @return the value of double  num
     */
    public static double readDouble()
    {
        //czy znak - fun
        boolean znak = getSign();
        // czytanie cechy fun
        char c = getChar();
        double wyn = 0;
        while(isDecDigit(c))
        {
            wyn *= 10;
            wyn += charDecDigit2Int(c);
            c = getChar();
        }
        if(c == ',' || c == '.') {c = getChar();}
        else
        {
            ungetChar(c);
            return znak? -wyn : wyn;
        }
        // czytanie mantysy
        double wyn2 = 0;
        double k = 0.1;
        while(isDecDigit(c))
        {
            wyn += charDecDigit2Int(c)*k;
            k *= 0.1;
            c = getChar();
        }
        //na koncu zwraca znak
        ungetChar(c);
        // zwrocenie wyniku (return)
        return znak? -wyn : wyn;
    }

    /**
     * Reads the hexadecimal value written in char case
     * @return returns value of proper hexadecimal value
     */
    public static int readHex() throws IOException
    {
        // poprzedajaca biale znaki
        skipSpaces();
        //sprawdzic czy format 0x 0X
        char c = getChar();
        if(c=='0')
        {
            c = getChar();
            if(c!='x'&&c!='X') throw new IOException("Error: no 0x");
        }
        else{throw new IOException("Error: no 0x");}

        int wyn = 0;
        while(isHexDigit(c=getChar()))
        {
            wyn *= 16;
            wyn += charHexDigit2Int(c);
        }

        //na koncu zwraca znak
        ungetChar(c);
        //return wynik
        return wyn;
    }

    //================ P R I V A T E INTERFACE ==========================================
    private static int readNum()
    {
        //czytanie liczby calk bez znaku poprzedz bialymi znakami
        //na koncu zwraca znak
        skipSpaces();
        int wynik = 0;
        char c;
        while((c=getChar())>=48&&c<=57)
        {
            wynik *= 10;
            wynik += charDecDigit2Int(c);
        }
        ungetChar(c);
        return wynik;
    }

    private static boolean getSign()
    {
        //czytanie znaku poprzedz bialymi znakami
        //jak byl minus to zwraca true
        //na koncu zwraca znak jesli nie wylo '-'
        skipSpaces();
        char c = getChar();
        if(c=='-') return true;
        else
        {
            ungetChar(c);
            return false;
        }
    }
    private static void skipSpaces()
    {
        // pomija spacje tabulatroy CR-y, LF-dy
        //na koncu zwraca znak
        char c;
        while((c=getChar())==10||c==13||c==32||c==9);
        ungetChar(c);
    }

    private static boolean isDecDigit( char c )
    {
        // jedna instr(linia) return . . .
        return c>='0'&&c<='9';
    }

    private static boolean isHexDigit( char c )
    {
        return isHexLetter(c) || isDecDigit(c);
    }

    private static char upperCase( char c )
    {
        return c>='a'&&c<='z'? (char)(c-32) : c;
    }

    private static boolean isHexLetter( char c )
    {

        return upperCase(c)>='A'&&upperCase(c)<='F';
    }

    private static int charDecDigit2Int( char c )
    {
        return c-48;
    }

    private static int charHexDigit2Int( char c )
    {
        //jenda instr return . . . .   (w tej jednej instr ma c zamianac na upperCase) // ma wywolac charDecDigit2Int
        return isHexLetter(c)? upperCase(c) - 55 : charDecDigit2Int(c);
    }

}