package arraySort;

import java.util.ArrayList;

public class ShellSort extends ArraySort {
	/**
	 * 构造方法
	 * 
	 * @param array   待排数组
	 * @param incSort 是否增量排序，true为增量，false为减量
	 */
	public ShellSort(ArrayList<Integer> array, boolean incSort) {
		originalArray = array;
		this.incSort = incSort;
		orderlyArray = new ArrayList<Integer>(originalArray.size());
		for (int i = 0; i < originalArray.size(); i++) {
			orderlyArray.add(originalArray.get(i));
		}
	}

	@Override
	public ArrayList<Integer> sort() {
		if (originalArray == null) {
			// 视情况进行异常处理
			return null;
		}

		int length = originalArray.size();
		if (length == 0 || length == 1) {
			return orderlyArray;
		}

		shellSort(0, length - 1);
		return orderlyArray;
	}

	private void shellSort(int leftIndex, int rightIndex) {
		int length = rightIndex - leftIndex + 1;
		int step = 1;
		while (step < length >> 1) {
			step = step * 2 + 1;
		}
		while (step >= 1) {
			for (int i = leftIndex + step; i <= rightIndex; i++) {
				for (int j = i - step; j >= leftIndex; j = j - step) {
					if (incSort) {
						if (orderlyArray.get(j) > orderlyArray.get(j + step)) {
							swapValue(j, j + step);
						}
					} else {
						if (orderlyArray.get(j) < orderlyArray.get(j + step)) {
							swapValue(j, j + step);
						}
					}
				}
			}
			step = step >> 1;
		}
	}
}
