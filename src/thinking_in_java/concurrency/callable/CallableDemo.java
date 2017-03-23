package thinking_in_java.concurrency.callable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableDemo {
	/*public static void main(String[] args) throws Exception {
		Callable c = Executors.callable(new Runnable() {

			@Override
			public void run() {
				System.out.println(111);
			}
			
		});
		
		c.call();
		Thread.sleep(1000);
	}*/
	
	public static void main(String[] args) {
		ExecutorService es = Executors.newCachedThreadPool();
		List<Future<String>> list = new ArrayList<Future<String>>();
		for (int i = 0; i < 5; i++) {
			Future<String> fu = es.submit(new TaskWithoutResult(), new String("result" + i));
			list.add(fu);
		}
		
		for (Future<String> f : list) {
			try {
				System.out.println(f.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} finally {
				es.shutdown();
			}
		}
	}
}
