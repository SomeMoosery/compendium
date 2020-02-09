// TODO improve on this solution, it's very slow 

class WordSearch {
    public static boolean exist(char[][] board, String word) {
        // Instantiate result variable         
        boolean[] isSolvable = new boolean[]{false};
        
        // Traverse board until first character is found (if at all)
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] == word.charAt(0)) {
                    // Expand outward from there in each direction recursively
                    isSolvable(row, col, board , word, 0, isSolvable);
                }
            }
        }
        
        // Return if it's solvable
        return isSolvable[0];
    }
    
    private static void isSolvable(int row, int col, char[][] board, String word, int currChar, boolean[] result) {
        currChar += 1;
        char temp = board[row][col];
        board[row][col] = ' ';
        
        // If we've reached the end of the word, we're done
        if (currChar == word.length()) {
            result[0] = true; 
            return;
        }
        
        // Check if we have the next letter in any direction
        if (row-1 >= 0 && board[row-1][col] == word.charAt(currChar)) {
            isSolvable(row-1, col, board, word, currChar, result);
        }
        if (row+1 < board.length && board[row+1][col] == word.charAt(currChar)) {
            isSolvable(row+1, col, board, word, currChar, result);
        }
        if (col-1 >= 0 && board[row][col-1] == word.charAt(currChar)) {
            isSolvable(row, col-1, board, word, currChar, result);
        }
        if (col+1 < board[0].length && board[row][col+1] == word.charAt(currChar)) {
            isSolvable(row, col+1, board, word, currChar, result);
        }
        
        board[row][col] = temp;
    }
}