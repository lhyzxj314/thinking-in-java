package thinking_in_java.concurrency.critical_section;

class PairManager2 extends PairManager {
  
  // 用显式锁设置临界区域
  @Override
  public void increment() {
    Pair temp;
    lock.lock();
    try {
      pair.incrementX();
      pair.incrementY();
      temp = getPair();
    } finally {
      lock.unlock();
    }
    store(temp);
  }

  // sychronized实现
//  @Override
//  public void increment() {
//    Pair temp;
//    synchronized(this) {
//      pair.incrementX();
//      pair.incrementY();
//      temp = getPair();
//    }
//    store(temp);
//  }
}
