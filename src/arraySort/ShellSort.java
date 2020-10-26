package arraySort;

import java.util.ArrayList;

/**
 * @author Spotted_Dog
 */
public class ShellSort {
    private static ArrayList<Integer> orderlyArray;

    /**
     * 通用的方法调用
     *
     * @param originalArray 待排数组
     * @param isIncSort     是否增量排序
     * @return 新的有序数组
     */
    public ArrayList<Integer> sort(ArrayList<Integer> originalArray, boolean isIncSort) {
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

        shellSort(0, length - 1, isIncSort);
        return orderlyArray;
    }

    private static void shellSort(int leftIndex, int rightIndex, boolean isIncSort) {
        int length = rightIndex - leftIndex + 1;
        int step = 1;
        while (step < length >> 1) {
            step = step * 2 + 1;
        }
        while (step >= 1) {
            for (int i = leftIndex + step; i <= rightIndex; i++) {
                for (int j = i - step; j >= leftIndex; j = j - step) {
                    if (isIncSort) {
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
