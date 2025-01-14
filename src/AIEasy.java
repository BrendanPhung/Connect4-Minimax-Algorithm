class AIEasy extends AI {

    public AIEasy(){

    }

    @Override
    public char[][] move(char[][] board, char player){
        board = randomMove(board, player);
        return board;
    }
}