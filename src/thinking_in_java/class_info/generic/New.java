package thinking_in_java.class_info.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试 Java的参数类型推断
 * @author xiaojun
 * @version 1.0.0
 * @date 2017年3月2日
 */
public class New {
	public static <T> List<T> getList() {
		return new ArrayList<T>();
	}
	
	// 编译不通过
	/*public static <T> T get() {
		return new T();
	}*/
	
	public static void foo(List<String> list) {
		return;
	}
	
	public static void main(String[] args) {
		/***
		 * case1: 用泛型方法给变量赋值，编译器会自行进行类型推断
		 */
		List<String> list = New.getList();
		list.add("str1");
		System.out.println(list.get(0));
		
		/***
		 * case2: 编译不通过，泛型方法作为入参，不能进行类型推断
		 * /
		/*foo(New.getList());*/
		
		/**
		 * case3: 在方法前加尖括号指定具体类型
		 */
		foo(New.<String>getList());
		
	}
}
