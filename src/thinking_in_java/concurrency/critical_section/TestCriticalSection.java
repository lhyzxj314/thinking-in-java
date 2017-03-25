package thinking_in_java.concurrency.critical_section;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestCriticalSection {
  
  static void testApproach(PairManager pman1, PairManager pman2) {
    ExecutorService exec = Executors.newCachedThreadPool();
    PairManipulator pm1 = new PairManipulator(pman1);
    PairManipulator pm2 = new PairManipulator(pman2);
    
    PairChecker pc1 = new PairChecker(pman1);
    PairChecker pc2 = new PairChecker(pman2);
    
    exec.execute(pm1);
    exec.execute(pm2);
    exec.execute(pc1);
    exec.execute(pc2);
    
    try {
      TimeUnit.MILLISECONDS.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("pm1:   " + pm1);
    System.out.println("pm2:   " + pm2);
    System.exit(0);
  }
  
  public static void main(String[] args) {
    PairManager
      pman1 = new PairManager1(),
      pman2 = new PairManager2();
    
    testApproach(pman1, pman2);
  }
}
