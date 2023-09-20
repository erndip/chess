

public class Square {
  // Fields
  public int x; // X position
  public int y; // Y position
  public boolean flag; // flag to mark if its specieal
  
  // Constructors
  public Square(int x , int y, boolean flag) {
    this.x = x;
    this.y = y;
    this.flag = flag;
  }
  
  public Square(int x, int y) {
    this(x,y, false);
  }
  
  // Getters
  public int getXPos() {
    return this.x;
  }
  
  public int getYPos() {
    return this.y;
  }
  
  public boolean getFlag() {
    return this.flag;
  }
  
  public String getSig() {
    return Integer.toString(x)+Integer.toString(y);
  }
  
  public boolean equals(Square sq) {
    // Returns true if the x and y coordinates match.
    return (this.x == sq.getXPos() && this.y == sq.getYPos());
  }
  
  @Override
  public String toString() {
    String strOut = "("+x+","+y+")" + ((flag) ? " SPEC FLAG" : "");
    return strOut;
  }
  
}
