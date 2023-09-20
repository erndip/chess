import java.util.ArrayList;

/**
 * The Rook is a piece that can move any amount of squares in a straight line (horizontally
 * or vertically).
 * The Rook is worth 5 points.
 * 
 * @author ernie
 *
 */
public class Rook extends Piece{
  public static final int VALUE = 5;

  public Rook(int xPos, int yPos, Player team) {
    super(xPos, yPos, team);
    this.setImage(Piece.getWindow().loadImage("images/"+team.getName()+"rook.png")); 
    
  }
  
  
  /**
   * Finds all available squares that the piece can move to. Modifies this object's
   * AvailableSquares ArrayList.
   */
  @Override
  public ArrayList<Square> findAvailableSquares(Board board) {  
    ArrayList<Square> squares = new ArrayList<>();
    
    checkVector(board, 1, 0, squares); // Right
    checkVector(board, -1, 0, squares); // Left
    checkVector(board, 0, 1, squares); // Down
    checkVector(board, 0, -1, squares); // Up
    
    return squares;
  }
  
  

  
}
