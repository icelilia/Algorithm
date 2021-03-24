package leetCode;

import java.util.HashMap;
import java.util.Map;

public class No3 {
    public int lengthOfLongestSubstring(String s) {
        char[] c = s.toCharArray();
        Map<Character, Integer> hash = new HashMap<>();
        int startIndex = 0;
        int length = 0;
        for (int i = 0; i < c.length; i++) {
            if (!hash.containsKey(c[i])) {
                hash.put(c[i], i);
            } else {
                if (i - startIndex > length) {
                    length = i - startIndex;
                }
                i = hash.get(c[i]);
                hash.clear();
                startIndex = i + 1;
            }
        }
        if (c.length - startIndex > length) {
            length = c.length - startIndex;
        }
        return length;
    }
}
