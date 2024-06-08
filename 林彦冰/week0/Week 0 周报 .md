# Week 0 周报  *20240602*

----

### 学习内容

1.根据视频学习git的知识

2.使用[Learn Git Branching](https://learngitbranching.js.org/?locale=zh_CN&NODEMO=)进行实操练习

----

### 遇到的难题

IDE学习；

---

### Leetcode刷题

题目：[5. 最长回文子串 - 力扣（LeetCode）](https://leetcode.cn/problems/longest-palindromic-substring/solutions/2768247/c-5-zui-chang-hui-wen-zi-chuan-qu-jian-d-8je7/)

题解：区间DP；

代码：

```c++
class Solution {
public:
    string longestPalindrome(string s) {
        if(s.size()<2) return s;
        bool f[1010][1010];
        //f[i][j]:s i_j 最长的回文子串
        memset(f,0,sizeof f);
        for(int i=0;i<s.size();i++) f[i][i] = true;
        int res = 1;
        int begin = 0;
        for(int len=2;len<=s.size();len++)
            for(int i=0;i+len-1<s.size();i++) {
                int j = i+len-1;
                if(len == 2 && s[i] == s[j])  f[i][j] = true;
                else  f[i][j] = f[i+1][j-1]&&(s[i] == s[j]);
                
                if(f[i][j]) {
                    if(res < j-i+1) {
                        res = j-i+1;
                        begin = i;
                    }
                }
            }
        
        return s.substr(begin,res);
    }
};
```







