package thinking_in_java.concurrency.join;

public class TestJoining {
	public static void main(String[] args) {
		Sleeper sleepy = new Sleeper("sleepy", 1500),
				grumpy = new Sleeper("Grumpy", 1500);
		
		Joiner Dopey = new Joiner("Dopey", sleepy),
				doc   = new Joiner("Doc", grumpy);
		
		// grumpy线程中，sleep()方法抛出InterruptedException异常
		//grumpy.interrupt();
		
		// dopey线程中，t.join()方法抛出InterruptedException异常
		Dopey.interrupt();
	}
}
