package thinking_in_java.concurrency.optimistic_lock;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * 测试乐观锁 
 * @author xiaojun
 * @version 1.0.0
 * @date 2017年4月5日
 */
public class FastSimilation {
	static final int N_ELEMENTS = 10000;
	static final int N_GENS = 30;
	static final int N_EVOLVERS = 50;
	static final AtomicInteger[][] GRID = new AtomicInteger[N_ELEMENTS][N_GENS];
	static Random rand = new Random(47);
	
	static class Evolver implements Runnable {

		@Override
		public void run() {
			while (!Thread.interrupted()) {
				int element = rand.nextInt(N_ELEMENTS);
				for (int i = 0; i < N_GENS; i++) {
					int previous = element - 1;
					if (previous < 0)
						previous = N_ELEMENTS - 1;
					int next = element + 1;
					if (next >= N_ELEMENTS)
						next = 0;
					int oldValue = GRID[element][i].get();
					int newValue = (oldValue + GRID[previous][i].get() + GRID[next][i].get()) / 3;
					if (!GRID[element][i].compareAndSet(oldValue, newValue)) {
						System.out.println("原始值: " + oldValue + " 已被修改");
					}
				}
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newCachedThreadPool();
		
		for (int i = 0; i < N_ELEMENTS; i++)
			for (int j = 0; j < N_GENS; j++)
				GRID[i][j] = new AtomicInteger(rand.nextInt(1000));
		
		for (int i = 0; i < N_EVOLVERS; i++)
			exec.execute(new Evolver());
		
		TimeUnit.SECONDS.sleep(5);
		exec.shutdownNow();
	}
}
