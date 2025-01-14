import java.util.*;

abstract class AI {
    static char AI = 'o';
    static char PLAYER = 'x';
    static int COLUMNS = 7;
    static int ROWS = 6;
    static char BLANK = ' ';


    abstract char[][] move(char [][] board, char player);

    public char[][] randomMove(char[][] board, char player){
        Random random = new Random();
        int AIMove = random.nextInt(0,6);
        for (int row = board.length-1; row >= 0; row--){
            if(board[row][AIMove] == ' '){
                board[row][AIMove] = player;
                break;
            }
        }
        return board;
    }


    public static boolean winCheck(char player, char[][] board){
        //check for 4 across
        for(int row = 0; row<board.length; row++){
            for (int col = 0;col < board[0].length - 3;col++){
                if (board[row][col] == player &&
                        board[row][col+1] == player &&
                        board[row][col+2] == player &&
                        board[row][col+3] == player){
                    return true;
                }
            }
        }
        //check for 4 up and down
        for(int row = 0; row < board.length - 3; row++){
            for(int col = 0; col < board[0].length; col++){
                if (board[row][col] == player &&
                        board[row+1][col] == player &&
                        board[row+2][col] == player &&
                        board[row+3][col] == player){
                    return true;
                }
            }
        }
        //check upward diagonal
        for(int row = 3; row < board.length; row++){
            for(int col = 0; col < board[0].length - 3; col++){
                if (board[row][col] == player   &&
                        board[row-1][col+1] == player &&
                        board[row-2][col+2] == player &&
                        board[row-3][col+3] == player){
                    return true;
                }
            }
        }
        //check downward diagonal
        for(int row = 0; row < board.length - 3; row++){
            for(int col = 0; col < board[0].length - 3; col++){
                if (board[row][col] == player   &&
                        board[row+1][col+1] == player &&
                        board[row+2][col+2] == player &&
                        board[row+3][col+3] == player){
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkDraw(char [][] board) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (board[i][j] == BLANK) {
                    return false;
                }
            }
        }
        return true;
    }

    // Assuming you have a function that makes the AI move
    public static char[][] aiMove(char[][] board, char player, long timeLimitMillis) {
        long startTime = System.nanoTime();  // Track time in nanoseconds
        int bestCol = -1;

        Move bestMove = null;

        // Perform Iterative Deepening based on time limit
        for (int depth = 1; ; depth++) {
            if (System.nanoTime() - startTime > timeLimitMillis * 1000000) {
                break;  // Stop if we've exceeded the time limit (time in nanoseconds)
            }

            bestMove = iterativeDeepening(player, board, depth, startTime, timeLimitMillis);
            bestCol = bestMove.col;

            // If we found a winning move or an optimal move, break early
            if (bestMove.score == 10000 || bestMove.score == -10000) {
                break;
            }
        }

        // Drop the piece in the chosen column
        for (int row = ROWS - 1; row >= 0; row--) {
            if (board[row][bestCol] == ' ') {
                board[row][bestCol] = player;  // Place the piece in the column
                break;  // Exit once the piece is placed
            }
        }

        return board;
    }

    // Modify iterativeDeepening to use time limit
    public static Move iterativeDeepening(char player, char[][] board, int maxDepth, long startTime, long timeLimitMillis) {
        Move bestMove = new Move();
        bestMove.score = (player == AI) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int depth = 1; depth <= maxDepth; depth++) {
            if (System.nanoTime() - startTime > timeLimitMillis * 1000000) {
                break;  // Stop if we've exceeded the time limit (time in nanoseconds)
            }

            Move currentMove = minimax(depth, player, Integer.MIN_VALUE, Integer.MAX_VALUE, board);

            // Track the best move across iterations
            if ((player == AI && currentMove.score > bestMove.score) ||
                    (player != AI && currentMove.score < bestMove.score)) {
                bestMove = currentMove;
            }
        }

        return bestMove;
    }

