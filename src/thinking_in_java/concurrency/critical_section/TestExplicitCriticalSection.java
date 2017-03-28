package thinking_in_java.concurrency.critical_section;

public class TestExplicitCriticalSection {
	public static void main(String[] args) {
		TestCriticalSection.testApproach(new ExplicitPairManager1(), new ExplicitPairManager2());
	}
}
