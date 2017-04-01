package thinking_in_java.concurrency.cooperation.lib_component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/*
 * 测试object pool的任务
 */
class CheckoutTask<T> implements Runnable {
  private static int count = 0;
  private final int id = count++;
  private Pool<T> pool;

  public CheckoutTask(Pool<T> p) {
    pool = p;
  }

  @Override
  public void run() {

    try {
      T item = pool.checkOut();
      System.out.println("checked out " + item);
      TimeUnit.SECONDS.sleep(1);
      System.out.println("checked in " + item);
      pool.checkIn(item);
    } catch (InterruptedException e) {
      // 合法退出方式
    }
  }
  
  @Override
  public String toString() {
    return "CheckoutTask " + id + " ";
  }

}

public class SemaphoreDemo {
  final static int SIZE = 25;
  
  public static void main(String[] args) throws Exception {
    final Pool<Fat> pool = new Pool<Fat>(Fat.class, SIZE);
    ExecutorService exec = Executors.newCachedThreadPool();
    for (int i = 0; i < SIZE; i++) {
      exec.execute(new CheckoutTask<Fat>(pool));
    }
    System.out.println("所有CheckoutTask创建完毕");
    
    List<Fat> list = new ArrayList<Fat>();
    for (int i = 0; i < SIZE; i++) {
      Fat t = pool.checkOut();
      System.out.println(i + ": main() thread checked out");
      t.operation();
    }
    Future<?> blocked = exec.submit(new Runnable() {
      
      public void run() {
        try {
          pool.checkOut();
        } catch (InterruptedException e) {
          System.out.println("checkOut()被interrupte");
        }
      }
      
    }); 
    TimeUnit.SECONDS.sleep(3);
    blocked.cancel(true);
    
    System.out.println("checking in 在" + list + "中的对象");
    for (Fat f : list)
      pool.checkIn(f);
    // 重复的check in会被忽略
    for (Fat f : list)
      pool.checkIn(f);
    exec.shutdown();
  }

}
