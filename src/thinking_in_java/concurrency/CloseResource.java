package thinking_in_java.concurrency;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
 * 通过释放IO资源来interrupt阻塞线程
 */
public class CloseResource {
	public static void main(String[] args) throws Exception {
		ExecutorService exec = Executors.newCachedThreadPool();
		ServerSocket server = new ServerSocket(8080);
		InputStream socketInput = new Socket("localhost", 8080).getInputStream();
		exec.execute(new IOBlocked(socketInput));
		exec.execute(new IOBlocked(System.in));
		
		// interrupt()关闭不了线程
		TimeUnit.MICROSECONDS.sleep(100);
		System.out.println("正在关闭所有线程");
		exec.shutdownNow();
		
		TimeUnit.SECONDS.sleep(1);
		System.out.println("正在关闭" + socketInput.getClass().getSimpleName());
		socketInput.close(); // 释放阻塞线程的IO资源
		
		TimeUnit.SECONDS.sleep(1);
		System.out.println("正在关闭" + System.in.getClass().getSimpleName());
		System.in.close();   // 释放阻塞线程的IO资源????????
		
	}
}
