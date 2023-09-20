import java.util.ArrayList;

/**
 * The Queen is a piece that can move any number of spaces in a straight line horizontally,
 * vertically, or diagonally. It is the most powerful attacking piece.
 * The Queen is worth 9 points.
 * @author ernie
 *
 */
public class Queen extends Piece{
  public static final int VALUE = 9;

  public Queen(int xPos, int yPos, Player team) {
    super(xPos, yPos, team);
    this.setImage(Piece.getWindow().loadImage("images/"+team.getName()+"queen.png")); 
    
  }
  
  /**
   * Finds all available squares that the piece can move to. Modifies this object's
   * AvailableSquares ArrayList.
   */
  public ArrayList<Square> findAvailableSquares(Board board) {
    ArrayList<Square> squares = new ArrayList<>();
    
    // STRAIGHTS
    checkVector(board, 1, 0, squares); // Right
    checkVector(board, -1, 0, squares); // Left
    checkVector(board, 0, 1, squares); // Down
    checkVector(board, 0, -1, squares); // Up  
    // DIAGONALS
    checkVector(board, 1,1, squares);    // Down-right
    checkVector(board, 1,-1, squares);   // Up-right
    checkVector(board, -1,1, squares);   // Down-left
    checkVector(board, -1,-1, squares);  // Up-left  
    
    return squares;
  }
}
