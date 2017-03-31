package thinking_in_java.concurrency.cooperation;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Philosopher implements Runnable {
	public final int id;
	public final int ponderFactor;
	private Chopstick left;
	private Chopstick right;
	private Random rand = new Random(47);
	
	public Philosopher(Chopstick left, Chopstick right,
			int ident, int ponder) {
		this.left = left;
		this.right = right;
		ponderFactor = ponder;
		id = ident;
	}
	
	private void pause() throws InterruptedException {
		if (ponderFactor == 0) return;
		TimeUnit.MILLISECONDS.sleep(rand.nextInt(250 * ponderFactor));
	}
	
	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				System.out.println(this + "正在思考");
				pause();
				// 哲学家饿了
				System.out.println(this + "试图拿右手边的筷子");
				right.take();
				System.out.println(this + "试图拿左手边的筷子");
				left.take();
				System.out.println(this + "在吃意大利面");
				pause();
				right.drop();
				left.drop();
			}
		} catch(InterruptedException e) {
			System.out.println(this + " 因interrupt退出");
		}
	}
	
	public String toString() {
		return "哲学家" + id;
	}

}
