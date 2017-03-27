package thinking_in_java.concurrency.catch_exception;

import java.util.concurrent.ThreadFactory;

public class HandlerThreadFactory implements ThreadFactory {
	@Override
	public Thread newThread(Runnable r) {
		System.out.println(this + " 正在创建新线程");
		Thread t = new Thread(r);
		System.out.println("创建了线程 " + t);
		t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
		System.out.println("exception handler = " + t.getUncaughtExceptionHandler());
		return t;
	}
}
