package arraySort;

import java.util.ArrayList;

/**
 * @author Spotted_Dog
 */
public class BucketSort {
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

        bucketSort(0, length - 1, isIncSort);
        return orderlyArray;
    }

    private static void bucketSort(int leftIndex, int rightIndex, boolean isIncSort) {
        int length = rightIndex - leftIndex + 1;

        // 确定最大值和最小值
        int maximum = orderlyArray.get(leftIndex);
        int minimum = orderlyArray.get(leftIndex);
        for (int i = leftIndex + 1; i < rightIndex; i++) {
            int x = orderlyArray.get(i++);
            int y = orderlyArray.get(i);
            if (x <= y) {
                if (x < minimum) {
                    minimum = x;
                }
                if (y > maximum) {
                    maximum = y;
                }
            } else {
                if (x > maximum) {
                    maximum = x;
                }
                if (y < minimum) {
                    minimum = y;
                }
            }
        }

        // 构造桶
        int bucketCount = (maximum - minimum) / length + 1;
        ArrayList<ArrayList<Integer>> bucketList = new ArrayList<>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            bucketList.add(new ArrayList<>());
        }

        // 填充桶
        for (int i = leftIndex; i <= rightIndex; i++) {
            int x = orderlyArray.get(i);
            int bucketIndex;
            if (isIncSort) {
                bucketIndex = (x - minimum) / length;
            } else {
                bucketIndex = (maximum - x) / length;
            }
            bucketList.get(bucketIndex).add(x);

        }

        ArrayList<Integer> bucket;

        // 桶内排序
        for (int i = 0; i < bucketList.size(); i++) {
            // 使用插入排序
            bucket = bucketList.get(i);
            bucketList.set(i, InsertSort.sort(bucket, isIncSort));
        }

        // 复制到orderlyArray
        orderlyArray.clear();
        for (ArrayList<Integer> integers : bucketList) {
            bucket = integers;
            orderlyArray.addAll(bucket);
        }
    }
}
