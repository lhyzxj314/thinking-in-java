package thinking_in_java.concurrency.critical_section;

public class Pair {
  private int x;
  private int y;
  
  public Pair(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  public Pair() {
    this(0, 0);
  }
  
  public int getX() {
    return x;
  }
  
  public int getY() {
    return y;
  }
  
  public void incrementX() {
    x++;
  }
  
  public void incrementY() {
    y++;
  }
  
  @Override
  public String toString() {
    return "x: " + x + ", y: " + y;
  }
  
  
  public void checkState() {
    if (x != y)
      throw new PairValuesNotEqualException();
  }
  
  
  public class PairValuesNotEqualException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PairValuesNotEqualException() {
      super("Pair内x,y值不相等:" + Pair.this);
    }
  }
}
