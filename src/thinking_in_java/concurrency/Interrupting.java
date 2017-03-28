package thinking_in_java.concurrency;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/*
 * 中断阻塞线程(3种阻塞情况)
 * @author xiaojun
 * @version 1.0.0
 * @date 2017年3月28日
 */
class SleepBlocked implements Runnable {

	@Override
	public void run() {
		try {
			TimeUnit.SECONDS.sleep(100);
		} catch(InterruptedException e) {
			System.out.println("InterruptedException");
		}
		System.out.println("正在退出 SleepBlocked.run()");
	}

}

class IOBlocked implements Runnable {
	private InputStream in;
	public IOBlocked(InputStream is) {
		in = is;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("正在等待 read()");
			in.read();
		} catch (IOException e) {
			if (Thread.currentThread().isInterrupted()) {
				System.out.println("Interrupt了I/O阻塞");
			} else {
				throw new RuntimeException(e);
			}
		}
		System.out.println("正在退出 IOBlocked.run()");
	}
}

class SynchronizedBlocked implements Runnable {

	public synchronized void f() {
		while (true) 
			Thread.yield();
	}
	
	public SynchronizedBlocked() {
		new Thread() {
			public void run() {
				f(); // 当前线程获取同步锁
			}
		}.start();
	}
	
	@Override
	public void run() {
		System.out.println("尝试调用 f()方法");
		f();
		System.out.println("正在退出 SynchronizedBlocked.run()");
	}
}

public class Interrupting {
	private static ExecutorService exec = Executors.newCachedThreadPool();
	
	static void test(Runnable r) throws InterruptedException {
		Future<?> f = exec.submit(r);
		TimeUnit.MILLISECONDS.sleep(100);
		System.out.println("正在interrupt " + r.getClass().getSimpleName());
		f.cancel(true);
		System.out.println("Interrupt信号已发送给" + r.getClass().getSimpleName());
	}
	
	public static void main(String[] args) throws Exception {
		test(new SleepBlocked());
		test(new IOBlocked(System.in));
		test(new SynchronizedBlocked());
		TimeUnit.SECONDS.sleep(3);
		System.out.println("正在使用System.exit(0)退出");
		System.exit(0);
	}
}



