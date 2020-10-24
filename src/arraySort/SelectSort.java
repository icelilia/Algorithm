package arraySort;

import java.util.ArrayList;

public class SelectSort extends ArraySort {
	/**
	 * 构造方法
	 * 
	 * @param array   待排数组对象
	 * @param incSort 是否增量排序，true为增量，false为减量
	 */
	public SelectSort(ArrayList<Integer> array, boolean incSort) {
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

		selectSort(0, length - 1);
		return orderlyArray;
	}

	private void selectSort(int leftIndex, int rightIndex) {
		if (incSort) {
			int maximum;
			int maximumIndex;
			for (int i = leftIndex; i <= rightIndex; i++) {
				maximum = orderlyArray.get(leftIndex);
				maximumIndex = leftIndex;
				for (int j = leftIndex + 1; j <= rightIndex + leftIndex - i; j++) {
					if (orderlyArray.get(j) >= maximum) {
						maximum = orderlyArray.get(j);
						maximumIndex = j;
					}
				}
				swapValue(maximumIndex, rightIndex + leftIndex - i);
			}
		} else {
			int minimum;
			int minimumIndex;
			for (int i = leftIndex; i <= rightIndex; i++) {
				minimum = orderlyArray.get(leftIndex);
				minimumIndex = leftIndex;
				for (int j = leftIndex + 1; j <= rightIndex + leftIndex - i; j++) {
					if (orderlyArray.get(j) <= minimum) {
						minimum = orderlyArray.get(j);
						minimumIndex = j;
					}
				}
				swapValue(minimumIndex, rightIndex + leftIndex - i);
			}
		}
	}
}
