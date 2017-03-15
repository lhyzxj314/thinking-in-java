package thinking_in_java.class_info.generic.erasure;

public class GenericBase<T> {
	private T elem;
	public void set(T arg) {
		elem = arg;
	}
	public T get() {
		return elem;
	}
}
