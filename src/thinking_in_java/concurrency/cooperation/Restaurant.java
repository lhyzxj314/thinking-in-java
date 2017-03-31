package thinking_in_java.concurrency.cooperation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
 * 消费者-生产者
 */
class Meal {
	private final int orderNum;
	
	public Meal(int orderNum) {
		this.orderNum = orderNum;
	}
	
	public String toString() {
		return "Meal " + orderNum;
	}
}

class WaitPerson implements Runnable {
	private Restaurant restaurant;
	
	public WaitPerson(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				synchronized(this) {
					while (restaurant.meal == null) {
						wait();
					}
				}
				
				TimeUnit.MILLISECONDS.sleep(500);
				System.out.println("消费者获得：" + restaurant.meal);
				synchronized(restaurant.chef) {
					restaurant.meal = null;
					restaurant.chef.notify();
				}
			}
		} catch(InterruptedException e) {
			System.out.println("消费者任务被interrupt了");
		}
	}
}

class Chef implements Runnable {
	private Restaurant restaurant;
	private int count = 0;
	
	public Chef(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				synchronized(this) {
					while (restaurant.meal != null) {
						wait();
					}
				}
				
				if (++count == 10) {
					System.out.println("生产完毕，准备关闭...");
					restaurant.exec.shutdownNow();
				}
				
				System.out.println("生产者进行生产！");
				synchronized(restaurant.waitPerson) {
					restaurant.meal = new Meal(count);
					TimeUnit.MILLISECONDS.sleep(500);
					restaurant.waitPerson.notify();
				}
			}
		} catch(InterruptedException e) {
			System.out.println("生产者任务被interrupt了");
		}
	}
}

public class Restaurant {
	Meal meal = null;
	Chef chef = new Chef(this);
	WaitPerson waitPerson = new WaitPerson(this);
	ExecutorService exec = Executors.newCachedThreadPool();
	
	public Restaurant() {
		exec.execute(chef);
		exec.execute(waitPerson);
	}
	
	public static void main(String[] args) {
		new Restaurant();
	}
}
