package thinking_in_java.concurrency.critical_section;

class PairManager1 extends PairManager {

  // 给整个方法加锁
  @Override
  public synchronized void increment() {
    pair.incrementX();
    pair.incrementY();
    store(pair);
  }

}
