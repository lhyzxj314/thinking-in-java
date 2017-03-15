package thinking_in_java.class_info.generic.compiler_interlligence;

import java.util.ArrayList;
import java.util.List;

public class ComplierIntelligence<T> {
	private T obj;
	public ComplierIntelligence(T obj) {
		this.obj = obj;
	}
	public void set(T obj) {
		this.obj = obj;
	}
	public static void main(String[] args) {
		ComplierIntelligence<? extends Number> cc = new ComplierIntelligence<Integer>(new Integer(1));
		//cc.set(new Object());
		
		
		List<? extends Number> flist = new ArrayList<Integer>();
		//flist.add(new Object());
		flist.contains(new Integer(1));
	}
}
