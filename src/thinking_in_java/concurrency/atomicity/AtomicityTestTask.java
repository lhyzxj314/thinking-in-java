package thinking_in_java.concurrency.atomicity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AtomicityTestTask implements Runnable {

	private int i = 0;
	
	public int getValue() {
		return i;      // 这是一个原子操作，但是并不线程安全(因会访问到处于不稳定中间态的数据)
	}
	// 加锁，修复并发问题
	/*public synchronized int getValue() {
		return i;
	}*/
	private synchronized void evenIncrement() {
		i++;
		i++;
	}
	
	@Override
	public void run() {
		while (true) {
			evenIncrement();
		}
	}
	
	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		AtomicityTestTask at = new AtomicityTestTask();
		exec.execute(at);
		while (true) {
			int val = at.getValue();
			if (val % 2 != 0) {
				System.out.println(val);
				System.exit(0);
			}
		}
	}
	
}
