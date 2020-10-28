package taylorFormula;

public class TaylorFormula {

    private static long[] factorialList = new long[50];

    static {
        factorialList[0] = 1L;
        factorialList[1] = 1L;
        for (int i = 2; i < 50; i++) {
            factorialList[i] = factorialList[i - 1] * i;
        }
    }

    public static double xPowerOfE(double x, int accuracy) {
        double res = 1.0;
        for (int i = 1; i < accuracy; i++) {
            res += (simplePow(x, i) / factorial(i));
        }
        return res;
    }

    public static double lnX(double x, int accuracy) {
        double res = 0.0;
        for (int i = 1; i <= accuracy; i++) {
            if (i % 2 == 1) {
                res += (simplePow((x - 1), i) / i);
            } else {
                res -= (simplePow((x - 1), i) / i);
            }
        }
        return res;
    }

    public static double pow(double x, double y, int accuracy) {
        double temp = lnX(x, accuracy) * y;
        return xPowerOfE(temp, accuracy);
    }


    public static double simplePow(double x, int n) {
        double res = 1;
        for (int i = 0; i < n; i++) {
            res *= x;
        }
        return res;
    }

    public static long factorial(int x) {
        return factorialList[x];
    }
}
