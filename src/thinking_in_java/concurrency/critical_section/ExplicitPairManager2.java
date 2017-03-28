package thinking_in_java.concurrency.critical_section;

class ExplicitPairManager2 extends PairManager {
	
	@Override
	public void increment() {
		lock.lock();
		Pair temp;
		try {
			pair.incrementX();
			pair.incrementY();
			temp = getPair();
		} finally {
			lock.unlock();
		}
		store(temp);
	}
}
