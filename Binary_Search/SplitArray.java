package Binary_Search;

public class SplitArray {
    public static void main(String[] args) {
        int[] nums = {7,2,5,10,8}; 
        int k = 2;
        System.out.println(splitArray(nums, k));
    }

/**
 * Splits the given array into k subarrays such that the maximum sum of any subarray is minimized.
 *
 * @param nums The array to be split.
 * @param k The number of subarrays to split the array into.
 * @return The minimum maximum sum of any subarray.
 */
public static int splitArray(int[] nums, int k) {
    // Initialize start and end variables
    int start = 0;
    int end = 0;

    // Find the maximum element in the array and calculate the sum of all elements
    for (int i = 0; i < nums.length; i++) {
        start = Math.max(start, nums[i]);
        end += nums[i];
    }

    // If k is 1, return the sum of all elements
    if (k == 1) return end;
    // If k is equal to the number of elements in the array, return the maximum element
    else if (k == nums.length) return start;

    // Perform binary search to find the minimum maximum sum of any subarray
    while (start < end) {
        // Calculate the mid point
        int mid = start + (end - start) / 2;

        // Initialize variables to track the sum of subarrays and the number of subarrays
        int sumOfSubArray = 0;
        int numOfSubArray = 1;

        // Iterate through the array
        for(int num : nums) {
            // If adding the current element exceeds the mid point, start a new subarray
            if (num + sumOfSubArray > mid) {
                numOfSubArray++;
                sumOfSubArray = num;
            } else sumOfSubArray += num;
        }

        // Adjust the start and end points based on the number of subarrays
        if (numOfSubArray <= k) end = mid;
        else start = mid + 1;
    }

    // Return the minimum maximum sum of any subarray
    return start;
}
}
   
