package thinking_in_java.concurrency.critical_section;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

abstract class PairManager {
  AtomicInteger checkCounter = new AtomicInteger(0);
  protected Pair pair = new Pair();
  private List<Pair> storage = Collections.synchronizedList(new ArrayList<Pair>());

  public synchronized Pair getPair() {
    return new Pair(pair.getX(), pair.getY());
  }

  /**
   * 模拟耗时操作
   * @param p
   */
  protected void store(Pair p) {
    storage.add(p);
    try {
      TimeUnit.MILLISECONDS.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  
  public abstract void increment();

}
