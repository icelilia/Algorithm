package stringMatch;

import java.util.ArrayList;

public class KMP {
    /**
     * KMP算法字符串匹配
     *
     * @param source  源字符串
     * @param pattern 待匹配的模式字符串
     * @return 模式字符串在源字符串中第一次出现的位置，如果无法匹配，则返回-1
     */
    public static int kmp(String source, String pattern) {
        ArrayList<String> childrenOfPattern = getChildrenOfPattern(pattern);
        int[] tempNextArray = getTempNextArray(childrenOfPattern);
        int[] nextArray = getNextArray(tempNextArray);
        return match(source, pattern, nextArray);
    }

    /**
     * 获得pattern的所有子串
     *
     * @param pattern 模式字符串
     * @return pattern的所有子串
     */
    private static ArrayList<String> getChildrenOfPattern(String pattern) {
        char[] patternChars = pattern.toCharArray();
        ArrayList<String> res = new ArrayList<>(pattern.length());
        String temp;
        for (int i = 1; i <= pattern.length(); i++) {
            temp = new String(patternChars, 0, i);
            res.add(temp);
        }
        return res;
    }

    /**
     * 获得所有子串的前缀和后缀的最大公共元素长度
     *
     * @param childrenOfPattern 模式字符串的所有子串
     * @return 最大公共元素长度数组，tempNextArray
     */
    private static int[] getTempNextArray(ArrayList<String> childrenOfPattern) {
        int[] res = new int[childrenOfPattern.size()];
        for (int i = 0; i < childrenOfPattern.size(); i++) {
            char[] childChars = childrenOfPattern.get(i).toCharArray();
            int start, end;
            for (start = 0, end = childChars.length - 1; start <= childChars.length - 1 && end >= 0; start++, end--) {
                if (childChars[start] != childChars[end]) {
                    break;
                }
            }
            res[i] = start;
        }
        return res;
    }

    /**
     * tempNextArray数组整体向右移动一位，第一位补-1，得到nextArray
     *
     * @param tempNextArray tempNextArray
     * @return nextArray
     */
    private static int[] getNextArray(int[] tempNextArray) {
        if (tempNextArray.length - 1 >= 0) {
            System.arraycopy(tempNextArray, 0, tempNextArray, 1, tempNextArray.length - 1);
        }
        tempNextArray[0] = -1;
        return tempNextArray;
    }

    private static int match(String source, String pattern, int[] nextArray) {
        int i = 0, j = 0;
        while (i < source.length() && j < pattern.length()) {
            if (j == -1 || source.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            } else {
                j = nextArray[j];
            }
        }
        return j == pattern.length() ? i - j : -1;
    }
}
