package thinking_in_java.concurrency.critical_section;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

abstract class PairManager {
  
  protected Lock lock = new ReentrantLock();
  
  AtomicInteger checkCounter = new AtomicInteger(0);
  protected Pair pair = new Pair();
  private List<Pair> storage = Collections.synchronizedList(new ArrayList<Pair>());

  // 显式锁方式
  public Pair getPair() {
    lock.lock();
    try {
      return new Pair(pair.getX(), pair.getY());
    } finally {
      lock.unlock();
    }
  }
  
  // synchronized版本
  /*public synchronized Pair getPair() {
    return new Pair(pair.getX(), pair.getY());
  }*/

  /**
   * 模拟耗时操作
   * @param p
   */
  protected void store(Pair p) {
    storage.add(p);
    try {
      TimeUnit.MILLISECONDS.sleep(50);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  
  public abstract void increment();

}
