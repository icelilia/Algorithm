package arraySort;

import java.util.ArrayList;

public abstract class ArraySort {
	protected ArrayList<Integer> originalArray;
	protected boolean incSort;
	protected ArrayList<Integer> orderlyArray;

	/**
	 * 外部调用排序的接口
	 * 
	 * @return 排序好的数组对象，原数组对象不改变
	 */
	public abstract ArrayList<Integer> sort();

	/**
	 * 内部使用的，交换数组中两个位置的值的方法
	 * 
	 * @param i 下标i
	 * @param j 下表j
	 */
	protected void swapValue(int i, int j) {
		if (i == j) {
			return;
		}
		int temp = orderlyArray.get(i);
		orderlyArray.set(i, orderlyArray.get(j));
		orderlyArray.set(j, temp);
	}
}
