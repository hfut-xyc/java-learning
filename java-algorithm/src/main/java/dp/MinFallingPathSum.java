package dp;

/**
 * # 931 最小下降路径和
 * https://leetcode-cn.com/problems/minimum-falling-path-sum
 */
public class MinFallingPathSum {

    public int minFallingPathSum(int[][] matrix) {
        int n = matrix.length;
        int[][] dp = new int[n][n];

        // 1.initial state
        for (int j = 0; j < n; j++) {
            dp[0][j] = matrix[0][j];
        }

        // 2.state transfer
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j == 0) {
                    dp[i][j] = Math.min(dp[i-1][j] + matrix[i][j], dp[i-1][j+1] + matrix[i][j]);
                } else if (j == n - 1) {
                    dp[i][j] = Math.min(dp[i-1][j] + matrix[i][j], dp[i-1][j-1] + matrix[i][j]);
                } else {
                    dp[i][j] = Math.min(dp[i-1][j-1] + matrix[i][j], dp[i-1][j+1] + matrix[i][j]);
                    dp[i][j] = Math.min(dp[i][j], dp[i-1][j] + matrix[i][j]);
                }
            }
        }

        // 3.target state
        int res = Integer.MAX_VALUE;
        for (int j = 0; j < n; j++) {
            res = Math.min(res, dp[n - 1][j]);
        }
        return res;
    }
}
