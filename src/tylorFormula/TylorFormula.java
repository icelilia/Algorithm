package tylorFormula;

public class TylorFormula {

    public static double powE(double x, int accuracy) {
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


    public static double simplePow(double x, int n) {
        double res = 1;
        for (int i = 0; i < n; i++) {
            res *= x;
        }
        return res;
    }

    public static long factorial(long x) {
        if (x == 0L) {
            return 1;
        }
        long res = 1;
        for (long i = 1; i <= x; i++) {
            res *= i;
        }
        return res;
    }
}
