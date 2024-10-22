package tictactoe;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * The {@code TicTacToeModel} class implements the {@link TicTacToe} interface,
 * providing the logic and state management for a Tic Tac Toe game. It manages
 * the game board, tracks player turns, checks for winners, and determines
 * when the game is over. This class ensures that the rules of the game are
 * enforced, including valid moves and checking for win conditions.
 *
 * The game is played on a three-by-three grid, with players alternating turns
 * to place their marks (X or O) in empty cells. The first player to align
 * three of their marks vertically, horizontally, or diagonally wins the game.
 * In the event that all cells are filled without a winner, the game is declared
 * a tie.
 *
 * <p>
 * Example usage:
 * <pre>
 *     TicTacToe game = new TicTacToeModel();
 *     game.move(0, 0); // Player X places an X in the top-left corner
 *     game.move(1, 1); // Player O places an O in the center
 *     // ... continue the game ...
 * </pre>
 * </p>
 */
public class TicTacToeModel implements TicTacToe {
  private final Player[][] board;
  private Player currentTurn;
  private int moveCount;

  /**
   * Constructs a new {@code TicTacToeModel} instance, initializing the game board,
   * setting the current turn to Player X, and resetting the move count to zero.
   * The game board is represented as a 3x3 grid, where each cell can hold a
   * {@link Player} mark (X or O) or be empty (null).
   *
   * <p>
   * This constructor prepares the game for a new session, ensuring that all cells
   * are empty, and establishes Player X as the starting player.
   * </p>
   */
  public TicTacToeModel() {
    board = new Player[3][3]; // 3x3 board
    currentTurn = Player.X; // Player X starts
    moveCount = 0; // No moves made yet
  }

  @Override
  public void isValidMove(int row, int col) {
    if (isGameOver()) {
      throw new IllegalStateException("Game is over.");
    }
    if (row < 0 || row >= 3 || col < 0 || col >= 3 || board[row][col] != null) {
      throw new IllegalArgumentException("Invalid move.");
    }

    board[row][col] = currentTurn; // Place the mark
    moveCount++; // Increment the move count

    if (!isGameOver()) {
      currentTurn = (currentTurn == Player.X) ? Player.O : Player.X; // Switch players
    }
  }
  
  
  @Override
  public Player getTurn() {
    if (isGameOver()) {
      throw new IllegalStateException("Game is over.");
    }
    return currentTurn;
  }

  @Override
  public boolean isGameOver() {
    return getWinner() != null || moveCount == 9; // Game ends if there's a winner or board is full
  }

  @Override
  public Player getWinner() {
    // Check rows, columns, and diagonals for a win
    for (int i = 0; i < 3; i++) {
      if (board[i][0] != null && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
        return board[i][0]; // Row win
      }
      if (board[0][i] != null && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
        return board[0][i]; // Column win
      }
    }
    // Check diagonals
    if (board[0][0] != null && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
      return board[0][0]; // Diagonal win
    }
    if (board[0][2] != null && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
      return board[0][2]; // Anti-diagonal win
    }
    return null; // No winner
  }

  //  @Override
  //  public Player[][] getBoard() {
  //    return board; // Return the current board state
  //  }
  
  @Override
  public Player[][] getBoard() {
    Player[][] boardCopy = new Player[3][3];
    for (int r = 0; r < 3; r++) {
      System.arraycopy(board[r], 0, boardCopy[r], 0, 3);
    }
    return boardCopy;
  }

  //  @Override
  //  public Player getMarkAt(int r, int c) {
  //    if (r < 0 || r >= 3 || c < 0 || c >= 3) {
  //      throw new IndexOutOfBoundsException("Cell is out of bounds.");
  //    }
  //    return board[r][c]; // Return the mark at the given position
  //  }
  
  @Override
  public Player getMarkAt(int r, int c) {
    if (r < 0 || r >= 3 || c < 0 || c >= 3) {
      throw new IndexOutOfBoundsException("Cell is out of bounds.");
    }
    return board[r][c];
  }


  @Override
  public String toString() {
    return Arrays.stream(getBoard())
              .map(row -> " " + Arrays.stream(row)
                      .map(p -> p == null ? " " : p.toString())
                      .collect(Collectors.joining(" | ")))
              .collect(Collectors.joining("\n-----------\n"));
  }
}
