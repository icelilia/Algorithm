package arraySort;

import java.util.ArrayList;
import java.util.Random;

public class QuickSort {
    private static ArrayList<Integer> orderlyArray;

    public static ArrayList<Integer> sort(ArrayList<Integer> originalArray, boolean incSort, boolean stable) {
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

        quickSort(0, length - 1, incSort, stable);
        return orderlyArray;
    }

    /**
     * 快速排序的内部递归调用
     *
     * @param leftIndex  当前待排区间的左下标
     * @param rightIndex 当前待排区间的右下标
     */
    private static void quickSort(int leftIndex, int rightIndex, boolean incSort, boolean stable) {
        if (leftIndex >= rightIndex) {
            return;
        }
        // 稳定性判断
        int pivotIndex = stable ? partitionStable(leftIndex, rightIndex, incSort) : partitionUnStable(leftIndex, rightIndex, incSort);
        quickSort(leftIndex, pivotIndex - 1, incSort, stable);
        quickSort(pivotIndex + 1, rightIndex, incSort, stable);
    }

    /**
     * 快速排序中稳定的分割方法，采用了类似归并排序的思想
     *
     * @param leftIndex  当前待排区间的左下标
     * @param rightIndex 当前待排区间的左下标
     * @return 分割完成后，主元pivot的下标
     */
    private static int partitionStable(int leftIndex, int rightIndex, boolean incSort) {
        Random random = new Random();
        int pivotIndex = random.nextInt(rightIndex - leftIndex + 1) + leftIndex;
        int pivotValue = orderlyArray.get(pivotIndex);

        ArrayList<Integer> leftArray = new ArrayList<>();
        ArrayList<Integer> rightArray = new ArrayList<>();

        if (incSort) {
            for (int i = leftIndex; i <= rightIndex; i++) {
                if (i == pivotIndex) {
                    continue;
                }
                if (orderlyArray.get(i) < pivotValue) {
                    leftArray.add(orderlyArray.get(i));
                } else if (orderlyArray.get(i) == pivotValue) {
                    if (i < pivotIndex) {
                        leftArray.add(orderlyArray.get(i));
                    } else {
                        rightArray.add(orderlyArray.get(i));
                    }
                } else {
                    rightArray.add(orderlyArray.get(i));
                }
            }
        } else {
            for (int i = leftIndex; i <= rightIndex; i++) {
                if (i == pivotIndex) {
                    continue;
                }
                if (orderlyArray.get(i) > pivotValue) {
                    leftArray.add(orderlyArray.get(i));
                } else if (orderlyArray.get(i) == pivotValue) {
                    if (i < pivotIndex) {
                        leftArray.add(orderlyArray.get(i));
                    } else {
                        rightArray.add(orderlyArray.get(i));
                    }
                } else {
                    rightArray.add(orderlyArray.get(i));
                }
            }
        }

        int j = 0;
        int k = 0;
        for (int i = leftIndex; i <= rightIndex; i++) {
            if (j < leftArray.size()) {
                orderlyArray.set(i, leftArray.get(j++));
            } else if (j == leftArray.size()) {
                orderlyArray.set(i, pivotValue);
                pivotIndex = i;
                j++;
            } else {
                orderlyArray.set(i, rightArray.get(k++));
            }
        }
        return pivotIndex;
    }

    /**
     * 快速排序中不稳定的分割方法
     *
     * @param leftIndex  当前待排区间的左下标
     * @param rightIndex 当前待排区间的左下标
     * @return 分割完成后，主元pivot的下标
     */
    private static int partitionUnStable(int leftIndex, int rightIndex, boolean incSort) {
        Random random = new Random();
        int pivotIndex = random.nextInt(rightIndex - leftIndex + 1) + leftIndex;
        int pivotValue = orderlyArray.get(pivotIndex);
        swapValue(pivotIndex, rightIndex);

        int i = leftIndex - 1;

        if (incSort) {
            // (leftIndex, i]为小于pivotValue的区间
            // [i + 1, j)为大于pivotValue的区间
            for (int j = leftIndex; j < rightIndex; j++) {
                if (orderlyArray.get(j) <= pivotValue) {
                    swapValue(i + 1, j);
                    i++;
                }
            }
        } else {
            for (int j = leftIndex; j < rightIndex; j++) {
                if (orderlyArray.get(j) >= pivotValue) {
                    swapValue(i + 1, j);
                    i++;
                }
            }
        }
        swapValue(i + 1, rightIndex);
        return i + 1;
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
