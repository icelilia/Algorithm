package reversePolishNotation;

import java.util.Stack;

import bracketMatch.BracketMatch;

public class ReversePolishNotation {
	private Stack<Character> stack = new Stack<Character>();
	private String str;

	public ReversePolishNotation(String string) {
		str = string;
	}

	/**
	 * 1. 操作数直接输出 
	 * 2. 操作符分三种情况： 
	 *    (1)空栈直接压栈 
	 *    (2)操作符优先级大于栈顶操作符，压栈
	 *    (3)操作符优先级低于栈顶操作符，弹栈，直至操作符优先级大于栈顶操作符。然后压栈 
	 * 3.操作符是左括号，压栈
	 * 4.操作符是右括号，弹栈，直至遇见左括号。然后将栈中的左括号弹出 
	 * 5.输入的中缀表达式扫描完成，但是栈中仍存在操作符时，应将栈中的操作符弹出
	 * 
	 * @return 转换完成的后缀表达式
	 */
	public String toPolishNotation() {
		if (!new BracketMatch(str).isMatch()) {
			return null;
		}
		char[] charArray = str.toCharArray();
		char c;
		String result = "";
		for (int i = 0; i < charArray.length; i++) {
			c = charArray[i];
			if (isOperator(c)) {
				if (stack.isEmpty()) {
					stack.push(c);
				} else {
					if (c == '+' || c == '-') {
						while (true) {
							if (stack.isEmpty()) {
								stack.push(c);
								break;
							}
							char temp = stack.pop();
							if (temp == '(' || temp == '[' || temp == '{') {
								stack.push(temp);
								stack.push(c);
								break;
							}
							result = result + temp;
						}
					} else if (c == '*' || c == '/') {
						while (true) {
							if (stack.isEmpty()) {
								stack.push(c);
								break;
							}
							char temp = stack.pop();
							if (temp == '(' || temp == '[' || temp == '{' || temp == '+' || temp == '-') {
								stack.push(temp);
								stack.push(c);
								break;
							}
							result = result + temp;
						}
					} else if (c == '(' || c == '[' || c == '{') {
						stack.push(c);
					} else if (c == ')') {
						while (true) {
							char temp = stack.pop();
							if (temp == '(') {
								break;
							} else {
								result = result + temp;
							}
						}
					} else if (c == ']') {
						while (true) {
							char temp = stack.pop();
							if (temp == '[') {
								break;
							} else {
								result = result + temp;
							}
						}
					} else if (c == '}') {
						while (true) {
							char temp = stack.pop();
							if (temp == '{') {
								break;
							} else {
								result = result + temp;
							}
						}
					}
				}
			} else {
				result = result + c;
			}
		}
		while (!stack.isEmpty()) {
			result = result + stack.pop();
		}
		return result;
	}

	private boolean isOperator(char c) {
		return c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')' || c == '[' || c == ']' || c == '{'
				|| c == '}';
	}
}
