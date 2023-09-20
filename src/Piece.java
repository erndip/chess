import processing.core.*;
import java.util.ArrayList;

public class Piece {  
  private int xPos;
  private int yPos;
  private PImage image;
  
  private Player team;
  private String imageString;
  private ArrayList<Square> availableSquares; 
      
  private static Chess window;

  
  //Constructor
  public Piece(int x, int y, Player team) {
    this.xPos = x * 60; // Value in should be an int on [0,7]
    this.yPos = y * 60; // Value in should be an int on [0,7]
    
    this.team = team; // Value should be either "white" or "black"
    this.availableSquares = new ArrayList<>(); // List of [x,y] arrays that represent the
                                               // coordinates of the squares that can be moved to
    team.addPiece(this);
   }
  
  /**
   * Set the PApplet to be used by all pieces
   */
  public static void setWindow(Chess window) {
    Piece.window = window;
  }
  
  public static Chess getWindow() {
    return Piece.window;
  }
  
  /**
   * @return the xPos
   */
  public int getXPos() {
    return xPos;
  }
  /**
   * @return the yPos
   */
  public int getYPos() {
    return yPos;
  }
  /**
   * @return the image
   */
  public PImage getImage() {
    return image;
  }
  /**
   * @return the team
   */
  public Player getTeam() {
    return team;
  }
  /**
   * @return the imageString
   */
  public String getImageString() {
    return imageString;
  }
  /**
   * @param xPos the xPos to set
   */
  public void setXPos(int xPos) {
    this.xPos = xPos;
  }
  /**
   * @param yPos the yPos to set
   */
  public void setYPos(int yPos) {
    this.yPos = yPos;
  }
  /**
   * @param imageString the imageString to set
   */
  public void setImageString(String imageString) {
    this.imageString = imageString;
  }
    
  // Setters/getters for availableSquares ArrayList
  public void addAvailableSquare(int x, int y) {
    this.availableSquares.add(new Square(x, y));
  }
  public void addAvailableSquare(Square square) {
    this.availableSquares.add(square);
  }
  public void addAllAvailableSquares(ArrayList<Square> squares) {
    this.availableSquares.addAll(squares);
  }

  
  
  public void clearAvailableSquares() {
    this.availableSquares.clear();
  }
  public ArrayList<Square> getAvailableSquares(){
    return this.availableSquares;
  }
  
  
  // Piece image initializer
  public void setImage(PImage image) {
    this.image = image;
  }
  
  /**
   * Method to set the position of the piece
   * Takes ez coordinate values on [0,7]
   * 
   * @param newXPos new x position, on [0,7]
   * @param newYPos new y position, on [0,7]
   */
  public void moveTo(Board board, int newXPos, int newYPos) {
    // Clear old square
    Piece[][] grid = board.getGrid();
    grid[this.xPos/60][this.yPos/60] = null;
    
    // Set new square
    this.xPos = newXPos*60;
    this.yPos = newYPos*60;
    
    // Remove from appropriate piece lists
    if (grid[newXPos][newYPos] != null) {
      team.getOpposingPlayer().getPieces().remove(grid[newXPos][newYPos]);
    }
    board.getPieces().remove(grid[newXPos][newYPos]);
    
    
    grid[newXPos][newYPos] = this;
    
    // Calculate the available squares for next move
    team.setControlledSquares();
    
    // Check if the opposing team is checked
    team.getOpposingPlayer().checkForCheck();
    
    // Clear this team's check
    // NOTE: this assumes that there are no bugs, i.e. that the only available moves for this piece
    // necessarily get its player out of check.
    team.getKing().setCheck(false);
    
  }
  
  /**
   * Appends all legal moves, represented as arrays of integer pairs,
   * with move[0] = x destination and move[1] = y destination
   * Because different pieces have different legal move patterns,
   * the default method definition is empty, and each child defines its
   * own setAvailableSquares() method.
   * 
   * @param board the board to get the grid from
   */
  public void setAvailableSquares(Board board) {
    this.clearAvailableSquares();
    ArrayList<Square> possibleSquares = this.findAvailableSquares(board);
    // If its checked, do the thing
    if (team.getKing().isChecked()) {
      ArrayList<Square> checkVectors = team.getCheckVectors();
      //System.out.println("Check vectors"+checkVectors);
      // If there are no legal moves for this piece
      if(checkVectors == null) {
        this.availableSquares = new ArrayList<Square>();
        //System.out.println("no legal moves");
        return;
        
      }
      // If there are legal moves, cross-reference them.
      else {
        if(team.getNumChecks() == 1) {
       // For every square in checkVector...
          for (int i = 0; i < checkVectors.size(); i++) {
            // If the square is marked with a special flag, continue since it isn't a legal blocking
            // move.
            if (checkVectors.get(i).getFlag()) continue;
            
            // compare it to every square in possibleSquares
            for (int j = 0; j < possibleSquares.size(); j++) {
              // Check whether this move would block the check vector
              if (possibleSquares.get(j).equals(checkVectors.get(i))) {
                this.availableSquares.add(checkVectors.get(i));
              }
            }
          }
        }
      }
    }
    
    else {
      this.availableSquares = possibleSquares;
    }
  }
  public ArrayList<Square> findAvailableSquares(Board board) {return null;}
  
  
  /**
   * Checks if the square at given coordinates is a valid square to move to and
   * adds it to the 
   * 
   * @param board
   * @param i x-position to be checked
   * @param j y-position to be checked
   * @return
   */
  public void checkSquare(Board board, int i, int j, ArrayList<Square> squares) {
    i += this.getXPos()/60;
    j += this.getYPos()/60;    
    // Check if the square is on the board
    if ((i<0||i>7)||(j<0||j>7)) return;   
    
    Piece square = board.getGrid()[i][j];
    // Actually check the square
    if(square==null || !square.getTeam().equals(this.getTeam())) {
      squares.add(new Square(i, j));
    }
    
  }
  
  /**
   * Checks in a straight line, from the piece (not including its own square)
   * until it hits the edge of the board or another piece. 
   *  
   * Checks in increments of |xDir| horizontally and |yDir| vertically
   * 
   * @param board the board to get the grid from
   * @param xDir >0 is right, >0 is left
   * @param yDir <0 is down, <0 is up
   */
  public void checkVector(Board board, int xDir, int yDir, ArrayList<Square> squares) {
    int x = this.getXPos()/60;
    int y = this.getYPos()/60;
    Piece[][] grid = board.getGrid();
    
    for(int i = x+xDir, j=y+yDir; ((i>=0&&i<=7)&&(j>=0&&j<=7));i += xDir, j+= yDir) {
      if (grid[i][j] != null) {
        if(grid[i][j].getTeam().equals(this.getTeam())) break;
        else squares.add(new Square(i, j));
        break;
      }
      else squares.add(new Square(i, j));
    }
  }
  
}
