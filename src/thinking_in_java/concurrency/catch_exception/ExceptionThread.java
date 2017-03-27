package thinking_in_java.concurrency.catch_exception;

public class ExceptionThread extends Thread {
	@Override
	public void run() {
		Thread t = Thread.currentThread();
		System.out.println("run()被线程" + t + "运行");
		System.out.println("exception handler = " + t.getUncaughtExceptionHandler());
		throw new RuntimeException();
	}
	
	
}
