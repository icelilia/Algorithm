package arraySort;

import java.util.ArrayList;

/**
 * @author Spotted_Dog
 */
public class MergeSort {
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

        mergeSort(0, length - 1, isIncSort);
        return orderlyArray;
    }

    /**
     * 归并排序内部的分治递归调用
     *
     * @param leftIndex  当前待排区间的左下标
     * @param rightIndex 当前待排区间的右下标
     */
    private static void mergeSort(int leftIndex, int rightIndex, boolean isIncSort) {
        if (leftIndex < rightIndex) {
            int middleIndex = leftIndex + rightIndex >> 1;
            mergeSort(leftIndex, middleIndex, isIncSort);
            mergeSort(middleIndex + 1, rightIndex, isIncSort);
            merge(leftIndex, rightIndex, isIncSort);
        }
    }

    /**
     * 归并排序内部的合并
     *
     * @param leftIndex  当前排序区间的左下标
     * @param rightIndex 当前排序区间的右下标
     */
    private static void merge(int leftIndex, int rightIndex, boolean isIncSort) {
        int middleIndex = (leftIndex + rightIndex) >> 1;
        int l = leftIndex;
        int r = middleIndex + 1;
        int[] tempArray = new int[rightIndex - leftIndex + 1];
        for (int i = 0; i < tempArray.length; i++) {
            if (isIncSort) {
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
