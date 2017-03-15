package thinking_in_java.class_info.generic.array;

import java.util.ArrayList;

import thinking_in_java.class_info.generic.contravariance.Apple;
import thinking_in_java.class_info.generic.contravariance.Fruit;
import thinking_in_java.class_info.generic.contravariance.Jonathan;

public class GenericArray2<T> {
	private Object[] array;
	
	public GenericArray2(int size) {
		array= new Object[size]; 
	}
	
	public void put(int index, T item) {
		array[index] = item;
	}
	
	@SuppressWarnings("unchecked")
	public T get(int index) {
		return (T) array[index];
	}
	
	@SuppressWarnings("unchecked")
	public T[] rep() {
		return (T[]) array;
	}
	
	public static void main(String[] args) {
		Number[] ls = new Integer[10];
		//Integer[] is = (Integer[]) new Number[11];
		
		GenericArray2<Integer> gai = new GenericArray2<Integer>(10);
		gai.put(0, new Integer(1));
		Integer elem = gai.get(0);
		System.out.println(elem);
		//! Integer[] ia = gai.rep();
		Object[] oa = gai.rep();
		System.out.println(oa.getClass().getSimpleName());
	}
}
