package thinking_in_java.concurrency.catch_exception;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestUncaughtException {
	public static void main(String[] args) {
		// 使用线程工厂获取默认绑定了UncaughtExceptionHandler的线程
		ExecutorService es = Executors.newCachedThreadPool(new HandlerThreadFactory());
		es.execute(new ExceptionThread());
		
		// 设置默认handler
		Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler2());
		ExecutorService es2 = Executors.newCachedThreadPool();
		es2.execute(new Runnable(){

			@Override
			public void run() {
				throw new RuntimeException();
			}
			
		});
	}
}
