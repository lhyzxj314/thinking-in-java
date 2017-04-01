package thinking_in_java.concurrency.cooperation.lib_component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

import thinking_in_java.concurrency.cooperation.LiftOff;

class LiftOffRunner implements Runnable {
	private BlockingQueue<LiftOff> queue;
	
	public LiftOffRunner(BlockingQueue<LiftOff> queue) {
		this.queue = queue;
	}
	
	public void add(LiftOff lo) {
		try {
			queue.put(lo);
		} catch (InterruptedException e) {
			System.out.println("在put()时被打断");
		}
	}
	
	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				LiftOff rocket = queue.take();
				rocket.run();
			}
		} catch(InterruptedException e) {
			System.out.println("在take()时被打断");
		}
		System.out.println("从LiftOffRunner退出");
	}
}

public class TestBlockingQueues {
	static void getKey() {
		try {
			new BufferedReader(
					new InputStreamReader(System.in)).readLine();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	static void getKey(String msg) {
		System.out.println(msg);
		getKey();
	}
	
	static void test(String msg, BlockingQueue<LiftOff> queue) {
		System.out.println(msg);
		LiftOffRunner runner = new LiftOffRunner(queue);
		Thread t = new Thread(runner);
		t.start();
		for (int i = 0; i < 5; i++) {
			runner.add(new LiftOff(5));
		}
		getKey("按回车终止对" + msg + "的测试");
		t.interrupt();
		System.out.println("对" + msg + "的测试结束");
	}
	
	public static void main(String[] args) {
		test("LinkedBlockingQueue", new LinkedBlockingQueue<LiftOff>());
		test("ArrayBlockingQueue", new ArrayBlockingQueue<LiftOff>(3));
		test("SynchronousQueue", new SynchronousQueue<LiftOff>());
	}

}
