package thinking_in_java.concurrency.cooperation.lib_component;

/*
 * 模拟构造消耗很大的对象
 */
public class Fat {
  private volatile double d; // 限制编译器优化
  private static int count = 0;
  private final int id = count++;
  
  public Fat() {
    for (int i = 1; i < 10000; i++) {
      d += (Math.E + Math.PI) / (double) i;
    }
  }
  
  public void operation() {
    System.out.println(this);
  }
  
  @Override
  public String toString() {
    return "Fat id： " + id;
  }
}
