package arraySort;

import java.util.ArrayList;

public class BubbleSort extends ArraySort {
    /**
     * 构造方法
     *
     * @param array   待排数组对象
     * @param incSort 是否增量排序，true为增量，false为减量
     */
    public BubbleSort(ArrayList<Integer> array, boolean incSort) {
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

        bubbleSort(0, length - 1);
        return orderlyArray;
    }

    private void bubbleSort(int leftIndex, int rightIndex) {
        // 排序时，每次冒泡都会使未排序部分中的最值“冒泡”至末端，所以可以减少j的遍历长度
        for (int i = leftIndex; i <= rightIndex; i++) {
            for (int j = leftIndex + 1; j <= rightIndex + leftIndex - i; j++) {
                if (incSort) {
                    if (orderlyArray.get(j - 1) > orderlyArray.get(j)) {
                        swapValue(j - 1, j);
                    }
                } else {
                    if (orderlyArray.get(j - 1) < orderlyArray.get(j)) {
                        swapValue(j - 1, j);
                    }
                }
            }
        }
    }
}
