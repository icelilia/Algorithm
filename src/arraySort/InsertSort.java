package arraySort;

import java.util.ArrayList;

public class InsertSort extends ArraySort {
    /**
     * 构造方法
     *
     * @param array   待排数组对象
     * @param incSort 是否增量排序，true为增量，false为减量
     */
    public InsertSort(ArrayList<Integer> array, boolean incSort) {
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

        insertSort(0, length - 1);
        return orderlyArray;
    }

    private void insertSort(int leftIndex, int rightIndex) {
        for (int i = leftIndex + 1; i <= rightIndex; i++) {
            for (int j = i - 1; j >= leftIndex; j--) {
                if (incSort) {
                    if (orderlyArray.get(j) > orderlyArray.get(j + 1)) {
                        swapValue(j, j + 1);
                    }
                } else {
                    if (orderlyArray.get(j) < orderlyArray.get(j + 1)) {
                        swapValue(j, j + 1);
                    }
                }
            }
        }
    }
}
