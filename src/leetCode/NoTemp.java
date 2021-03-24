package leetCode;

import java.util.HashMap;
import java.util.Map;

public class NoTemp {

    public boolean possibleBipartition(int N, int[][] dislikes) {
        if (dislikes.length == 0) {
            return true;
        }

        Map<Integer, Boolean> group1 = new HashMap<>();
        Map<Integer, Boolean> group2 = new HashMap<>();

        group1.put(dislikes[0][0], true);
        group2.put(dislikes[0][1], true);

        boolean remainder = true;
        boolean add = false;
        while (remainder) {
            remainder = false;
            add = false;
            int tempX = 0;
            int tempY = 0;
            for (int i = 1; i < dislikes.length; i++) {
                int x = dislikes[i][0];
                int y = dislikes[i][1];
                if (!group1.containsKey(x) && !group1.containsKey(y) && !group2.containsKey(x) && !group2.containsKey(y)) {
                    tempX = x;
                    tempY = y;
                    remainder = true;
                } else if (group1.containsKey(x) && group1.containsKey(y)) {
                    return false;
                } else if (group2.containsKey(x) && group2.containsKey(y)) {
                    return false;
                } else if (group1.containsKey(x) && !group1.containsKey(y) && !group2.containsKey(y)) {
                    group2.put(y, true);
                    add = true;
                } else if (group2.containsKey(x) && !group1.containsKey(y) && !group2.containsKey(y)) {
                    group1.put(y, true);
                    add = true;
                } else if (group1.containsKey(y) && !group1.containsKey(x) && !group2.containsKey(x)) {
                    group2.put(x, true);
                    add = true;
                } else if (group2.containsKey(y) && !group1.containsKey(x) && !group2.containsKey(x)) {
                    group1.put(x, true);
                    add = true;
                }
            }
            if (!add) {
                group1.put(tempX, true);
                group2.put(tempY, true);
            }
        }
        return true;
    }
}
