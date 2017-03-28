package thinking_in_java.concurrency;

import java.util.concurrent.TimeUnit;

/*
 * interrupt一个线程的通用模版
 */
class NeedCleanup {
	private final int id;
	public NeedCleanup(int ident) {
		this.id = ident;
		System.out.println("NeedCleanup " + id);
	}
	
	public void cleanup() {
		System.out.println("Cleaning up " + id);
	}
}

class Blocked3 implements Runnable {
	private volatile double d = 0.0;
	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				// point1
				NeedCleanup n1 = new NeedCleanup(1);
				// 在定义NeedCleanup后立马开启一个try-catch块，保证n1的资源正确释放
				try {
					System.out.println("Sleeping");
					TimeUnit.SECONDS.sleep(1);
					// point2
					NeedCleanup n2 = new NeedCleanup(2);
					// 保证n2的资源正确释放
					try {
						System.out.println("正在执行耗时计算任务");
						// 耗时的，非阻塞计算任务
						for (int i = 1; i < 250000; i++) {
							d = d + (Math.PI + Math.E) / d;
						}
						System.out.println("完成了耗时的计算任务");
					} finally {
						n2.cleanup();
					}
				} finally {
					n1.cleanup();
				}
			}
		} catch(InterruptedException e) {
			System.out.println("因为InterruptedException退出");
		}
	}
}

public class InterruptingIdiom {
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.out.println("正确用法： java InterruptingIdion [delay-time]");
			System.exit(1);
		}
		Thread t = new Thread(new Blocked3());
		t.start();
		TimeUnit.MILLISECONDS.sleep(new Integer(args[0]));
		t.interrupt();
	}
}
