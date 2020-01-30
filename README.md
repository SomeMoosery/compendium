# I'll sleep when I'm rich
Decided to compile all the resources I've used intermittently for Leetcode prep over the years into one place so this time I can go in and absolutely dominiate this part of the interview, which is usually my weakest point.

## Definitions:
* **Pass by reference**: you pass the actual memory reference of the object into the following function. Whatever you change in that function changes the actual value of the object. If you pass a CoffeeCup object into a fill(CoffeeCup) method from main(), the CoffeeCup object in main will be filled up
* **Pass by value**: you pass in a copy of the object into the following function. If you pass a CoffeeCup object into a fill(CoffeeCup) method from main(), the CoffeeCup object in main is not filled up.
* **Postorder Traversal:** left, right, root
* **Inorder Traversal:** left, root, right
* **Preorder Traversal:** root, left, right

## General Tips & Tricks:
* If you want to pass an int (**or any primitive type**) by reference in Java, you inherently cannot. What you *can* do, however, is create a 1-element array to hold the value of that primitive, and pass that by reference
    * [Example Here](https://leetcode.com/problems/max-area-of-island/) - **another technique given as well**

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

* BFS will always give the shortest path from A to B on a graph. It goes outwards layer by layer, so when it hits point B, we're at the "nearest" layer 
* DFS will give us, maybe, the fastest path

* You can validate a BST by just checking if the inorder traversal of that tree is sorted! 
    * Think: in a valid BST, you're always able to go from a "left" node, to its root, to the right of the root in increasing order!
    * Corollary: An inorder traversal is how you sort a BST! 

* Always think about lookup tables in bit manipulation problems, as they're often the key to many of these! 
* Java has no unsigned integeters
* All bitwise operations are O(1)

* The least significant digit of any number x is x % 10
* The number of digits in an input x is the log (base 10) of x
* The most significant digit of any number x is x / 10^(n-1) where n is the number of digits in x
    * These can be used for masking the most and least significant digits to, say, check if an int is a palindrome

## General Strategies/Formulas:
Why bother deriving everything when you can just memorize a small, useful toolset?

### Bit Manipulation:
- y = x & ~(x-1) yields a y such that there is only a set bit at the lowest bit of x that is 1 -> it's ISOLATED 
- y = x & (x-1) yields a y that replaces the lowest set bit of x with 0
    - This replaces the need to search through each bit, going from O(n) to O(k) for many problems since you only hit set bits
- y = x | (x-1) right-propagates the rightmost set bit of x

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

### Reversing a Linked List:
```java
private ListNode reverse(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        
        while(curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        
        return prev;
    }
```

### Merging two Linked Lists:
```java
private void merge(ListNode l1, ListNode l2) {
    while (l1 != null) {
        ListNode l1Next = l1.next;
        ListNode l2Next = l2.next;
        
        l1.next = l2;
        
        if (l1Next == null) break;
        l2.next = l1Next;
        l1 = l1Next;
        l2 = l2Next;
    }
}
```

### Pre-Interview Musts:
1. [Reorder List](https://leetcode.com/problems/reorder-list/) - implements splitting and reversing lists
2. [Longest palindrome in a string](https://leetcode.com/problems/longest-palindromic-substring/)
3. DFS and BFS graph and tree 
    - See this repo for basic implementations
    - [DFS Implementation: Search a Maze](https://leetcode.com/problems/unique-paths/) (NOTE: A* works here too!)
4. Preorder, Inorder traversals of a tree 
    - [Preorder](https://leetcode.com/problems/binary-tree-preorder-traversal/)
    - [Inorder](https://leetcode.com/problems/binary-tree-inorder-traversal/)
5. [Max Continguous Subarray Sum](https://leetcode.com/problems/maximum-subarray/)
6. [Buy/Sell Stock with K Transactions to Maximize Profit](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/submissions/)
7. [Number of Coins to Make Amount (Knapsack)](https://leetcode.com/problems/coin-change-2/discuss/99212/Knapsack-problem-Java-solution-with-thinking-process-O(nm)-Time-and-O(m)-Space)
8. [Clone Graph (with random pointer)](https://leetcode.com/problems/copy-list-with-random-pointer/)
9. [Unique Paths](https://leetcode.com/problems/unique-paths/)
10. [Search a Maze for Any Path](https://www.youtube.com/watch?v=W9F8fDQj7Ok&t=193s)
11. [Validate BST](https://leetcode.com/problems/validate-binary-search-tree/)
12. [Construct Binary Tree from Preorder, Inorder](https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/)
13. [Game of Life](https://leetcode.com/problems/game-of-life/)
14. [Minimum Size Subarray Sum](https://leetcode.com/problems/minimum-size-subarray-sum/)

## Links/Resourecs:
* [Hacking the Coding Interview](https://www.educative.io/courses/coderust-hacking-the-coding-interview/jv314)
* [Backtracking (Subsets, Permutations, Combination Sum, Palindrome Partioning)](https://leetcode.com/problems/permutations/discuss/18239/A-general-approach-to-backtracking-questions-in-Java-(Subsets-Permutations-Combination-Sum-Palindrome-Partioning))
* [Substring problems guide](https://leetcode.com/problems/minimum-window-substring/discuss/26808/here-is-a-10-line-template-that-can-solve-most-substring-problems)
* [General Patterns](https://hackernoon.com/14-patterns-to-ace-any-coding-interview-question-c5bb3357f6ed)