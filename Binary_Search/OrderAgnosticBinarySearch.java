package Binary_Search;

public class OrderAgnosticBinarySearch {

    /**
     * An "Order Agnostic Binary Search" is a variation of the binary search algorithm
     * that can be used to search for a target value in a sorted array, regardless of
     * whether the array is in ascending or descending order.
     *
     * @param arr    the array to search in
     * @param target the target element to search for
     * @param start  the starting index of the search range
     * @param end    the ending index of the search range
     * @return the index of the target element if found, -1 otherwise
     */
    static int orderAgnosticBinarySearch(int[] arr, int target, int start, int end) {
        boolean isAsc = arr[start] < arr[end];

        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (arr[mid] == target) {
                return mid;
            }
            if (isAsc) {
                if (arr[mid] > target) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            } else {
                if (arr[mid] < target) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arr = { 51, 50, 44, 20, 13, 11, 10, 9, 7, 5, 3, 1, -1, -10 };
        int target = 44;
        int result = orderAgnosticBinarySearch(arr, target, 0, arr.length - 1);
        if (result == -1) {
            System.out.println("Element not found");
        } else {
            System.out.println("Element found at index " + result);
        }
    }
}
