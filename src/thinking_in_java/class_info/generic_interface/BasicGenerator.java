package thinking_in_java.class_info.generic_interface;

public class BasicGenerator<T> implements Generator<T> {
  private Class<T> type;
  
  public BasicGenerator(Class<T> t) {
    type = t;
  }
  
  @Override
  public T next() {
    try {
      return type.newInstance();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  // 给定类型信息type，产生一个generator
  public static <T> Generator<T> create(Class<T> type) {
    return new BasicGenerator<T>(type);
  }

}
