### 学习笔记，记录：


1，看黑马程序员html和css课程，初步了解了html和css的基本语法和使用规范。

2.自己制作了一个简单网页，并修改了字体，文字等。

3，刷力扣上的题.


### 遇到的难题，解决方法：

1，对html的语言不熟悉，由部分标签和用法记不住，多操作几次以后就慢慢熟悉了。

### leetcode刷题；
无重复字符的最长子串。

### 题解；

滑动窗口，开一个vector数组来记录一个字母如果出现重复时i应该调整到的新位置。每次保存字母后的位置。

### 代码：
    int lengthOfLongestSubstring(string s) {
        vector<int> a(150, 0);
        int ans = 0;
        int i = 0;
        for (int j = 0; j < s.size(); j++) {
            i = max(i, a[s[j]]);
            a[s[j]] = j + 1;
            ans = max(ans, j - i + 1);
        }
        return ans;
    }




