package thinking_in_java.concurrency.thread_local_storage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestThreadLocalStorage {
	public static void main(String[] args) throws Exception {
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 5; i++) {
			exec.execute(new Accessor(i));
		}
		TimeUnit.MILLISECONDS.sleep(50);
		exec.shutdownNow();
	}
}
