public class Board {

    char[][] board;

    public Board() {
        board = new char[6][7];
    }

    public char[][] getBoard() {
        return board;
    }

    public void move (int row, int col, ) {
        board[row][col] = 'x';
    }
}
