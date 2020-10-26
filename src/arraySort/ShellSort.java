package arraySort;

import java.util.ArrayList;

public class ShellSort {
    private static ArrayList<Integer> orderlyArray;

    public ArrayList<Integer> sort(ArrayList<Integer> originalArray, boolean incSort) {
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

        shellSort(0, length - 1, incSort);
        return orderlyArray;
    }

    private static void shellSort(int leftIndex, int rightIndex, boolean incSort) {
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

    private static void swapValue(int i, int j) {
        if (i == j) {
            return;
        }
        int temp = orderlyArray.get(i);
        orderlyArray.set(i, orderlyArray.get(j));
        orderlyArray.set(j, temp);
    }
}
