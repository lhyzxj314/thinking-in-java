package thinking_in_java.concurrency.optimistic_lock;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ActiveObjectDemo {
	private ExecutorService ex = Executors.newSingleThreadExecutor();
	private Random rand = new Random(47);

	public Future<Integer> caculateInt(final int x, final int y) {
		return ex.submit(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				System.out.println("开始计算 " + x + "+" + y);
				pause(500);
				return x + y;
			}
		});
	}

	public Future<Float> caculateFloat(final float x, final float y) {
		return ex.submit(new Callable<Float>() {

			@Override
			public Float call() throws Exception {
				System.out.println("开始计算 " + x + "+" + y);
				pause(2000);
				return x + y;
			}
		});
	}

	public void shutdown() {
		ex.shutdown();
	}

	// 模拟计算时间
	private void pause(int factor) {
		try {
			TimeUnit.MILLISECONDS.sleep(100 + rand.nextInt(factor));
		} catch (InterruptedException e) {
			System.out.println("sleep() 被interrupted了");
		}
	}

	public static void main(String[] args) {
		ActiveObjectDemo d1 = new ActiveObjectDemo();
		List<Future<?>> results = new CopyOnWriteArrayList<Future<?>>();
		for (float f = 0.0f; f < 1.0f; f += 0.2f)
			results.add(d1.caculateFloat(f, f));
		for (int i = 0; i < 5; i++)
			results.add(d1.caculateInt(i, i));
		System.out.println("已执行完所有的异步调用");
		while (results.size() > 0) {
			for (Future<?> f : results)
				if (f.isDone()) {
					try {
						System.out.println(f.get());
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
					results.remove(f);
				}
		}
		d1.shutdown();
	}
}
