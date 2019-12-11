import java.util.Stack;

public class DFSAdjacencyMatrix {
    // Perform the DFS
    static void dfs(int[][] matrix, int source) {
        // Keep track of visited nodes
        boolean[] visited = new boolean[matrix.length];

        // The node we start at (which we usually want to be 0 but can be any arbitrary starting point) is visited 
        visited[source] = true;

        // Show our initially-visited array
        System.out.print("Visited: [ "); for (boolean i : visited) { System.out.print(i + " ");} System.out.print("]"); System.out.println();

        // Create and initialize stack to do DFS as per usual
        Stack<Integer> stack = new Stack<>();
        stack.push(source);
        
        System.out.println("Depth first order: ");
        System.out.println("visited " + (source+1));

        // Until we go through every node...
        while(!stack.isEmpty()) {
            // Get the first node (where we start, usually 0 but could be arbitrary)
            int curr = stack.pop();

            // Iterate over that row of the adjacency matrix
            for (int i = 0; i < matrix.length; i++) {
                // See if 1) our current node connects to this node and 2) if this node hasn't been visited
                // EX) when we're at node 0 (starting node), it connects to 1 (matrix[0][1]) and visited[1] is false so we haven't visited it. Continue...
                if (matrix[curr][i] == 1 && visited[i] == false) {
                    // Push that new, connected node onto the stack
                    stack.push(curr);

                    // Mark that node as visited so we don't count it twice
                    visited[i] = true;
                    System.out.println("visited " + (i+1));

                    // Move curr to that node
                    curr = i;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]{{0,1,1,0,0},{1,0,0,1,1},{1,0,0,0,0},{0,1,0,0,0},{0,1,0,0,0}};

        // Print readable adjacency matrix
        System.out.println(" _________________");
        for (int row = 0; row < matrix.length; row++) {
            System.out.print(" | ");
            for (int col = 0; col < matrix[0].length; col++) {
                System.out.print(matrix[row][col] + " | ");
            }
            System.out.println();
        }
        System.out.println(" _________________");

        // Run DFS
        dfs(matrix, 0);
    }
}