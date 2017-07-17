package thinking_in_java.concurrency.optimistic_lock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * 读写锁测试实例
 */
class ReaderWriterList<T> {
	private ArrayList<T> lockedList;
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
	
	public ReaderWriterList(int size, T initialValue) {
		lockedList = new ArrayList<T>(Collections.nCopies(size, initialValue));
	}
	
	public T set(int index, T elem) {
		Lock wlock = lock.writeLock();
		wlock.lock();
		try {
			return lockedList.set(index, elem);
		} finally {
			wlock.unlock();
		}
	}
	
	public T get(int index) {
		Lock rlock = lock.readLock();
		rlock.lock();
		try {
			if (lock.getReadLockCount() > 1)
				System.out.println(lock.getReadLockCount());
			return lockedList.get(index);
		} finally {
			rlock.unlock();
		}
	}
}

public class ReaderWriterListTest {
	ExecutorService exec = Executors.newCachedThreadPool();
	private final static int SIZE = 100;
	private static Random rand = new Random(47);
	private ReaderWriterList<Integer> list = new ReaderWriterList<Integer>(SIZE, 0); 
	
	public ReaderWriterListTest(int readers, int writers) {
		for (int i = 0; i < readers; i++)
			exec.execute(new Reader());
		for (int i = 0; i < writers; i++)
			exec.execute(new Writer());
	}
	
	private class Writer implements Runnable {

		@Override
		public void run() {
			try {
				for (int i = 0; i < 20; i++) {
					list.set(i, rand.nextInt());
					TimeUnit.MILLISECONDS.sleep(100);
				}
			} catch(InterruptedException e) {
				// OK
			}
			System.out.println("Writer执行结束,shutdown..");
			exec.shutdownNow();
		}
	}
	
	private class Reader implements Runnable {
		@Override
		public void run() {
			try {
				while (!Thread.interrupted()) {
					for (int i = 0; i < SIZE; i++) {
						list.get(i);
						TimeUnit.SECONDS.sleep(1);
					}
				}
			} catch(InterruptedException e) {
				// OK
			}
		}
	}
	
	public static final void main(String[] args) {
		new ReaderWriterListTest(30, 1);
	}
}
