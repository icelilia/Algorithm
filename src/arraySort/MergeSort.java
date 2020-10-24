package arraySort;

import java.util.ArrayList;

public class MergeSort extends ArraySort {
    /**
     * 构造方法
     *
     * @param array   待排数组对象
     * @param incSort 是否增量排序，true为增量，false为减量
     */
    public MergeSort(ArrayList<Integer> array, boolean incSort) {
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

        mergeSort(0, length - 1);
        return orderlyArray;
    }

    /**
     * 归并排序内部的分治递归调用
     *
     * @param leftIndex  当前待排区间的左下标
     * @param rightIndex 当前待排区间的右下标
     */
    private void mergeSort(int leftIndex, int rightIndex) {
        if (leftIndex < rightIndex) {
            int middleIndex = leftIndex + rightIndex >> 1;
            mergeSort(leftIndex, middleIndex);
            mergeSort(middleIndex + 1, rightIndex);
            merge(leftIndex, rightIndex);
        }
    }

    /**
     * 归并排序内部的合并
     *
     * @param leftIndex  当前排序区间的左下标
     * @param rightIndex 当前排序区间的右下标
     */
    private void merge(int leftIndex, int rightIndex) {
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
