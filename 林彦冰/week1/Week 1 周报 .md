# Week 0 周报  *20240602*

----

### 学习内容

1.根据黑马程序员视频学习前端HTML和CSS的知识

2.完成新闻网的作业

----

### 遇到的难题

感觉一周的学习时间不够，黑马程序员的那个B站视频好长，看不完。

---

### Leetcode刷题

题目：[80. 删除有序数组中的重复项 II - 力扣（LeetCode）](https://leetcode.cn/problems/remove-duplicates-from-sorted-array-ii/solutions/702644/shan-chu-pai-xu-shu-zu-zhong-de-zhong-fu-yec2/?envType=study-plan-v2&envId=top-interview-150)

题解：双指针；

因为给定数组是有序的，所以相同元素必然连续。定义两个指针 slow\textit{slow}*slow* 和 fast\textit{fast}*fast* 分别为慢指针和快指针，本题要求相同元素最多出现两次而非一次，所以我们需要检查上上个应该被保留的元素。

代码：

```c++
class Solution {
public:
    int removeDuplicates(vector<int>& nums) {
        int n = nums.size();
        if (n <= 2) {
            return n;
        }
        int slow = 2, fast = 2;
        while (fast < n) {
            if (nums[slow - 2] != nums[fast]) {
                nums[slow] = nums[fast];
                ++slow;
            }
            ++fast;
        }
        return slow;
    }
};
```







