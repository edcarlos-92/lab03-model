package tictactoe;

/**
 * Represents a player in the game of tic-tac-toe.
 */
public enum Player {
  X("X"), O("O");

  private final String disp;

  private Player(String display) {
    this.disp = display;
  }

  @Override
  public String toString() {
    return disp;
  }
}

