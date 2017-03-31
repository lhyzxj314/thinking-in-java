package thinking_in_java.concurrency.cooperation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
 * DelayQueue使用实例
 */
class DelayedTask implements Runnable, Delayed {
	private static int count = 0;
	private final int id = count++;
	private final int delta;
	private final long trigger;
	protected static List<DelayedTask> sequence = new ArrayList<DelayedTask>();
	
	public DelayedTask(int delayMillisec) {
		delta = delayMillisec;
		trigger = System.nanoTime() + TimeUnit.NANOSECONDS.convert(delta, TimeUnit.MILLISECONDS);
		sequence.add(this);
	}
	
	@Override
	public void run() {
		System.out.println(this + " ");
	}

	@Override
	public int compareTo(Delayed o) {
		DelayedTask that = (DelayedTask) o;
		if (this.trigger > that.trigger) return 1;
		if (this.trigger < that.trigger) return -1;
		return 0;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return
		unit.convert(trigger - System.nanoTime(), TimeUnit.NANOSECONDS);
	}
	
	@Override
	public String toString() {
		return "[" + delta + "] " + "Task " + id;
	}
	
	public String summary() {
		return "(" + id + ":" + delta + ")";
	}
	
	public static class EndSentinel extends DelayedTask {
		private ExecutorService exec;
		
		public EndSentinel(int delay, ExecutorService e) {
			super(delay);
			exec = e;
		}
		//@Override
		public void run() {
			for (DelayedTask pt : sequence) {
				System.out.println(pt.summary()); 
			}
			System.out.println(this + "执行shutdownNow()");
			exec.shutdownNow();
		}
	}
}

class DelayedTaskConsumer implements Runnable {
	private DelayQueue<DelayedTask> q;
	
	public DelayedTaskConsumer(DelayQueue<DelayedTask> queue) {
		q = queue;
	}
	
	@Override
	public void run() {
		try {
			while (!Thread.interrupted())
				q.take().run();
		} catch (InterruptedException e) {
		}
		System.out.println("DelayedTaskConsumer执行完毕");
	}
	
}

public class DelayQueueDemo {
	public static void main(String[] args) {
		Random rand = new Random(47);
		ExecutorService exec = Executors.newCachedThreadPool();
		DelayQueue<DelayedTask> queue = new DelayQueue<DelayedTask>();
		for (int i = 0; i < 20; i++) {
			queue.put(new DelayedTask(rand.nextInt(5000)));
		}
		queue.add(new DelayedTask.EndSentinel(5000, exec));
		
		exec.execute(new DelayedTaskConsumer(queue));
	}
}
