package thinking_in_java.string;

public class SprinklerSystem {
	private String valve1, valve2, valve3, valve4;
	private WaterSource source = new WaterSource();
	private int i;
	private float f;
	
	/*public String toString() {
		return "valve1 = " + valve1 + " " +
	           "valve2 = " + valve2 + " " +
			   "valve3 = " + valve3 + " " +
	           "valve4 = " + valve4 + "\n"+
			   "i = " + i + " " + "f = " + f + " " +
	           "source = " + source; 
	}*/
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("valve1 = ");
		sb.append(valve1);
		sb.append(" ");
		sb.append("valve2 = ");
		sb.append(valve2);
		sb.append(" ");
		sb.append("valve3 = ");
		sb.append(valve3);
		sb.append(" ");
		sb.append("valve4 = ");
		sb.append(valve4);
		sb.append("\n");
		sb.append("i = ");
		sb.append(i);
		sb.append(" ");
		sb.append("f = ");
		sb.append(f);
		sb.append(" ");
		sb.append("source = ");
		sb.append(source);
		return sb.toString();
	}
	
	public void test() {
		StringBuilder sb = new StringBuilder();
		sb.append("valv1 = ").append(valve1);
		sb.append(valve2 + "," + valve3);
	}
	
	public static void main(String[] args) {
		SprinklerSystem sprinklers = new SprinklerSystem();
		System.out.println(sprinklers);
	}
}
