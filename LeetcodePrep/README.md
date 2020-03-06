# Leetcode Time READY TO ROLL

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
* DFS will give us, maybe, the fastest path<br/><br/>

* When doing a BFS problem on a matrix similar to number of islands, but you just want want to check if something is adjacent to your current spot in the matrix (like [word search](https://leetcode.com/problems/word-search/)), you can frontload the conditionals instead of having each one in the if statement looking for that something:

```java
public static boolean bfs(int[][] grid, int row, int col) {
    ...
    if (row < 0 || row >= grid.length || col < 0 || col >= board[0].length || board[row][col] != <WHATEVER>) return false;

    // ...continue with your dfs...
}
```

* You can validate a BST by just checking if the inorder traversal of that tree is sorted! 
    * Think: in a valid BST, you're always able to go from a "left" node, to its root, to the right of the root in increasing order!
    * Corollary: An inorder traversal is how you sort a BST!<br/><br/>

* Always think about lookup tables in bit manipulation problems, as they're often the key to many of these! 
* Java has no unsigned integeters
* All bitwise operations are O(1)<br/><br/>

* Hash tables are good for fast lookups on a special-property data structure (so if you have BST, for example, instead of having O(h) time to search the tree and lookup a value, you can back it up with a hash table which has O(1) lookup)1<br/><br/>

* The least significant digit of any number x is x % 10
* The number of digits in an input x is the log (base 10) of x
* The most significant digit of any number x is x / 10^(n-1) where n is the number of digits in x
    * These can be used for masking the most and least significant digits to, say, check if an int is a palindrome<br/><br/>

* **MYTH**: Objects are passed by reference, primitives are passed value
    * **FACT**: Everything in Java is passed by value. Objects, however, are never passed at all, **just references to objects**
    * **FACT**: The values of variables are always primitives or references, never objects<br/><br/>

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

### Getting to the Middle of a Linked List:
```java
public ListNode middleNode(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        fast = fast.next.next;
        slow = slow.next;
    }
    
    return slow; // slow is at the middle of the list!
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

### Dealing with Tree Path/Traversal:
Can be broken down into some basic general steps

1. You have your root, pass it into a helper function and start using recursion
2. If the current node is null, return null/zero/etc...
3. Do some comparison (like Math.max() of the left and right subtrees) recursively
4. Return one final comparison of the two complete subtrees

Good reference on a hard problem [here](https://youtu.be/mOdetMWwtoI?t=700)

**Example:**
```java
int maxPath;
public int maxPath(TreeNode root) {
    maxPath = Integer.MIN_VALUE;
    pathSum(root);
    return maxPath;
}

private int pathSum(TreeNode node) {
    if (node == null) return 0;

    int left = pathSum(node.left);
    int right = pathSum(node.right);

    ...

    return Math.max(left, right) + node.val
}
```

Alternatively, we can do a calculation at the root before bubbling back up. After the two recursive calls: 
```java
...
// This does a calculation at the leaf node
if (node.left == null && node.right == null && <CALCULATION>) {
    return x;
}

// Now we bubble back up using that newly-calculated value
return y;
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
15. [Binary Tree Maximum Path Sum](https://leetcode.com/problems/binary-tree-maximum-path-sum/)
16. [BST Iterator](https://leetcode.com/problems/binary-search-tree-iterator/solution/)
17. [Number of Unique BSTs](https://leetcode.com/problems/unique-binary-search-trees/)

## Links/Resourecs:
* [Hacking the Coding Interview](https://www.educative.io/courses/coderust-hacking-the-coding-interview/jv314)
* [Backtracking (Subsets, Permutations, Combination Sum, Palindrome Partioning)](https://leetcode.com/problems/permutations/discuss/18239/A-general-approach-to-backtracking-questions-in-Java-(Subsets-Permutations-Combination-Sum-Palindrome-Partioning))
* [Substring problems guide](https://leetcode.com/problems/minimum-window-substring/discuss/26808/here-is-a-10-line-template-that-can-solve-most-substring-problems)
* [General Patterns](https://hackernoon.com/14-patterns-to-ace-any-coding-interview-question-c5bb3357f6ed)
* [Nuances to Pass by Reference/Value in Java](https://jonskeet.uk/java/passing.html)

## Continued Reading, Useful Blogs, etc...
* [Martin Fowler's Blog](https://martinfowler.com/)
    * [His deep dive into microservices](https://martinfowler.com/articles/microservices.html#EvolutionaryDesign)
    * [His deep dive into serverless](https://martinfowler.com/articles/serverless.html)
    * [His guide on testing microservices](https://martinfowler.com/articles/microservice-testing/)
    