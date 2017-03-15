package thinking_in_java.class_info.generic.array;

public class GenericArray<T> {
	private T[] array;
	
	@SuppressWarnings("unchecked")
	public GenericArray(int size) {
		array= (T[]) new Object[size]; 
	}
	
	public void put(int index, T item) {
		array[index] = item;
	}
	
	public T get(int index) {
		return array[index];
	}
	
	public T[] rep() {
		return array;
	}
	
	public static void main(String[] args) {
		GenericArray<Integer> gai = new GenericArray<Integer>(10);
//		gai.put(0, new Integer(1));
//		Integer elem = gai.get(0);
//		System.out.println(elem);
		//Integer[] ia = gai.rep();
		Object[] oa = gai.rep();
//		Object[] oa = gai.rep();
//		System.out.println(oa.getClass().getSimpleName());
	}
}
