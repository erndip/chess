import java.util.ArrayList;

/**
 * The Knight is a piece that moves in an L-shape (2 squares horizontally or vertically, then one
 * in a perpendicular direction. The Knight is the only piece that can jump over any piece.
 * The Knight is worth 3 points.
 * 
 * @author ernie
 *
 */
public class Knight extends Piece{
  public static final int VALUE = 3;

  public Knight(int xPos, int yPos, Player team) {
    super(xPos, yPos, team);
    this.setImage(Piece.getWindow().loadImage("images/"+team.getName()+"horse.png")); 
    
  }
  
  /**
   * Finds all available squares that the piece can move to. Modifies this object's
   * AvailableSquares ArrayList.
   */
  public ArrayList<Square> findAvailableSquares(Board board) {
    ArrayList<Square> squares = new ArrayList<>();
    
    checkSquare(board, 2, 1, squares);
    checkSquare(board, 2, -1, squares);
    
    checkSquare(board, -2, 1, squares);
    checkSquare(board, -2, -1, squares);
    
    checkSquare(board, 1, 2, squares);
    checkSquare(board, 1, -2, squares);
    
    checkSquare(board, -1, 2, squares);
    checkSquare(board, -1, -2, squares);
    
    return squares;
  }
}
