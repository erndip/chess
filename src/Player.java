import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

public class Player {
  private String name;
  private King king;
  private ArrayList<Piece> pieces;
  private Player opposingPlayer;
  
  private ArrayList<Square> controlledSquares;
  private ArrayList<Square> checkVectors;
  
  private Board board;
  int numChecks;
  
  public Player(String name, Board board) {
    this.name = name;
    pieces = new ArrayList<Piece>();
    checkVectors = new ArrayList<Square>();
    controlledSquares = new ArrayList<Square>();
    
    this.board = board;
    
  }
  
  public int getNumChecks() {
    return this.numChecks;
  }
  
  public ArrayList<Square> getCheckVectors(){
    return this.checkVectors;
  }
  
  public void setOpposingTeam(Player player) {
    this.opposingPlayer = player;
  }
  
  public Player getOpposingPlayer() {
    return opposingPlayer;
  }
  
  // controlledSquares methods
  public void setControlledSquares() {
   for (int i = 0; i < pieces.size(); i++) {
     if (pieces.get(i) instanceof King) {
       controlledSquares.addAll(pieces.get(i).findAvailableSquares(board));
     }
     // For pawns, only add squares that are attacked, not just all moveable squares
     else if (pieces.get(i) instanceof Pawn) {
       Pawn piece = (Pawn) pieces.get(i);
       controlledSquares.addAll(piece.findAttackedSquares(board));
     }
     
     else {
       pieces.get(i).setAvailableSquares(board);
       controlledSquares.addAll(pieces.get(i).getAvailableSquares());
  
     }
   }   
  }
  public ArrayList<Square> getControlledSquares() {
    return this.controlledSquares;
  }
  public void addControlledSquares(ArrayList<Square> arrList) {
    controlledSquares.addAll(arrList);
  }
  public void clearControlledSquares() {
    this.controlledSquares.clear();
  }
  
  
 
  public ArrayList<Piece> getPieces(){
    return this.pieces;
  }
  
  public String getName() {
    return name;
  }
  
  // King methods
  public void setKing(King king) {
    this.king = king;
  }
  public King getKing() {
    return king;
  }
  
  public boolean equals(Player team) {
    return name.equals(team.getName());
  }
  
  public void addPiece(Piece piece) {
    pieces.add(piece);
  }
  
  // Determine whether it's checked
  public void checkForCheck() {
    numChecks = 0;
    
    checkVectors = new ArrayList<Square>();
    
    ArrayList<Piece> oppPieces = opposingPlayer.getPieces();
    
    opposingPlayer.clearControlledSquares();
    
    for (int i = 0; i < oppPieces.size(); i++) {
      
      Piece piece = oppPieces.get(i);
      piece.setAvailableSquares(board);
      ArrayList<Square> squares = piece.getAvailableSquares();
      opposingPlayer.addControlledSquares(squares);
      
      
      // -- DEBUG --
      /*System.out.println(piece+" "+piece.getTeam().getName());
      for(int k = 0; k < squares.size(); k++) {
        System.out.println(squares.get(k));
      }*/
      // -- END DEBUG -- 
      
      for (int j = 0; j < squares.size(); j++) {
        Square square = squares.get(j);
        
        // Actually check for check
        if (square.getXPos() == king.getXPos()/60 && square.getYPos() == king.getYPos()/60) {
          king.setCheck(true);
          ArrayList<Square> checkVector = king.getCheckVector(board, piece);
          checkVectors.addAll(checkVector);
          numChecks++;
          break;
        }
        
      }  
    }
    
    
    }
  
  public void setCheck(boolean check) {
    this.king.setCheck(check);
  }  
 
}
