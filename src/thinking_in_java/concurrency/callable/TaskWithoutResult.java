package thinking_in_java.concurrency.callable;

import java.util.concurrent.TimeUnit;

public class TaskWithoutResult implements Runnable {
	protected int countDown = 10;
	private static int taskCount = 0;
	private final int id = taskCount++; // 任务id
	
	public String status() {
		return "#" + id + "(" + 
				(countDown > 0 ? countDown : "结束!") + "),";
	}
	
	@Override
	public void run() {
		while (countDown-- > 0) {
			System.out.println(status());
		}
		try {
			TimeUnit.MINUTES.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
