import java.util.Arrays;


/** A connect game
 */
class Game {
    private final int connect;
    private final char[][] board;
    private char winner = '-';
    private int lastRow;
    private int lastCol = -1;

    /** Constructs a new connect game
     *
     * @param connect the number of slots to connect to win
     * @param numRows the number of rows of the board
     * @param numCols the number of columns of the board
     */
    public Game(int connect, int numRows, int numCols) {
        this.connect = connect;
        board = new char[numRows][numCols];

        // All slots initially empty
        for (char[] slot : board) Arrays.fill(slot, ' ');
    }

    /** Returns the board in string format by separating columns with |
     *
     * @return a string representation of the board
     */
    @Override
    public String toString() {
        StringBuilder boardString = new StringBuilder(board.length * board[0].length);
        for (char[] rows : board) {
            for (char slot : rows) {
                boardString.append('|');
                boardString.append(slot);
            }
            boardString.append("|\n");
        }

        // Display column numbers underneath
        for (int i=0; i < board[0].length; i++) {
            boardString.append(' ');
            boardString.append(i);
        }
        return boardString.toString();
    }

    /** Fills the appropriate slot in the column picked by player
     *
     * @param player the player whose turn it is
     * @param column the column chosen by the player
     * @return whether the column chosen was valid
     */
    public boolean pickColumn(char player, int column) {
        // Return false if a player picks a column outside the dimensions of the board.
        if (column < 0 || column >= board[0].length) return false;

        int i = board.length-1;
        while (i >= 0) {
            if (board[i][column] == ' ') {
                board[lastRow=i][lastCol=column] = player;
                return true;
            }
            i--;
        }
        // Return false if a player picks a column that is already full.
        return false;
    }

    /** Returns true if the game is still active
     *
     * @return whether the game has not yet been completed
     */
    public boolean isPlaying() {
        // Return true if the board is empty.
        if (lastCol == -1) return true;
        else if (isHorizontalWin() || isVerticalWin() || isForwardDiagonalWin() ||
                isBackwardDiagonalWin()) {
            winner = board[lastRow][lastCol];
            return false;
        }

        // Record that there was a tie and return false if the board has been completely filled with
        // no player having won.
        for (char[] rows : board) {
            for (char slot : rows) if (slot == ' ') return true;
        }
        winner = '=';
        return false;
    }

    /** Gets the winner of the game, '=' if the game was a tie, '-' if the game is ongoing
     *
     * @return a char representing the current winner
     */
    public char getWinner() {
        return winner;
    }

    private boolean isHorizontalWin() {
        char lastPlayer = board[lastRow][lastCol];
        int streak = 1;
        int i = 1;

        // Start from the last move and go down the board, counting the length of the streak of
        // slots filled by the last player. Stop if the streak is broken or the edge of the board is hit.
        while (lastRow+i < board.length && board[lastRow+i][lastCol] == lastPlayer) {
            streak++;
            i++;
        }
        return streak == connect;
    }

    private boolean isVerticalWin() {
        char lastPlayer = board[lastRow][lastCol];
        int streak = 1;
        int i = -1;

        // Start from the last move and go both left and right, counting the length of the streak of
        // slots filled by the last player. Stop if the streak is broken or the edge of the board is hit.
        while (0<=lastCol+i && board[lastRow][lastCol+i] == lastPlayer) {
            streak++;
            i--;
        }
        i = 1;
        while (lastCol+i < board[0].length && board[lastRow][lastCol+i] == lastPlayer) {
            streak++;
            i++;
        }
        return streak == connect;
    }

    private boolean isForwardDiagonalWin() {
        char lastPlayer = board[lastRow][lastCol];
        int streak = 1;
        int i = -1;

       /* Start from the last move and go both upper-right and lower-down, counting the length of
       the streak of slots filled by the last player. Stop if the streak is broken or the edge of the
       board is hit. Notice we use lastRow-i here because the row index must go in the opposite
       direction of the column index. */
        while (lastRow-i < board.length && 0<=lastCol+i &&
                board[lastRow-i][lastCol+i] == lastPlayer) {
            streak++;
            i--;
        }
        i = 1;
        while (0<=lastRow-i && lastCol+i < board[0].length &&
                board[lastRow-i][lastCol+i] == lastPlayer) {
            streak++;
            i++;
        }
        return streak == connect;
    }

    private boolean isBackwardDiagonalWin() {
        char lastPlayer = board[lastRow][lastCol];
        int streak = 1;
        int i = -1;

        // Start from the last move and go both lower-right and upper-down, counting the length of the
        // streak of slots filled by the last player. Stop if the streak is broken or the edge of the
        // board is hit.
        while (0<=lastRow+i && 0<=lastCol+i && board[lastRow+i][lastCol+i] == lastPlayer) {
            streak++;
            i--;
        }
        i = 1;
        while (lastRow+i < board.length && lastCol+i < board[0].length &&
                board[lastRow+i][lastCol+i] == lastPlayer) {
            streak++;
            i++;
        }
        return streak == connect;
    }
}
