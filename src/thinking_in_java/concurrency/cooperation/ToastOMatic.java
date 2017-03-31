package thinking_in_java.concurrency.cooperation;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import thinking_in_java.concurrency.cooperation.Toast.Status;

/*
 * 测试BlockingQueue
 */
class Toast {
	public enum Status{
		DRY, BUTTERED, JAMMED
	}
	private Status status = Status.DRY;
	private final int id;
	
	public Toast(int idn) {
		id = idn;
	}
	
	public void buffered() {
		status = Status.BUTTERED;
	}
	
	public void jam() {
		status = Status.JAMMED;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public int getId() {
		return id;
	}
	
	public String toString() {
		return "Toast " + id + ": " + status;
	}
}

class ToastQueue extends LinkedBlockingQueue<Toast> {
	private static final long serialVersionUID = 1L;
}

class Toaster implements Runnable {
	private ToastQueue toastQueue;
	private int count = 0;
	private Random random = new Random(47);
	
	public Toaster(ToastQueue tq) {
		toastQueue = tq;
	}
	
	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				TimeUnit.MILLISECONDS.sleep(random.nextInt(500));
				Toast t = new Toast(count++);
				System.out.println(t);
				toastQueue.put(t);
			}
		} catch(InterruptedException e) {
			System.out.println("Toaster被interrupt");
		}
		System.out.println("关闭Toaster");
	}
}

class Butter implements Runnable {
	private ToastQueue dryQueue;
	private ToastQueue butteredQueue;
	
	public Butter(ToastQueue dry, ToastQueue buttered) {
		dryQueue = dry;
		butteredQueue = buttered;
	}
	
	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				Toast t = dryQueue.take();
				t.buffered();
				System.out.println(t);
				butteredQueue.put(t);
			}
		} catch(InterruptedException e) {
			System.out.println("Butter被interrupt了");
		}
		System.out.println("关闭Butter");
	}
}

class Jammer implements Runnable {
	private ToastQueue butteredQueue;
	private ToastQueue finishedQueue;
	
	public Jammer(ToastQueue buttered, ToastQueue finished) {
		butteredQueue = buttered;
		finishedQueue = finished;
	}
	
	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				Toast t = butteredQueue.take();
				t.jam();
				System.out.println(t);
				finishedQueue.put(t);
			}
		} catch(InterruptedException e) {
			System.out.println("Jammer被interrupt了");
		}
		System.out.println("关闭Jammer");
	}
}

class Eater implements Runnable {
	private ToastQueue finishedQueue;
	private int count = 0;
	
	public Eater(ToastQueue finished) {
		finishedQueue = finished;
	}
	
	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				Toast t = finishedQueue.take();
				if (t.getId() != count++ && t.getStatus() != Status.JAMMED) {
					System.out.println(">>>>>错误：" + t);
					System.exit(1);
				} else {
					System.out.println("吃一个：" + t);
				}
			}
		} catch(InterruptedException e) {
			System.out.println("Eater被interrupt了");
		}
		System.out.println("关闭Eater");
	}
}

public class ToastOMatic {
	public static void main(String[] args) throws InterruptedException {
		ToastQueue dryQueue = new ToastQueue();
		ToastQueue bufferedQueue = new ToastQueue();
		ToastQueue jamedQueue = new ToastQueue();
		
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new Toaster(dryQueue));
		exec.execute(new Butter(dryQueue, bufferedQueue));
		exec.execute(new Jammer(bufferedQueue, jamedQueue));
		exec.execute(new Eater(jamedQueue));
		
		// 运行5秒钟
		TimeUnit.SECONDS.sleep(5);
		exec.shutdownNow();
	}
}