    // Minimax function
    public static Move minimax(int depth, char player, int alpha, int beta, char[][] board) {
        // Base case: check for a win or draw
        if (winCheck('o', board)) {
            return new Move(100);  // AI wins
        }
        if (winCheck('x', board)) {
            return new Move(-100);  // Player wins
        }
        if (checkDraw(board) || depth == 0) {
            // If it's a draw or depth limit reached, return board evaluation score
            return new Move(evaluatePosition(player, board));
        }

        Move bestMove = new Move();
        bestMove.score = (player == AI) ? Integer.MIN_VALUE : Integer.MAX_VALUE; // Maximizing or minimizing player

        // Priority column order: center first, then outside columns
        int[] columnOrder = {3, 2, 4, 1, 5, 0, 6};  // Center column is often most strategic

        // Winning move detection for AI
        for (int colIdx = 0; colIdx < columnOrder.length; colIdx++) {
            int col = columnOrder[colIdx];

            // Check if the column is not full
            if (board[0][col] != BLANK) {
                continue; // Skip this column if it's already full
            }

            // Try dropping a piece in the column
            for (int row = ROWS - 1; row >= 0; row--) {
                if (board[row][col] == BLANK) {
                    // Simulate AI's move
                    board[row][col] = AI;
                    if (winCheck(AI, board)) {
                        // AI can win here; prioritize it
                        board[row][col] = BLANK;
                        return new Move(col, 10000); // Winning move
                    }
                    // Undo the simulation
                    board[row][col] = BLANK;
                    break;
                }
            }
        }

        // Blocking move detection for opponent
        char opponent = (player == 'o') ? 'x' : 'o';  // Assuming 'o' is AI and 'x' is Player
        for (int colIdx = 0; colIdx < columnOrder.length; colIdx++) {
            int col = columnOrder[colIdx];

            // Check if the column is not full
            if (board[0][col] != BLANK) {
                continue; // Skip this column if it's already full
            }

            // Try dropping a piece in the column
            for (int row = ROWS - 1; row >= 0; row--) {
                if (board[row][col] == BLANK) {
                    // Simulate opponent's move
                    board[row][col] = opponent;
                    if (winCheck(opponent, board)) {
                        // Opponent can win here; block it
                        board[row][col] = BLANK;
                        return new Move(col, -10000); // Block this move
                    }
                    // Undo the simulation
                    board[row][col] = BLANK;
                    break;
                }
            }
        }

        // Main minimax recursion
        for (int colIdx = 0; colIdx < columnOrder.length; colIdx++) {
            int col = columnOrder[colIdx];

            // Check if the column is not full
            if (board[0][col] != BLANK) {
                continue; // Skip this column if it's already full
            }

            // Try dropping a piece in the column
            for (int row = ROWS - 1; row >= 0; row--) {
                if (board[row][col] == BLANK) {
                    // Make the move
                    board[row][col] = player;

                    // Recurse to the next depth level
                    Move currentMove = minimax(depth - 1, (player == AI) ? PLAYER : AI, alpha, beta, board);

                    // Undo the move
                    board[row][col] = BLANK;

                    // Store the column of the current move
                    currentMove.col = col;

                    // Alpha-Beta pruning
                    if (player == AI) {
                        if (currentMove.score > bestMove.score) {
                            bestMove = currentMove;
                            alpha = Math.max(alpha, bestMove.score);
                        }
                    } else {
                        if (currentMove.score < bestMove.score) {
                            bestMove = currentMove;
                            beta = Math.min(beta, bestMove.score);
                        }
                    }

                    // Prune the search tree if beta <= alpha
                    if (beta <= alpha) {
                        break;  // Stop exploring further moves
                    }
                }
            }
        }

        return bestMove;
    }

    // Drops a piece into the specified column and returns the row index
    private static int dropPiece(char[][] board, int col, char player) {
        for (int row = ROWS - 1; row >= 0; row--) {
            if (board[row][col] == BLANK) {
                board[row][col] = player;
                return row;
            }
        }
        return -1; // Column is full
    }

    // Undoes a move by setting the cell back to BLANK
    private static void undoMove(char[][] board, int row, int col) {
        board[row][col] = BLANK;
    }

