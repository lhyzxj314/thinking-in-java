package thinking_in_java.concurrency.threadfactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestClient {
	public static void main(String[] args) throws Exception {
		// 使用线程工厂 为线程池每一个线程设定指定属性(如：设定deamon、优先级、名称)
		ExecutorService es = Executors.newCachedThreadPool(new DaemonThreadFactory());
		for (int i = 0; i < 10; i++) {
			es.submit(new TaskForThread());
		}
		System.out.println("所有Daemon线程已启动！");
		TimeUnit.MILLISECONDS.sleep(500);
	}
	
}
