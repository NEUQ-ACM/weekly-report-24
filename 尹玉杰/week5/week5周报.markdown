###  week3周报

### 学习笔记，记录：

    1，看黑马的javaweb入门课，初步了解了js的基础语法

    2.黑马的vue的基础语法和命令，以及生命周期，自己创建了一个vue工程

### 遇到的困难即解决方法；

    1，在准备vue的环境的时候淘宝进行报错，调试了很久

    解决办法：经查询后发现是之前的淘宝镜像过期了，换了一个新的地址，解决了问题


###  leetcode刷题：
有效的括号，给定一个字符串，判定该字符串的括号是否符合要求

题解：将左括号全部放入栈顶，遇到右括号则进行判断，如果匹配则把该做括号移除并继续，若不匹配则可以直接返回false

代码如下；
class Solution {
public:
    bool isValid(string s) {
        int n = s.size();
        if (n % 2 == 1) {
            return false;
        }

        unordered_map<char, char> pairs = {
            {')', '('},
            {']', '['},
            {'}', '{'}
        };
        stack<char> stk;
        for (char ch: s) {
            if (pairs.count(ch)) {
                if (stk.empty() || stk.top() != pairs[ch]) {
                    return false;
                }
                stk.pop();
            }
            else {
                stk.push(ch);
            }
        }
        return stk.empty();
    }
};

