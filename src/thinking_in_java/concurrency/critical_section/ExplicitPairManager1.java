package thinking_in_java.concurrency.critical_section;

class ExplicitPairManager1 extends PairManager {
	
	@Override
	public void increment() {
		lock.lock();
		try {
			pair.incrementX();
			pair.incrementY();
			store(getPair());
		} finally {
			lock.unlock();
		}
	}
}