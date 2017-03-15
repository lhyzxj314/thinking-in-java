package thinking_in_java.string.try_catch.exception_restriction;

import thinking_in_java.string.try_catch.exception_restriction.exception.BaseballException;
import thinking_in_java.string.try_catch.exception_restriction.exception.Foul;
import thinking_in_java.string.try_catch.exception_restriction.exception.PopFoul;
import thinking_in_java.string.try_catch.exception_restriction.exception.RainedOut;
import thinking_in_java.string.try_catch.exception_restriction.exception.Strike;

/**
 * 测试：异常限制条件
 * @author xshrimp
 * 2016年11月17日
 */
public class StormyInning extends Inning implements Storm {

  // 1.必须声明出父类构造器异常(或异常的父类, 与普通方法相反:可接受子类异常)，且可在父类构造器基础增加新异常
  // 2.子类构造器抓不到父类构造器抛出的异常
  public StormyInning() 
      throws BaseballException, RainedOut {
    super();
  }
  public StormyInning(String s) 
      throws BaseballException, Foul {
    super();
  } 
  
  // 编译错误，重载方法抛了父类方法不存在的异常
  // 若允许编译，可能出现错误：调用Inning.walk()不需要处理任何异常，但是重载方法实际却抛出了异常（或：增加异常抛出种类）
  /* ! public void walk() 
   *       throws RainedOut {
    
  }*/
  
  // 编译错误，接口Storm中定义的event()方法"异常列表定义" 无法改变 Inning中event()的"异常列表定义" 
  /* ! public void event() 
   *      throws RainedOut {
    
  }*/
  
  // 合法:重载方法可选择不抛出任何异常(或：减少异常抛出种类)
  public void event() {
    
  }
  
  public void rainHard() 
      throws RainedOut {
    
  }
  
  // 合法：重载方法可减少异常抛出种类，且抛出的异常可以是父类异常的继承类(is-a关系，未新增异常种类)
  public void atBat() 
      throws PopFoul {
    
  }
  
  public static void main(String[] args) {
      try {
        StormyInning si = new StormyInning();
        si.atBat();
      } catch (PopFoul e) { // 此catch子句可删去，将该异常作为其父异常BaseballException处理
        e.printStackTrace();
      } catch (RainedOut e) {
        e.printStackTrace();
      } catch (BaseballException e) {
        e.printStackTrace();
      }
      
      try {
        Inning i = new StormyInning();
        i.atBat();
      } catch (Strike e) {
        e.printStackTrace();
      } catch (Foul e) {
        e.printStackTrace();
      } catch (RainedOut e) {
        e.printStackTrace();
      } catch (BaseballException e) {
        e.printStackTrace();
      }
      
  }

}
