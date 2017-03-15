package thinking_in_java.class_info.generic.array;

import java.lang.reflect.Array;

public class GenericArrayWithTypeToken<T> {
	private T[] array;

	@SuppressWarnings("unchecked")
	public GenericArrayWithTypeToken(Class<T> type, int size) {
		array = (T[]) Array.newInstance(type, size);
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
		GenericArrayWithTypeToken<Integer> gai = new GenericArrayWithTypeToken<Integer>(Integer.class, 10);
		gai.put(0, new Integer(1));
		Integer elem = gai.get(0);
		System.out.println(elem);
		
		// 加了typeToken，就不会再抛ClassCastException
		// 因运行时数组是真正的T[]类型
		
		Integer[] ia = gai.rep();
		System.out.println(ia);
		Object[] oa = gai.rep();
		System.out.println(oa.getClass().getSimpleName());
	}
}
