import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Connect4IO extends JFrame {
//
//    private Connect4Board boardPanel;
//    private JTextArea inputArea;
//    private JButton submitButton;
//
//    public Connect4IO(Dimension boardDimension, char[][] initialBoard) {
//        // Set up the frame
//        setTitle("Connect4 Game");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new BorderLayout());
//
//        // Left side: Input area
//        JPanel inputPanel = new JPanel();
//        inputPanel.setLayout(new BorderLayout());
//        inputPanel.setPreferredSize(new Dimension(200, boardDimension.height));
//
//        JLabel inputLabel = new JLabel("Enter Input:");
//        inputPanel.add(inputLabel, BorderLayout.NORTH);
//
//        inputArea = new JTextArea();
//        inputArea.setLineWrap(true);
//        inputArea.setWrapStyleWord(true);
//        JScrollPane scrollPane = new JScrollPane(inputArea);
//        inputPanel.add(scrollPane, BorderLayout.CENTER);
//
//        submitButton = new JButton("Submit");
//        inputPanel.add(submitButton, BorderLayout.SOUTH);
//
//        // Center: Game board
//        boardPanel = new Connect4Board(boardDimension, initialBoard);
//
//        // Add components to the frame
//        add(inputPanel, BorderLayout.WEST);
//        add(boardPanel, BorderLayout.CENTER);
//
//        // Set frame size
//        setSize(boardDimension.width + 200, boardDimension.height);
//        setResizable(true);
//        setLocationRelativeTo(null);
//
//        // Add event listener to the submit button
//        submitButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                handleInput(inputArea.getText());
//            }
//        });
//    }
//
//    // Handle input from the text box
//    private void handleInput(String input) {
//        // For demonstration, just print the input to the console
//        System.out.println("User input: " + input);
//
//        // Clear the text area after processing
//        inputArea.setText("");
//
//        // You can extend this logic to update the game state based on the input
//    }
//
//    public static void main(String[] args) {
//        // Initial board setup
//        char[][] initialBoard = {
//                {' ', ' ', ' ', ' ', ' ', ' ', ' '},
//                {' ', ' ', ' ', ' ', ' ', ' ', ' '},
//                {' ', ' ', ' ', ' ', ' ', ' ', ' '},
//                {' ', ' ', ' ', ' ', ' ', ' ', ' '},
//                {' ', ' ', ' ', ' ', ' ', ' ', ' '},
//                {' ', ' ', ' ', ' ', ' ', ' ', ' '}
//        };
//
//        Dimension boardDimension = new Dimension(700, 600); // Width x Height of the board panel
//        Connect4IO game = new Connect4IO(boardDimension, initialBoard);
//        game.setVisible(true);
//    }
}
