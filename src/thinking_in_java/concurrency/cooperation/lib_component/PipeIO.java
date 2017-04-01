package thinking_in_java.concurrency.cooperation.lib_component;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
 * 使用管道来实现线程间的IO
 * 没有BlockingQueue好用
 */
class Sender implements Runnable {
	private Random rand = new Random(47);
	PipedWriter out = new PipedWriter();
	
	public PipedWriter getPipedWriter() {
		return out;
	}
	
	@Override
	public void run() {
		try {
			for (int c = 'A'; c <= 'z'; c++) {
				out.write(c);
				TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
			}
		} catch(IOException e) {
			System.out.println(e + " Sender write exception");
		} catch (InterruptedException e) {
			System.out.println(e + " Sender被终止了");
		}
	}
	
}

class Receiver implements Runnable {
	private PipedReader in;
	
	public Receiver(Sender sender) throws IOException {
		in = new PipedReader(sender.out);
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				char c = (char) in.read();
				System.out.println("读到了: " + c + ", ");
			}
			
		} catch(IOException e) {
			System.out.println(e + " Receiver read exception");
		}
	}
	
}
public class PipeIO {
	public static void main(String[] args) throws Exception {
		Sender sender = new Sender();
		Receiver receiver = new Receiver(sender);
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(sender);
		exec.execute(receiver);
		
		TimeUnit.SECONDS.sleep(15);
		exec.shutdownNow();
	}
}
