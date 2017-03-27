package thinking_in_java.concurrency.thread_local_storage;

import java.util.Random;

public class ThreadLocalVariableHolder {
	private static ThreadLocal<Integer> value = new ThreadLocal<Integer>() {
		private Random rand = new Random();
		protected synchronized Integer initialValue() {
			return rand.nextInt(10000);
		}
	};
	
	
	public static void increment() {
		value.set(value.get() + 1);
	}
	
	public static int get() {
		return value.get();
	}
}
