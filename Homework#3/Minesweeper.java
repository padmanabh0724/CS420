
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Minesweeper extends JFrame {
    private final int rows = 10;
    private final int cols = 10;
    private final int numMines = 10;
    private final JButton[][] grid;
    private final boolean[][] isMine;
    private final boolean[][] isRevealed;
    private int remainingCells;

    public Minesweeper() {
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(rows, cols));

        grid = new JButton[rows][cols];
        isMine = new boolean[rows][cols];
        isRevealed = new boolean[rows][cols];
        remainingCells = rows * cols - numMines;

        initializeGrid();
        placeMines();

        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new JButton();
                final int row = i;
                final int col = j;
                grid[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        revealCell(row, col);
                    }
                });
                add(grid[i][j]);
            }
        }
    }

    private void placeMines() {
        Random random = new Random();
        int minesPlaced = 0;
        while (minesPlaced < numMines) {
            int randRow = random.nextInt(rows);
            int randCol = random.nextInt(cols);
            if (!isMine[randRow][randCol]) {
                isMine[randRow][randCol] = true;
                minesPlaced++;
            }
        }
    }

    private void revealCell(int row, int col) {
        if (isRevealed[row][col]) {
            return;
        }

        isRevealed[row][col] = true;
        grid[row][col].setEnabled(false);
        remainingCells--;

        if (isMine[row][col]) {
            grid[row][col].setText("X");
            gameOver();
        } else {
            int count = countAdjacentMines(row, col);
            if (count > 0) {
                grid[row][col].setText(String.valueOf(count));
            } else {
                for (int i = row - 1; i <= row + 1; i++) {
                    for (int j = col - 1; j <= col + 1; j++) {
                        if (isValidCell(i, j)) {
                            revealCell(i, j);
                        }
                    }
                }
            }

            if (remainingCells == 0) {
                JOptionPane.showMessageDialog(this, "You win!");
                resetGame();
            }
        }
    }

    private void gameOver() {
        JOptionPane.showMessageDialog(this, "Game Over");
        resetGame();
    }

    private void resetGame() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j].setText("");
                grid[i][j].setEnabled(true);
                isMine[i][j] = false;
                isRevealed[i][j] = false;
            }
        }
        placeMines();
        remainingCells = rows * cols - numMines;
    }

    private int countAdjacentMines(int row, int col) {
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (isValidCell(i, j) && isMine[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Minesweeper::new);
    }
}

