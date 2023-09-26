package Binary_Search;

public class RotatedRepeatedSearch {
    public static void main(String[] args) {
        int[] array = {3,3,0,1,3};
        System.out.println(search(array, 0));
    }
   /*
    public boolean search(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;
        //2,5,5,0,2,2,2
        //0,1,1,3,4,4,6
        //1,1,1,1,2,1,1
        int pivotIndex = -1;
        int result = -1;
        while(start < end) {
            int mid = start + (end - start)/2;

            if (nums[mid] == target) return true;
            // else if(nums[mid] == nums[start] && nums[mid] == nums[end]) {
            //     start++;
            //     end--;
            // } else 
            if (nums[start] < nums[mid]) {
                if (nums[mid] > nums[mid + 1]) {
                    pivotIndex = mid;
                    break;
                } else {
                    start = mid + 1;
                }
            // } else if (nums[start] > nums[mid]) {
            } else {
                end = mid - 1;
            }
        }
        pivotIndex = start;

         if(nums[0] <= target && target <= nums[pivotIndex]) {
            result = binarySearch(nums, target, 0, pivotIndex);
        } else {
            result = binarySearch(nums, target, pivotIndex + 1, nums.length - 1);
        }
        return result == -1 ? false : true;
    }
        
        int binarySearch(int[] arr, int target, int start, int end) {
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (arr[mid] == target) {
                return mid;
            }
            else if(arr[mid] > target) end = mid - 1;
            else start = mid + 1;
        }
        return -1;
    }
*/
    public static boolean search(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;
        if (nums.length == 1) return nums[0] == target ? true : false;

        while (start < end) {
            //1,0,1,1,1
            int mid = start + (end - start) / 2;
            if (nums[mid] == target || nums[start] == target || nums[end] == target) return true;
            if (nums[mid] == nums[end] || nums[end] == nums[start]) {
                start++;
                end--;
            }
            else if (nums[start] < nums[mid]) {
                if(target >= nums[start] && target <= nums[mid]) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            } else if (nums[start] > nums[mid]) {
                if (target <= nums[mid] && target >= nums[start])
                    end = mid - 1;
                else
                    start = mid + 1;
            } 
        }
        return false;
    }
}
