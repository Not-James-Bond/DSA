package Binary_Search;

// https://www.geeksforgeeks.org/find-position-element-sorted-array-infinite-numbers/
public class InfiniteArray {
    public static void main(String[] args) {
        int[] arr = {3, 5, 7, 9, 10, 90, 100, 130, 140, 160, 170};
                   //0  1  2  3  4   5   6    7    8    9    10
        int target = 160;
        System.out.println(InfiniteSearch(arr, target));
    }
    /**
     * This method implements the binary search algorithm to find a target value in a given array.
     * It starts by finding the range of the target value by doubling the size of the search box until the target 
     * value is greater than the value at the end index. Finally, it performs a binary search within the determined 
     * range to find the target value and returns its index if found, otherwise -1.
     *
     * @param arr the array to search in
     * @param target the target value to find
     * @return the index of the target value if found, -1 otherwise
     */
    static int InfiniteSearch(int[] arr, int target) {
        // Initialize the start and end indices
        int start = 0;
        int end = 1;

        // Determine the range of the target value
        while (target > arr[end]) {
            int temp = end + 1;
            end = end + (end - start + 1) * 2;
            start = temp;
        }

        // Perform binary search within the range
        return binarySearch(arr, target, start, end);
    }

    /**
     * This method performs a binary search within the specified range of an array to find a target value.
     * It returns the index of the target value if found, otherwise -1.
     *
     * @param arr the array to search in
     * @param target the target value to find
     * @param start the start index of the range
     * @param end the end index of the range
     * @return the index of the target value if found, -1 otherwise
     */
    static int binarySearch(int[] arr, int target, int start, int end) {
        while(start <= end) {
            int mid = start + (end - start) / 2;

            if (target < arr[mid]) {
                end = mid - 1;
            } else if (target > arr[mid]) {
                start = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }
}
