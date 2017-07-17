package thinking_in_java.concurrency.interrupt;

import java.util.concurrent.TimeUnit;
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
			System.out.println("f()方法因尝试获取锁而产生的阻塞状态而中断了");
		}
	}
}

class Blocked2 implements Runnable {
	BlockedMutex blocked = new BlockedMutex();
	@Override
	public void run() {
		System.out.println("等待f()方法执行结束...");
		blocked.f();
		System.out.println("f()方法执行结束...");
	}
}

public class Interrupting2 {
	public static void main(String[] args) throws Exception {
		Thread t = new Thread(new Blocked2());
		t.start();
		TimeUnit.SECONDS.sleep(2);
		System.out.println("执行t.interrupt()方法...");
		t.interrupt();
	}
}
