package thinking_in_java.class_info.generic.erasure;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Erased {
	public static void main(String[] args) {
		Integer[] strArray = (Integer[]) Array.newInstance(Integer.class, 9);
		System.out.println(Arrays.toString(strArray));
	}
}
