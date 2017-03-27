package thinking_in_java.concurrency.volatility;

public class SerialNumberGenerator {
	private static volatile int i = 0;
	
	public static int nextSerialNumber() {
		return i++; // 该修改对任意其他任务是立即可见的，但是并不能保证线程安全(原因：i++操作并不是原子操作，线程可读到不稳定中间态)
	}
	
	// 使用同步锁修复上述问题
	/*public synchronized static int nextSerialNumber() {
		return i++;
	}*/
}
