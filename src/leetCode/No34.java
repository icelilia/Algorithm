package leetCode;

public class No34 {
    public int[] searchRange(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;
        int mid = (start + end + 1) / 2;

        while (start <= end) {
            if (nums[mid] < target) {
                start = mid + 1;
                mid = (start + end + 1) / 2;
            } else if (nums[mid] > target) {
                end = mid - 1;
                mid = (start + end + 1) / 2;
            } else {
                int i, j;
                for (i = mid; i >= start; i--) {
                    if (nums[i] != target) {
                        break;
                    }
                }
                i++;
                for (j = mid; j <= end; j++) {
                    if (nums[j] != target) {
                        break;
                    }
                }
                j--;
                int[] res = new int[2];
                res[0] = i;
                res[1] = j;
                return res;
            }
        }
        return new int[]{-1, -1};
    }
}
