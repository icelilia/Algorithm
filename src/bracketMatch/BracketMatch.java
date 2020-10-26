package bracketMatch;

import java.util.Stack;

public class BracketMatch {
    private static final Stack<Character> stack = new Stack<>();

    /**
     * 括号匹配，检查小、中、大括号的嵌套关系
     *
     * @return 是否匹配，true为匹配，false为不匹配
     */
    public static boolean isMatch(String str) {
        char[] charArray = str.toCharArray();
        char c;
        for (char value : charArray) {
            c = value;
            if (c == '(') {
                stack.push(c);
            } else if (c == '[') {
                if (stack.isEmpty()) {
                    stack.push(c);
                } else {
                    char temp = stack.pop();
                    // 中括号不能嵌套在小括号内
                    if (temp == '(') {
                        return false;
                    } else {
                        stack.push(temp);
                        stack.push(c);
                    }
                }
            } else if (c == '{') {
                if (stack.isEmpty()) {
                    stack.push(c);
                } else {
                    char temp = stack.pop();
                    // 大括号不能嵌套在小括号或中括号内
                    if (temp == '(' || temp == '[') {
                        return false;
                    } else {
                        stack.push(temp);
                        stack.push(c);
                    }
                }
            } else if (c == ')') {
                if (stack.isEmpty() || stack.pop() != '(') {
                    return false;
                }
            } else if (c == ']') {
                if (stack.isEmpty() || stack.pop() != '[') {
                    return false;
                }
            } else if (c == '}') {
                if (stack.isEmpty() || stack.pop() != '{') {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}
