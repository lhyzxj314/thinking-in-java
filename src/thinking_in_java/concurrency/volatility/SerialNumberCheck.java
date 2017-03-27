package thinking_in_java.concurrency.volatility;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SerialNumberCheck {
	private static final int SIZE = 10;
	private static CircularSet set = new CircularSet(1000);
	private static ExecutorService es = Executors.newCachedThreadPool();
	
	static class SerialChecker implements Runnable {

		@Override
		public void run() {
			while (true) {
				int serial = SerialNumberGenerator.nextSerialNumber();
				if (set.contains(serial)) {
					System.out.println("重复的数：" + serial);
					System.exit(0);
				}
				set.add(serial);
			}
		}
		
	}
	
	public static void main(String[] args) {
		// 开10个任务同时使用serialGenerator产生序列
		for (int i = 0; i < SIZE; i++) {
			es.submit(new SerialChecker());
		}
	}
}
