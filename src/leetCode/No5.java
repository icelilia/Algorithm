package leetCode;

public class No5 {
    public String longestPalindrome(String s) {
        char[] c = s.toCharArray();
        int i = 0;
        int j = 0;
        int p;
        int bias;
        int length = 1;
        // 长度为奇数
        for (p = 0; p < c.length; p++) {
            for (bias = 0; bias < c.length; bias++) {
                if (p - bias >= 0 && p + bias <= c.length - 1) {
                    if (c[p - bias] != c[p + bias]) {
                        if (bias * 2 - 1 > length) {
                            i = p - bias + 1;
                            j = p + bias - 1;
                            length = bias * 2 - 1;
                        }
                        break;
                    }
                } else {
                    if (bias * 2 - 1 > length) {
                        i = p - bias + 1;
                        j = p + bias - 1;
                        length = bias * 2 - 1;
                    }
                    break;
                }
            }
        }
        // 长度为偶数
        for (p = 0; p < c.length - 1; p++) {
            if (c[p] != c[p + 1]) {
                continue;
            }
            for (bias = 0; bias < c.length; bias++) {
                if (p - bias >= 0 && p + 1 + bias <= c.length - 1) {
                    if (c[p - bias] != c[p + 1 + bias]) {
                        if (bias * 2 > length) {
                            i = p - bias + 1;
                            j = p + 1 + bias - 1;
                            length = bias * 2;
                        }
                        break;
                    }
                } else {
                    if (bias * 2 > length) {
                        i = p - bias + 1;
                        j = p + 1 + bias - 1;
                        length = bias * 2;
                    }
                    break;
                }
            }
        }
        return s.substring(i, j + 1);
    }
}
