package dp;

/**
 * # 198
 * https://leetcode-cn.com/problems/house-robber
 */
public class Rob {

    public int rob(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];

        // 1.initial state
        dp[0] = nums[0];
        if (n > 1) {
            dp[1] = Math.max(nums[0], nums[1]);
        }

        // 2.state transfer
        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
        }

        // 3.target state
        return dp[n - 1];
    }
}
