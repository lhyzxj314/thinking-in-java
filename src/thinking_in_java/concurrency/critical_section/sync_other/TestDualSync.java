package thinking_in_java.concurrency.critical_section.sync_other;

public class TestDualSync {
	// f()方法和g()方法从不同对象获取锁，起不到同步作用
	public static void main(String[] args) {
		final DualSynch ds = new DualSynch();
		new Thread() {
			public void run() {
				ds.f();
			}
		}.start();
		ds.g();
	}
}
