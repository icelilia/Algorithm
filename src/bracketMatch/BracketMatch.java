package bracketMatch;

import java.util.Stack;

public class BracketMatch {
	private Stack<Character> stack = new Stack<Character>();
	private String str;

	public BracketMatch(String string) {
		str = string;
	}

	/**
	 * 括号匹配，检查小、中、大括号的嵌套关系
	 * 
	 * @return 是否匹配，true为匹配，false为不匹配
	 */
	public boolean isMatch() {
		char[] charArray = str.toCharArray();
		char c;
		for (int i = 0; i < charArray.length; i++) {
			c = charArray[i];
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
