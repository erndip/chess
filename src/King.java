import java.util.ArrayList;

/**
 * The King is a piece that can move one square in any direction (horizontall, vertically, or
 * diagnoally). When one player's King is in checkmate, the other player wins.
 * 
 * @author ernie
 */
public class King extends Piece {
  private boolean canCastle;
  private boolean isChecked;

  public King(int xPos, int yPos, Player team) {
    super(xPos, yPos, team);
    this.setImage(Piece.getWindow().loadImage("images/" + team.getName() + "king.png"));
    this.canCastle = true;
    this.isChecked = false;
    
    team.setKing(this);
  }

  /**
   * Setter for isChecked
   */
  public void setCheck(boolean check) {
    isChecked = check;
  }

  /**
   * Getter of isChecked
   */
  public boolean isChecked() {
    return isChecked;
  }

  /**
   * Finds all available squares that the piece can move to. Modifies this object's AvailableSquares
   * ArrayList.
   */
  @Override
  public ArrayList<Square> findAvailableSquares(Board board) {
    ArrayList<Square> squares = new ArrayList<>();
    
    // STRAIGHTS
    checkSquare(board, 1, 0, squares); // Right
    checkSquare(board, -1, 0, squares); // Left
    checkSquare(board, 0, 1, squares); // Down
    checkSquare(board, 0, -1, squares); // Up
    // DIAGONALS
    checkSquare(board, 1, 1, squares); // Right-up
    checkSquare(board, -1, 1, squares); // Left-up
    checkSquare(board, 1, -1, squares); // Right-down
    checkSquare(board, -1, -1, squares); // Left-down
    
    return squares;
    
  }
  
  
  
  @Override
  public void setAvailableSquares(Board board) {
    this.clearAvailableSquares();
    ArrayList<Square> possibleSquares = this.findAvailableSquares(board);
    
    /*
    // If its checked, do the thing
    if (isChecked) {
      ArrayList<Square> checkVectors = getTeam().getCheckVectors();
      System.out.println("Check Vectors: "+checkVectors);
      
      // If there are check vectors, cross-reference them.
      for (int i = 0; i < possibleSquares.size(); i++) {
        
        System.out.println("\nCurrent possibleSquare: " + possibleSquares.get(i));
        
        boolean isAttacked = false;
        // Check if the current possibleSquare is in checkVector
        for (int j = 0; j < checkVectors.size(); j++) {
          // If the square is in checkVector (i.e. the square is also checked), make sure that the
          // square won't be added to availableSquares
          if (checkVectors.get(j).equals(possibleSquares.get(i))) {
            isAttacked = true;
          }
        }
        
        // Check if the current possibleSquare coincides with any of the opponent's other
        // controlledSquares
        ArrayList<Square> attackedSquares = getTeam().getOpposingPlayer().getControlledSquares();
        for (int j = 0; j < attackedSquares.size(); j++) {
          // If the square is in the opponent's controlled squares, mark it as attacked so that
          // it won't be added to availabeSquares
          System.out.println("Current attackedSquare: " + attackedSquares.get(j));

          if (attackedSquares.get(j).equals(possibleSquares.get(i))) {
            isAttacked = true;
          }
        }
        
        
        // If we make it through checkVectors and the current square is still not attacked, add it
        // as an available square.
        if (!isAttacked) addAvailableSquare(possibleSquares.get(i));
      }
    }
    // If it's not checked, add all possibleSquares to availbleSquares
    else {
      addAllAvailableSquares(possibleSquares);
    }
    */
    
    for (int i = 0; i < possibleSquares.size(); i++) {
      
      System.out.println("\nCurrent possibleSquare: " + possibleSquares.get(i));

      boolean isAttacked = false; // Whether or not the current square is attacked by an opposing
                                  // piece
      // If the king is currently in check, check all check vectors
      if (isChecked) {
        ArrayList<Square> checkVectors = getTeam().getCheckVectors();
        // Check if the current possibleSquare is in checkVector
        for (int j = 0; j < checkVectors.size(); j++) {
          if (checkVectors.get(j).equals(possibleSquares.get(i))) {
            // If the square is in checkVector (i.e. the square is also checked), make sure that the
            // square won't be added to availableSquares
            isAttacked = true;
          }
        }        
      }
      // Check if the current possibleSquare coincides with any of the opponent's other
      // controlledSquares
      getTeam().getOpposingPlayer().setControlledSquares();
      ArrayList<Square> attackedSquares = getTeam().getOpposingPlayer().getControlledSquares();
      for (int j = 0; j < attackedSquares.size(); j++) {
        if (attackedSquares.get(j).equals(possibleSquares.get(i))) {
          // If the square is in checkVector (i.e. the square is also checked), make sure that the
          // square won't be added to availableSquares
          isAttacked = true;
        }
      }
      
      
      // If we make it through checkVectors and the current square is still not attacked, add it
      // as an available square.
      if (!isAttacked) addAvailableSquare(possibleSquares.get(i));
      
      
      
    }
    
    
    
    

  }
  
