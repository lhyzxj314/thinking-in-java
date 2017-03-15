package thinking_in_java.class_info.generic.contravariance;

import java.util.ArrayList;
import java.util.List;

public class TestCovariant {
	public static void main(String[] args) {
		List<? extends Fruit> list = new ArrayList<Apple>();
		// 方法入参类型为 ? extends Fruit时，为了不让程序员在List<Apple>中存入Orange，故不允许传参
		// ! list.add(new Fruit());
		// ! list.add(new Object());
		Fruit f = list.get(0);

		List<? super Apple> list1 = new ArrayList<Fruit>();
		// 方法泛型参数类型为 ? super Apple，只能传Apple及其子类(必然安全)
		// ! list1.add(new Fruit());
		list1.add(new Apple());
		list1.add(new Jonathan());
		// 方法返回值类型为 ? super Apple时,由于确定不了list1到底会返回哪种父类类型，故只允许用Object类型接收引用
		// ! Fruit apple = list1.get(0);
		Object ref = list1.get(0);
		
		List<?> list2 = new ArrayList<Apple>();
		// 方法泛型参数类型为 ?，则不允许传参
		// list2.add(new Apple());
		Object a = list2.get(0);
		
		List<Fruit> list3 = new ArrayList<Fruit>();
		list3.add(new Apple());
	}
}
