package thinking_in_java.string.try_catch.constructors;

public class FailingConstructor {
  
  private int i = 2;
  
  private DisposalClass member1;
  private DisposalClass member2;
  
  FailingConstructor() throws NullPointerException {
    try {
      member1 = new DisposalClass();
      if (i == 2) {
        throw new NullPointerException();
      }
      
      try {
        member2 = new DisposalClass(); 
      } finally {
        member2.dispose();
      }
      
    } finally {
      member1.dispose();
    }
  }
  
  void dispose() {
    System.out.println("FailngConstructor is disposed");
  }
  
  public static void main(String[] args) {
    try {
      FailingConstructor fc = new FailingConstructor();
      try {
        System.out.println("use FailingConstructor to do something");
      } finally {
        fc.dispose();
      }
    } catch (NullPointerException e) {
      System.out.println("Handle the exception");
    }
  } 
  
}
