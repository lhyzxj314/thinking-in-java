package thinking_in_java.concurrency.cooperation.lib_component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
 * CyclicBarrier使用示例
 */
class Horse implements Runnable {
	
	private static int count = 0;
	private int id = count++;
	private int strides = 0;
	private static Random rand = new Random(47);
	private CyclicBarrier barrier;
	
	public Horse(CyclicBarrier b) {
		this.barrier = b;
	}
	
	//FIXME why synchronized
	public int getStrides() {
		return strides;
	}
	
	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				//FIXME why this
				/*synchronized(this) {
					strides += rand.nextInt(3); // 马前进步数:0 1 2
				}*/
				strides += rand.nextInt(3); // 马前进步数:0 1 2
				barrier.await();
			}
		} catch (InterruptedException e) {
			// 合法的退出方式
		} catch (BrokenBarrierException e) {
			// 非法退出方式，是我们感兴趣的异常
			throw new RuntimeException(e);
		}
	}
	
	public String tracks() {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < getStrides(); i++)
			s.append("*");
		s.append(id);
		return s.toString();
	}
	
	@Override
	public String toString() {
		return "赛马" + id + " ";
	}
}

public class HorseRace {
	static final int FINISH_LINE = 79;
	private List<Horse> horses = new ArrayList<Horse>();
	private ExecutorService exec = Executors.newCachedThreadPool();
	private CyclicBarrier barrier;
	
	public HorseRace(int nHorse, final int pause) {
		barrier = new CyclicBarrier(nHorse, new Runnable() {

			@Override
			public void run() {
				StringBuilder s = new StringBuilder();
				// 终点
				for (int i = 0; i < FINISH_LINE; i++)
					s.append("=");
				System.out.println(s);
			
				for (Horse h : horses)
					System.out.println(h.tracks());
				
				for (Horse h : horses) {
					if (h.getStrides() >= FINISH_LINE) {
						System.out.println(h + "赢了！");
						exec.shutdownNow();
						return;
					}
				}
				
				try {
					TimeUnit.MILLISECONDS.sleep(pause);
				} catch (InterruptedException e) {
					System.out.println("barrier-action sleep interruped");
				}
			}
		});
		
		for (int i = 0; i < nHorse; i++) {
			Horse horse = new Horse(barrier);
			horses.add(horse);
			exec.execute(horse);
		}
	}
	
	public static void main(String[] args) {
		int nHorse = 7;
		if (args.length > 0)
			nHorse = Integer.parseInt(args[0]);
		int pause = 200;
		if (args.length > 1)
			pause = Integer.parseInt(args[1]);
		new HorseRace(nHorse, pause);
	}
}
