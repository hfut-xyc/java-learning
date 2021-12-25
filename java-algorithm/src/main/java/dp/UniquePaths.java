package dp;

/**
 * # 62
 * https://leetcode-cn.com/problems/unique-paths/
 */
public class UniquePaths {

    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];

        // 1.initial state
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int j = 0; j < n; j++) {
            dp[0][j] = 1;
        }

        // 2.state transfer
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }

        // 3.target state
        return dp[m-1][n-1];
    }
}
