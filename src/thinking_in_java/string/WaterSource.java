package thinking_in_java.string;

public class WaterSource {
	private String s;
	WaterSource() {
		System.out.println("WaterSource");
		s = "Constructed";
	}
	
	public String toString() {
		return s;
	}
}
