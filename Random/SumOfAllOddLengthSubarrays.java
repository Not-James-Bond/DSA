package Random;
/*
    Given an array of positive integers arr, return the sum of all possible odd-length subarrays of arr.
    A subarray is a contiguous subsequence of the array.


    Example 1:
        Input: arr = [1,4,2,5,3]
        Output: 58
        Explanation: The odd-length subarrays of arr and their sums are:
        [1] = 1
        [4] = 4
        [2] = 2
        [5] = 5
        [3] = 3
        [1,4,2] = 7
        [4,2,5] = 11
        [2,5,3] = 10
        [1,4,2,5,3] = 15
        If we add all these together we get 1 + 4 + 2 + 5 + 3 + 7 + 11 + 10 + 15 = 58
 */
public class SumOfAllOddLengthSubarrays {
/**
 * Calculates the sum of all subarrays with odd length in an array.
 * 
 * @param A The input array.
 * @return The sum of all subarrays with odd length.
 */
public int sumOddLengthSubarrays(int[] A) {
    int res = 0; // Variable to store the sum of subarrays
    int n = A.length; // Length of the input array
    
    // Iterate over each element in the array
    for (int i = 0; i < n; ++i) {
        // Calculate the number of subarrays that include the current element
        // This is done by multiplying the number of elements before and after the current element,
        // and adding 1 to account for the subarray that consists only of the current element
        int numSubarrays = ((i + 1) * (n - i) + 1) / 2;
        
        // Multiply the number of subarrays by the value of the current element and add it to the result
        res += numSubarrays * A[i];
    }
    
    return res;
}
}
