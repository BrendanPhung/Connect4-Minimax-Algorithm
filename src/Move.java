public class Move {
    public int col;   // The column where the move is made
    public int score; // The score of the move (used in minimax)

    // Default constructor
    public Move() {
        this.col = -1; // Default invalid column
        this.score = 0; // Default score
    }

    // Constructor with score
    public Move(int score) {
        this.col = -1; // Default invalid column
        this.score = score;
    }

    // Constructor with column and score
    public Move(int col, int score) {
        this.col = col;
        this.score = score;
    }

}
