package thinking_in_java.concurrency.critical_section;

class PairManager1 extends PairManager {

  // 给整个方法加锁(显式锁方式)
  @Override
  public void increment() {
    lock.lock();
    try {
      pair.incrementX();
      pair.incrementY();
      store(getPair());
    } finally {
      lock.unlock();
    }
  }
  
  // 给整个方法加锁(synchronized方式)
 /* @Override
  public synchronized void increment() {
    pair.incrementX();
    pair.incrementY();
    store(getPair());
  }*/

}
