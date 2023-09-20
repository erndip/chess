import java.util.ArrayList;

public class Pawn extends Piece{
  // Teams
  private static Player white = Piece.getWindow().white;
  private static Player black = Piece.getWindow().black;
  
  private boolean hasMoved;
  
  public static final int VALUE = 1;
  
  
  public Pawn(int xPos, int yPos, Player team) {
    super(xPos, yPos, team);
    this.setImage(Piece.getWindow().loadImage("images/"+team.getName()+"pawn.png")); 
    
    this.hasMoved = false;
    
  }
  
  @Override
  public ArrayList<Square> findAvailableSquares(Board board) {
    ArrayList<Square> squares = new ArrayList<>();
    
    int x = this.getXPos()/60;
    int y = this.getYPos()/60;
    Piece[][] grid = board.getGrid();
    
    // For white pawns
    if(this.getTeam().equals(white)) {
      // Bonus range on first move
      if(!hasMoved) {
        if(grid[x][y-2] == null) {
          this.addAvailableSquare(x, y-2);
        }
      }
      // Regular movement square
      if(grid[x][y-1] == null) {
        this.addAvailableSquare(x, y-1);
      }
      
      // Up-right attack square
      if((x<7) && grid[x+1][y-1] != null && !grid[x+1][y-1].getTeam().equals(white)) {
        this.addAvailableSquare(x+1, y-1);
      }
      // Up-left attack square
      if((x>0) && grid[x-1][y-1] != null && !grid[x-1][y-1].getTeam().equals(black)) {
        this.addAvailableSquare(x-1, y-1);
      }
    }
    
    // For black pawns
    if(this.getTeam().equals(black)) {
      // Bonus range on first move
      if(!hasMoved) {
        if(grid[x][y+2] == null) {
          this.addAvailableSquare(x, y+2);
        }
      }
      // Regular movement square
      if(grid[x][y+1] == null) {
        this.addAvailableSquare(x, y+1);
      }
      
      // Down-right attack square
      if((x<7) && grid[x+1][y+1] != null && !grid[x+1][y+1].getTeam().equals(white)) {
        this.addAvailableSquare(x+1, y+1);
      }
      // Down-left attack square
      if((x>0) && grid[x-1][y+1] != null && !grid[x-1][y+1].getTeam().equals(white)) {
        this.addAvailableSquare(x-1, y+1);
      }
    }
    return squares;
    
  }
  
  public ArrayList<Square> findAttackedSquares(Board board){
    ArrayList<Square> squares = new ArrayList<>();
    
    int x = this.getXPos()/60;
    int y = this.getYPos()/60;
    Piece[][] grid = board.getGrid();
    
    // For white pawns
    if(this.getTeam().equals(white)) {
      // Up-right attack square
      if((x<7) && grid[x+1][y-1] != null && !grid[x+1][y-1].getTeam().equals(white)) {
        this.addAvailableSquare(x+1, y-1);
      }
      // Up-left attack square
      if((x>0) && grid[x-1][y-1] != null && !grid[x-1][y-1].getTeam().equals(black)) {
        this.addAvailableSquare(x-1, y-1);
      }
    }
    
    // For black pawns
    if(this.getTeam().equals(black)) {
      // Down-right attack square
      if((x<7) && grid[x+1][y+1] != null && !grid[x+1][y+1].getTeam().equals(white)) {
        this.addAvailableSquare(x+1, y+1);
      }
      // Down-left attack square
      if((x>0) && grid[x-1][y+1] != null && !grid[x-1][y+1].getTeam().equals(white)) {
        this.addAvailableSquare(x-1, y+1);
      }
    }
    return squares;
  }
  
  @Override
  public void moveTo(Board board, int newXPos, int newYPos) {
    super.moveTo(board, newXPos, newYPos);
    // ---Begin new stuff---
    this.hasMoved = true;
    
    // Promote if at the end of the board
    if(this.getTeam().equals(white)) {
      if(newYPos == 0) {
        Queen newQueen = new Queen(newXPos, newYPos, black);
        board.getGrid()[newXPos][newYPos] = newQueen;
        board.getPieces().remove(this);
        board.getPieces().add(newQueen);
      }
      else board.getGrid()[newXPos][newYPos] = this;
    }
    
    // Promote if at the end of the board
    if(this.getTeam().equals(black)) {
      if(newYPos == 7) {
        Queen newQueen = new Queen(newXPos, newYPos, white);
        board.getGrid()[newXPos][newYPos] = newQueen;
        board.getPieces().remove(this);
        board.getPieces().add(newQueen);
      }
      else board.getGrid()[newXPos][newYPos] = this;
    }
    // ---End new stuff---
  }
  
}
