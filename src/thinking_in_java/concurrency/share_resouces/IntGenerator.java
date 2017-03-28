package thinking_in_java.concurrency.share_resouces;

public abstract class IntGenerator {
	private volatile boolean canceled = false;
	
	protected abstract int next();
	
	public void cancel() {
		this.canceled = true;
	}
	
	public boolean isCanceled() {
		return canceled;
	}
}
