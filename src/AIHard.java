class AIHard extends AI {

    public AIHard(){

    }

    @Override
    public char[][] move(char[][] board, char player){
        board = aiMove(board, player, 3000);
        return board;
    }
}