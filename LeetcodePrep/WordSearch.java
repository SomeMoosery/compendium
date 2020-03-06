package LeetcodePrep;

// TODO improve on this solution, it's very slow
// https://www.youtube.com/watch?v=vYYNp0Jrdv0&t=5s

class WordSearch {
    public static boolean exist(char[][] board, String word) {
        // Traverse board until first character is found (if at all)
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if ((board[row][col] == word.charAt(0)) && isSolvable(row, col, board, word, 0)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    private static boolean isSolvable(int row, int col, char[][] board, String word, int currChar) {
        if (currChar == word.length()) {
            return true;
        }

        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length || board[row][col] != word.charAt(currChar)) {
            return false;
        }
        
        char temp = board[row][col];
        board[row][col] = ' ';
        
        // Check if we have the next letter in any direction
        boolean found = isSolvable(row-1, col, board, word, currChar+1)
            || isSolvable(row+1, col, board, word, currChar+1)
            || isSolvable(row, col-1, board, word, currChar+1)
            || isSolvable(row, col+1, board, word, currChar+1);
        
        board[row][col] = temp;
        return found;
    }

    public static void main(String[] args) {
        char[][] board = new char[][]{{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}};
        String test1 = "ABCCED";
        String test2 = "SEE";
        String test3 = "ABCB";

        System.out.println(test1 + " " + exist(board, test1));
        System.out.println(test2 + " " + exist(board, test2));
        System.out.println(test3 + " " +exist(board, test3));

    }
}