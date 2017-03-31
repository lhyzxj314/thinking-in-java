package thinking_in_java.concurrency.cooperation;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/*
 * PriorityBlockingQueue演示示例
 */
class PrioritizedTask implements Runnable, Comparable<PrioritizedTask> {
  private Random rand = new Random(47);
  private static int count = 0;
  private final int id = count++;
  private int priority;
  protected static List<PrioritizedTask> sequence = new ArrayList<PrioritizedTask>();

  public PrioritizedTask(int p) {
    priority = p;
    sequence.add(this);
  }

  @Override
  public int compareTo(PrioritizedTask that) {
    if (this.priority < that.priority) return -1;
    if (this.priority > that.priority) return 1;
    return 0;
  }

  @Override
  public void run() {
    try {
      TimeUnit.MILLISECONDS.sleep(rand.nextInt(250));
    } catch (InterruptedException e) {
      
    }
    System.out.println(this + " ");
  }
  
  public String summary() {
    return "("+ id + ") " + priority + "   ";
  }
  
  @Override
  public String toString() {
    return "[" + priority + "]" + " 任务id" + id;
  }
  
  public static class EndSentinel extends PrioritizedTask {
    private ExecutorService exec;
    
    public EndSentinel(ExecutorService ex) {
      super(-1);
      exec = ex;
    }
    
    @Override
    public void run() {
      int count = 0;
      for (PrioritizedTask t : sequence) {
        System.out.print(t.summary());
        if (++count % 5 == 0)
          System.out.println();
      }
      System.out.println();
      System.out.println(this + "调用shutdownNow()");
      exec.shutdownNow();
    }
    
  }
}

class PrioritizedTaskProducer implements Runnable {
  private Queue<PrioritizedTask> queue;
  private ExecutorService exec;
  private Random rand = new Random(47);
  public PrioritizedTaskProducer(Queue<PrioritizedTask> q, ExecutorService e) {
    queue = q;
    exec = e;
  }
  
  @Override
  public void run() {
    try {
      for (int i = 0; i < 20; i++) {
        queue.add(new PrioritizedTask(rand.nextInt(10)));
        Thread.yield();
      }
      for (int i = 0; i < 10; i++) {
        queue.add(new PrioritizedTask(10));
        TimeUnit.MILLISECONDS.sleep(250);
      }
      for (int i = 0; i < 10; i++) {
        queue.add(new PrioritizedTask(i));
      }
      queue.add(new PrioritizedTask.EndSentinel(exec));
    } catch(InterruptedException e) {
      
    }
    System.out.println("Producer完成任务生成");
  }
}

class PrioritizedTaskConsumer implements Runnable {
  private PriorityBlockingQueue<PrioritizedTask> q;
  
  public PrioritizedTaskConsumer(PriorityBlockingQueue<PrioritizedTask> queue) {
    q = queue;
  }
  
  @Override
  public void run() {
    try {
      while (!Thread.interrupted()) {
        q.take().run();
      }
    } catch(InterruptedException e) {
      
    }
    System.out.println("Consumer完成消费");
  }
  
}

public class PriorityBlockingQueueDemo {
  public static void main(String[] args) {
    PriorityBlockingQueue<PrioritizedTask> q = new PriorityBlockingQueue<PrioritizedTask>();
    ExecutorService exec = Executors.newCachedThreadPool();
    exec.execute(new PrioritizedTaskConsumer(q));
    exec.execute(new PrioritizedTaskProducer(q, exec));
  }
}
