package thinking_in_java.concurrency.cooperation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
 * 基础的线程协作方法
 */
class Car {
	private boolean waxOn = false;
	
	/*void getKey() {
		try {
			new BufferedReader(
					new InputStreamReader(System.in)).readLine();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	void getKey(String msg) {
		System.out.println(msg);
		getKey();
	}*/
	
	public synchronized void waxed() {
		waxOn = true;
		notify();
		//System.out.println("notify() in waxed");
		//getKey();
	}
	
	public synchronized void buffed() {
		waxOn = false;
		notify();
		//System.out.println("notify() in buffed");
		//getKey();
	}
	
	public synchronized void waitForWaxing() throws InterruptedException {
		while (waxOn == false)
			wait();
	}
	
	public synchronized void waitForBuffing() throws InterruptedException {
		while (waxOn == true)
			wait();
	}
}

class WaxOnTask implements Runnable {
	private Car car;
	
	public WaxOnTask(Car c) {
		car = c;
	}
	
	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				System.out.println("Wax On! ");
				TimeUnit.MILLISECONDS.sleep(200);
				car.waxed();
				car.waitForBuffing();
			}
		} catch(InterruptedException e) {
			System.out.println("因为InterruptException退出");
		}
		System.out.println("退出Wax On任务");
	}
	
}

class WaxOffTask implements Runnable {
	private Car car;
	
	public WaxOffTask(Car c) {
		car = c;
	}
	
	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				car.waitForWaxing();
				System.out.println("Wax Off! ");
				TimeUnit.MILLISECONDS.sleep(200);
				car.buffed();
			}
		} catch(InterruptedException e) {
			System.out.println("因为InterruptException退出");
		}
		System.out.println("退出Wax Off任务");
	}
	
}

public class WaxOMatic {
	public static void main(String[] args) throws InterruptedException {
		Car car = new Car();
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new WaxOnTask(car));
		exec.execute(new WaxOffTask(car));
		TimeUnit.SECONDS.sleep(5);
		exec.shutdownNow();
	}
	
}
