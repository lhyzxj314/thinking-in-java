package thinking_in_java.class_info.generic.array;

public class ArrayOfGeneric {
	static final int SIZE = 100;
	static Generic<Integer>[] gia;
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// 能编译通过，但是运行时会抛 ClassCastException(为啥？？？？？)
		//! gia = (Generic<Integer>[]) new Object[SIZE];
		gia = (Generic<Integer>[]) new Generic[SIZE];
		System.out.println(gia.getClass().getSimpleName());
		gia[0] = new Generic<Integer>();
		//! gia[1] = new Object();
		//! gia[2] = new Generic<Double>();
	}
}
