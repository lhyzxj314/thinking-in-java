package thinking_in_java.string.try_catch.exception_restriction.test2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Pair {
	private int x, y;
	
	public Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Pair() {
		this(0, 0);
	}
	
	public int getX() {return x;}
	public int getY() {return y;}
	public void incrementX() {x++;}
	public void incrementY() {y++;}
	
	@Override
	public String toString() {
		return "x: " + x + ", y: " + y;
	}
	
	public class PairValuesNotEqualException extends RuntimeException {
		public PairValuesNotEqualException() {
			super("Pair value not equal: " + Pair.this);
		}
	}
	
	public void checkState() {
		if(this.getX() != this.getY())
			throw new PairValuesNotEqualException();
	}
}

class PairChecker implements Runnable {
	  private PairManager pm;
	  
	  public PairChecker(PairManager pm) {
	    this.pm = pm;
	  }
	  
	  @Override
	  public void run() {
	    while (true) {
	      pm.checkCounter.incrementAndGet();
	      pm.getPair().checkState();
	    }
	  }

	
}

abstract class PairManager {
	AtomicInteger checkCounter = new AtomicInteger(0); 
	
	private List<Pair> storage = Collections.synchronizedList(new ArrayList<Pair>());
	
	protected Pair pair = new Pair();
	
	public synchronized Pair getPair() {
		return new Pair(pair.getX(), pair.getY());
	}
	
	public void store(Pair p) {
		storage.add(p);
		try {
			TimeUnit.MILLISECONDS.sleep(50);
		} catch (InterruptedException e) {}
	}
	
	public abstract void increment();
}

class PairManipulator implements Runnable{
	private PairManager pm;
	
	public PairManipulator(PairManager pm) {
		this.pm = pm;
	}
	
	public void run() {
		while(true)
			pm.increment();
	}
	
	public String toString() {
		return "Pair: " + pm.getPair() + " checkCounter = " + pm.checkCounter.get();
	}
}

class ExplicitPairManager1 extends PairManager {
	private Lock lock = new ReentrantLock();
	
	@Override
	public synchronized void increment() {
		lock.lock();
		try {
			pair.incrementX();
			pair.incrementY();
			store(getPair());
		} finally {
			lock.unlock();
		}
	}
}

class ExplicitPairManager2 extends PairManager {
	private Lock lock = new ReentrantLock();
	
	@Override
	public void increment() {
		lock.lock();
		Pair temp;
		try {
			pair.incrementX();
			pair.incrementY();
			temp = getPair();
		} finally {
			lock.unlock();
		}
		store(temp);
	}
}


public class CriticalSection {
	
	static void testApproaches(PairManager pman1, PairManager pman2) {
		ExecutorService exec = Executors.newCachedThreadPool();
		PairManipulator pm1 = new PairManipulator(pman1);
		PairManipulator pm2 = new PairManipulator(pman2);
		
		PairChecker pc1 = new PairChecker(pman1);
		PairChecker pc2 = new PairChecker(pman2);
		
		exec.submit(pm1);
		exec.submit(pm2);
		exec.submit(pc1);
		exec.submit(pc2);
		
		try {
			TimeUnit.MILLISECONDS.sleep(500);
		} catch (InterruptedException e) {System.out.println("睡眠中断！");}
		
		System.out.println("pm1: " + pm1 + ", pm2: " + pm2);
		System.exit(0);
	}

	public static void main(String[] args) {
		testApproaches(new ExplicitPairManager1(), new ExplicitPairManager2());
	}

}
