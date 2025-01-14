import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Connect4Board extends JPanel {

    int rows = 6;
    int cols = 7;
    boolean winner = false;
    int gameMode = 0; // Default game mode

    boolean modeSelected = false;

    Color[][] grid = new Color[rows][cols];
    private ColumnClickListener columnClickListener; // Callback for column clicks

    public Connect4Board(Dimension dimension, char[][] board) {
        setLayout(new BorderLayout()); // Use BorderLayout to place components
        setSize(dimension);
        setPreferredSize(dimension);

        // Initialize the grid based on the board
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (board[row][col] == ' ') {
                    grid[row][col] = Color.white;
                } else if (board[row][col] == 'x') {
                    grid[row][col] = Color.red;
                } else if (board[row][col] == 'o') {
                    grid[row][col] = Color.yellow;
                }
            }
        }

        // Create the panel for the buttons (Game Mode selection)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setPreferredSize(new Dimension(150, dimension.height));
        buttonPanel.setBackground(new Color(200, 200, 200));

        // Buttons for selecting game mode
        JButton aiModeButton = new JButton("AI Mode");
        JButton twoPlayerModeButton = new JButton("2-Player Mode");


        // Inside ActionListeners for buttons
        aiModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setGameMode(1);  // AI Mode (1)
                JOptionPane.showMessageDialog(null, "AI Mode Selected!", "Game Mode", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("AI Mode Selected!");
            }
        });

        twoPlayerModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setGameMode(2);  // 2-Player Mode (2)
                JOptionPane.showMessageDialog(null, "2-Player Mode Selected!", "Game Mode", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("2-Player Mode Selected!");
            }
        });

        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(aiModeButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(twoPlayerModeButton);

        add(buttonPanel, BorderLayout.WEST); // Add the button panel on the left side


        // Add mouse listener for column clicks
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int panelWidth = getWidth() - 150; // Account for button panel width
                int cellSize = panelWidth / cols;

                int clickedX = e.getX() - 150; // Adjust for button panel offset
                if (clickedX >= 0 && clickedX < panelWidth) {
                    int column = clickedX / cellSize;
                    System.out.println("You clicked column: " + (column + 1)); // Print the clicked column
                    if (columnClickListener != null) {
                        columnClickListener.onColumnClick(column);
                    }
                }
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Enable anti-aliasing for smoother rendering
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Dimension d = getSize();
        int panelWidth = d.width - 150; // Adjust for button panel width
        int panelHeight = d.height;

        // Calculate dynamic cell size based on panel size and grid dimensions
        int cellSize = Math.min(panelWidth / cols, panelHeight / rows);

        // Calculate margins to center the board
        int marginX = ((panelWidth - (cols * cellSize)) / 2) + 150; // Adjust for button panel
        int marginY = (panelHeight - (rows * cellSize)) / 2;

        // Draw background
        g2.setColor(new Color(0, 0, 255)); // Blue background
        g2.fillRect(0, 0, panelWidth + 150, panelHeight);

        // Draw the grid with smoother ovals
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                int startX = marginX + col * cellSize;
                int startY = marginY + row * cellSize;

                // Fill the cell with the corresponding color
                g2.setColor(grid[row][col]);
                g2.fillOval(startX, startY, cellSize, cellSize);

                // Draw a black border around each cell
                g2.setColor(Color.black);
                g2.drawOval(startX, startY, cellSize, cellSize);
            }
        }
    }

    // Set the column click listener
    public void setColumnClickListener(ColumnClickListener listener) {
        this.columnClickListener = listener;
    }

    // Interface for column click callback
    public interface ColumnClickListener {
        void onColumnClick(int column);
    }

    public int getGameMode() {
        return gameMode;
    }

    public void setGameMode(int mode) {
        this.gameMode = mode;
        this.modeSelected = true; // Mode is selected
    }

    public boolean isModeSelected() {
        return modeSelected;
    }
}
