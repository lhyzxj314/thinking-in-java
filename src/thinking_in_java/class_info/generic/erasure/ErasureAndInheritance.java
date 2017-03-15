package thinking_in_java.class_info.generic.erasure;

public class ErasureAndInheritance {
	/**
	 * 通过继承解除了泛型的限制
	 * @author xiaojun
	 * @version 1.0.0
	 * @param args
	 * @date 2017年3月8日
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Derived1 d2 = new Derived1();
		Object obj = d2.get();
		d2.set(obj);
	}
}
