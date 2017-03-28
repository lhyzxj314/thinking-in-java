package thinking_in_java.concurrency.share_resouces;

public class EvenGenerator extends IntGenerator{
	private int currentValue = 0;
	
	@Override
	protected int next() {
		// 线程不安全的操作！！
		++currentValue;
		++currentValue;
		return currentValue;
	}
}
