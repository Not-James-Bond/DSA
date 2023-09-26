package Binary_Search;

public class DuplicateNumber {
    public static void main(String[] args) {
        int[] arr = {8,1,1,9,5,4,2,7,3,6};
        System.out.println(findDuplicate(arr));
    }
    
    public static int findDuplicate(int[] nums) {
        int n = nums.length;
        if(nums[0] == nums[n - 1]) return nums[0];
        int i = 1;
        int end = n - 1;
        int start = 0;
         while (i < end){
            // i = start + 1;
            if(nums[start] - nums[i] == 0 || nums[end] - nums[i] == 0) {
                return nums[i];
            } 
            // int a = nums[0] - nums[i];
            // int b = nums[n-1] - nums[i];
            if(i + 1 == end){
                start++;
                end--;
                i = start;
            }
            i++;
        }
        return -1;
    }
}
