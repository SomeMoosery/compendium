# I'll sleep when I'm rich
Decided to compile all the resources I've used intermittently for Leetcode prep over the years into one place so this time I can go in and absolutely dominiate this part of the interview, which is usually my weakest point.

## Definitions:
* **Pass by reference**: you pass the actual memory reference of the object into the following function. Whatever you change in that function changes the actual value of the object. If you pass a CoffeeCup object into a fill(CoffeeCup) method from main(), the CoffeeCup object in main will be filled up
* **Pass by value**: you pass in a copy of the object into the following function. If you pass a CoffeeCup object into a fill(CoffeeCup) method from main(), the CoffeeCup object in main is not filled up.

## General Tips & Tricks:
* If you want to pass an int (**or any primitive type**) by reference in Java, you inherently cannot. What you *can* do, however, is create a 1-element array to hold the value of that primitive, and pass that by reference

```java
public static void main(String[] args) {
    int[] count = new int[]{0};
    for (int i = 0; i < 10; i++) {
        tick(count);
    }
}

private void tick(int[] count) {
    count[0]++;
}
```

## General Strategies:

### Palindromes:
- Look to go through each element and expand outwards from each element
- There's two cases where things can be palindromes: 
    1. There's only one letter that the palindrome expands from, like the 'e' in racecar 
    2. There's two letters that the palindrome expands from, like 'dd' in 'abcddefg' 
```java
for(int i = 0; i < s.length(); i++) {
    expand(s, i, i); // Covers the first case
    expand(s, i, i+1); // Covers the second case
    ...
}
```

### Pre-Interview Musts:
1. [Reverse a Linked List](https://leetcode.com/problems/reverse-linked-list/)
2. [Longest palindrome in a string](https://leetcode.com/problems/longest-palindromic-substring/)
3. DFS and BFS graph and tree 
    - See this repo for basic implementations
4. Preorder, Inorder traversals of a tree 
    - [Preorder](https://leetcode.com/problems/binary-tree-preorder-traversal/)
    - [Inorder](https://leetcode.com/problems/binary-tree-inorder-traversal/)
5. [Max Continguous Subarray Sum](https://leetcode.com/problems/maximum-subarray/)
6. [Buy/Sell Stock with K Transactions to Maximize Profit](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/submissions/)
7. [Number of Coins to Make Amount (Knapsack)](https://leetcode.com/problems/coin-change-2/discuss/99212/Knapsack-problem-Java-solution-with-thinking-process-O(nm)-Time-and-O(m)-Space)
8. [Clone Graph (with random pointer)](https://leetcode.com/problems/copy-list-with-random-pointer/)

## Links/Resourecs:
* [Hacking the Coding Interview](https://www.educative.io/courses/coderust-hacking-the-coding-interview/jv314)
* [Backtracking (Subsets, Permutations, Combination Sum, Palindrome Partioning)](https://leetcode.com/problems/permutations/discuss/18239/A-general-approach-to-backtracking-questions-in-Java-(Subsets-Permutations-Combination-Sum-Palindrome-Partioning))
* [Substring problems guide](https://leetcode.com/problems/minimum-window-substring/discuss/26808/here-is-a-10-line-template-that-can-solve-most-substring-problems)
* [General Patterns](https://hackernoon.com/14-patterns-to-ace-any-coding-interview-question-c5bb3357f6ed)