  /**
   * Utility method to find check vectors. Similar to Piece's checkVector method, but it x-rays the
   * King.
   * 
   * @param board board to get the grid from
   * @param piece piece that's checking the King
   * @param xDir x direction to look in (+ is right, - is left)
   * @param yDir y direction to look in (+ is down, - is up)
   * @return an ArrayList<int[]> of squares between the piece and the king.
   */
  public ArrayList<Square> findCheckVector(Board board, Piece piece, int xDir, int yDir){
    ArrayList<Square> vector = new ArrayList<>();
    
    int x = piece.getXPos()/60;
    int y = piece.getYPos()/60;
    
    Piece[][] grid = board.getGrid();

    boolean seenKing = false; // Tells whether or not we've passed the king
    
    for(int i = x+xDir, j=y+yDir; ((i>=0&&i<=7)&&(j>=0&&j<=7));i += xDir, j+= yDir) {
      System.out.println(grid[i][j]);
      if (grid[i][j] != null) {
        // If the piece is on the same team, break the loop
        if(grid[i][j].getTeam().equals(piece.getTeam())) {
          break;
        }
        // If the piece is a King (that is also on the opposing team), mark that we have seen the
        // King along this vector, and then all further Squares will have a special flag
        else if (grid[i][j] instanceof King) {
          seenKing = true;
          continue;
        }
        else break;
      }
      else {
        vector.add(new Square(i, j, seenKing));
      }
    }
    return vector;
  }
  
  /**
   * Get the the
   * @param board
   * @param piece
   * @return
   */
  public ArrayList<Square> getCheckVector(Board board, Piece piece){
    ArrayList<Square> vector = new ArrayList<>();
    
    int x = piece.getXPos()/60;
    int y = piece.getYPos()/60;
    
    int kingX = this.getXPos()/60;
    int kingY = this.getYPos()/60;
    
    Piece[][] grid = board.getGrid();
    
    vector.add(new Square(x, y));

    // Find the direction of the attack vector
    try {
      float ratio = ((x-kingX)/(y-kingY));
            
      // For knights, only add the knight's position
      if (Math.abs(ratio) == 0.5 || Math.abs(ratio) == 2) {
        return vector;
      }
      
      // For diagonals:
      // For down-right or up-left
      if (ratio == 1) {
        // If the piece is to the right of the king
        if(x < kingX) { 
          vector.addAll(findCheckVector(board, piece, 1, 1));  
        }
        // If the piece is to the left of the king
        else {
          vector.addAll(findCheckVector(board, piece, -1, -1));
        }
        return vector;
      }
      
      // For up-right or down-left
      if (ratio == -1) {
        // If the piece is to the right of the king
        if(x > kingX) { 
          vector.addAll(findCheckVector(board, piece, -1, 1));  
        }
        // If the piece is to the left of the king
        else {
          vector.addAll(findCheckVector(board, piece, 1, -1));
        }
        return vector;
      }
      
      // For straight-aways
      // For vertical 
      if (ratio == 0) {
        // If the piece is above the king
        if(y < kingY) { 
          vector.addAll(findCheckVector(board, piece, 0, 1));  
        }
        // If the piece is below the king
        else {
          vector.addAll(findCheckVector(board, piece, 0, -1));
        }
        return vector;
      }
   
    }
    // If the ratio causes division by 0, i.e. the vector is horizontal
    catch (ArithmeticException e) {
   // If the piece is to the left of the king
      if(x < kingX) {
        vector.addAll(findCheckVector(board, piece, 1, 0));  
      }
      // If the piece is to the right of the king
      else {
        vector.addAll(findCheckVector(board, piece, -1, 0));
      }
      return vector;
    }
    return vector;
  }
  
  
  
  
  
}
