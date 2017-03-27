package thinking_in_java.concurrency.catch_exception;

import java.lang.Thread.UncaughtExceptionHandler;

public class MyUncaughtExceptionHandler implements UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.out.println("caught " + e);
	}

}
