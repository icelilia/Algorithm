package leetCode;

public class No4 {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int length = nums1.length + nums2.length;
        double res = 0;

        int i = 0;
        int j = 0;

        if (length % 2 != 0) {
            for (int k = 0; k <= length / 2; k++) {
                if (i < nums1.length && j < nums2.length) {
                    if (nums1[i] < nums2[j]) {
                        res = nums1[i++];
                    } else {
                        res = nums2[j++];
                    }
                } else if (i < nums1.length) {
                    res = nums1[i++];
                } else {
                    res = nums2[j++];
                }
            }
            return res;
        } else {
            for (int k = 0; k < length / 2; k++) {
                if (i < nums1.length && j < nums2.length) {
                    if (nums1[i] < nums2[j]) {
                        res = nums1[i++];
                    } else {
                        res = nums2[j++];
                    }
                } else if (i < nums1.length) {
                    res = nums1[i++];
                } else {
                    res = nums2[j++];
                }
            }
            if (i < nums1.length && j < nums2.length) {
                res += Math.min(nums1[i], nums2[j]);
            } else if (i < nums1.length) {
                res += nums1[i];
            } else {
                res += nums2[j];
            }
            return res / 2;
        }
    }
}
