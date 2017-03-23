package thinking_in_java.concurrency.threadfactory;

import java.util.concurrent.TimeUnit;

public class TaskForThread implements Runnable {

	@Override
	public void run() {
		try {
			while (true) {
				TimeUnit.MILLISECONDS.sleep(100);
				System.out.println(Thread.currentThread() + " " + this + "  isDaemon:" + Thread.currentThread().isDaemon());
			}
		} catch (InterruptedException e) {
			System.out.println("Interrunpted");
		}
	}

}
