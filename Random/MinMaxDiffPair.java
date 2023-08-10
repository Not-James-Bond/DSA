//[3,4,2,3,2,1,2] p=3
//[1,2,2,2,3,3,4]
//1119 / 1582 testcases passed
package Random;

import java.util.Arrays;

public class MinMaxDiffPair {
    public static int minimizeMax(int[] nums, int p) {
        int[] priority = new int[nums.length - 1];
        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 1; i++ ) {
            priority[i] = nums[i+1] - nums[i];
        }

        Arrays.sort(priority);

        int result = 0;
        if (p != 0) result = priority[p-1];
        
        return result;
    }

    public static void main(String[] args) {
        int[] arr = {3,4,2,3,2,1,2}; 
            System.out.println(minimizeMax(arr, 3));
    }
}