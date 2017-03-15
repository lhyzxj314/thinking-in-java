package thinking_in_java.string.try_catch.constructors;

public class DisposalClass {
  
  public static int count = 1;
  private final int id = count++; 
  
  public void dispose() {
    System.out.println("DisposalClass" + id + " disposed");
  }
}
