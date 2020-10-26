package arraySort;

import java.util.ArrayList;

public class InsertSort {
    private static ArrayList<Integer> orderlyArray;

    public static ArrayList<Integer> sort(ArrayList<Integer> originalArray, boolean incSort) {
        if (originalArray == null) {
            // 视情况进行异常处理
            return null;
        }

        int length = originalArray.size();
        orderlyArray = new ArrayList<>(length);
        orderlyArray.addAll(originalArray);

        if (length == 0 || length == 1) {
            return orderlyArray;
        }

        insertSort(0, length - 1, incSort);
        return orderlyArray;
    }

    private static void insertSort(int leftIndex, int rightIndex, boolean incSort) {
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

    private static void swapValue(int i, int j) {
        if (i == j) {
            return;
        }
        int temp = orderlyArray.get(i);
        orderlyArray.set(i, orderlyArray.get(j));
        orderlyArray.set(j, temp);
    }
}
