package thinking_in_java.concurrency.interrupt;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/*
 * interrupt一个阻塞的NIO channel
 *  
 * @author xiaojun
 * @version 1.0.0
 * @date 2017年3月28日
 */
class NIOBlocked implements Runnable {
	private final SocketChannel sc;
	
	public NIOBlocked(SocketChannel sc) {
		this.sc = sc;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("等待read()方法 in " + this);
			sc.read(ByteBuffer.allocate(1));
		} catch (ClosedByInterruptException e) {
			System.out.println("ClosedByInterruptException");
		} catch (AsynchronousCloseException e) {
			System.out.println("AsynchronousCloseException");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("正在退出 NIOBlocked.run() " + this);
	}
	
}

public class NIOInterruption {
	public static void main(String[] args) throws Exception {
		ExecutorService exec = Executors.newCachedThreadPool();
		ServerSocket server = new ServerSocket(8080);
		
		InetSocketAddress isa = new InetSocketAddress("localhost", 8080);
		SocketChannel sc1 = SocketChannel.open(isa);
		SocketChannel sc2 = SocketChannel.open(isa);
		
		Future<?> f = exec.submit(new NIOBlocked(sc1));
		exec.execute(new NIOBlocked(sc2));
		exec.shutdown();
		TimeUnit.SECONDS.sleep(1);
		
		// 通过cancel()方法调用interrupt
		f.cancel(true);
		TimeUnit.SECONDS.sleep(1);
		// 通过释放资源调用interrupt
		sc2.close();
	}
}
