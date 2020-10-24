package arraySort;

import java.util.ArrayList;

public class BucketSort extends ArraySort {
    /**
     * 构造方法
     *
     * @param array   待排数组对象
     * @param incSort 是否增量排序，true为增量，false为减量
     */
    public BucketSort(ArrayList<Integer> array, boolean incSort) {
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

        bucketSort(0, length - 1);
        return orderlyArray;
    }

    private void bucketSort(int leftIndex, int rightIndex) {
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
        ArrayList<ArrayList<Integer>> bucketList = new ArrayList<ArrayList<Integer>>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            bucketList.add(new ArrayList<Integer>());
        }

        // 填充桶
        for (int i = leftIndex; i <= rightIndex; i++) {
            int x = orderlyArray.get(i);
            if (incSort) {
                int bucketIndex = (x - minimum) / length;
                bucketList.get(bucketIndex).add(x);
            } else {
                int bucketIndex = (maximum - x) / length;
                bucketList.get(bucketIndex).add(x);
            }

        }

        ArrayList<Integer> bucket;

        // 桶内排序
        for (int i = 0; i < bucketList.size(); i++) {
            // 使用插入排序
            bucket = bucketList.get(i);
            bucketList.set(i, new InsertSort(bucket, incSort).sort());
        }

        // 复制到orderlyArray
        orderlyArray.clear();
        for (int i = 0; i < bucketList.size(); i++) {
            bucket = bucketList.get(i);
            for (int j = 0; j < bucket.size(); j++) {
                orderlyArray.add(bucket.get(j));
            }
        }
    }
}
