###  week6周报

### 学习笔记，记录：

    1，看黑马的javaweb入门课，初步了解了js的基础语法

    2.看尚硅谷的Ajax相关知识，初步了解了http相关协议，了解了发送ajax申请的几种方式

### 遇到的困难即解决方法；

    在学习Ajax是有部分代码没有基础，看不太懂。

    解决方法：又去b站上搜了相关的课程，大致理解了相关语句的作用


###  leetcode刷题：

买卖股票的最佳时机，给出第i天的价格，看哪一天卖出利润最大

题解：只需要找出给定数组中两个数字之间的最大差值，并保证卖出价格一定大于买进价格即可。但如果直接暴力计算会超时，这是可以找到历史最低点，优化算法

代码：
class Solution {
public:
    int maxProfit(vector<int>& prices) {
        int mProfit = 0, prevMin = prices[0];
        int n = prices.size();
        for (int i = 1; i < n; i++) {
            mProfit = max(mProfit, prices[i] - prevMin);
            prevMin = min(prevMin, prices[i]);
        }
        return mProfit;
    }
};
