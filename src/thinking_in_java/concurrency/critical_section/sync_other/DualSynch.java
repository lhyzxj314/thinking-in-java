package thinking_in_java.concurrency.critical_section.sync_other;

public class DualSynch {

	private Object syncObj = new Object();

	public synchronized void f() {
		for (int i = 0; i < 100; i++) {
			System.out.println("f()");
			Thread.yield();
		}
	}

	// f()和g()使用相同对象的锁，可修复上述问题
	/*public void f() {
		synchronized (syncObj) {
			for (int i = 0; i < 100; i++) {
				System.out.println("f()");
				Thread.yield();
			}
		}
	}*/

	public void g() {
		synchronized (syncObj) {
			for (int i = 0; i < 100; i++) {
				System.out.println("g()");
				Thread.yield();
			}
		}
	}
}
