package leetCode;

public class No9 {
    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        } else if (x == 0) {
            return true;
        } else {
            char[] c = String.valueOf(x).toCharArray();
            int i = 0;
            int j = c.length - 1;
            while (i < j) {
                if (c[i++] != c[j--]) {
                    return false;
                }
            }
            return true;
        }
    }
}
