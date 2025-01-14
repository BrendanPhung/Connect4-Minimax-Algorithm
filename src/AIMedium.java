import java.util.*;

class AIMedium extends AI {

    public AIMedium(){

    }

    @Override
    public char[][] move(char[][] board, char player){
        Random random = new Random();
        int randomOrAI = random.nextInt(1,2);
        if (randomOrAI == 1){
            board = aiMove(board, player, 3000);
        } else {
            board = randomMove(board, player);
        }
        return board;
    }
}
