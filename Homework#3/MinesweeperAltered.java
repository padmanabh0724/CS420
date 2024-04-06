
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

// CellFactory interface
interface CellFactory {
    Cell createCell();
}

// Concrete Creator - MineCellFactory
class MineCellFactory implements CellFactory {
    @Override
    public Cell createCell() {
        return new MineCell();
    }
}

// Concrete Creator - EmptyCellFactory
class EmptyCellFactory implements CellFactory {
    @Override
    public Cell createCell() {
        return new EmptyCell();
    }
}

// Product - Abstract Class
abstract class Cell {
    boolean isMine;
    boolean isRevealed;
    int neighborMines;

    abstract void reveal();
    abstract void flag();
}

// Concrete Product - MineCell
class MineCell extends Cell {
    @Override
    void reveal() {
        isRevealed = true;
        System.out.println("MineCell revealed");
        // Additional logic for MineCell reveal
    }

    @Override
    void flag() {
        System.out.println("MineCell flagged");
        // Additional logic for MineCell flag
    }
}

// Concrete Product - EmptyCell
class EmptyCell extends Cell {
    @Override
    void reveal() {
        isRevealed = true;
        System.out.println("EmptyCell revealed");
        // Additional logic for EmptyCell reveal
    }

    @Override
    void flag() {
        System.out.println("EmptyCell flagged");
        // Additional logic for EmptyCell flag
    }
}

// Minesweeper class
public class MinesweeperAltered extends JFrame {
    private final int rows = 10;
    private final int cols = 10;
    private final int numMines = 10;
    private final Cell[][] grid;
    private GameState gameState;
    private final CellFactory cellFactory;

    public Minesweeper() {
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(rows, cols));

        grid = new Cell[rows][cols];
        gameState = new PlayingState();
        cellFactory = new MineCellFactory(); // Default factory

        initializeGrid();
        startGame();

        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = cellFactory.createCell();
                final int row = i;
                final int col = j;
                grid[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        gameState.handleClick(row, col);
                    }
                });
                add(grid[i][j]);
            }
        }
    }

    private void startGame() {
        // Place mines on the grid
        Random random = new Random();
        int minesPlaced = 0;
        while (minesPlaced < numMines) {
            int randRow = random.nextInt(rows);
            int randCol = random.nextInt(cols);
            if (!grid[randRow][randCol].isMine) {
                grid[randRow][randCol].isMine = true;
                minesPlaced++;
            }
        }
    }

    private void resetGame() {
        // Reset the game to initial state
        gameState = new PlayingState();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j].isMine = false;
                grid[i][j].isRevealed = false;
                grid[i][j].neighborMines = 0;
            }
        }
        startGame();
    }

    // Additional Operations for Minesweeper class
    public void revealCell(int row, int col) {
        grid[row][col].reveal();
    }

    public void flagCell(int row, int col) {
        grid[row][col].flag();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Minesweeper::new);
    }
}

// GameState interface
interface GameState {
    void handleClick(int row, int col);
}

// Concrete State - PlayingState
class PlayingState implements GameState {
    @Override
    public void handleClick(int row, int col) {
        // Implement logic for handling clicks during game
        System.out.println("Handling click in Playing State");
    }
}

// Concrete State - GameOverState
class GameOverState implements GameState {
    @Override
    public void handleClick(int row, int col) {
        // Implement logic for handling clicks after game over
        System.out.println("Game Over: Cannot handle click in Game Over State");
    }
}

// Concrete State - WinState
class WinState implements GameState {
    @Override
    public void handleClick(int row, int col) {
        // Implement logic for handling clicks after game win
        System.out.println("You Win: Cannot handle click in Win State");
    }
}

