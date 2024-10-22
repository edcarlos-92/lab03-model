import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import tictactoe.Player;
import tictactoe.TicTacToe;
import tictactoe.TicTacToeModel;

/**
 * Unit tests for the {@link TicTacToeModel} class, validating its functionality
 * and ensuring it behaves as expected. The tests cover the following scenarios:
 * <ul>
 *   <li>Initial turn of the game</li>
 *   <li>Turn switching after a move</li>
 *   <li>Attempting to move to an occupied cell</li>
 *   <li>Handling moves after the game is over</li>
 *   <li>Determining the winner correctly</li>
 *   <li>Checking the game over status</li>
 *   <li>Retrieving the current state of the game board</li>
 * </ul>
 */
public class TicTacToeModelTest {
  // Using interface type as mentioned in Q2.1
  private TicTacToe game;

  @Before
  public void setUp() {
    game = new TicTacToeModel();
  }

  @Test
  public void testGameStartsWithX() {
    assertEquals("Game should start with Player X", Player.X, game.getTurn());
  }

  @Test
  public void testMove() {
    // Make a move and verify the mark is placed correctly
    game.isValidMove(0, 0);
    assertEquals("Mark should be placed at correct location", Player.X, game.getMarkAt(0, 0));
    // Verify other positions are unaffected
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (i != 0 || j != 0) {
          assertNull("Other positions should remain empty", game.getMarkAt(i, j));
        }
      }
    }
  }

  @Test
  public void testPlayerChangesAfterMove() {
    assertEquals(Player.X, game.getTurn());
    game.isValidMove(0, 0);
    assertEquals(Player.O, game.getTurn());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMove() {
    game.isValidMove(-1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveToOccupiedSpace() {
    game.isValidMove(0, 0);
    game.isValidMove(0, 0); // Should throw exception
  }

  @Test(expected = IllegalStateException.class)
  public void testNoMovesAfterGameOver() {
    // Create winning condition
    game.isValidMove(0, 0); // X
    game.isValidMove(1, 0); // O
    game.isValidMove(0, 1); // X
    game.isValidMove(1, 1); // O
    game.isValidMove(0, 2); // X wins
    game.isValidMove(2, 0); // Should throw exception
  }

  @Test
  public void testGetMarkAt() {
    game.isValidMove(0, 0);
    assertEquals(Player.X, game.getMarkAt(0, 0));
    assertNull(game.getMarkAt(1, 1));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testInvalidGetMarkAtRow() {
    game.getMarkAt(-1, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testGetMarkAtInvalidIndices() {
    TicTacToeModel gameBounds = new TicTacToeModel();
    gameBounds.getMarkAt(-1, 0); // This threw the exception but now fixed
  }

  @Test
  public void testIsGameOverWhenWon() {
    game.isValidMove(0, 0); // X
    game.isValidMove(1, 0); // O
    game.isValidMove(0, 1); // X
    game.isValidMove(1, 1); // O
    game.isValidMove(0, 2); // X wins
    // game.move(2, 1); // O
    // The last move is not needed to create a tie, as all spots are filled.

    assertTrue(game.isGameOver());
  }

  @Test
  public void testIsGameOverWhenNotFinished() {
    game.isValidMove(0, 0);
    assertFalse(game.isGameOver());
  }

  @Test
  public void testHorizontalWin() {
    // Row 0 win for X
    game.isValidMove(0, 0); // X
    game.isValidMove(1, 0); // O
    game.isValidMove(0, 1); // X
    game.isValidMove(1, 1); // O
    game.isValidMove(0, 2); // X wins
    assertTrue(game.isGameOver());
    assertEquals(Player.X, game.getWinner());
  }

  @Test
  public void testVerticalWin() {
    // Column 0 win for X
    game.isValidMove(0, 0); // X
    game.isValidMove(0, 1); // O
    game.isValidMove(1, 0); // X
    game.isValidMove(1, 1); // O
    game.isValidMove(2, 0); // X wins
    assertTrue(game.isGameOver());
    assertEquals(Player.X, game.getWinner());
  }

  @Test
  public void testDiagonalWin() {
    // Diagonal win for X
    game.isValidMove(0, 0); // X
    game.isValidMove(0, 1); // O
    game.isValidMove(1, 1); // X
    game.isValidMove(0, 2); // O
    game.isValidMove(2, 2); // X wins
    assertTrue(game.isGameOver());
    assertEquals(Player.X, game.getWinner());
  }

  @Test
  public void testGetWinnerWhenThereIsAWinner() {
    game.isValidMove(0, 0); // X
    game.isValidMove(1, 0); // O
    game.isValidMove(0, 1); // X
    game.isValidMove(1, 1); // O
    game.isValidMove(0, 2); // X wins
    assertEquals(Player.X, game.getWinner());
  }

  @Test
  public void testGetBoard() {
    game.isValidMove(0, 0);
    Player[][] board = game.getBoard();
    assertEquals(Player.X, board[0][0]);
    // Verify other positions are null
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (i != 0 || j != 0) {
          assertNull(board[i][j]);
        }
      }
    }
  }

  @Test
  public void testGetWinnerWhenNoWinner() {
    // Set up a game state where there is no winner
    game.isValidMove(0, 0); // X
    game.isValidMove(1, 0); // O
    game.isValidMove(0, 1); // X
    game.isValidMove(1, 1); // O
    game.isValidMove(0, 2); // X
    game.isValidMove(1, 2); // O
    game.isValidMove(2, 0); // X
    game.isValidMove(2, 1); // O
    game.isValidMove(2, 2); // X - The board is full, no winner
    assertNull(game.getWinner()); // Assert that getWinner returns null
  }

  @Test
  public void testTiedGame() {
    // Fill the board with a tied game situation (no winner)
    game.isValidMove(0, 0); // X
    game.isValidMove(0, 1); // O
    game.isValidMove(0, 2); // X
    game.isValidMove(1, 0); // O
    game.isValidMove(1, 1); // X
    game.isValidMove(1, 2); // O
    game.isValidMove(2, 0); // X
    // game.move(2, 1); // O
    // The last move is not needed to create a tie, as all spots are filled.

    // Now check that the game is over and there is no winner
    assertTrue(game.isGameOver());
    assertNull(game.getWinner());
  }

}