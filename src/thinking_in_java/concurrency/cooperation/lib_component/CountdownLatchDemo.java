package thinking_in_java.concurrency.cooperation.lib_component;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class TaskPortion implements Runnable {
	private static int counter = 0;
	private final int id = counter++;
	private static Random rand = new Random(47);
	private final CountDownLatch latch;
	
	TaskPortion(CountDownLatch latch) {
		this.latch = latch;
	}
	
	@Override
	public void run() {
		try {
			doWork();
			latch.countDown();
		} catch(InterruptedException e) { }
	}
	
	private void doWork() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(rand.nextInt(2000));
		System.out.println(this + "完成");
	}
	
	public String toString() {
		return "分任务" + id;
	}
}

class WaitPortion implements Runnable {
	private static int count = 0;
	private final int id = count++;
	private final CountDownLatch latch;
	
	WaitPortion(CountDownLatch latch) {
		this.latch = latch;
	}
	
	@Override
	public void run() {
		try {
			latch.await();
			System.out.println(this + "通过了Latch barrier");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		return "等待任务" + id;
	}
	
}

public class CountdownLatchDemo {
	static final int SIZE = 100;
	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		CountDownLatch latch = new CountDownLatch(SIZE);
		for (int i = 0; i < 10; i++)
			exec.execute(new WaitPortion(latch));
		for (int i = 0; i < SIZE; i++)
			exec.execute(new TaskPortion(latch));
		System.out.println("开启了所有任务");
		exec.shutdown();
	}
}
