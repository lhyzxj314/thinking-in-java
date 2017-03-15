package thinking_in_java.class_info.generic.contravariance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenericReading {
	static List<Apple> apples = Arrays.asList(new Apple());
	static List<Fruit> fruit = Arrays.asList(new Fruit());
	
	static class Reader<T> {
		T readExact(List<T> list) {
			return list.get(0);
		}
	}
	
	static class CovariantReader<T> {
		T readCovariant(List<? extends T> list) {
			return list.get(0);
		}
	}
	
	@SuppressWarnings("unused")
	static void f2() {
		Reader<Fruit> fruitReader = new Reader<Fruit>();
		Fruit f = fruitReader.readExact(fruit);
		// 方法参数为List<Fruit>，则不能传入List<Apple>
		//! fruitReader.readExact(apples);
		
		CovariantReader<Fruit> fruitReader1 = new CovariantReader<Fruit>();
		// 方法参数为List<? extend Fruit>，则可传入List<Apple>
		Fruit f1 = fruitReader1.readCovariant(fruit);
		Fruit a1 = fruitReader1.readCovariant(apples);
		
		List<? super Apple> apples2 = new ArrayList<Apple>();
		apples2.add(new Jonathan());
	}
}
