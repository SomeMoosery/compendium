/**
 * Given an MxN grid, how many ways can we get from the top left to the bottom
 * right of the grid? NOTE: You can only move down or to the right
 * 
 * Some key points 1. This doesn't have to be strictly from the top left to the
 * bottom right. This can be any point A to point B. Essentially, you can
 * abstract away all the rest of the graph even if it expands infinitely outside
 * of this MxN grid 2. This can easily be applied to moving only up and right,
 * down and left, etc...
 */
public class UniquePaths {
    /**
     * Subproblems: 1. How many ways can we get to the square to the right? One ( ->
     * right ) 2. How many ways can we get to the square below? One ( -> down ) 3.
     * How many ways can we get to the square diagonal down-right? Two ( -> right ->
     * down AND -> down -> right )
     */

    /**
     * To go all the way to the last column, you can only move to the right To go
     * all the way to the last row, you can only move down You can fill those rows
     * and column with ones, and the spread from there:
     * 
     * | 1 | 1 | 1 | 
     * | 1 | 2 | 3 | 
     * | 1 | 2 | 5 |
     */

    public static int uniquePaths(int m, int n) {
        // DP array to hold our traversal pattern shown above
        int[][] dp = new int[m][n]; 

        // Fill all of the first column with 1 (since you can only get there one way)
        for (int i = 0; i < m; i++) dp[i][0] = 1;

        // Fill all of the first row with 1 (since you can only get there one way)
        for (int i = 0; i < n; i++) dp[0][i] = 1;

        /**
         * Construct the rest of the graph
         * You can only get to each subsequent point by coming in from the left or from above
         * So that point can only have the number of points gotten to from that left or above point
         */
        for (int row = 1; row < m; row++) {
            for (int col = 1; col < n; col++) {
                dp[row][col] = dp[row-1][col] + dp[row][col-1];
            }
        }

        // return the result at the bottom right corner, the number of paths
        return dp[m - 1][n - 1]; 
    }

    public static void main(String[] args) {
        int[][] testSuite = new int[][]{{3,2}, {7,3}, {3,3}, {5,9}};

        for (int[] arr : testSuite) {
            int numWays = uniquePaths(arr[0], arr[1]);
            System.out.println("Given a " + arr[0] + "x" + arr[1] + " grid, there are " + numWays + " paths from the top left to bottom right");
        }
    }
}