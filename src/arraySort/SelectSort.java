package arraySort;

import java.util.ArrayList;

/**
 * @author Spotted_Dog
 */
public class SelectSort {
    private static ArrayList<Integer> orderlyArray;

    /**
     * 通用的方法调用
     *
     * @param originalArray 待排数组
     * @param isIncSort     是否增量排序
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

        selectSort(0, length - 1, isIncSort);
        return orderlyArray;
    }

    private static void selectSort(int leftIndex, int rightIndex, boolean isIncSort) {
        if (isIncSort) {
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

    private static void swapValue(int i, int j) {
        if (i == j) {
            return;
        }
        int temp = orderlyArray.get(i);
        orderlyArray.set(i, orderlyArray.get(j));
        orderlyArray.set(j, temp);
    }
}
