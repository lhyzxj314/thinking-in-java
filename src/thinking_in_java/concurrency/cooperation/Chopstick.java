package thinking_in_java.concurrency.cooperation;

/*
 * 哲学家进餐问题用到的筷子类
 */
public class Chopstick {
	private boolean taken = false;
	
	public synchronized void take() throws InterruptedException {
		while (taken)
			wait();
		taken = true;
	}
	
	public synchronized void drop() {
		taken = false;
		notifyAll();
	}
}
