
import java.util.*;
import java.io.*;
// import java.awt.event.*;
// import java.awt.*;
// import javax.swing.*;
// import java.awt.*;
// import java.awt.geom.*;
// import javax.swing.*;
// import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

class Main {

    static Scanner sc = new Scanner(System.in);
    static Scanner inputStream = null;

    public static void printLeaderboard(){
        //Scanner inputStream = null;
        boolean hasPlayers = false;
        System.out.println("*Leaderboard # of wins*");
        try {
            inputStream = new Scanner(new BufferedReader(new FileReader("leaderboard.txt")));
            while (inputStream.hasNext()) {
                System.out.println(inputStream.next() + " " + inputStream.next() + " " + inputStream.nextInt());
                hasPlayers = true;
            }
            mainMenu();
        }
        catch (IOException e) {
            System.out.println(e);
        }
        finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        if (hasPlayers == false){
            System.out.println("No games have been played yet.");
        }
    }


    public static void display(char [][] board){
        System.out.println("---------------");
        for (int row = 0; row < board.length; row++){
            System.out.print("|");
            for (int col = 0; col < board[0].length; col++){
                System.out.print(board[row][col]);
                System.out.print("|");
            }
            System.out.println();
            System.out.println("---------------");
        }
        System.out.println(" 1 2 3 4 5 6 7");
        System.out.println();
    }


