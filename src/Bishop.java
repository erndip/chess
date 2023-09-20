
import java.util.ArrayList;

/**
 * The Bishop is a piece that can move any amount of squares in diagonal line. As a result of this,
 * a bishop may only move among the color of its starting square
 * This Bishop is worth 3 points.
 * 
 * @author ernie
 */
public class Bishop extends Piece{
  public static final int VALUE = 3;

  public Bishop(int xPos, int yPos, Player team) {
    super(xPos, yPos, team);
    this.setImage(Piece.getWindow().loadImage("images/"+team.getName()+"bishop.png"));
  }
 
  /**
   * Finds all available squares that the piece can move to. Modifies this object's
   * AvailableSquares ArrayList.
   */
  @Override
  public ArrayList<Square> findAvailableSquares(Board board) {
    ArrayList<Square> squares = new ArrayList<Square>();
    
    checkVector(board, 1,1, squares);    // Down-right
    checkVector(board, 1,-1, squares);   // Up-right
    checkVector(board, -1,1, squares);   // Down-left
    checkVector(board, -1,-1, squares);  // Up-left    
    
    return squares;
  }
}
