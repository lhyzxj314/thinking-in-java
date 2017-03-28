package thinking_in_java.string.try_catch.exception_restriction.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

abstract class PairManager {
	AtomicInteger checkCouter = new AtomicInteger(0); 
	
	private List<Pair> storage = Collections.synchronizedList(new ArrayList<Pair>());
	
	protected Pair p = new Pair();
	
	public synchronized Pair getPair() {
		return new Pair(p.getX(), p.getY());
	}
	
	public void store(Pair p) {
		storage.add(p);
		try {
			TimeUnit.MILLISECONDS.sleep(50);
		} catch (InterruptedException e) {}
	}
	
	public abstract void increment();
}