    public static void AIMove(AI AI, char [][] board, char player){
        board = AI.move(board, player);
        System.out.println("AI is calibrating its move...");
        try {
            Thread.sleep(100);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        updateBoard(board);
    }

    public static void playerMove(char [][] board, char player){
        int playerMove = 0;
        while (playerMove < 1 || playerMove > 7){
            System.out.println("Player 1 (" + player + ") please enter row number: ");
            playerMove = sc.nextInt();
        }
        for (int row = board.length-1; row >= 0; row--){
            if(board[row][playerMove-1] == ' '){
                board[row][playerMove-1] = player;
                break;
            }
        }
        updateBoard(board);
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


    public static int gameMode(){
        int userGameMode = 0;
        while (userGameMode != 1 && userGameMode != 2){
            try {
                System.out.println("Type (1) for single player, Type (2) for multiplayer player");
                userGameMode = sc.nextInt();
            }
            catch (InputMismatchException e){
                System.out.println("Invalid Input. Please Enter 1 or 2\n");
                sc.nextLine();
            }
        }
        if (userGameMode == 1){
            return 1;
        }
        return 2;
    }


    public static void searchPlayer(String firstName){
        //Scanner inputStream2 = null;
        String fname = "";
        boolean found = false;
        try {
            inputStream = new Scanner(new BufferedReader(new FileReader("leaderboard.txt")));
            while (inputStream.hasNext()) {
                fname = inputStream.next();
                if (fname.equals(firstName)){
                    if(inputStream.hasNextInt()){
                        int win = inputStream.nextInt();
                        System.out.println(fname + " has " + win + " win(s).");
                        found = true;
                        break;
                    }
                }
                inputStream.nextInt();
            }
            if (found == false){
                System.out.println(firstName + " not found in leaderboard.\n");
            }
            mainMenu();
        }
        catch (IOException e) {
            System.out.println ("Error reading leaderboard file");
        }
        finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }




    public static void mainMenu(){
        int option = 0;

        while (option < 1 || option > 3){
            try {
                System.out.println("[Main Menu] Type:\n1 to start\n2 to display leaderboard\n3 to search for a player");
                option = sc.nextInt();
            }
            catch (InputMismatchException e){
                System.out.println("Invalid Input. Please Enter a number from 1-3\n");
                sc.nextLine();
            }
        }
        if (option == 2){
            printLeaderboard();
            sc.nextLine();
        }
        if (option == 3){
            //clear scanner
            sc.nextLine();
            String firstName = "";
            while (firstName.contains(" ") == true || firstName.equals("") == true || firstName.matches("[a-zA-Z]+") == false){
                System.out.println("\nEnter First Name:");
                firstName = sc.nextLine();
                System.out.println(" ");
            }
            searchPlayer(firstName);
        }
    }


    static JFrame frame;
    public static void updateBoard(char [][] board){
        frame = new JFrame("DrawGrid");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(frame.getSize());
        frame.add(new Connect4Board(frame.getSize(), board));
        frame.pack();
        frame.setVisible(true);
        display(board);
    }



    //Find the size of txt file
    public static void updateFile(String player1Name, String player2Name, int winner){
        int counter2 = 0;
        try {
            inputStream = new Scanner(new BufferedReader(new FileReader("leaderboard.txt")));
            while (inputStream.hasNext()) {
                inputStream.next();
                inputStream.next();
                counter2++;
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }
        finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        //Read file and add it to the array.
        String[][] array = new String[counter2][2];
        int counter3 = 0;
        try {
            inputStream = new Scanner(new BufferedReader(new FileReader("leaderboard.txt")));
            while (inputStream.hasNext()) {
                array[counter3][0] = inputStream.next();
                array[counter3][1] = inputStream.next();
                counter3++;
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }
        finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        //check if name is in the array. If it isnt add it into the array.
        boolean name1Check = false;
        boolean name2Check = false;
        boolean aiCheck = false;
        for (int flag2 = 0; flag2 < array.length; flag2++){
            if (array[flag2][0].equals(player1Name)){
                name1Check = true;
            }
            if (array[flag2][0].equals(player2Name)){
                name2Check = true;
            }
        }
        if (player2Name.equals("AI")){
            name2Check = true;
            aiCheck = true;
        }
        if (name1Check == false){
            String[][] tempArray = new String [array.length+1][2];
            for (int flag3 = 0; flag3<array.length; flag3++){
                tempArray[flag3][0] = array[flag3][0];
                tempArray[flag3][1] = array[flag3][1];
            }
            tempArray[tempArray.length-1][0] = player1Name;
            tempArray[tempArray.length-1][1] = "0";
            array = tempArray;
        }
        if (name2Check == false){
            String[][] tempArray = new String [array.length+1][2];
            for (int flag4 = 0; flag4<array.length; flag4++){
                tempArray[flag4][0] = array[flag4][0];
                tempArray[flag4][1] = array[flag4][1];
            }
            tempArray[tempArray.length-1][0] = player2Name;
            tempArray[tempArray.length-1][1] = "0";
            array = tempArray;
        }

        //Add to the winner's number of wins.
        int addToInt;
        if (winner == 1){
            for (int flag5 =0; flag5<array.length; flag5++){
                if (array[flag5][0].equals(player1Name)){
                    addToInt = Integer.parseInt(array[flag5][1]) + 1;
                    array[flag5][1] = String.valueOf(addToInt);
                }
            }
        } else {
            if (aiCheck == false){
                for (int flag5 =0; flag5<array.length; flag5++){
                    if (array[flag5][0].equals(player2Name)){
                        addToInt = Integer.parseInt(array[flag5][1]) + 1;
                        array[flag5][1] = String.valueOf(addToInt);
                    }
                }
            }
        }

        //Sort array from high to low with bubble sort.
        for (int i = 1; i < array.length; i++) {
            boolean sorted = true;
            for (int j = 0; j < array.length - i; j++) {
                if (Integer.parseInt(array[j][1]) < Integer.parseInt(array[j + 1][1]))  {
                    String temp;
                    temp = array[j][1] ;
                    array[j][1] = array[j+1][1];
                    array[j+1][1] = temp;
                    String temp2;
                    temp2 = array[j][0] ;
                    array[j][0] = array[j+1][0];
                    array[j+1][0] = temp2;
                    sorted = false;
                }

            }
            if (sorted) {
                break;
            }
        }

        //Write the 2d array back into the file.
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter("leaderboard.txt", false));
            for (int flag = 0; flag<array.length; flag++){
                pw.println((array[flag][0]) + " " + (array[flag][1]));
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }
        finally {
            if (pw != null) {
                pw.close();
            }
        }
        sc.close();
    }


    public static void main(String[] args) {

        System.out.print("\033[H\033[2J");

        int rows = 6; //num of rows
        int columns = 7; // num of columns
        char player1 = 'x';
        char player2 = 'o';
        char player = ' ';

        //make empty board
        char [][] board = new char [rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = ' ';
            }
        }

        frame = new JFrame("DrawGrid");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(frame.getSize());
        frame.add(new Connect4Board(frame.getSize(), board));
        frame.pack();
        frame.setVisible(true);
        Mouse ml = new Mouse();
        frame.addMouseListener(ml);


        AI AI = new AIEasy();

        Connect4Board boardPanel = new Connect4Board(frame.getSize(), board);
        frame.add(boardPanel);
        frame.pack();
        frame.setVisible(true);

        //mainMenu();

        // Wait for the user to select the game mode via buttons
        // This is non-blocking; the UI remains responsive
        // Instead of polling, use the isModeSelected method to wait for mode selection
        while (!boardPanel.isModeSelected()) {
            try {
                Thread.sleep(1000); // Allow UI to process updates
                System.out.println(boardPanel.isModeSelected());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        System.out.println("Got here");

        // After mode is selected, proceed with the game
        int gameMode = boardPanel.getGameMode(); // Retrieve the game mode selected
        System.out.println("Game Mode: " + (gameMode == 1 ? "AI" : "2-Player"));

        // Start the game based on the selected mode
        String player1Name = "";
        String player2Name = "AI";

        if (gameMode == 1) {
            sc.nextLine();
            String userAI = "";
            while (userAI.equals("easy") == false && userAI.equals("medium") == false && userAI.equals("hard") == false){
                System.out.println("Type (easy) for easy bot\nType (medium) for medium bot\nType (hard) for hard bot");
                userAI = sc.nextLine();
            }
            if (userAI.equals("medium")){
                AI = new AIMedium();
            }
            if (userAI.equals("hard")){
                AI = new AIHard();
            }
            while ((player1Name.contains(" ") == true || player1Name.equals("") == true || player1Name.matches("[a-zA-Z]+") == false)){
                System.out.println("Please enter your name:");
                player1Name = sc.nextLine();
                System.out.println("");
            }
        } else {
            while ((player1Name.contains(" ") == true || player1Name.equals("") == true || player1Name.matches("[a-zA-Z]+") == false)){
                System.out.println("Player 1 please enter your name:");
                player1Name = sc.nextLine();
                System.out.println("");
            }
            while ((player2Name.contains(" ") == true || player2Name.equals("") == true || player2Name.matches("[a-zA-Z]+") == false)){
                System.out.println("Player 2 please enter your name:");
                player2Name = sc.nextLine();
                System.out.println("");
            }
        }

        //gamemode 1 is 1 player
        //gamemode 2 is 2 player


        int moves = 0;
        boolean gameOver = false;
        int winner = 2;

        while (gameOver == false && moves < 43){
            //display(board);
            player = player1;
            playerMove(board, player);
            //display(board);
            if (winCheck(player, board) == true){
                gameOver = true;
                System.out.println("Player " + player + " You won!");
                winner = 1;
                break;
            }
            player = player2;

            if (gameMode == 1){
                AIMove(AI, board, player);
            } else {
                playerMove(board, player);
            }
            if (winCheck(player, board) == true){
                //display(board);
                gameOver = true;
                if (gameMode == 1){
                    System.out.println("You lost!");
                } else {
                    System.out.println("Player " + player + " You won!");
                }
            }
            moves++;
        }

        updateFile(player1Name, player2Name, winner);
    }
}
