package thinking_in_java.concurrency.share_resouces;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EvenChecker implements Runnable {
	private final int id;
	private IntGenerator generator;
	
	public EvenChecker(IntGenerator g, int ident) {
		this.generator = g;
		this.id = ident;
	}
	
	@Override
	public void run() {
		while (!generator.isCanceled()) {
			int val = generator.next();
			if (val % 2 != 0) {
				System.out.println(val + "不是偶数！");
				generator.cancel();
			}
		}
	}
	
	public static void test(IntGenerator g, int count) {
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < count; i++) {
			exec.execute(new EvenChecker(g, i));
		}
		exec.shutdown();
	}
	
	public static void test(IntGenerator g) {
		test(g, 10);
	}

}
