package thinking_in_java.class_info.generic;

public class Manipulator<T extends HasF> {
	private T obj;
	
	public Manipulator(T x) {
		this.obj = x;
	}
	
	public void manipulae() {
		/*
		 * 因类型擦出，T不包含具体类型信息，编译不通过;
		 * 但是，通过extends关键字对泛型T进行限制，则可以通过编译
		 * */
		obj.f();
	}
	
	public static void main(String[] args) {
		HasF hf = new HasF(); 
		Manipulator<HasF> manipulator = new Manipulator<HasF>(hf);
		manipulator.manipulae();
	}
}
