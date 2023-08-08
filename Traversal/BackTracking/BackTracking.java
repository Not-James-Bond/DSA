package Traversal.BackTracking;
import java.util.ArrayList;
import java.util.List;

public class BackTracking {
    public static void permutation(int[] a, int n, int k, int depth, boolean[] used, List<Integer> curr, List<List<Integer>> ans) {
        System.out.println("\na: " + a + " " + a.length + " n: " + n + " k: " + k + "\ndepth: " + depth + "\nused: " + used + "\ncurr: " + curr + "\nans: " + ans);
        if (depth == k) { // End condition
            ans.add(new ArrayList<>(curr)); // Use ArrayList constructor to create a copy of curr
            return;
        }

        for (int i = 0; i < n; i++) {
            if (!used[i]) {
                // Generate the next solution from curr
                curr.add(a[i]);
                used[i] = true;
                System.out.println(curr);

                // Move to the next solution
                permutation(a, n, k, depth + 1, used, curr, ans);

                // Backtrack to the previous partial state
                curr.remove(curr.size() - 1);
                System.out.println("Backtrack: " + curr);
                used[i] = false;
            }
        }
        System.out.println("ANSWER:: " + ans);
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 3};
        int k = 3;
        int n = a.length;
        List<List<Integer>> ans = new ArrayList<>();
        boolean[] used = new boolean[n];
        permutation(a, n, k, 0, used, new ArrayList<>(), ans);
    }

}
