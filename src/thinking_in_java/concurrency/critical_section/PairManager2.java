package thinking_in_java.concurrency.critical_section;

class PairManager2 extends PairManager {
	@Override
	public void increment() {
		Pair temp;
		synchronized(this) {
			pair.incrementX();
			pair.incrementY();
			temp = getPair();
		}
		store(temp);
	}
}
