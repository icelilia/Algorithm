package arraySort;

import java.util.ArrayList;

public class MergeSort {
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

        mergeSort(0, length - 1, incSort);
        return orderlyArray;
    }

    /**
     * 归并排序内部的分治递归调用
     *
     * @param leftIndex  当前待排区间的左下标
     * @param rightIndex 当前待排区间的右下标
     */
    private static void mergeSort(int leftIndex, int rightIndex, boolean incSort) {
        if (leftIndex < rightIndex) {
            int middleIndex = leftIndex + rightIndex >> 1;
            mergeSort(leftIndex, middleIndex, incSort);
            mergeSort(middleIndex + 1, rightIndex, incSort);
            merge(leftIndex, rightIndex, incSort);
        }
    }

    /**
     * 归并排序内部的合并
     *
     * @param leftIndex  当前排序区间的左下标
     * @param rightIndex 当前排序区间的右下标
     */
    private static void merge(int leftIndex, int rightIndex, boolean incSort) {
        int middleIndex = (leftIndex + rightIndex) >> 1;
        int l = leftIndex;
        int r = middleIndex + 1;
        int[] tempArray = new int[rightIndex - leftIndex + 1];
        for (int i = 0; i < tempArray.length; i++) {
            if (incSort) {
                if (l > middleIndex) {
                    tempArray[i] = orderlyArray.get(r++);
                } else if (r > rightIndex) {
                    tempArray[i] = orderlyArray.get(l++);
                } else {
                    int left = orderlyArray.get(l);
                    int right = orderlyArray.get(r);
                    if (left <= right) {
                        tempArray[i] = left;
                        l++;
                    } else {
                        tempArray[i] = right;
                        r++;
                    }
                }
            } else {
                if (l > middleIndex) {
                    tempArray[i] = orderlyArray.get(r++);
                } else if (r > rightIndex) {
                    tempArray[i] = orderlyArray.get(l++);
                } else {
                    int left = orderlyArray.get(l);
                    int right = orderlyArray.get(r);
                    if (left >= right) {
                        tempArray[i] = left;
                        l++;
                    } else {
                        tempArray[i] = right;
                        r++;
                    }
                }
            }
        }
        for (int i = 0; i < tempArray.length; i++) {
            orderlyArray.set(i + leftIndex, tempArray[i]);
        }
    }
}
