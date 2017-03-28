package thinking_in_java.concurrency.critical_section;

class PairManager1 extends PairManager {
	@Override
	public synchronized void increment() {
		pair.incrementX();
		pair.incrementY();
		store(getPair());
	}
}
