package Binary_Search;

import java.util.Arrays;

public class FirstAndLastPosition {
    public static void main(String[] args) {
        int[] nums = {5,7,7,8,8,8,10};
        int target = 8;
        System.out.println(Arrays.toString(searchRange(nums, target)));
    }
        
    public static int[] searchRange(int[] nums, int target) {
        int[] resArr = {-1, -1};

        resArr[0] = searchElement(nums, target, true);
        if(resArr[0] != -1) resArr[1] = searchElement(nums, target, false);

        return resArr;
    }

    static int searchElement(int[] nums, int target, boolean findElementLeft) {
        int ans = -1;
        int start = 0; 
        int end = nums.length - 1;

        while (start <= end) {
            int mid = start + (end - start)/2;

            if (nums[mid] > target) end = mid - 1;
            else if (nums[mid] < target) start = mid + 1;
            else {
                ans = mid;
                if (findElementLeft) end = mid - 1;
                else start = mid + 1;
            }
        }
        return ans;
    }
}
