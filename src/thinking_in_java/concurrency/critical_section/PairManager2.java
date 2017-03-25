package thinking_in_java.concurrency.critical_section;

class PairManager2 extends PairManager {

  // 用锁产生临界区域
  @Override
  public void increment() {
    synchronized(this) {
      pair.incrementX();
      pair.incrementY();
    }
    store(pair);
  }

}
