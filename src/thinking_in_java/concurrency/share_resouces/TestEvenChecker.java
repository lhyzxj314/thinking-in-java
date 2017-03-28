package thinking_in_java.concurrency.share_resouces;

public class TestEvenChecker {
	public static void main(String[] args) {
		EvenChecker.test(new EvenGenerator());
	}
}
