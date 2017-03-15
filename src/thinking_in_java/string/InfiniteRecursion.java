package thinking_in_java.string;

public class InfiniteRecursion {
	public String toString() {
		//return "InfiniteRecursion address: " + this +"\n";
		return "InfiniteRecursion address: " + super.toString() +"\n";
	}
	
	public static void main(String[] args) {
		InfiniteRecursion ir = new InfiniteRecursion();
		System.out.println(ir);
	}
}
