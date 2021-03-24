package leetCode;

public class No8 {
    public int myAtoi(String s) {
        char[] c = s.toCharArray();
        int i = 0;
        int e = 1;
        int startIndex = 0;
        // 除去前导空格
        for (i = 0; i < c.length; i++) {
            if (c[i] != ' ') {
                if (c[i] == '-') {
                    e = -1;
                    startIndex = ++i;
                } else if (c[i] == '+') {
                    e = 1;
                    startIndex = ++i;
                } else {
                    e = 1;
                    startIndex = i;
                }
                break;
            }
        }
        // 取有效数字
        for (i = startIndex; i < c.length; i++) {
            if (c[i] < '0' || c[i] > '9') {
                break;
            }
        }
        int res = 0;
        try {
            if (startIndex < i) {
                res = e * Integer.parseInt(s.substring(startIndex, i));
            }
        } catch (Exception exception) {
            if (e == 1) {
                return Integer.MAX_VALUE;
            } else {
                return Integer.MIN_VALUE;
            }
        }
        return res;
    }
}
