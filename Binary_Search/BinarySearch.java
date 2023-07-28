package Binary_Search;

public class BinarySearch{
    /**
     * This method performs a binary search on the given array to find a target element.
     * It uses an iterative approach to repeatedly divide the search range in half until 
     * the target element is found or the search range is exhausted.
     * 
     * @param arr The sorted array to be searched
     * @param target The element to be found
     * @return The index of the target element if found, -1 otherwise
     */
    static int binarySearch(int[] arr, int target) {
        int start = 0;
        int end = arr.length - 1;
        while (start <= end) {
            /*
             * We use start + (end - start) / 2 to calculate the middle index of the search range.
             * This is done to avoid the possibility of an integer overflow when the sum of start and end
             * exceeds the maximum value that an integer can hold.
             * By subtracting start from end first and then dividing the difference by 2, we ensure that
             * the calculation is performed correctly and prevent an integer overflow from occurring.
             */
            int mid = start + (end - start) / 2;
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] > target) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return -1;
    }

    /**
     * Performs a binary search on a sorted array recursively.
     *
     * @param  arr    the sorted array to search
     * @param  target the target value to search for
     * @param  start  the starting index of the search range
     * @param  end    the ending index of the search range
     * @return        the index of the target value in the array, or -1 if it is not found
     */
    static int binarySearchRecursive(int[] arr, int target, int start, int end) {
        if (start > end) {
            return -1;
        }
        int mid = start + (end - start) / 2;
        if (arr[mid] == target) {
            return mid;
        } else if (arr[mid] > target) {
            return binarySearchRecursive(arr, target, start, mid - 1);
        } else {
            return binarySearchRecursive(arr, target, mid + 1, end);
        }
    }
    
    public static void main(String[] args) {
        int[] arr = {-10, -1, 1, 3, 5, 7, 9, 10, 11, 13, 20, 44, 50, 51};
        int target = -44;
        int result = binarySearch(arr, target);
        int recursiveResult = binarySearchRecursive(arr, target, 0, arr.length - 1);
        if (result == -1 || recursiveResult == -1) {
            System.out.println("Element not found");
        } else {
            System.out.println("Element found at index using Iterative " + result);
            System.out.println("Element found at index using Recursive " + recursiveResult);

        }
        
    }
}
/*
 * If there are multiple elements in the array with the same value, then return mid needs to handled differently
 * 1. Any element in the array with the same value is in the middle of the array [same as above]
 * 2. Any element in the array with the same value is at the end of the array [start = mid + 1]
 * 3. Any element in the array with the same value is at the beginning of the array [start = mid - 1]
 * 4. All elements in the array with the same value in the array [result.push(mid)]
 */