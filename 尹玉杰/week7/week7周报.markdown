###  week7周报

### 学习笔记，记录：

    1，学习黑马的就是入门视频，主要开了qpi的相关内容，以及一部分的js进阶课程

    2.学习了部分ts课程

### 遇到的困难即解决方法；
     
     在做js练习的时候感觉一些语法掌握不熟练，同时一部分css的内容有遗忘或不清楚

     解决方法：多做练习，同时又去看了css的一些课程

###  leetcode刷题：

题目：打家劫舍，要求在数组中找出两两不相邻的数组的和的最大值

题解：可以用动态规划求解，不偷窃第i间房屋，偷窃总金额为前i−1间房屋的最高总金额，则能列出动态转移方程：dp[i]=max(dp[i−2]+nums[i],dp[i−1])

代码：
class Solution {
    public int rob(int[] nums) {
        if (nums == null || nums.len == 0) {
            return 0;
        }
        int len = nums.length;
        if (len == 1) {
            return nums[0];
        }
        int[] dp = new int[len];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < len; i++) {
            dp[i] = Math.max(dp[i - 2] + nums[i], dp[i - 1]);
        }
        return dp[len - 1];
    }
}


