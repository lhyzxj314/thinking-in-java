package thinking_in_java.string.try_catch.exception_restriction;

import thinking_in_java.string.try_catch.exception_restriction.exception.RainedOut;

public interface Storm {
  
  public void event() throws RainedOut;
  
  public void rainHard() throws RainedOut;
  
}
