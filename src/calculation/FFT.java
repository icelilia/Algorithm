package calculation;

public class FFT {
    private static final int MAX_LENGTH = 5100;
    private static int n = 1;
    private static String num1;
    private static String num2;
    private static int len1;
    private static int len2;
    private static char[] c1;
    private static char[] c2;
    private static String res;

    public static void main(String[] args) {

    }

    private static String FFT(String num1, String num2) {
        FFT.num1 = num1;
        FFT.num2 = num2;


        return null;
    }

    private static void init() {
        len1 = num1.length();
        len2 = num2.length();
        while (n < len1 + len2) {
            n <<= 1;
        }    }

    private static void DFT() {

    }

    private static void IDFT() {

    }

}
