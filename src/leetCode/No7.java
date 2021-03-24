package leetCode;

import java.util.Arrays;

public class No7 {
    public int reverse(int x) {
        if (x == 0) {
            return 0;
        } else if (x < 0) {
            x = -x;
            char[] c = new char[10];
            for (int i = 0; i <= 9; i++) {
                c[i] = (char) (x % 10 + '0');
                x = x / 10;
            }

            int i;
            for (i = 9; i >= 0; i--) {
                if (c[i] != '0') {
                    break;
                }
            }
            char[] temp = Arrays.copyOfRange(c, 0, i + 1);

            try {
                return -Integer.parseInt(new String(temp));
            } catch (Exception e) {
                return 0;
            }
        } else {
            char[] c = new char[10];
            for (int i = 0; i <= 9; i++) {
                c[i] = (char) (x % 10 + '0');
                x = x / 10;
            }

            int i;
            for (i = 9; i >= 0; i--) {
                if (c[i] != '0') {
                    break;
                }
            }
            char[] temp = Arrays.copyOfRange(c, 0, i + 1);

            try {
                return Integer.parseInt(new String(temp));
            } catch (Exception e) {
                return 0;
            }
        }
    }
}
