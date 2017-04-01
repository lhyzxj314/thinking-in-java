package thinking_in_java.concurrency.cooperation.lib_component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/*
 * 使用Semaphore限制object pool使用者数量
 */
public class Pool<T> {
   private final int size;
   private List<T> items = new ArrayList<T>();
   private volatile boolean[] checkOut;
   private Semaphore available;
   
   public Pool(Class<T> classObject, int s) {
     size = s;
     checkOut = new boolean[size];
     available = new Semaphore(size, true);
     for (int i = 0; i < size; i++) {
      try {
        items.add(classObject.newInstance());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
     }
   }
   
   public T checkOut() throws InterruptedException {
     available.acquire();
     return getItem();
   }
   
   public void checkIn(T t) {
     if (releaseItem(t))
       available.release();
   }
   
   private synchronized boolean releaseItem(T t) {
     int index = items.indexOf(t);
     if (index == -1) 
       return false;
     if (checkOut[index]) {
       checkOut[index] = false;
       return true;
     }
     return false; // 并未还回对象
   }
   
   private synchronized T getItem() {
     for (int i = 0; i < size; i++) {
       if (!checkOut[i]) {
         checkOut[i] = true;
         return items.get(i);
       }
     }
     return null; // 因为semaphore对Pool使用者数目的限制，该段代码肯定不会执行
   }
   
}
