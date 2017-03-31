package thinking_in_java.concurrency.interrupt;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BlockedMutex {
	private Lock lock = new ReentrantLock();
	public BlockedMutex() {
		lock.lock();
	}
	
	public void f() {
		try {
			lock.lockInterruptibly();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
	}
}



public class Interrupting2 {

}
