package thinking_in_java.string.try_catch.exception_restriction.test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ExplicitPairManager1 extends PairManager {
	private Lock lock = new ReentrantLock();
	
	@Override
	public synchronized void increment() {
		lock.lock();
		try {
			p.incrementX();
			p.incrementY();
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
			p.incrementX();
			p.incrementY();
			temp = getPair();
		} finally {
			lock.unlock();
		}
		store(temp);
	}
}

public class ExplicitCriticalSection {

	public static void main(String[] args) {
		CriticalSection.testApproaches(new ExplicitPairManager1(), new ExplicitPairManager2());
	}
}
