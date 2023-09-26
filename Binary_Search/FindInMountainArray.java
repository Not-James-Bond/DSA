package Binary_Search;

// https://leetcode.com/problems/find-in-mountain-array/
/**
 * // This is MountainArray's API interface.
 * // You should not implement it, or speculate about its implementation
 * interface MountainArray {
 *     public int get(int index) {}
 *     public int length() {}
 * }
 */

interface MountainArray {
    public int get(int index);
    public int length();
}

class MountainArrayImpl implements MountainArray {
    private int[] array;

    public MountainArrayImpl(int[] arr) {
        this.array = arr;
    }

    @Override
    public int get(int index) {
        return array[index];
    }

    @Override
    public int length() {
        return array.length;
    }
}

public class FindInMountainArray {

    public static void main(String[] args) {
        int[] array = {1,2,3,4,3,1};
        MountainArray mountainArray = new MountainArrayImpl(array);
        int res = findInMountainArray(3, mountainArray);
        System.out.println(res);
    }

    public static int findInMountainArray(int target, MountainArray mountainArr) {
        int arrLength = mountainArr.length();
        int peakIndex = findPeak(mountainArr, arrLength);
        int ascArrIndex = binarySearch(mountainArr, target, 0, peakIndex, true);
        if (ascArrIndex != -1){
            return ascArrIndex;
        }
        int descArrIndex = binarySearch(mountainArr, target, peakIndex + 1, arrLength - 1, false);
        return descArrIndex;
    }

    public static int findPeak(MountainArray mountainArr, int arrayLength) {
        int start = 0;
        int end = arrayLength - 1;

        while (start < end) {
            int mid = start + (end - start) / 2;
            if (mountainArr.get(mid) > mountainArr.get(mid + 1)) end = mid;
            else start = mid + 1;
        }
        return start;
    }

    public static int binarySearch(MountainArray mountainArr, int target, int start, int end, boolean isAsc) {

        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (mountainArr.get(mid) == target) {
                return mid;
            } 
            if(isAsc){
                if (mountainArr.get(mid) > target) end = mid - 1;
                else start = mid + 1;
            } else {
                if (mountainArr.get(mid) < target) end = mid - 1;
                else start = mid + 1;
            }
        }
        return -1;
    }
}
