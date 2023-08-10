package Random;

// Sorted Array Pivot Rotation Search
public class SortedArrayPivotRotationSearch {
    public static int search(int[] nums, int target) {
        int start = 0, end = nums.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            System.out.println(start + " " + mid + " " + end);
            if (nums[mid] == target) return mid;
            else if (nums[start] < nums[mid]) {
                System.out.println("Inside Outer If");
                if (target > nums[mid]){
                    if (nums[start] > target) start = mid + 1;
                    else if (nums[start] < target) end = mid - 1;
                } else {
                    if (nums[start] > target) start = mid + 1;
                    else end = mid - 1;
                }         
            } else {
                System.out.println("Inside Outer Else");
                if (target < nums[mid]) end = mid - 1;
                else if (nums[mid] < target && nums[mid] < nums[end]) start = mid + 1;
            }
        }
        return -1;
    }
    public static void main(String[] args){
        int[] array = {4,5,6,7,0,1,2};
        System.out.println(search(array, 0));
    }
}
