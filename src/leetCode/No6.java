package leetCode;

public class No6 {
    public String convert(String s, int numRows) {
        char[] c = s.toCharArray();
        char[] res = new char[c.length];
        int index = 0;
        if (numRows == 1){
            return s;
        }
        else{
            int numGroup = c.length / (2 * numRows - 2) + 1;
            for (int line = 1; line <= numRows; line++) {
                int i1 = line - 1;
                int i2 = (2 * numRows - 2) - (line - 1);
                for (int group = 1; group <= numGroup; group++) {
                    if (line == 1) {
                        if ((2 * numRows - 2) * (group - 1) < c.length) {
                            res[index++] = c[(2 * numRows - 2) * (group - 1)];
                        }
                    } else if (line == numRows) {
                        if (numRows - 1 + (2 * numRows - 2) * (group - 1) < c.length) {
                            res[index++] = c[numRows - 1 + (2 * numRows - 2) * (group - 1)];
                        }
                    } else {
                        if (i1 + (2 * numRows - 2) * (group - 1) < c.length) {
                            res[index++] = c[i1 + (2 * numRows - 2) * (group - 1)];
                        }
                        if (i2 + (2 * numRows - 2) * (group - 1) < c.length) {
                            res[index++] = c[i2 + (2 * numRows - 2) * (group - 1)];
                        }
                    }
                }
            }
            return new String(res);
        }
    }
}
