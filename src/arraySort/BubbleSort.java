package arraySort;

import java.util.ArrayList;

/**
 * @author Spotted_Dog
 */
public class BubbleSort {
    private static ArrayList<Integer> orderlyArray;

    /**
     * 通用的方法调用
     *
     * @param originalArray 待排数组
     * @param isIncSort       是否增量排序
     * @return 新的有序数组
     */
    public static ArrayList<Integer> sort(ArrayList<Integer> originalArray, boolean isIncSort) {
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

        bubbleSort(0, length - 1, isIncSort);
        return orderlyArray;
    }

    private static void bubbleSort(int leftIndex, int rightIndex, boolean isIncSort) {
        // 排序时，每次冒泡都会使未排序部分中的最值“冒泡”至末端，所以可以减少j的遍历长度
        for (int i = leftIndex; i <= rightIndex; i++) {
            for (int j = leftIndex + 1; j <= rightIndex + leftIndex - i; j++) {
                if (isIncSort) {
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

    private static void swapValue(int i, int j) {
        if (i == j) {
            return;
        }
        int temp = orderlyArray.get(i);
        orderlyArray.set(i, orderlyArray.get(j));
        orderlyArray.set(j, temp);
    }
}
