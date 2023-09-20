import java.util.ArrayList;
import processing.core.*;

public class Board {  
  private Piece[][] grid;
  
  private ArrayList<Piece> pieces;
  
  private int width;
  private int height;
  
  public Board() {
    this.width = 8;
    this.height = 8;
    
    this.grid = new Piece[this.height][this.width];
    
    this.pieces = new ArrayList<>(); 
    
  }

  /**
   * @return the grid
   */
  public Piece[][] getGrid() { return grid; }

  /**
   * @return the pieces
   */
  public ArrayList<Piece> getPieces() { return pieces; }
  
  private void setPieces(ArrayList<Piece> pieces) {
    this.pieces = pieces;
  }

  /**
   * @return the width
   */
  public int getWidth() { return width; }

  /**
   * @return the height
   */
  public int getHeight() { return height; }

  /**
   * @param grid the grid to set
   */
  public void setGrid(Piece[][] grid) { this.grid = grid; }

  /**
   * Adds the reference to the Piece object to pieces ArrayList,
   * as well as to its appropriate position [x][y] on grid Pieces[][]
   * 
   * @param piece the piece to add
   */
  public void addPiece(Piece piece) {
    this.pieces.add(piece);
    grid[piece.getXPos()/60][piece.getYPos()/60] = piece;
    
    } 
}