    public static int evaluatePosition(char player, char[][] board) {
        int score = 0;

        // Opponent is the opposite of the current player
        char opponent = (player == 'o') ? 'x' : 'o';  // Assuming 'o' is AI and 'x' is Player

        // Loop through all rows and columns to evaluate the board
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (board[i][j] == player) {
                    score += 1;  // Player's piece gives +1
                } else if (board[i][j] == opponent) {
                    score -= 1;  // Opponent's piece gives -1
                }
            }
        }

        // Look for two-in-a-row or three-in-a-row patterns for player and opponent
        score += evaluateThreats(player, opponent, board);

        // Center control: Central columns are more valuable
        score += evaluateCenterControl(player, board);

        return score;
    }

    // Evaluate the number of two-in-a-row, three-in-a-row, and potential wins
    private static int evaluateThreats(char player, char opponent, char[][] board) {
        int score = 0;

        // Horizontal, Vertical, and Diagonal checks
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (col + 3 < COLUMNS) {  // Horizontal
                    score += evaluateLine(player, opponent, row, col, 0, 1, board);
                }
                if (row + 3 < ROWS) {  // Vertical
                    score += evaluateLine(player, opponent, row, col, 1, 0, board);
                }
                if (row + 3 < ROWS && col + 3 < COLUMNS) {  // Diagonal (down-right)
                    score += evaluateLine(player, opponent, row, col, 1, 1, board);
                }
                if (row + 3 < ROWS && col - 3 >= 0) {  // Diagonal (down-left)
                    score += evaluateLine(player, opponent, row, col, 1, -1, board);
                }
            }
        }

        return score;
    }

    private static int evaluateLine(char player, char opponent, int row, int col, int rowDelta, int colDelta, char[][] board) {
        int playerCount = 0;
        int opponentCount = 0;
        int emptyCount = 0;

        for (int i = 0; i < 4; i++) {
            int r = row + i * rowDelta;
            int c = col + i * colDelta;

            if (r >= 0 && r < ROWS && c >= 0 && c < COLUMNS) {
                char cell = board[r][c];
                if (cell == player) {
                    playerCount++;
                } else if (cell == opponent) {
                    opponentCount++;
                } else {
                    emptyCount++;
                }
            }
        }

        // Strongly prioritize blocking opponent wins
        if (opponentCount == 3 && emptyCount == 1) {
            return -10000; // Huge penalty for missing a block
        }
        if (playerCount == 3 && emptyCount == 1) {
            return 10000; // Huge reward for winning
        }
        if (playerCount == 2 && emptyCount == 2) {
            return 10; // Moderate score for AI's progress
        }
        if (opponentCount == 2 && emptyCount == 2) {
            return -10; // Moderate penalty for opponent's progress
        }

        return 0; // Neutral for no significant patterns
    }

    // Evaluate the center control: Central columns are more valuable
    private static int evaluateCenterControl(char player, char[][] board) {
        int score = 0;

        // Focus on the center columns (more valuable in games like Connect Four)
        for (int row = 0; row < ROWS; row++) {
            if (board[row][COLUMNS / 2] == player) {
                score += 3;  // Center column is worth more points
            }
        }

        return score;
    }

    //working
    // public static Move minimax(int depth, char player, int alpha, int beta, char[][] board) {
    //     // Base case: check for a win for either player
    //     if (winCheck('o', board)) {
    //         return new Move(100);  // AI wins
    //     }
    //     if (winCheck('x', board)) {
    //         return new Move(-100);  // Player wins
    //     }
    //     if (checkDraw(board) || depth == 0) {
    //         // If it's a draw or depth limit reached, return board evaluation score
    //         return new Move(evaluatePosition(player, board));
    //     }

    //     Move bestMove = new Move();
    //     if (player == AI) {
    //         bestMove.score = Integer.MIN_VALUE;  // Maximizing player (AI)
    //     } else {
    //         bestMove.score = Integer.MAX_VALUE;  // Minimizing player (Player)
    //     }

    //     // Priority column order: center first, then outside columns
    //     int[] columnOrder = {3, 2, 4, 1, 5, 0, 6};

    //     for (int colIdx = 0; colIdx < columnOrder.length; colIdx++) {
    //         int i = columnOrder[colIdx];  // Get column from the order array

    //         // Try dropping a piece in the column if it is not full
    //         for (int j = ROWS - 1; j >= 0; j--) {
    //             if (board[j][i] == BLANK) {
    //                 // Make the move
    //                 board[j][i] = player;

    //                 // Recurse to the next depth level
    //                 Move currentMove = minimax(depth - 1, (player == AI) ? PLAYER : AI, alpha, beta, board);

    //                 // Undo the move
    //                 board[j][i] = BLANK;

    //                 // Store the column of the current move
    //                 currentMove.col = i;

    //                 // Alpha-Beta pruning
    //                 if (player == AI) {
    //                     if (currentMove.score > bestMove.score) {
    //                         bestMove = currentMove;
    //                         alpha = Math.max(alpha, bestMove.score);
    //                     }
    //                 } else {
    //                     if (currentMove.score < bestMove.score) {
    //                         bestMove = currentMove;
    //                         beta = Math.min(beta, bestMove.score);
    //                     }
    //                 }

    //                 // Prune the search tree if beta <= alpha
    //                 if (beta <= alpha) {
    //                     break;  // Stop exploring further moves
    //                 }
    //             }
    //         }
    //     }

    //     return bestMove;
    // }


}