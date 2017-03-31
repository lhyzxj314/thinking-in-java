package thinking_in_java.concurrency.cooperation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FixedDiningPhilosophers {
	
	public static void main(String[] args) throws Exception {
		int ponder = 5;
		if (args.length > 0)
			ponder = Integer.parseInt(args[0]);
		int size = 5;
		if (args.length > 1)
			size = Integer.parseInt(args[1]);
		
		ExecutorService exec = Executors.newCachedThreadPool();
		Chopstick[] sticks = new Chopstick[size];
		for (int i = 0; i < size; i++)
			sticks[i] = new Chopstick();
		for (int i = 0; i < size; i++) {
			// 破坏环路等待条件，防止死锁
			if (i == size - 1)
				exec.execute(new Philosopher(sticks[0], sticks[i], i, ponder));
			else
				exec.execute(new Philosopher(sticks[i], sticks[i+1], i, ponder));
		}
		
		if (args.length == 3 && args[2].equals("timeout"))
			TimeUnit.SECONDS.sleep(5);
		else {
			System.out.println("按'回车键'退出");
			System.in.read();
		}
		exec.shutdownNow();
	}
}