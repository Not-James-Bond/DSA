package Binary_Search;

public class RotatedArraySearch {
    public static void main(String[] args) {
        int[] array = {5,1,1,3,3};
        System.out.println(search(array, 1));
    }

    /******************************First Approach**************************** */
    public static int search(int[] nums, int target) {
        int arrayLength = nums.length;
        int result = -1;
        // int start = 0;
        // int end = arrayLength - 1;

        // 6,7,0,1,2,3,4,5
        // 2,3,4,5,6,7,0,1
        // 0,1,2,3,4,5,6,7

        /* 1st Approach Most Optimized
        // while (start < end) {
        //     int mid = start + (end - start)/2;
        //     if (nums[mid] > nums[mid+1]) end = mid;
        //     else if (nums[start] > nums[mid]) end = mid - 1;
        //     else start = mid + 1;
        // } 
         */

        // 2nd Approach FindPivot
        // Also Works for duplicate values but gives random answer.
        int pivotIndex = findPivot(nums);
        if(nums[0] <= target && target <= nums[pivotIndex]) {
            result = binarySearch(nums, target, 0, pivotIndex);
        } else {
            result = binarySearch(nums, target, pivotIndex + 1, arrayLength - 1);
        }
        return result;
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

    static int binarySearch(int[] arr, int target, int start, int end) {
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (arr[mid] == target) {
                //Condition will be updated to find the First or Last index of Duplicate Value
                return mid;
            }
            else if(arr[mid] > target) end = mid - 1;
            else start = mid + 1;
        }
        return -1;
    }
    /*********************************************************************** */
    /*****************************Second Approach*************************** */
    // Approach to Directly Find the target without using Pivot
    public int search2(int[] nums, int target) {
        int start = 0, end = nums.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (target == nums[mid])
                return mid;
            if (nums[mid] < nums[start]) {
                // Right Sorted After Mid
                // 6,7,0,1,2,3,4,5
                if (target < nums[mid] || target >= nums[start])
                    end = mid - 1;
                else
                    start = mid + 1;
            } else {
                //Left Side Sorted Before Mid
                // 2,3,4,5,6,7,0,1
                if (target > nums[mid] || target < nums[start])
                    start = mid + 1;
                else
                    end = mid - 1;
            }
        }
        return -1;
    }
}

