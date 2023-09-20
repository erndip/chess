import java.util.ArrayList;
import processing.core.*;

public class Chess extends PApplet {
  PImage bg;
  ArrayList<Piece> pieces;
  Board board;
  Piece[][] grid;
  Piece selectedPiece;
  
  Player turnTeam;
  
  public Player white;
  public Player black;
  
  final int highlightColor = color(0,200,0,80);
  final int selectorColor =  color(200,200,200,80);
  final int checkColor =     color(200, 0, 0, 180);
  
  /**
   * This method creates Piece objects for all of the pieces in a standard chess game.
   * Calls board.addPiece(), which adds it to the pieces ArrayList, as well as to the
   * grid array.
   */
  public void setupPieces() {
    // Rooks
    board.addPiece(new Rook(0,0, black));
    board.addPiece(new Rook(7,0, black));
    board.addPiece(new Rook(0,7, white));
    board.addPiece(new Rook(7,7, white));
    // Knights
    board.addPiece(new Knight(1,0, black));
    board.addPiece(new Knight(6,0, black));
    board.addPiece(new Knight(1,7, white));
    board.addPiece(new Knight(6,7, white));
    // Bishops
    board.addPiece(new Bishop(2,0, black));
    board.addPiece(new Bishop(5,0, black));
    board.addPiece(new Bishop(2,7, white));
    board.addPiece(new Bishop(5,7, white));
    // Kings
    board.addPiece(new King(4,0, black));
    board.addPiece(new King(4,7, white));
    // Queens
    board.addPiece(new Queen(3,0, black));
    board.addPiece(new Queen(3,7, white));
    
    // Black pawns
    for (int i = 0; i <= 7; i++) {
      board.addPiece(new Pawn(i,1,black));
    }
    // White pawns
    for (int i = 0; i <= 7; i++) {
      board.addPiece(new Pawn(i,6,white));
    }
    
    // Calculate available squares for each piece
    for (int i = 0; i < pieces.size(); i++) {
      pieces.get(i).setAvailableSquares(board);
    }
    
    
  }

  
  public void settings() {
    size(480,480);

  }
  
  public void setup() {
    // Declare all the variables
    bg = loadImage("images/chessboard.png");
    board = new Board();
    pieces = board.getPieces();
    grid = board.getGrid();
        
    // Set up teams
    white = new Player("white", board);
    black = new Player("black", board);
    
    white.setOpposingTeam(black);
    black.setOpposingTeam(white);
    
    turnTeam = white;
    // Initialize all the pieces
    Piece.setWindow(this);
    setupPieces();    
  }
  
  public void draw() {
    // Draw the background
    background(bg);
    
    // Draw every piece in pieces ArrayList
    for (int i = 0; i < pieces.size(); i++) {
      Piece piece = pieces.get(i);
      // Draw a the check square if the piece is a king that's in check
      if (piece instanceof King) {
        King king = (King) piece;
        if (king.isChecked()) {
          drawCheck(king.getXPos(), king.getYPos());
        }
      }
      // Draw the piece's sprite
      image(piece.getImage(), piece.getXPos(), piece.getYPos());
      
    }
    drawSelector();
    
    // If a piece is selected, highlight its available moves
    if(selectedPiece!=null) {
      // Highlight available moves
      drawAvailableSquares(selectedPiece);
      // Highlight the piece that is selected
      fill(color(0,200,0,50));
      noStroke();
      rect(selectedPiece.getXPos(),selectedPiece.getYPos(),60,60);
    }
    
    
  }
  
  public void mousePressed() {
    int x = mouseX / 60;
    int y = mouseY / 60;
    Piece square = grid[x][y];
    
    
    // Move the selected piece
    if(selectedPiece != null) {
      
      Square otherSquare = new Square(x, y);
      
      for (int i = 0; i < selectedPiece.getAvailableSquares().size(); i++) {
        Square pieceCoords = selectedPiece.getAvailableSquares().get(i);
        if(pieceCoords.equals(otherSquare)) {
          selectedPiece.moveTo(board, x, y);
          selectedPiece = null;
          // Swap turnTeam to the other team after the move
          turnTeam = (turnTeam.equals(white)) ? black : white;
          
          return;
        }
      }
      
    }
    
    // If the clicked square has a piece on it, select that piece
    if(square != null && square.getTeam().equals(turnTeam)) {
      selectPiece(square);
    }
    
    else {
      selectedPiece = null;
    }
  }
  /**
   * Draws a yellow box over the square that the mouse is over if there is a piece there.
   */
  public void drawSelector() {
    fill(selectorColor);
    noStroke();
    int x = mouseX / 60;
    int y = mouseY / 60;
    
    //System.out.println("("+x+","+y+")");
    
    Piece piece = grid[x][y];
    if(piece != null && piece.getTeam().equals(turnTeam)) {
      rect(60*x,60*y,60,60);
    }
    
  }
  public void drawCheck(int x, int y) {
    fill(checkColor);
    noStroke();
    rect(x,y,60,60);
  }
  
  /**
   * Draws the squares that piece can move to by calling setAvailableSquares for the piece.
   * 
   * @param piece the piece to show the squares of
   */
  public void drawAvailableSquares(Piece piece) {
    ArrayList<Square> availableSquares = piece.getAvailableSquares();
    int x, y;
    // Set graphic-related stuff
    fill(highlightColor);
    noStroke();
    
    for (int i = 0; i < availableSquares.size(); i++) {
      
      x = availableSquares.get(i).getXPos(); // x value stored in first index of coord array
      y = availableSquares.get(i).getYPos(); // y value stored in second index of coord array
      rect(60*x,60*y,60,60); // Actually display the square
      
    }
  }
  
  /**
   * Stores a reference to the piece to be selected and calculates its available moves
   * @param piece
   */
  public void selectPiece(Piece piece) {
    this.selectedPiece = piece;
    
    piece.setAvailableSquares(board);
    
  }
  
  
  public static void main(String[] args) {
    String appletArgs = "Chess";
    PApplet.main(appletArgs);
    
  }
  
}
