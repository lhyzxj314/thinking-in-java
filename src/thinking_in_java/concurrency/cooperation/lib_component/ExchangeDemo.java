package thinking_in_java.concurrency.cooperation.lib_component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import thinking_in_java.class_info.generic_interface.BasicGenerator;
import thinking_in_java.class_info.generic_interface.Generator;

/*
 * Exchanger使用实例
 */
class ExchangerProducer<T> implements Runnable {
  private Generator<T> generator;
  private Exchanger<List<T>> exchanger;
  private List<T> holder;
  
  ExchangerProducer(Exchanger<List<T>> exchg, Generator<T> gen, List<T> holder) {
    exchanger = exchg;
    generator = gen;
    this.holder = holder;
  }
  
  @Override
  public void run() {
    try {
      while (!Thread.interrupted()) {
        for (int i = 0; i < ExchangeDemo.size; i++) {
          holder.add(generator.next());
          //TimeUnit.MILLISECONDS.sleep(250);
        }
        holder = exchanger.exchange(holder);
      } 
    } catch (InterruptedException e) {
      // OK
    }
  }
  
}

class ExchangerConsumer<T> implements Runnable {
  private List<T> holder;
  private Exchanger<List<T>> exchanger;
  private T value;  //FIXME why volatile
  
  ExchangerConsumer(Exchanger<List<T>> exchg, List<T> holder) {
    exchanger = exchg;
    this.holder = holder;
  }
  
  @Override
  public void run() {
    try {
      holder = exchanger.exchange(holder);
      System.out.println("交换完毕");
      for (T t : holder) {
        value = t;
        holder.remove(t); // CopyOnWriteArrayList可以在遍历的同时进行remove()
      }
    } catch (InterruptedException e) {
      // OK
    }
    System.out.println("Final value: " + value);
  }
  
}

public class ExchangeDemo {
  static int size = 10;
  static int delay = 5;
  
  public static void main(String[] args) throws InterruptedException {
    if (args.length > 0)
      size = new Integer(args[0]);
    if (args.length > 1)
      delay = new Integer(args[1]);
    ExecutorService exec = Executors.newCachedThreadPool();
    Generator<Fat> gen = BasicGenerator.create(Fat.class);
    List<Fat> producerList = new CopyOnWriteArrayList<Fat>();
    List<Fat> consumerList = new CopyOnWriteArrayList<Fat>();
    Exchanger<List<Fat>> exchanger = new Exchanger<List<Fat>>();
    exec.execute(new ExchangerProducer<Fat>(exchanger, gen, producerList));
    exec.execute(new ExchangerConsumer<Fat>(exchanger, consumerList));
    TimeUnit.SECONDS.sleep(delay);
    exec.shutdownNow();
  }
}
