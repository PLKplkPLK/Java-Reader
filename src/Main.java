public class Main {
    public static void main( String[] args ) {
        try {
            System.out.println("Wprowadz Int: ");
            int i = Reader.readInt();
            System.out.println(i);
            System.out.println("Wprowadz Double: ");
            double d = Reader.readDouble();
            System.out.println(d);
            System.out.println("Wprowadz Hex: ");
            int h = Reader.readHex();
            System.out.println(h);
        }
        catch( java.io.IOException e )
        {
            System.out.println(e.toString());
        }
    }
}