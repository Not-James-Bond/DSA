package Binary_Search;

public class RotationCount {
    public static void main(String[] args) {
        int[] array = {5,1,1,3,3};
        System.out.println(findCount(array));
    }

    static int findCount(int[] arr) {
        int pivot = findPivot(arr);
        if (pivot == arr.length - 1) return 0;
        return pivot + 1;
    }
    static int findPivot(int[] nums) {
        int start = 0;
        int end = nums.length - 1;
        while (start < end) {
            int mid = start + (end - start) / 2;
            if (nums[start] <= nums[mid]) {
                if (nums[mid] > nums[mid + 1]) {
                    return mid;
                } else {
                    start = mid + 1;
                }
            } else {
                end = mid - 1;
            }
        }
        return start;
    }
}
