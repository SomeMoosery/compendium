import Common.TreeNode;

public class BinaryTreeMaxPathSum {
    int maxPathSum;

    public int maxPathSum(TreeNode root) {
        maxPathSum = Integer.MIN_VALUE;
        pathSum(root);
        return maxPathSum;
    }

    private int pathSum(TreeNode node) {
        // Deal with null node edge case
        if (node == null)
            return 0;

        // Check left and right subtrees recursively, and keep these values
        int left = Math.max(0, pathSum(node.left));
        int right = Math.max(0, pathSum(node.right));

        maxPathSum = Math.max(maxPathSum, left + right + node.val);

        // Return which of the two subtrees are larger
        return Math.max(left, right) + node.val;
    }
}