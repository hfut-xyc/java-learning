package dp;

/**
 * # 64 最小路径和
 * https://leetcode-cn.com/problems/minimum-path-sum/
 */
public class MinPathSum {

    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];

        // 1.initial state
        dp[0][0] = grid[0][0];
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i-1][0] + grid[i][0];
        }
        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j-1] + grid[0][j];
        }

        // 2.state transfer
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i-1][j] + grid[i][j], dp[i][j-1] + grid[i][j]);
            }
        }
        // 3.target state
        return dp[m-1][n-1];
    }
}
