package thinking_in_java.string.try_catch.exception_restriction;

import thinking_in_java.string.try_catch.exception_restriction.exception.BaseballException;
import thinking_in_java.string.try_catch.exception_restriction.exception.Foul;
import thinking_in_java.string.try_catch.exception_restriction.exception.Strike;

public abstract class Inning {
  
  public Inning() throws BaseballException {
    
  }
  
  public void event() throws BaseballException {
    
  }
  
  public abstract void atBat() throws Strike, Foul;
  
  
  // 未抛任何异常
  protected void walk() {
    
  }
  
}
