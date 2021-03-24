package leetCode;

import java.util.Stack;

public class No12 {
    // 1 <= num <= 3999
    public String intToRoman(int num) {
        char[] c = String.valueOf(num).toCharArray();
        char[] one = new char[]{'I', 'X', 'C', 'M'};
        char[] five = new char[]{'V', 'L', 'D'};
        Stack<Character> stack = new Stack<>();
        int i;
        int j = 0;
        for (i = c.length - 1; i >= 0; i--) {
            switch (c[i]) {
                case '0':
                    break;
                case '1':
                    stack.push(one[j]);
                    break;
                case '2':
                    stack.push(one[j]);
                    stack.push(one[j]);
                    break;
                case '3':
                    stack.push(one[j]);
                    stack.push(one[j]);
                    stack.push(one[j]);
                    break;
                case '4':
                    stack.push(five[j]);
                    stack.push(one[j]);
                    break;
                case '5':
                    stack.push(five[j]);
                    break;
                case '6':
                    stack.push(one[j]);
                    stack.push(five[j]);
                    break;
                case '7':
                    stack.push(one[j]);
                    stack.push(one[j]);
                    stack.push(five[j]);
                    break;
                case '8':
                    stack.push(one[j]);
                    stack.push(one[j]);
                    stack.push(one[j]);
                    stack.push(five[j]);
                    break;
                case '9':
                    stack.push(one[j + 1]);
                    stack.push(one[j]);
                    break;
            }
            j++;
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return new String(sb);
    }
}
